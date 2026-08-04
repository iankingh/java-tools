# java-tools

Java 21 多模組工具庫，將通用工具與選用整合拆成獨立 artifact。核心模組不依賴 Spring；HTTP、Excel、Redis、JMS 與 JAXB 功能只在對應模組引入。

這個 repository 是 library 專案，不是 Spring Boot application，也沒有長駐服務可啟動。API 採明確例外、`Optional` 與安全預設；舊版遷移注意事項見 [MIGRATION.md](MIGRATION.md)。

## 模組與原始碼導覽

| Gradle module / artifact | Package 與目前內容 | 主要外部依賴 |
| --- | --- | --- |
| `java-tools-core` | `collection`（陣列、Map）、`io`（Base64、classpath properties）、`number`、`text`（字串、UUID、文字模板）、`time`、`validation`、`security`（AES-GCM、log 單行化）、`legacy`（Triple DES 遷移） | 無 |
| `java-tools-http` | `http.JsonHttpClient` 的 GET、JSON POST、form POST 與自訂 `HttpRequest`；`JsonValues` 清除 JSON tree 的 null/空字串；非 2xx 回應使用 `HttpStatusException` | `java.net.http`、Jackson |
| `java-tools-excel` | `excel.ExcelTemplateRenderer` 取代 workbook 中的 `${name}` placeholder，並依資料列重複 template row | Apache POI |
| `java-tools-spring-redis` | `redis.RedisStringStore` 封裝字串值的 set/get/delete、expiry 與 TTL | Spring Data Redis |
| `java-tools-jakarta` | `jakarta.XmlBindings` 的 JAXB marshal/unmarshal；`JmsTextClient` 的文字訊息 send/receive | Jakarta JAXB/JMS |
| `java-tools-examples` | `examples.UtilityExamples`，示範日期、數字格式與字串遮罩；這是唯一套用 `application` plugin 的模組，不會發布 | `java-tools-core` |

所有 production code 位於各模組的 `src/main/java/io/github/iankingh/javatools/`，單元測試位於 `src/test/java/`。API 的輸入、錯誤與邊界行為可直接從同名測試導覽；Redis 的真實服務測試另位於 `java-tools-spring-redis/src/integrationTest/java/`。

## 環境需求

- Java 21（Gradle toolchain 與 CI 均固定為 21）
- repository 內建 Gradle Wrapper 9.6.1；不需要另行安裝 Gradle
- 執行選用 Redis integration test 時需要可用的 Docker daemon，測試透過 Testcontainers 啟動 Redis

## 取得 artifact

發布目標是 GitHub Packages。預設開發版本由 root build 設為 `2.0.0-SNAPSHOT`；實際可下載版本以 repository 的 Packages/Release 為準。存取 GitHub Packages 需要具有對應 package 權限的 credentials。

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

只加入實際使用的模組；各 library module 都會個別產生 sources 與 Javadoc JAR。

## 已實作 API 範例

核心工具：

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
import io.github.iankingh.javatools.http.JsonHttpClient;
import java.net.URI;
import java.time.Duration;

record Response(String name) {}

var client = JsonHttpClient.create(Duration.ofSeconds(10), Duration.ofSeconds(30));
var response = client.get(URI.create("https://example.test/value"), Response.class);
```

`JsonHttpClient` 只接受 `http`/`https` URI，使用 JVM trust store 的標準 TLS 憑證與 hostname 驗證，不提供 trust-all 模式。更多可執行用法見 `java-tools-examples`；各整合模組的具體呼叫方式見其同名測試。

## 建置、測試與執行

CI 的完整 library 驗證：

```bash
./gradlew check javadoc publishAllToMavenLocal
```

其中 `check` 會執行 JUnit、Spotless、SpotBugs、JaCoCo 與 Java compiler `-Xlint:all -Werror`；`java-tools-core` 另要求至少 80% line coverage、70% branch coverage。HTML coverage report 會產生於各 library module 的 `build/reports/jacoco/test/html/`。

執行範例 application：

```bash
./gradlew :java-tools-examples:run
```

Redis 真實服務測試不在預設 `check` 內：

```bash
./gradlew :java-tools-spring-redis:integrationTest
```

格式問題可使用 build 已定義的 task 修正：

```bash
./gradlew spotlessApply
```

## 狀態與限制

- `com.ian.tools.*` deprecated wrapper 已於 2.0 移除；目前 package namespace 為 `io.github.iankingh.javatools.*`。
- `LegacyTripleDes` 僅供既有資料遷移；新資料應使用 `AesGcmCrypto`。
- Redis module 只封裝 `StringRedisTemplate` 的字串操作，不提供 Redis server 或 Spring Boot 自動設定。
- JMS client 需要呼叫端提供 `ConnectionFactory` 與 `Destination`；Excel renderer 需要呼叫端提供 workbook template。
- root build 沒有宣告 BOM；每個模組是獨立 dependency。

## 延伸文件

- [MIGRATION.md](MIGRATION.md)：舊 package/API 對照與行為變更
- [CONTRIBUTING.md](CONTRIBUTING.md)：開發與驗證規則
- [SECURITY.md](SECURITY.md)：支援版本、安全預設與回報方式
- [LICENSE](LICENSE)：MIT License

## 發布

`.github/workflows/publish.yml` 可由 GitHub Release 或手動 workflow dispatch 發布至 GitHub Packages。版本可透過 `-PversionOverride=x.y.z` 覆寫。
