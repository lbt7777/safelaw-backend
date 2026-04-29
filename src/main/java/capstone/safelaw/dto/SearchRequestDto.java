package capstone.safelaw.dto;

import lombok.Data;
import java.util.List;

@Data // Lombok을 이용해 Getter, Setter 등을 자동 생성합니다.
public class SearchRequestDto {
    private List<String> keywords; // 예: ["전세 사기", "보증금"]
}