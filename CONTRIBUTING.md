# 貢獻指南

## 開發環境

使用 Java 21，並只透過 repository 內的 Gradle Wrapper 執行建置。

```bash
./gradlew check javadoc publishAllToMavenLocal
```

Redis integration test 需要 Docker：

```bash
./gradlew :java-tools-spring-redis:integrationTest
```

## 變更原則

- 核心模組不得新增 Spring 或 Jakarta 依賴。
- 公開 API 必須有英文 Javadoc、成功與失敗路徑測試。
- 不得加入 trust-all TLS、硬編碼 secret、空 catch 或以 `null` 偽裝錯誤。
- 新增 dependency 前先確認無 JDK 或既有 library 可重用的功能。
- deprecated wrapper 只用於 1.x 遷移，不得成為新功能入口。

送出 PR 前執行 `./gradlew check`。格式問題可用 `./gradlew spotlessApply` 修正。
