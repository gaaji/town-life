package com.gaaji.townlife.service.controller.comment;

import com.gaaji.townlife.service.applicationservice.comment.CommentSaveService;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveRequestDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentSaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentSaveService commentSaveService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentSaveResponseDto saveParent(
            @PathVariable String postId,
            @RequestBody CommentSaveRequestDto dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId
    ) {
        return commentSaveService.saveParent(authId, postId, dto);
    }

    @PostMapping("/{parentCommentId}/children")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentSaveResponseDto saveChild(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String parentCommentId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        return commentSaveService.saveChild(authId, postId, parentCommentId, dto);
    }
}

