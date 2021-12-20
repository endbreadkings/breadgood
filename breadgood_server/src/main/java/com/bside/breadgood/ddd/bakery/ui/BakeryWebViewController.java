package com.bside.breadgood.ddd.bakery.ui;

import com.bside.breadgood.ddd.bakery.application.BakeryService;
import com.bside.breadgood.ddd.bakery.application.dto.BakeryResponseDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchRequestDto;
import com.bside.breadgood.ddd.bakery.application.dto.BakerySearchResponseDto;
import com.bside.breadgood.ddd.bakerycategory.application.BakeryCategoryService;
import com.bside.breadgood.ddd.bakerycategory.application.dto.BakeryCategoryResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/pages/webview-api/bakery")
@RestController
public class BakeryWebViewController {

    private final BakeryService bakeryService;
    private final BakeryCategoryService bakeryCategoryService;

    @PostMapping("/{bakeryId:\\d+}/user/{userId:\\d+}")
    public BakeryResponseDto findByIdAndUserId(@PathVariable Long bakeryId, @PathVariable Long userId) {
        return bakeryService.findByIdAndUserId(bakeryId, userId);
    }

    @PostMapping("/search")
    public List<BakerySearchResponseDto> search(@RequestBody(required = false) BakerySearchRequestDto dto) {
        return bakeryService.search(dto);
    }

    @GetMapping("/category")
    public List<BakeryCategoryResponseDto> findAll() {
        return bakeryCategoryService.findAll();
    }
}
