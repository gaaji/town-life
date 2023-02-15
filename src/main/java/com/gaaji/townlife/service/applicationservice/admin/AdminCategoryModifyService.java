package com.gaaji.townlife.service.applicationservice.admin;

import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryModifyDto;

public interface AdminCategoryModifyService {

    void modify(String categoryId, AdminCategoryModifyDto dto);

}
