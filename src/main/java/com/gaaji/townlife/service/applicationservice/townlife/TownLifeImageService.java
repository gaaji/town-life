package com.gaaji.townlife.service.applicationservice.townlife;

import org.springframework.web.multipart.MultipartFile;

public interface TownLifeImageService {

    void upload(String townLifeId, int[] orderIndex, MultipartFile... multipartFiles);
    void update(String townLifeId, int[] orderIndex, MultipartFile... multipartFiles);
    void deleteAll(String townLifeId);

}
