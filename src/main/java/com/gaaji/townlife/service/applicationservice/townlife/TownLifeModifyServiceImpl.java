package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exceptions.api.ApiErrorCode;
import com.gaaji.townlife.global.exceptions.api.exception.ResourceNotFoundException;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeModifyRequestDto;
import com.gaaji.townlife.service.controller.townlife.dto.builder.ResponseDtoBuilder;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.event.nonkafka.dto.TownLifeEventBody;
import com.gaaji.townlife.service.event.nonkafka.townlife.TownLifeUpdatedEvent;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.gaaji.townlife.global.utils.validation.ValidateResourceAccess.validateAuthorizationModifying;

@Slf4j
@Service
@RequiredArgsConstructor
public class TownLifeModifyServiceImpl implements TownLifeModifyService {

    private final TownLifeRepository townLifeRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Override
    @Transactional
    public TownLifeDetailDto modify(String townLifeId, String authorId, TownLifeModifyRequestDto dto) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        validateAuthorizationModifying(authorId, townLife.getAuthorId());

        townLife.updateContent(dto.getTitle(), dto.getText(), dto.getLocation());

        eventPublisher.publishEvent(new TownLifeUpdatedEvent(
                TownLifeModifyService.class,
                TownLifeEventBody.of(townLife.getAuthorId(), townLife.getSubscriptions())
        ));

        return ResponseDtoBuilder.townLifeDetailDto(townLife, townLife.getTownLifeCounter());
    }

}
