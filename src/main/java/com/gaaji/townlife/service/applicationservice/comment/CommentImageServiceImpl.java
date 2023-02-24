package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.NotYourResourceException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceRemoveException;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceSaveException;
import com.gaaji.townlife.global.exceptions.internalServer.InternalErrorCode;
import com.gaaji.townlife.global.exceptions.internalServer.exception.IllegalValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.NullValueException;
import com.gaaji.townlife.global.exceptions.internalServer.exception.TownLifeAwsS3Exception;
import com.gaaji.townlife.service.adapter.aws.AwsS3Client;
import com.gaaji.townlife.service.controller.comment.dto.CommentImageSaveResponseDto;
import com.gaaji.townlife.service.domain.comment.Comment;
import com.gaaji.townlife.service.repository.CommentRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Objects;

@Service
@Transactional
@RequiredArgsConstructor
public class CommentImageServiceImpl implements CommentImageService {
    private final AwsS3Client awsS3Client;
    private final CommentRepository commentRepository;
    private final TownLifeRepository townLifeRepository;

    @Override
    public CommentImageSaveResponseDto save(String authId, String postId, String commentId, MultipartFile imageFile) {
        checkTownLife(postId);
        Comment comment = getCommentById(commentId);
        if(!comment.getUserId().equals(authId)) throw new NotYourResourceException("요청자의 것이 아닌 Comment에 대한 이미지 추가는 불가합니다.");
        if(comment.hasImageSrc()) removeImageFile(comment.getContent().getImageSrc());
        String imageSrc = uploadImageFile(imageFile);
        comment.replaceImageSrc(imageSrc);
        return CommentImageSaveResponseDto.create(comment.getId(), imageSrc);
    }

    @Override
    public void remove(String authId, String postId, String commentId) {
        checkTownLife(postId);
        Comment comment = getCommentById(commentId);
        if(!comment.getUserId().equals(authId)) throw new NotYourResourceException("요청자의 것이 아닌 Comment에 대한 이미지 추가는 불가합니다.");
        removeImageFile(comment.getContent().getImageSrc());
        comment.clearImageSrc();
    }

    private void removeImageFile(String imageSrc) {
        try {
            awsS3Client.deleteFile(imageSrc);
        } catch (TownLifeAwsS3Exception e) {
            throw new ResourceRemoveException(ApiErrorCode.IMAGE_DELETE_ERROR, e);
        } catch (NullValueException e) {
            throw new ResourceRemoveException(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST, e);
        }
    }

    private String uploadImageFile(MultipartFile imageFile) {
        try {
            if ( !Objects.requireNonNull(imageFile.getContentType()).contains("image") ) {
                throw new IllegalValueException(InternalErrorCode.ERROR_FILE_TYPE_IMAGE);
            }
            return awsS3Client.uploadFile(imageFile);
        } catch (TownLifeAwsS3Exception e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_UPLOAD_ERROR, e);
        } catch (NullValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_REQUIRE_VALUE_BAD_REQUEST, e);
        } catch (IllegalValueException e) {
            throw new ResourceSaveException(ApiErrorCode.IMAGE_CONTENT_TYPE_ERROR, e);
        }
    }

    private void checkTownLife(String postId) {
        if (!townLifeRepository.existsById(postId))
            throw new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND);
    }

    private Comment getCommentById(String commentId) {
        return commentRepository.findById(commentId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.COMMENT_NOT_FOUND));
    }
}
