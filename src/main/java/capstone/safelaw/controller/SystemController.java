package capstone.safelaw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/system")
@Tag(name = "System & Updates", description = "AI 모델 및 판례 DB 업데이트 관련 API")
public class SystemController {

    @Operation(summary = "최신 AI 모델 다운로드 주소 조회", description = "온디바이스 AI 구동을 위한 최신 모델 파일(S3)의 주소를 반환합니다.")
    @GetMapping("/model/latest")
    public ResponseEntity<String> getLatestModelUrl() {
        // 실제 구현 시 S3 주소를 반환하지만, 임시로 가짜 주소를 반환합니다.
        return ResponseEntity.ok("https://safelaw-storage.s3.ap-northeast-2.amazonaws.com/models/v1.2.0-core.gguf");
    }

    @Operation(summary = "최신 판례 DB 파일 다운로드", description = "로컬 검색에 필요한 최신 판례 데이터베이스 파일을 다운로드합니다.")
    @GetMapping("/database/sync")
    public ResponseEntity<String> getLatestDatabaseUrl() {
        return ResponseEntity.ok("https://safelaw-storage.s3.ap-northeast-2.amazonaws.com/db/precedent-v2026.05.db");
    }
}
