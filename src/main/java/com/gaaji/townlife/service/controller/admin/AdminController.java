package com.gaaji.townlife.service.controller.admin;

import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryFindService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryModifyService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategoryRemoveService;
import com.gaaji.townlife.service.applicationservice.admin.AdminCategorySaveService;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryListDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategoryModifyDto;
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
    private final AdminCategoryModifyService modifyService;
    private final AdminCategoryRemoveService removeService;

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

    @PutMapping("/categories/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void categoryModify(@PathVariable String categoryId, @RequestBody AdminCategoryModifyDto dto) {
        modifyService.modify(categoryId, dto);
    }

    @DeleteMapping("/categories/{categoryId}")
    public void categoryRemove(@PathVariable String categoryId) {
        removeService.remove(categoryId);
    }
}
