package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.TownLifeDetailDto;
import com.gaaji.townlife.service.controller.townlife.dto.TownLifeSaveRequestDto;
import com.gaaji.townlife.service.domain.category.Category;
import com.gaaji.townlife.service.domain.townlife.*;
import com.gaaji.townlife.service.repository.CategoryRepository;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import com.gaaji.townlife.service.repository.TownLifeSubscriptionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TownLifeSaveServiceImpl implements TownLifeSaveService {

    private final TownLifeRepository townLifeRepository;
    private final TownLifeSubscriptionRepository townLifeSubscriptionRepository;
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional
    public TownLifeDetailDto save(TownLifeType type, TownLifeSaveRequestDto dto) {
        Category category = categoryRepository.findById(dto.getCategoryId()).orElseThrow();

//        if(dto.getAttachedImageRequestFiles() != null) {
//            // save s3
//            // return s3 url
//            // set attached image src
//        }

        TownLifeDetailDto townLifeDetailDto = null;
        switch (type) {
            case POST:
                PostTownLife postTownLife = townLifeRepository.save(PostTownLife.create(dto));
                postTownLife.associateCategory(category);

                saveSubscription(postTownLife, dto.getAuthorId());
                townLifeDetailDto = TownLifeDetailDto.of(postTownLife);
                break;
            case QUESTION:
                QuestionTownLife questionTownLife = townLifeRepository.save(QuestionTownLife.create(dto));
                questionTownLife.associateCategory(category);

                saveSubscription(questionTownLife, dto.getAuthorId());
                townLifeDetailDto = TownLifeDetailDto.of(questionTownLife);
                break;
        }
        assert townLifeDetailDto != null;

        return townLifeDetailDto;
    }

    private <T extends TownLife> void saveSubscription(T townLife, String authorId) {
        TownLifeSubscription subscription = townLifeSubscriptionRepository.save(TownLifeSubscription.of(authorId));
        subscription.associateTownLife(townLife);
    }

}
