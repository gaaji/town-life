package com.gaaji.townlife.service.controller.townlife.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class AttachedImageDto {

    private String id;
    private int orderIndex;
    private String src;

}
