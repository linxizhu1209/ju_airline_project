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
- **1:1 고객센터 채팅** 
- **오픈 채팅방 기능** (stomp 기반 다대다 메시지브로드캐스트, [서버확장성 고려한 RabbitMQ 사용](https://velog.io/@juju129/rabbitMq-%EC%84%9C%EB%B2%84-%ED%99%95%EC%9E%A5%EC%84%B1%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%B4-rabbitMq-%EB%8F%84%EC%9E%85%ED%95%B4%EB%B3%B4%EA%B8%B0))
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
- [개발블로그1](https://velog.io/@juju129/posts)
- [개발블로그2](https://blog.naver.com/dlahj1209)