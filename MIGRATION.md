# 1.0 遷移指南

## 基礎需求

- 最低 Java 版本由 11 提升為 21。
- Gradle Wrapper 升級為 9.6.1。
- Maven group 由 `com.ian` 改為 `io.github.iankingh`。
- 專案由單一 Spring Boot application 改為多模組 library；不再提供 `ToolsApplication`。

## API 對照

| 舊 API | 新 API |
| --- | --- |
| `com.ian.tools.collection.ArrayUtils` | `io.github.iankingh.javatools.collection.Arrays` |
| `com.ian.tools.collection.MapUtil` | `io.github.iankingh.javatools.collection.Maps` |
| `com.ian.tools.string.StrUtils` | `io.github.iankingh.javatools.text.Strings` |
| `com.ian.tools.string.RandomStringUtils` | `io.github.iankingh.javatools.text.Identifiers` |
| `com.ian.tools.number.NumberUtils` | `io.github.iankingh.javatools.number.Numbers` |
| `com.ian.tools.other.EmailValidator` | `io.github.iankingh.javatools.validation.EmailAddresses` |
| `com.ian.tools.file.ImageUtil` | `io.github.iankingh.javatools.io.Base64Files` |
| `com.ian.tools.properties.ConnectionUrl` | `io.github.iankingh.javatools.io.ResourceProperties` |
| `com.ian.tools.date.DateUtil` | `io.github.iankingh.javatools.time.DateTimes` |
| `com.ian.tools.other.STD3Des` | `io.github.iankingh.javatools.security.AesGcmCrypto` |
| `com.ian.tools.httpclient.HttpClientUtils` | `io.github.iankingh.javatools.http.JsonHttpClient` |
| `com.ian.tools.other.ExcelUtil` | `io.github.iankingh.javatools.excel.ExcelTemplateRenderer` |
| `com.ian.tools.redis.RedisStrUtils` | `io.github.iankingh.javatools.redis.RedisStringStore` |
| `com.ian.tools.jaxb.JaxbUtil` | `io.github.iankingh.javatools.jakarta.XmlBindings` |

deprecated wrapper 已於 2.0 移除（1.x 期間僅供遷移使用）。沒有實際可運作內容、重複、空殼或只包含大段註解的類別不提供 wrapper。

## 重要行為變更

- 錯誤不再被空 catch、`printStackTrace()` 或假成功回傳值吞掉。
- 新 API 對無效輸入拋出明確例外；可缺少的值使用 `Optional`。
- HTTP 只接受 `http`/`https` URI，使用標準 TLS 驗證，不再修改 JVM 全域 SSL state。
- 新加密資料必須使用 AES-GCM。Triple DES 僅保留 migration-only API。
- 日期 API 使用 thread-safe `java.time`，不再共用可變 `SimpleDateFormat`。
- Redis 改為 constructor injection，集合操作會保存集合元素，而不是把整個集合當成單一元素。
- JAXB 解析禁止 DOCTYPE 與 external entities。
- Excel 重複列依 template cell placeholder 對應，不依賴 `Map` iteration order。

## 已移除內容

- Servlet 內建立 `Timer` 的排程範例。
- 未完成的 ActiveMQ/JNDI servlet、硬編碼 queue 與永遠回傳 null 的 JMS 設定。
- 空殼 FTP、mail、JSON 與舊 Apache HttpClient 範例。
- domain-specific account enum、未驗證的 lunar calendar、純 console 練習程式。
- 原始碼中的敏感示範資料與不安全 TLS bypass。

Git 歷史版本曾包含示範憑證；這些值已於 2.0 整理時透過 history rewrite 從預設分支歷史移除。若其中任何值曾為真實憑證，請立即輪替（公開 repository 的歷史副本可能已被快取或 fork）。
