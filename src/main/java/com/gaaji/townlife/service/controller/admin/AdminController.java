package com.gaaji.townlife.service.controller.admin;

import com.gaaji.townlife.service.applicationservice.admin.AdminCategorySaveService;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveRequestDto;
import com.gaaji.townlife.service.controller.admin.dto.AdminCategorySaveResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {
    private final AdminCategorySaveService saveService;

    @PostMapping("/category")
    @ResponseStatus(HttpStatus.CREATED)
    public AdminCategorySaveResponseDto saveCategory(@RequestBody AdminCategorySaveRequestDto dto) {
        return saveService.save(dto);
    }
}
