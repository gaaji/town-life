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
import com.gaaji.townlife.service.domain.townlife.AttachedImage;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.AttachedImageRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import static com.gaaji.townlife.global.utils.validation.ValidateObjectValue.validateRequireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeImageServiceImpl implements TownLifeImageService {

    private final AwsS3Client awsS3Client;
    private final TownLifeRepository townLifeRepository;
    private final AttachedImageRepository attachedImageRepository;

    @Override
    @Transactional
    public void upload(String townLifeId, int[] orderIndexes, MultipartFile... multipartFiles) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        validateArraysLength(orderIndexes.length, multipartFiles.length, ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST);

        try {
            validateContentType(multipartFiles);

            int length = orderIndexes.length;
            String[] srcs = awsS3Client.uploadFile(multipartFiles);

            validateArraysLength(length, srcs.length, ApiErrorCode.IMAGE_UPLOAD_ERROR);

            for (int i = 0; i < length; i++) {
                AttachedImage attachedImage = attachedImageRepository.save(AttachedImage.of(orderIndexes[i], srcs[i]));
                townLife.addAttachedImage(attachedImage);
            }
            log.info("Upload image files: {}", townLife.getAttachedImages());

        } catch (TownLifeAwsS3Exception e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_UPLOAD_ERROR, e);
        } catch (NullValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST, e);
        } catch (IllegalValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_CONTENT_TYPE_ERROR, e);
        }
    }

    @Override
    public void update(String townLifeId, int[] orderIndex, MultipartFile... multipartFiles) {

    }

    @Override
    @Transactional
    public void deleteAll(String townLifeId) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        try {
            String[] originFileSrcs = townLife.removeAllAttachedImages();
            awsS3Client.deleteFile(originFileSrcs);

            log.info("Delete image files: {}", townLife.getAttachedImages());

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
