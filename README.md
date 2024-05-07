# Minflearn
IT 직군 지식 공유 플랫폼

## 프로젝트 목적
- IT 직군 지식 공유 및 커뮤니티
- IT 직군 접근성 완화 및 인프라 구축

## 목차
1. 개발 환경
2. 설계
3. 구현 기능
4. 추가 계획

---

## 개발 환경
### 프로젝트 인원 : 1명
### 개발 기간 : 24.03.11 ~
### 기술 스택
- Language : Java 17
- Framework : Spring Boot 3.2.3 / Spring Security
- DB : H2 Database / Redis / Elasticsearch
- ORM : JPA(Hibernate)
- Library : QueryDsl

## 설계
#### ERD
![minflearn_erd](https://github.com/MoonInHo/Minflearn/assets/122209421/ff408707-c1b4-45c7-8b09-2f8cdbeca74d)


## 구현 기능
### 회원
- 회원가입 - 비밀번호 암호화
- 로그인 - JWT 방식 인증/인가
- 로그아웃

### 강좌
- 강좌 생성
- 강좌 목록 조회 (검색 가능)
- 강좌 상세 조회

### 섹션
- 섹션 추가

### 강의
- 강의 추가
- 강의 수정 - 강의 내용 추가 / 변경 및 강의 파일 key 연결

### 강의 파일
- 강의 동영상 업로드
  - 파일 검증 및 데이터 저장
  - chunk 분할 업로드
  - 업로드 실패 chunk 조회
  - 파일 병합 (이어쓰기 지원)

## 추가 계획
- 지식 공유 권한 관리 매커니즘 구현
- 강의 구매 api 구현
- 마이페이지 관련 테이블 추가 및 api 구현
- 강의 스트리밍 api 구현
- 테스트 코드 추가


