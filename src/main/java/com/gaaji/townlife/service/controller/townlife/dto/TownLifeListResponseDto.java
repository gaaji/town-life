package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter @ToString
@NoArgsConstructor
@AllArgsConstructor
public class TownLifeListResponseDto {

    private Boolean hasNext;
    private List<TownLifeListDto> content;

}
