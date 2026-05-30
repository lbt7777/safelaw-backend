package capstone.safelaw.config;

import capstone.safelaw.domain.MyObjectBox;
import io.objectbox.BoxStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class ObjectBoxConfig {

    @Bean
    public BoxStore boxStore() {
        System.out.println("📦 1.3GB 진짜 ObjectBox DB 폴더 연결 시도 중...");

        // 1. 우리가 눈으로 확인한 그 폴더("safelaw-db")를 정확히 타겟팅합니다.
        File dbDirectory = new File("safelaw-db");

        // 2. name() 대신 directory() 방식을 써서 무조건 저 폴더만 읽게 멱살을 잡습니다!
        return MyObjectBox.builder().directory(dbDirectory).build();
    }
}