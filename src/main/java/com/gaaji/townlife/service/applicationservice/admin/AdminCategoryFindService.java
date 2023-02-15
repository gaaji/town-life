package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryListDto;

import java.util.List;

public interface AdminCategoryFindService {

    List<AdminCategoryListDto> findList();

}
