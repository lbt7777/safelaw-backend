package capstone.safelaw.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "legal_data")
public class LegalData {

    @Id
    public Long id;

    @Column(name = "case_name")
    public String caseName;

    @Column(name = "case_num")
    public String caseNum;

    @Column(name = "court_name")
    public String courtName;

    public String content;
}