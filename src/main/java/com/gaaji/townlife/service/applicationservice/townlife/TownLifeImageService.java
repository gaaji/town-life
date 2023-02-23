package com.gaaji.townlife.service.applicationservice.townlife;

import com.gaaji.townlife.service.controller.townlife.dto.AttachedImageDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface TownLifeImageService {

    List<AttachedImageDto> upload(String townLifeId, String authId, int[] orderIndexes, MultipartFile... multipartFiles);
    List<AttachedImageDto> update(String townLifeId, String authId, int[] orderIndexes, MultipartFile... multipartFiles);
    List<AttachedImageDto> deleteAll(String townLifeId, String authId);

}
