package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.global.exception.api.ApiErrorCode;
import com.gaaji.townlife.global.exception.api.ResourceNotFoundException;
import com.gaaji.townlife.global.exception.api.ResourceRemoveException;
import com.gaaji.townlife.service.domain.townlife.TownLife;
import com.gaaji.townlife.service.repository.TownLifeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static com.gaaji.townlife.global.utils.validation.ValidateResourceAccess.validateAuthorizationRemoving;

@Service
@RequiredArgsConstructor
public class TownLifeRemoveServiceImpl implements TownLifeRemoveService {

    private final TownLifeRepository townLifeRepository;

    @Override
    public void remove(String townLifeId, String authorId) {
        TownLife townLife = townLifeRepository.findById(townLifeId)
                .orElseThrow(() -> new ResourceNotFoundException(ApiErrorCode.TOWN_LIFE_NOT_FOUND));

        validateAlreadyRemoved(townLife);

        validateAuthorizationRemoving(authorId, townLife.getAuthorId());

        townLifeRepository.delete(townLife);
    }

    // @Query로 한다고 하지 않았나???
    private void validateAlreadyRemoved(TownLife townLife) {
        if( townLife.getDeletedAt() != null ) {
            throw new ResourceRemoveException(ApiErrorCode.TOWN_LIFE_REMOVE_ERROR);
        }
    }
}
