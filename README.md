# 📦 SEESAW CORE

> **SEESAW 프로젝트의 근간이 되는 비즈니스 도메인 및 공통 기반 모듈**

SEESAW CORE는 `seesaw-api`와 `seesaw-web` 등 다른 실행 모듈에서 공통으로 참조하는 핵심 라이브러리 모듈입니다. 도메인 엔티티, 영속성 계층, 그리고 프로젝트 전반에서 사용되는 공통 유틸리티와 핵심 추상화 계층을 정의합니다.

---

## ✨ Key Components

### 1. Domain Modeling
- **JPA Entity**: `tb_` 접두사를 사용하는 표준 데이터베이스 테이블 매핑
- **BaseEntity**: 모든 엔티티의 공통 속성(ID, 생성/수정 정보, 낙관적 락 등) 정의
- **VO (Value Object)**: 도메인의 풍부한 표현을 위한 값 객체 및 Enum 관리

### 2. Foundational Infrastructure
- **File Management**: 파일 업로드/다운로드 및 저장소 추상화 (`FileManager`)
- **Hierarchy Management**: 카테고리나 댓글 등 트리 구조 데이터를 처리하기 위한 `Hierarchical` 인터페이스 및 로직
- **Validation**: 비즈니스 규칙 검증을 위한 커스텀 어노테이션 및 검증기

### 3. Model & Command
- **Immutable Models**: API 및 View 응답을 위한 불변 데이터 객체 (DTO)
- **Command Objects**: 데이터 생성 및 수정을 위한 입력 전용 객체
- **Model Mapping**: 엔티티에서 모델로의 변환 로직 내재화

### 4. Core Utilities
- **Excel Library**: 데이터 엑셀 추출 및 처리를 위한 유틸리티
- **HTML Sanitization**: Jsoup 기반의 본문 정제 및 보안 처리
- **Common Support**: 프로젝트 전반의 설정(`Config`), 메시지 처리(`Message`), 이벤트 리스닝(`Event`) 기반 구조

---

## 🛠 Tech Stack

- **Framework**: Spring Context, Spring Data JPA
- **Language**: Java 17
- **Persistence**: Hibernate, QueryDSL
- **Utilities**: Jsoup, Apache POI (Excel), Lombok

---

## 🏗 Modular Role

SEESAW 프로젝트의 계층형 구조에서 CORE 모듈은 최하단에 위치하며, 다음과 같은 역할을 수행합니다.

- **데이터 명세의 단일화**: 모든 실행 모듈이 동일한 도메인 모델과 데이터 포맷을 공유
- **공통 로직의 중앙화**: 파일 처리, 보안 정제 등 반복되는 핵심 로직의 중복 제거
- **추상화 제공**: 구현 모듈(`api`, `web`)이 구체적인 기술 스택에 매몰되지 않도록 비즈니스 중심의 인터페이스 제공

---

## 📂 Project Structure
```text
seesaw-core/src/main/java/kr/me/seesaw/
├── domain/       # JPA 엔티티 및 VO
├── repository/   # JPA/QueryDSL 리포지토리 인터페이스 및 구현체
├── model/        # 출력용 불변 모델 (Model)
├── command/      # 입력용 커맨드 객체 (Command)
├── context/      # 비즈니스 컨텍스트 인터페이스
├── core/         # 파일, 계층구조, 엑셀, 검증 등 핵심 유틸리티
└── config/       # 공통 빈 설정 및 프로퍼티
```

---

## 🔗 Related Modules
- [seesaw-api](../seesaw-api/README.md): REST API 제공 모듈
- [seesaw-web](../seesaw-web/README.md): MVC 기반 웹 서비스 모듈
