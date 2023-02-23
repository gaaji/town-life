package com.gaaji.townlife.service.controller.comment;

import com.gaaji.townlife.service.applicationservice.comment.CommentLikeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/town-lives/{postId}/comments/{commentId}/likes")
@RequiredArgsConstructor
public class CommentLikeController {
    private final CommentLikeService commentLikeService;

    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void commentLikeSave(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String commentId)
    {
        commentLikeService.like(authId, postId, commentId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void commentLikeRemove(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String commentId
    ) {
        commentLikeService.unlike(authId,postId, commentId);
    }
}
