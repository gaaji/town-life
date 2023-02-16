package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TownLifeSaveRequestDto {

    private String categoryId;
    private String authorId;
    private String townId;
    private String title;
    private String text;
    private String location;

}
