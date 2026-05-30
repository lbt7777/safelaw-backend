package capstone.safelaw.repository;

import capstone.safelaw.domain.LegalData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LegalDataRepository extends JpaRepository<LegalData, Long> {
    // JpaRepository를 상속받으면 기본 검색 기능(findById 등)이 자동으로 완성됩니다!
}