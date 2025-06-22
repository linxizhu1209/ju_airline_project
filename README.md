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
- 유저별 마지막 읽은 메시지까지 자동 스크롤 이동 ([UX개선](https://velog.io/@juju129/flutter-%EC%B1%84%ED%8C%85%EB%B0%A9-%EC%9E%85%EC%9E%A5%EC%8B%9C-%EB%A7%88%EC%A7%80%EB%A7%89-%EC%9D%BD%EC%9D%80-%EB%A9%94%EC%8B%9C%EC%A7%80%EB%A5%BC-%EA%B8%B0%EC%A4%80%EC%9C%BC%EB%A1%9C-%EC%9E%90%EB%8F%99-%EC%8A%A4%ED%81%AC%EB%A1%A4-%EC%9D%B4%EB%8F%99-%ED%95%98%EA%B8%B0-scrollablepositionedlist))

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

## 📈성능 최적화 및 UX 개선
- Redis 임시 저장소 활용 : 실시간 조회 필요한 메시지의 읽지않은사람수와 유저의 마지막읽은메시지를 Redis에 임시 저장 및 조회 시 사용, 스케쥴러로 DB와 주기적 동기화
- RabbitMQ로 서버확장성 고려 : 채팅서버 확장성을 고려하여 중앙에서 채팅메시지를 브로드캐스트/전달할 수 있는 RabbitMQ 추가 [더보기](https://velog.io/@juju129/rabbitMq-%EC%84%9C%EB%B2%84-%ED%99%95%EC%9E%A5%EC%84%B1%EC%9D%84-%EA%B3%A0%EB%A0%A4%ED%95%B4-rabbitMq-%EB%8F%84%EC%9E%85%ED%95%B4%EB%B3%B4%EA%B8%B0)
- 자동스크롤이동 도입으로 UX 개선 : 유저 채팅방입장 시 마지막읽은메시지로 자동스크롤 이동 구현하여 UX 개선 [더보기](https://velog.io/@juju129/flutter-%EC%B1%84%ED%8C%85%EB%B0%A9-%EC%9E%85%EC%9E%A5%EC%8B%9C-%EB%A7%88%EC%A7%80%EB%A7%89-%EC%9D%BD%EC%9D%80-%EB%A9%94%EC%8B%9C%EC%A7%80%EB%A5%BC-%EA%B8%B0%EC%A4%80%EC%9C%BC%EB%A1%9C-%EC%9E%90%EB%8F%99-%EC%8A%A4%ED%81%AC%EB%A1%A4-%EC%9D%B4%EB%8F%99-%ED%95%98%EA%B8%B0-scrollablepositionedlist)

---

## 💡트러블 슈팅 (Flutter 관련)

- AGP 업그레이드에 따른 이전 버전 패키지 빌드 실패 문제 : 라이브러리 패키지를 로컬로 복사해오는 방법으로 이슈 해결 [더보기](https://velog.io/@juju129/flutter-Namespace-not-specified.-Specify-a-namespace-in-the-modules-build-file.-%EC%97%90%EB%9F%AC-%ED%95%B4%EA%B2%B0-%EB%B0%A9%EB%B2%95)
- 앱 릴리즈 빌드 시 Missing classes R8 오류 문제 : 코드최적화 및 난독화 역할을 하는 R8을 사용하면서 이슈해결할 수 있는 방법 발견 [더보기](https://velog.io/@juju129/flutter-ERROR-Missing-classes-detected-while-running-R8.-%EC%98%A4%EB%A5%98-%ED%95%B4%EA%B2%B0-R8%EC%9D%B4-%EB%AD%94%EB%8D%B0) 
## 📷 주요 화면

[테스트영상-유튜브링크](https://youtube.com/shorts/1v2ZCgy9umw?feature=share)

![채팅테스트GIF](https://github.com/linxizhu1209/ju_airline_project/blob/master/test.gif?raw=true)


---



## 🙋‍♀️ 만든 사람

- 임희주
- dlahj1209@naver.com
- [개발블로그1](https://velog.io/@juju129/posts)
- [개발블로그2](https://blog.naver.com/dlahj1209)