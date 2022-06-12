package com.bside.breadgood.ddd.termstype.ui;

import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/pages/admin/termsType")
public class TermsAdminPageController {

    private final TermsTypeService termsTypeService;

    @GetMapping("/{termsTypeId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String termsTypePage(@PathVariable Long termsTypeId, Model model) {

        // 해당 약관 타입 정보
        final TermsTypeInfoResponseDto termsTypeInfoResponseDto = termsTypeService.findById(termsTypeId);
        model.addAttribute("termsType", termsTypeInfoResponseDto);

        return "pages/termsType";
    }
}