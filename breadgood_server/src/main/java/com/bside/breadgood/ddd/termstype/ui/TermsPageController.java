package com.bside.breadgood.ddd.termstype.ui;

import com.bside.breadgood.ddd.termstype.application.TermsTypeService;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeInfoResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class TermsPageController {

    private final TermsTypeService termsTypeService;

    @GetMapping("/pages/termsType/{termsTypeId}")
    public String termsTypePage(@PathVariable Long termsTypeId, Model model) {

        // 해당 약관 타입 정보
        final TermsTypeInfoResponseDto termsTypeInfoResponseDto = termsTypeService.findById(termsTypeId);
        model.addAttribute("termsType", termsTypeInfoResponseDto);

        return "pages/termsType";
    }


    @GetMapping("/pages/termsType/{termsTypeId}/terms/{termsId}")
    public String termsPage(@PathVariable Long termsId, Model model, @PathVariable Long termsTypeId) {

        // 해당 약관 정보
        model.addAttribute("terms", termsTypeService.findByIdAndTermsId(termsTypeId, termsId));

        return "pages/terms";
    }
}


// TODO 약관 페이지 생성 1. 히스토리까지 볼 수 있는 페이지 (해당 약관 타입에 해당되는)
//  2. 현재 진행중인 약관을 볼 수 있는 페이지 (해당 약관 타입에 해당되는)
//  3. 현재 진행중인 약관을 볼 수 있는 페이지 URL 리턴 하는 API 만들기 (해당 약관 타입에 해당되는)
// TODO 약관을 생성할 때
