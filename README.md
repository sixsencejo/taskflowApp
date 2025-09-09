# 📋 TaskFlow - 협업을 위한 스마트 작업 관리 API 서버

**TaskFlow**는 팀의 생산성 향상을 위한 직관적이고 강력한 작업 관리 API 서버입니다.  
Spring Boot 기반 RESTful 아키텍처로 설계되었으며, 작업 생성, 담당자 지정, 댓글 소통, 활동 로그, 대시보드 등 협업에 필요한 기능을 모두 제공합니다.

---

## ✨ 주요 기능 (Key Features)

### 👨‍💻 사용자 인증 및 관리 (User & Auth)
- **JWT 기반 인증**: Stateless한 JWT를 활용한 안전하고 확장 가능한 인증 시스템
- **회원가입 및 로그인**: BCrypt로 비밀번호 암호화
- **Soft Delete & 계정 복구**: 탈퇴 시 데이터를 삭제하지 않고 `deleted_at` 처리, 추후 복구 가능

---

### ✅ 작업 관리 (Task)
- 작업 생성, 전체/개별 조회, 수정, 삭제 (CRUD)
- 작업 담당자(Assignee) 지정 및 변경
- 상태(TODO, IN_PROGRESS, DONE), 우선순위, 카테고리 설정

---

### 💬 댓글 관리 (Comment)
- 특정 작업에 대한 댓글 작성, 수정, 삭제
- **대댓글 지원** (계층형 구조)

---

### 📈 활동 로그 (Activity Log)
- **AOP 기반 자동 기록**: Task/Comment 변경 시 자동으로 기록
- **강력한 필터링**: 활동 유형, 사용자, 작업 ID, 생성일, 마감일 등 다양한 조건 검색

---

### 📊 대시보드 (Dashboard)
- **개인 통계**: 전체, 완료, 진행 중, 예정, 기한 초과 작업 수
- **작업 요약**: 오늘 할 일, 예정된 작업, 기한 초과 작업
- **팀 진행률**: 팀별 작업 완료율 시각화
- **주간 트렌드 분석**: 최근 7일간 작업 생성/완료 추이 분석

---

## 🛠️ 기술 스택 (Tech Stack)

| 구성 요소       | 기술 스택                    |
|----------------|------------------------------|
| Backend        | Java 17, Spring Boot 3.x     |
| Database       | MySQL 8.0                    |
| Data Access    | Spring Data JPA, Hibernate   |
| Authentication | Spring Security, JWT         |
| Build Tool     | Gradle                       |

---

## 🔗 API 명세서 (API Endpoints)

### Public API (인증 불필요)

| 기능   | HTTP Method | URL                  | 설명                   |
|--------|-------------|-----------------------|------------------------|
| Auth   | POST        | `/api/auth/register`  | 회원가입               |
|        | POST        | `/api/auth/login`     | 로그인 (JWT 토큰 발급) |
|        | POST        | `/api/auth/recover`   | 탈퇴한 계정 복구       |

---

### Private API (인증 필요 – `Authorization: Bearer {JWT}`)

#### 🔐 Auth

| HTTP Method | URL                  | 설명                 |
|-------------|-----------------------|----------------------|
| POST        | `/api/auth/withdraw`  | 회원 탈퇴 (Soft Delete) |

#### 📋 Task

| HTTP Method | URL                             | 설명                     |
|-------------|----------------------------------|--------------------------|
| POST        | `/api/tasks`                    | 새로운 작업 생성         |
| GET         | `/api/tasks`                    | 모든 작업 목록 조회      |
| GET         | `/api/tasks/{taskId}`           | 특정 작업 상세 조회      |
| PUT         | `/api/tasks/{taskId}`           | 작업 전체 정보 수정      |
| PATCH       | `/api/tasks/{taskId}/status`    | 작업 상태만 변경         |
| DELETE      | `/api/tasks/{taskId}`           | 작업 삭제                |

#### 💬 Comment

| HTTP Method | URL                                                       | 설명          |
|-------------|------------------------------------------------------------|---------------|
| POST        | `/api/tasks/{taskId}/comments`                             | 댓글 작성     |
| PUT         | `/api/tasks/{taskId}/comments/{commentId}`                | 댓글 수정     |
| DELETE      | `/api/tasks/{taskId}/comments/{commentId}`                | 댓글 삭제     |

#### 📈 Activity Log

| HTTP Method | URL                 | 설명                              |
|-------------|----------------------|-----------------------------------|
| GET         | `/api/activities`    | 활동 로그 조회 (다양한 필터 지원) |

#### 📊 Dashboard

| HTTP Method | URL                             | 설명                                 |
|-------------|----------------------------------|--------------------------------------|
| GET         | `/api/dashboard/stats`          | 개인 대시보드 통계 조회              |
| GET         | `/api/dashboard/my-tasks`       | 오늘/예정/기한 초과 작업 요약 조회   |
| GET         | `/api/dashboard/team-progress`  | 팀별 작업 진행률 조회                |
| GET         | `/api/dashboard/weekly-trend`   | 주간 작업 완료 추세 조회             |

---

## 🗄️ 데이터베이스 구조 (ERD)

주요 엔티티 간 관계:

- **Users ↔ Tasks (1:N)**  
  → 사용자는 여러 작업을 생성할 수 있습니다.

- **Tasks ↔ Comments (1:N)**  
  → 하나의 작업에는 여러 댓글이 달릴 수 있습니다.

- **Activities ↔ Users/Tasks (N:1)**  
  → 하나의 활동 로그는 사용자 및 작업과 연결됩니다.

- **TaskHistory ↔ Users/Tasks (N:1)**  
  → 대시보드의 주간 트렌드 분석을 위해 일자별 작업 생성/완료 기록을 추적합니다.

---

## 🚀 시작하기 (Getting Started)

### 1. 프로젝트 클론

```bash
git clone https://github.com/your-username/taskflow.git
```

###2. application.yml 설정

src/main/resources/application.yml 파일 수정:

```bash
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/${DB_NAME}
    username: ${USER_NAME}
    password: ${PASSWORD}
jwt:
  secret: ${JWT_SECRET}
```

### 3. 애플리케이션 실행
IntelliJ 등 IDE에서

TaskflowApplication.java 실행

또는 터미널에서
```bash
./gradlew bootRun
```
