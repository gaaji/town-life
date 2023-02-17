package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.townlife.TownLife;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Slice;

import java.util.List;
import java.util.stream.Collectors;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class TownLifeListResponseDto {

    private List<TownLifeListDto> content;
    private Boolean hasNext;

    public static TownLifeListResponseDto of(Slice<TownLife> townLives) {
        return new TownLifeListResponseDto(
                convertContentFromEntityList(townLives.getContent()),
                townLives.hasNext()
        );
    }

    private static List<TownLifeListDto> convertContentFromEntityList(List<TownLife> townLives) {
        return townLives.stream()
                .map(townLife -> TownLifeListDto.of(townLife, townLife.getTownLifeCounter()))
                .collect(Collectors.toList());
    }
}
