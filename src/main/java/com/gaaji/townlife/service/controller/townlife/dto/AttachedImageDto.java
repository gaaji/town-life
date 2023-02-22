package com.gaaji.townlife.service.controller.townlife.dto;

import com.gaaji.townlife.service.domain.townlife.AttachedImage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttachedImageDto {

    private int orderIndex;
    private String src;

    public static AttachedImageDto of(AttachedImage entity) {
        return new AttachedImageDto(entity.getOrderIndex(), entity.getSrc());
    }

}
