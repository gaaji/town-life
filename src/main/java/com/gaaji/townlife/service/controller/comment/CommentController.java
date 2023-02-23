package com.gaaji.townlife.service.controller.comment;

import com.gaaji.townlife.service.applicationservice.comment.CommentFindService;
import com.gaaji.townlife.service.applicationservice.comment.CommentModifyService;
import com.gaaji.townlife.service.applicationservice.comment.CommentRemoveService;
import com.gaaji.townlife.service.applicationservice.comment.CommentSaveService;
import com.gaaji.townlife.service.controller.comment.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/town-lives/{postId}/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentSaveService commentSaveService;
    private final CommentFindService commentFindService;
    private final CommentModifyService commentModifyService;
    private final CommentRemoveService commentRemoveService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CommentSaveResponseDto parentSave(
            @PathVariable String postId,
            @RequestBody CommentSaveRequestDto dto,
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId
    ) {
        return commentSaveService.saveParent(authId, postId, dto);
    }

    @PostMapping("/{parentCommentId}/children")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentSaveResponseDto childSave(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String parentCommentId,
            @RequestBody CommentSaveRequestDto dto
    ) {
        return commentSaveService.saveChild(authId, postId, parentCommentId, dto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParentCommentListDto> parentList(@PathVariable String postId) {
        return commentFindService.findParentCommentListByTownLifeId(postId);
    }

    @GetMapping("/{parentId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ChildCommentListDto> childList(
            @PathVariable String postId,
            @PathVariable String parentId,
            @RequestParam(required = false) String lastCommentId,
            @RequestParam(required = false) Integer size
    ) {
        return commentFindService.findChildCommentListByParentCommentId(postId, parentId, lastCommentId, size);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentListDto commentModify(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String commentId,
            @RequestBody CommentModifyRequestDto dto) {
        return commentModifyService.modify(authId, postId, commentId, dto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void commentRemove(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authId,
            @PathVariable String postId,
            @PathVariable String commentId
    ) {
        commentRemoveService.remove(authId, postId, commentId);
    }
}

