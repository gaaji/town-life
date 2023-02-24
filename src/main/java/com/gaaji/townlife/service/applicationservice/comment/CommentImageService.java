package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.service.controller.comment.dto.CommentImageSaveResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface CommentImageService {

    CommentImageSaveResponseDto save(String authId, String postId, String commentId, MultipartFile imageFile);

    void remove(String authId, String postId, String commentId);
}
