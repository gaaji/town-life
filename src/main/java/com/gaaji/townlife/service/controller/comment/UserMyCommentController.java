package com.gaaji.townlife.service.controller.comment;

import com.gaaji.townlife.service.applicationservice.comment.CommentFindService;
import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comments/my")
@RequiredArgsConstructor
public class UserMyCommentController {
    private final CommentFindService commentFindService;
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CommentListDto> myCommentList(@RequestHeader(HttpHeaders.AUTHORIZATION) String authId) {
        return commentFindService.findListByUserId(authId);
    }
}
