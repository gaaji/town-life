package com.gaaji.townlife.service.controller.comment;

import com.gaaji.townlife.service.applicationservice.comment.CommentLikeService;
import com.gaaji.townlife.service.controller.comment.dto.CommentLikeRequestDto;
import lombok.RequiredArgsConstructor;
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
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestBody CommentLikeRequestDto dto
    ) {
        commentLikeService.like(postId, commentId, dto);
    }
}
