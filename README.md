# java-tools

Java 21 多模組工具庫，提供安全預設、明確錯誤處理、可測試的整合層，以及可發布到 Maven/GitHub Packages 的 artifacts。

這個專案不再是 Spring Boot 應用程式。核心工具不依賴 Spring；Redis、JMS、JAXB、Excel 與 HTTP 功能各自隔離，使用者只需引入需要的模組。

## 模組

| Artifact | 用途 | 主要外部依賴 |
| --- | --- | --- |
| `java-tools-core` | 字串、集合、日期、數字、Base64、properties、AES-GCM、模板 | 無 |
| `java-tools-http` | JDK HTTP client、Jackson JSON、JSON tree 清理 | Jackson |
| `java-tools-excel` | Excel placeholder 與重複列模板 | Apache POI |
| `java-tools-spring-redis` | constructor-injected Redis string/object operations | Spring Data Redis |
| `java-tools-jakarta` | 安全 JAXB 與 JMS text client | Jakarta JAXB/JMS |
| `java-tools-examples` | 精選、可執行範例 | core |

## 環境需求

- Java 21 LTS
- 專案內建 Gradle Wrapper 9.6.1
- 選用 Redis integration test 時需要 Docker

## 使用方式

GitHub Packages repository：

```groovy
repositories {
    maven {
        url = uri('https://maven.pkg.github.com/iankingh/java-tools')
        credentials {
            username = System.getenv('GITHUB_ACTOR')
            password = System.getenv('GITHUB_TOKEN')
        }
    }
}

dependencies {
    implementation 'io.github.iankingh:java-tools-core:2.0.0-SNAPSHOT'
    implementation 'io.github.iankingh:java-tools-http:2.0.0-SNAPSHOT'
}
```

核心 API：

```java
import io.github.iankingh.javatools.security.AesGcmCrypto;
import io.github.iankingh.javatools.text.Strings;
import io.github.iankingh.javatools.time.DateTimes;
import java.security.SecureRandom;

var key = AesGcmCrypto.generateKey(256);
var encrypted = AesGcmCrypto.encrypt("secret".getBytes(), key, null, new SecureRandom());
var plaintext = AesGcmCrypto.decrypt(encrypted, key, null);

String masked = Strings.maskLast("1234567890", 4, '*');
var date = DateTimes.parseDate("2026-07-30");
```

HTTP JSON：

```java
var client = JsonHttpClient.create(Duration.ofSeconds(10), Duration.ofSeconds(30));
var response = client.post(uri, request, Response.class);
```

HTTP client 使用 JVM trust store 的標準 TLS 憑證與 hostname 驗證，不提供 trust-all 模式。

## 建置與品質檢查

```bash
./gradlew check
./gradlew javadoc
./gradlew publishAllToMavenLocal
```

`check` 包含：

- JUnit 測試
- Spotless 格式檢查
- SpotBugs 靜態分析
- JaCoCo 報告
- `java-tools-core` 至少 80% line coverage、70% branch coverage
- Java compiler `-Xlint:all -Werror`

Redis 真實服務測試為選用 task，不影響預設本機 build：

```bash
./gradlew :java-tools-spring-redis:integrationTest
```

執行範例：

```bash
./gradlew :java-tools-examples:run
```

## 相容性與遷移

`com.ian.tools.*` deprecated wrapper 已於 2.0 移除。請直接使用 `io.github.iankingh.javatools.*`。

完整對照與行為變更請見 [MIGRATION.md](MIGRATION.md)。安全政策請見 [SECURITY.md](SECURITY.md)。

## 發布

`publish.yml` 可由 GitHub Release 或手動 workflow dispatch 發布至 GitHub Packages。版本可透過 `-PversionOverride=x.y.z` 覆寫；預設開發版本為 `2.0.0-SNAPSHOT`。

## License

[MIT](LICENSE)
