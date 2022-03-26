package com.bside.breadgood.fixtures.termstype;

import com.bside.breadgood.ddd.termstype.domain.Terms;
import com.bside.breadgood.ddd.termstype.domain.TermsType;
import com.bside.breadgood.ddd.termstype.ui.dto.TermsTypeSaveRequestDto;

import java.time.LocalDate;

/**
 * author : haedoang
 * date : 2022/03/20
 * description :
 */
public class TermsTypeFixtures {

    public static Terms 추가된약관 =
            Terms.builder()
                    .content("추가된 약관입니다")
                    .executionDate(LocalDate.now())
                    .build();

    public static Terms 약관_시행일자_1년후 =
            Terms.builder()
                    .content("추가된 가장 최근의 약관입니다")
                    .executionDate(LocalDate.now().plusYears(1))
                    .build();

    public static TermsType 집행중인약관1 =
            TermsType.builder().name("집행중인약관1")
                    .content("집행중인약관1 내용입니다.")
                    .required(true)
                    .executionDate(LocalDate.now().minusDays(1))
                    .sortNumber(1)
                    .build();

    public static TermsType 필수약관2 =
            TermsType.builder().name("필수약관2")
                    .content("필수약관2 내용입니다.")
                    .required(true)
                    .executionDate(LocalDate.now())
                    .sortNumber(2)
                    .build();

    public static TermsType 선택약관1 =
            TermsType.builder().name("선택약관1")
                    .content("선택약관1 내용입니다.")
                    .required(false)
                    .executionDate(LocalDate.now())
                    .sortNumber(3)
                    .build();

    public static TermsType 선택약관2 =
            TermsType.builder().name("선택약관2")
                    .content("선택약관2 내용입니다.")
                    .required(false)
                    .executionDate(LocalDate.now())
                    .sortNumber(4)
                    .build();

    public static TermsTypeSaveRequestDto 필수약관1등록요청 =
            TermsTypeSaveRequestDto.valueOf("필수약관1", "필수약관내용입니다.", LocalDate.now(), true, 1);
}

