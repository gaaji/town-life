package com.gaaji.townlife.service.controller.admin;

import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryFindService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategorySaveService;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryListDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminCategorySaveService saveService;
    private final AdminCategoryFindService findService;

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminCategorySaveResponseDto categorySave(@RequestBody AdminCategorySaveRequestDto dto) {
        return saveService.save(dto);
    }

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public List<AdminCategoryListDto> categoryList() {
        return findService.findList();
    }
}
