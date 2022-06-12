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
public class TermsTypeFixture {
    public static TermsType 필수_개인정보_수집_및_이용_동의_약관_100 =
            TermsType.builder().name("개인정보 수집 및 이용 동의")
                    .content("필수 개인정보수집 및 이용 동의 약관의 내용입니다.")
                    .required(true)
                    .executionDate(LocalDate.now().minusDays(1))
                    .sortNumber(100)
                    .build();

    public static TermsType 필수_위치_기반_서비스_약관_동의_200 =
            TermsType.builder().name("위치 기반 서비스 약관 동의")
                    .content("필수 위치 기반 서비스 약관 동의 약관의 내용입니다.")
                    .required(true)
                    .executionDate(LocalDate.now())
                    .sortNumber(200)
                    .build();

    public static TermsType 선택_광고_이용_정보_동의_300 =
            TermsType.builder().name("광고 이용 정보 동의")
                    .content("선택 광고 이용 정보 동의 약관 입니다.")
                    .required(false)
                    .executionDate(LocalDate.now())
                    .sortNumber(300)
                    .build();

    public static Terms 광고_약관_내용 =
            Terms.builder()
                    .content("이것은 광고 약관의 내용입니다.")
                    .executionDate(LocalDate.now())
                    .build();

    public static Terms 추가된_약관의_내용_만료기간_1년 =
            Terms.builder()
                    .content("추가된 가장 최근의 약관입니다")
                    .executionDate(LocalDate.now().plusYears(1))
                    .build();

    public static TermsTypeSaveRequestDto 필수_개인정보_수집_및_이용_동의_약관_등록요청 =
            TermsTypeSaveRequestDto.valueOf("개인정보 수집 및 이용 동의", "필수 개인정보수집 및 이용 동의 약관의 내용입니다.", LocalDate.now(), true);

    public static TermsTypeSaveRequestDto 선택_광고_이용_정보_동의_등록요청 =
            TermsTypeSaveRequestDto.valueOf("광고 이용 정보 동의", "선택 광고 이용 정보 동의 약관 입니다.", LocalDate.now(), false);
}

