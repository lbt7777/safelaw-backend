package capstone.safelaw.dto;

import lombok.Data;

@Data
public class SearchResponseDto {
    private String caseId;    // 판례 번호
    private String title;     // 판례 제목
    private String summary;   // 판례 요약
}