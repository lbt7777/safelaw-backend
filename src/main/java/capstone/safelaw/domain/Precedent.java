package capstone.safelaw.domain;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import io.objectbox.annotation.HnswIndex;

@Entity
public class Precedent {
    // 📍 1. 찾아내신 정답! ID를 수동으로 넣을 수 있게 허용합니다.
    @Id(assignable = true)
    public long id;

    // 📍 2. 찾아내신 정답! 차원 수를 정확히 128로 맞춥니다.
    @HnswIndex(dimensions = 384)
    public float[] embedding; // (만약 전에 vector로 바꾸셨다면 vector로 적어주세요!)
}