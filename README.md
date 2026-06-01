# 득템시루 Backend

## 소개

마감 임박 할인 상품의 지역 픽업 서비스를 제공하는 구매자·판매자 Android 앱 공통 REST API 서버입니다.

## 기술 스택

Kotlin · Spring Boot · Spring Data JPA · Spring Security · PostgreSQL · Flyway · JWT ([build.gradle.kts](build.gradle.kts))

### 아키텍처

헥사고날·클린 아키텍처를 사용하며 경계는 [기술 구조](https://github.com/deuktem-siru/.github/blob/main/03-tech/01-architecture.md#아키텍처와-경계)를 따릅니다.

## 시작하기

JDK 21(`JAVA_HOME`)과 Docker(또는 PostgreSQL 16)를 준비하고 저장소 루트에서 실행합니다(개발 기본은 `create-drop`, 기존 컨테이너는 `docker start deuktemsiru-postgres`, Windows는 `.\gradlew`, 로컬 설정은 [application.properties](src/main/resources/application.properties)):

```bash
docker run --name deuktemsiru-postgres -p 5432:5432 -d \
  -e POSTGRES_DB=deuktemsiru -e POSTGRES_USER=deuktemsiru -e POSTGRES_PASSWORD=deuktemsiru \
  postgres:16-alpine
./gradlew bootRun --args='--spring.profiles.active=dev'
```

[docker-compose.yml](docker-compose.yml)(백엔드 `prod`·PostgreSQL·Prometheus `:9090`·Grafana `:3000`) 실행 시에는 기존 서버·DB를 먼저 종료하고, 빈 DB는 [배포 전 DB 준비](https://github.com/deuktem-siru/.github/blob/main/05-ops/01-deploy.md#배포-전-db-준비)와 [EC2 초기 셋업](https://github.com/deuktem-siru/.github/blob/main/05-ops/01-deploy.md#2-ec2-초기-셋업)을 따릅니다:

```bash
export POSTGRES_PASSWORD=deuktemsiru APP_JWT_SECRET="$(openssl rand -hex 32)" GRAFANA_PASSWORD=local-grafana
docker compose up -d
```

## 사용 방법

| 대상 | 주소 | 비고 |
| --- | --- | --- |
| 로컬 API / 에뮬레이터 | `http://localhost:8080` / `http://10.0.2.2:8080` | [현재 업무 규칙](https://github.com/deuktem-siru/.github/blob/main/01-func/01-requirements.md#현재-업무-규칙) 참고 |
| Scalar / OpenAPI | `http://localhost:8080/docs` / `/v3/api-docs` | HTTP 상태 우선 판정(찜 생성 201의 `code` 200, 필터 빈 오류 주의) |

`prod`가 아니고 DB가 비어 있으면 [DataInitializer.kt](src/main/kotlin/com/deuktemsiru/configuration/DataInitializer.kt)가 구매자 1명과 시흥시 매장 5곳(오이도굽는집·배곧 로스터리·정왕시장 분식·은행동 찬찬도시락·목감 우리반찬) 시드를 생성하며, `dev`에서는 `POST /api/v1/auth/debug/login`을 사용할 수 있습니다.

## 테스트

Docker를 실행한 상태에서 검증합니다(Testcontainers 결제 통합 테스트 스킵 주의; `check`는 Jacoco·Stub 검사 포함):

```bash
./gradlew test check
```

Node.js·npm·k6 및 실행 중인 `dev` 서버(또는 [bruno](bruno) 폴더 Local 환경)로 API·부하 테스트를 실행합니다(스모크 테스트는 주문 생성·취소 수행; macOS Docker k6는 `--network host` 대신 `-e BASE_URL=http://host.docker.internal:8080` 사용):

```bash
npm ci && npm run bruno:test && npm run k6:smoke
BASE_URL=http://localhost:8080 VUS=20 RAMP_UP=1m HOLD=3m RAMP_DOWN=30s npm run k6:load
docker run --rm -i --network host -v "$PWD:/work" -w /work grafana/k6 run tests/k6/smoke.js
```

## 관련 문서

- [프로젝트 문서](https://github.com/deuktem-siru/.github#관련-문서) · [배포 가이드](https://github.com/deuktem-siru/.github/blob/main/05-ops/01-deploy.md) · [Android 공통 안내](https://github.com/deuktem-siru/android-app)
