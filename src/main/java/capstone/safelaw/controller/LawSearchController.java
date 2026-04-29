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

        mockData.setCaseId("2023다12345");
        mockData.setTitle("전세보증금반환청구의 소");
        mockData.setSummary("요청하신 키워드 " + request.getKeywords() + " 에 대한 임시 검색 결과입니다.");

        responseList.add(mockData);
        return responseList;
    }
}