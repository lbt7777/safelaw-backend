package capstone.safelaw.controller;

import capstone.safelaw.dto.SearchRequestDto;
import capstone.safelaw.dto.SearchResponseDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/laws") // 이 API의 기본 주소입니다.
@Tag(name = "Law Search", description = "판례 검색 API") // Swagger 탭 이름
public class LawSearchController {

    @PostMapping("/search")
    @Operation(summary = "키워드 기반 판례 검색", description = "온디바이스 AI에서 추출한 키워드를 받아 관련 판례를 반환합니다.")
    public List<SearchResponseDto> searchLaws(@RequestBody SearchRequestDto request) {

        // TODO: 향후 service 패키지에서 Vector DB 검색 로직을 연결할 예정입니다.
        // 지금은 프론트엔드 테스트를 위해 가짜(Mock) 데이터를 반환합니다.

        List<SearchResponseDto> responseList = new ArrayList<>();
        SearchResponseDto mockData = new SearchResponseDto();

        mockData.setPrecedId("338315");
        mockData.setCaseName("업무방해등");
        mockData.setCaseNum("2022고단420");
        mockData.setSentenceDate("2022.12.09.");
        mockData.setCourtName("춘천지방법원 영월지원");
        mockData.setSummary("2022년 10월 식당에서 소란을 피우며 재물을 손괴하고, 경찰관의 직무집행을 방해한 사건에 대해...");
        responseList.add(mockData);
        return responseList;
    }
}