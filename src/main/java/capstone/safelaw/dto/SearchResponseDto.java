package capstone.safelaw.dto;

import lombok.Data;

@Data
public class SearchResponseDto {
    // 1. 판례 식별 정보
    private String precedId;      // 판례 일련번호

    // 2. 판례 기본 정보
    private String caseName;      // 사건명
    private String caseNum;       // 사건번호
    private String sentenceDate;  // 선고일자
    private String courtName;     // 법원명

    // 3. 판례 요약
    private String summary;       // 판례 요약 (사진의 label.output 내용)
}