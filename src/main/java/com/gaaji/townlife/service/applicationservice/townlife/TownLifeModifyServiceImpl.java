package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceAuthorizationException;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeModifyRequestDto;
import com.gaaji.townlife.service.domain.townlife.PostTownLife;
import com.gaaji.townlife.service.domain.townlife.QuestionTownLife;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeModifyServiceImpl implements TownLifeModifyService {

    private final TownLifeRepository townLifeRepository;

    @Override
    @Transactional
    public TownLifeDetailDto modify(String townLifeId, String authorId, TownLifeModifyRequestDto dto) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        validateAuthorizationModifying(townLife.getAuthorId(), authorId);

        TownLife before = null;
        if( townLife instanceof PostTownLife ) {
            before = PostTownLife.newInstance(townLife);
        } else if ( townLife instanceof QuestionTownLife ){
            before = QuestionTownLife.newInstance(townLife);
        }

        townLife.updateContent(dto.getTitle(), dto.getText(), dto.getLocation());

        log.info("MODIFY :: before: {}", before);
        log.info("MODIFY :: after: {}", townLife);

        // 게시글 수정 되었다는 알림 이벤트 발행

        return TownLifeDetailDto.of(townLife, townLife.getTownLifeCounter());
    }

    private void validateAuthorizationModifying(String authorId, String requestAuthorId) {
        if( !Objects.equals(authorId, requestAuthorId) ) {
            throw new ResourceAuthorizationException(ApiErrorCode.AUTHORIZATION_MODIFY_ERROR);
        }
    }
}