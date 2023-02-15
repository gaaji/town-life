package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;

public interface AdminCategorySaveService {

    AdminCategorySaveResponseDto save(AdminCategorySaveRequestDto dto);

}
