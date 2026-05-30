package capstone.safelaw.service;

import capstone.safelaw.domain.Precedent;
import io.objectbox.Box;
import io.objectbox.BoxStore;
import org.springframework.stereotype.Service;

@Service
public class LawService {

    private final Box<Precedent> precedentBox;

    public LawService(BoxStore boxStore) {
        // 담당자가 꽂아줄 DB 파일에서 판례 데이터(Precedent)를 꺼낼 준비
        this.precedentBox = boxStore.boxFor(Precedent.class);
    }


    // 나중에 검색(find) 로직만 여기에 추가
}