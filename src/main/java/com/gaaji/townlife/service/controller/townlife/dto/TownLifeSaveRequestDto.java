package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.*;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Setter //TODO 테스트 때문에 추가한 것. 나중에 삭제요망.
public class TownLifeSaveRequestDto {

    private String categoryId;
    private String authorId;
    private String townId;
    private String title;
    private String text;
    private String location;

}
