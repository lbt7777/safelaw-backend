package capstone.safelaw;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

public class DbDetector {
    public static void main(String[] args) {
        System.out.println("🔍 프로젝트 폴더의 모든 DB 파일을 정밀 스캔합니다...");

        File currentDir = new File(System.getProperty("user.dir"));
        File[] files = currentDir.listFiles();

        boolean foundAny = false;

        if (files != null) {
            for (File f : files) {
                // .db 또는 .sqlite 로 끝나는 파일 모두 찾기
                if (f.getName().endsWith(".db") || f.getName().endsWith(".sqlite")) {
                    foundAny = true;
                    System.out.println("\n========================================");
                    System.out.println("📁 발견된 파일: " + f.getName());

                    // 실제 용량 계산 (중요!)
                    long kb = f.length() / 1024;
                    System.out.println("⚖️ 실제 용량: " + kb + " KB");

                    if (kb < 10) {
                        System.out.println("❌ 경고: 용량이 너무 작습니다! (알맹이가 없는 껍데기 파일입니다)");
                        System.out.println("👉 해결책: DB 담당자에게 LawData.db 파일을 다시 받아서 덮어씌워야 합니다.");
                        continue;
                    }

                    // 파일 용량이 정상이면 테이블 구조 까보기
                    try (Connection conn = DriverManager.getConnection("jdbc:sqlite:" + f.getAbsolutePath())) {
                        DatabaseMetaData meta = conn.getMetaData();
                        ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});

                        boolean hasTable = false;
                        while (tables.next()) {
                            hasTable = true;
                            String tableName = tables.getString("TABLE_NAME");
                            System.out.println("  📦 찐 테이블 이름: [" + tableName + "]");

                            ResultSet columns = meta.getColumns(null, null, tableName, "%");
                            while (columns.next()) {
                                System.out.println("    ┣ 📜 컬럼: " + columns.getString("COLUMN_NAME"));
                            }
                        }
                        if (!hasTable) System.out.println("  ❌ 파일은 큰데 테이블이 하나도 없습니다.");
                    } catch (Exception e) {
                        System.out.println("  ❌ 데이터베이스 읽기 에러: " + e.getMessage());
                    }
                }
            }
        }

        if (!foundAny) {
            System.out.println("\n❌ 프로젝트 최상단 폴더에 .db 나 .sqlite 파일이 단 하나도 없습니다!");
        }
        System.out.println("========================================");
    }
}