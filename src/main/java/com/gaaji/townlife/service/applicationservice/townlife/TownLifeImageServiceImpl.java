package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceSaveException;
import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;
import com.gaaji.townlife.global.exceptions.internalServer.exception.IllegalValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.TownLifeAwsS3Exception;
import com.gaaji.townlife.service.adapter.aws.AwsS3Client;
import com.gaaji.townlife.service.controller.townlife.dto.AttachedImageDto;
import com.gaaji.townlife.service.controller.townlife.dto.builder.ResponseDtoBuilder;
import com.gaaji.townlife.service.domain.townlife.AttachedImage;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.AttachedImageRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.gaaji.townlife.global.utils.validation.ValidateRequireValue.validateRequireNonNull;
import static com.gaaji.townlife.global.utils.validation.ValidateResourceAccess.validateAuthorizationModifying;

@Service
@RequiredArgsConstructor
public class TownLifeImageServiceImpl implements TownLifeImageService {

    private final AwsS3Client awsS3Client;
    private final TownLifeRepository townLifeRepository;
    private final AttachedImageRepository attachedImageRepository;

    @Override
    @Transactional
    public List<AttachedImageDto> upload(String townLifeId, String authId, int[] orderIndexes, MultipartFile... multipartFiles) {
        TownLife townLife = prepareUpload(townLifeId, authId, orderIndexes, multipartFiles);

        upload(townLife, orderIndexes, multipartFiles);

        return ResponseDtoBuilder.attachedImageDtoList(townLife);
    }

    @Override
    @Transactional
    public List<AttachedImageDto> update(String townLifeId, String authId, int[] orderIndexes, MultipartFile... multipartFiles) {
        TownLife townLife = prepareUpload(townLifeId, authId, orderIndexes, multipartFiles);

        deleteAll(townLife);

        upload(townLife, orderIndexes, multipartFiles);

        return ResponseDtoBuilder.attachedImageDtoList(townLife);
    }

    @Override
    @Transactional
    public List<AttachedImageDto> deleteAll(String townLifeId, String authId) {
        TownLife townLife = prepare(townLifeId, authId);

        deleteAll(townLife);

        return ResponseDtoBuilder.attachedImageDtoList(townLife);
    }

    private TownLife getTownLifeById(String id) {
        return townLifeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));
    }

    private TownLife prepare(String townLifeId, String authId) {
        TownLife townLife = getTownLifeById(townLifeId);

        validateAuthorizationModifying(authId, townLife.getAuthorId());

        return townLife;
    }

    private TownLife prepareUpload(String townLifeId, String authId, int[] orderIndexes, MultipartFile[] multipartFiles) {
        TownLife townLife = prepare(townLifeId, authId);

        validateArraysLength(orderIndexes.length, multipartFiles.length, ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST);

        return townLife;
    }

    private void upload(TownLife townLife, int[] orderIndexes, MultipartFile[] multipartFiles) {
        try {
            validateContentType(multipartFiles);

            int length = orderIndexes.length;
            String[] srcs = awsS3Client.uploadFile(multipartFiles);

            validateArraysLength(length, srcs.length, ApiErrorCode.IMAGE_UPLOAD_ERROR);

            for (int i = 0; i < length; i++) {
                AttachedImage attachedImage = attachedImageRepository.save(AttachedImage.of(orderIndexes[i], srcs[i]));
                townLife.addAttachedImage(attachedImage);
            }

        } catch (TownLifeAwsS3Exception e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_UPLOAD_ERROR, e);
        } catch (NullValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST, e);
        } catch (IllegalValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_CONTENT_TYPE_ERROR, e);
        }
    }

    private void deleteAll(TownLife townLife) {
        try {
            String[] originFileSrcs = townLife.removeAllAttachedImages();
            awsS3Client.deleteFile(originFileSrcs);

        } catch (TownLifeAwsS3Exception e) {
            throw new ResourceRemoveException(ApiErrorCode.IMAGE_DELETE_ERROR, e);
        } catch (NullValueException e) {
            throw new ResourceRemoveException(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST, e);
        }
    }

    private void validateContentType(MultipartFile... multipartFiles) throws NullValueException, IllegalValueException {
        for (MultipartFile multipartFile : multipartFiles) {

            validateRequireNonNull(multipartFile, multipartFile.getContentType());

            if ( !multipartFile.getContentType().contains("image") ) {
                throw new IllegalValueException(InternalErrorCode.ERROR_FILE_TYPE_IMAGE);
            }
        }
    }

    private void validateArraysLength(int x, int y, ApiErrorCode errorCode) {
        if (x < 0 || y < 0) throw new ResourceSaveException(errorCode);
        if (x != y) throw new ResourceSaveException(errorCode);
    }

}
