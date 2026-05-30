package capstone.safelaw.controller;

import capstone.safelaw.domain.LegalData;
import capstone.safelaw.domain.Precedent;
import capstone.safelaw.domain.Precedent_;
import capstone.safelaw.repository.LegalDataRepository;
import capstone.safelaw.service.EmbeddingService;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class LawSearchController {

    private final EmbeddingService embeddingService;
    private final LegalDataRepository legalDataRepository;
    private final Box<Precedent> precedentBox;

    public LawSearchController(EmbeddingService embeddingService, LegalDataRepository legalDataRepository, BoxStore boxStore) {
        this.embeddingService = embeddingService;
        this.legalDataRepository = legalDataRepository;
        this.precedentBox = boxStore.boxFor(Precedent.class);
    }

    @PostMapping("/search")
    public List<LegalData> searchLaw(@RequestBody Map<String, String> request) {
        try {
            System.out.println("\n=========================================");

            // 🚨 1. DB에 들어있는 실제 판례의 벡터 규격 확인!
            Precedent sample = precedentBox.query().build().findFirst();
            if (sample != null && sample.embedding != null) {
                // 📍 vector를 embedding으로 수정 완료
                System.out.println("🔍 1. DB 안의 자물쇠(벡터) 규격: " + sample.embedding.length + "개");
            } else {
                System.out.println("❌ DB의 판례 벡터 데이터가 비어있습니다 (null)!");
            }

            String keyword = request.get("keyword");
            float[] queryVector = embeddingService.getEmbedding(keyword);
            System.out.println("🔍 2. AI가 뽑아낸 열쇠(벡터) 규격: " + queryVector.length + "개");

            // 🚨 2. AI 열쇠로 DB 검색 시도
            List<Precedent> topResults = precedentBox.query()
                    // 📍 Precedent_.vector를 Precedent_.embedding으로 수정 완료
                    .nearestNeighbors(Precedent_.embedding, queryVector, 3)
                    .build()
                    .find();

            System.out.println("💡 3. 매칭 성공한 판례 개수: " + topResults.size() + "개");
            System.out.println("=========================================\n");

            List<LegalData> finalResults = new ArrayList<>();
            for (Precedent p : topResults) {
                legalDataRepository.findById(p.id).ifPresent(finalResults::add);
            }
            return finalResults;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}