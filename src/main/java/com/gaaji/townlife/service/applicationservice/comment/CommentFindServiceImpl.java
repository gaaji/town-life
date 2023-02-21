package com.gaaji.townlife.service.applicationservice.comment;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.comment.dto.ChildCommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.CommentListDto;
import com.gaaji.townlife.service.controller.comment.dto.ParentCommentListDto;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CommentFindServiceImpl implements CommentFindService {
    private final TownLifeRepository townLifeRepository;

    @Override
    public List<ChildCommentListDto> findChildCommentListByParentCommentId(String parentCommentId, String lastChildCommentId, int size) {
        return null;
    }

    @Override
    public List<CommentListDto> findListByUserId(String userId) {
        return null;
    }

    @Override
    public List<ParentCommentListDto> findParentCommentListByTownLifeId(String townLifeId) {
        return townLifeRepository.findById(townLifeId).orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND))
                .getComments().stream()
                .map(ParentCommentListDto::create)
                .collect(Collectors.toList());
    }
}
