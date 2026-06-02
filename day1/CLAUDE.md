# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

Spring Boot 기반의 학습용 데모 웹 애플리케이션. `com.example.demo` 패키지에 단일 REST 엔드포인트(`HelloController`의 `GET /`)만 존재하는 최소 구성이다.

## 빌드 / 실행 / 테스트

Gradle Wrapper(`./gradlew`)를 사용한다. Windows 환경이지만 셸은 bash이므로 `./gradlew` 형태로 호출한다.

```bash
./gradlew build          # 컴파일 + 테스트 + jar 패키징
./gradlew bootRun        # 애플리케이션 실행 (기본 포트 8080)
./gradlew test           # 전체 테스트
./gradlew test --tests "com.example.demo.DemoApplicationTests"          # 단일 테스트 클래스
./gradlew test --tests "com.example.demo.DemoApplicationTests.contextLoads"  # 단일 테스트 메서드
```

실행 후 동작 확인: `curl "http://localhost:8080/?param1=a&param2=b&param3=c"` → `message`와 param 값을 담은 JSON Map 반환.

## 버전 환경 (중요)

이 프로젝트는 의도적으로 구형 스택에 고정되어 있다. 코드를 추가/수정할 때 이 제약을 따라야 한다.

- **Java 8** (`sourceCompatibility = 1.8`) — Java 9+ 문법/API 사용 금지.
- **Spring Boot 2.1.8.RELEASE** — `build.gradle`에서 플러그인 classpath로 직접 지정. (참고: `HELP.md`는 자동 생성된 파일로 Spring Boot 3.5.0을 언급하지만 실제 버전과 무관하니 무시할 것.)
- **JUnit 4** — 테스트는 `@RunWith(SpringRunner.class)` + `org.junit.Test` 사용. JUnit 5(Jupiter) 어노테이션 사용 금지.
- **Gradle 4.5** (`gradle/wrapper/gradle-wrapper.properties`).
- **Lombok** 사용 가능 (`compileOnly` 의존성).

### 인증서 우회 설정

`gradle.properties`에 JDK 8(1.8.0_144)의 오래된 인증서 문제를 우회하기 위한 커스텀 트러스트스토어 설정이 있다:

```
org.gradle.jvmargs=-Djavax.net.ssl.trustStore=C:/Users/y.kwon/Downloads/modern-cacerts ...
```

이 경로가 존재해야 mavenCentral 의존성 다운로드가 동작한다. 빌드가 SSL 핸드셰이크 오류로 실패하면 이 트러스트스토어 파일과 경로를 먼저 확인할 것.
