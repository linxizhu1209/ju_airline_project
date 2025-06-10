# ✈️ Ju's Airline Project

> **2025.1 ~ 개발중**  
> 항공권 조회/예약/결제가 가능한 **항공사 전용 앱**  
> Flutter + Spring 기반의 모바일 & 백엔드 통합 프로젝트

---

## 📱 주요 기능

### 🧭 항공권 기능
- 항공편 조회 (출발지/도착지/날짜 기준)
- 항공권 예약 및 결제
- 예약 내역 확인 및 취소
- **QR코드 생성/조회** (탑승권 확인 용도)

### 💬 실시간 채팅
- **1:1 고객센터 채팅** (RabbitMQ 기반)
- **오픈 채팅방 기능** (복수 사용자 간 대화)
- 실시간 메시지 수신/읽음 여부 처리
- 관리자 채팅방 목록/상세 분리 UI

### 📤 공유 기능
- **이미지 공유하기** 기능 (항공권 캡처 및 SNS 공유 등)
- Flutter 기반 이미지 처리 및 시스템 공유 연동

### 🔐 사용자 인증
- **OAuth2 기반 소셜 로그인** (카카오/구글)
- **Spring Security 기반 사용자 권한 관리**
- 유저/관리자 역할 구분 및 접근 제어

### 💳 결제 시스템
- **Flutter 내에서 토스페이먼츠(TossPayments) API 연동**
- Flutter WebView 결제 흐름 구현
- 결제 완료 후 상태 업데이트 및 알림 처리

---

## ⚙️ 기술 스택

| 영역 | 기술 |
|------|------|
| **Frontend** | Flutter, Dart |
| **Backend** | Spring Boot, Spring Security, Spring WebSocket |
| **Database** | MongoDB, MySQL |
| **Auth** | OAuth2, JWT |
| **Messaging** | RabbitMQ, STOMP |
| **Payment** | **TossPayments (Flutter 연동)** |
| **Deploy/Infra** | Docker |

---

## 📷 주요 화면

todo

---



## 🙋‍♀️ 만든 사람

- Lim Heeju
- 📧 dlahj1209@naver.com