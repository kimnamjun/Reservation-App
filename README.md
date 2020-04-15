# QR 코드를 이용한 예약 앱


## < 개요 >

- QR 코드를 촬영하여 식당을 예약할 수 있는 안드로이드 모바일 앱
- 이메일로 로그인, 웹 뷰 지원, 알림 및 주문 기능, 이니시스 결제

![시작 화면](https://user-images.githubusercontent.com/26537112/79399585-b840f500-7fbe-11ea-8c7a-08148ffc0c11.jpg)

![예약 시스템](https://user-images.githubusercontent.com/26537112/79399587-baa34f00-7fbe-11ea-9867-dbae0abd43ea.jpg)

![알림 서비스](https://user-images.githubusercontent.com/26537112/79399586-b9722200-7fbe-11ea-8c72-998990b71349.jpg)

![주문하기](https://user-images.githubusercontent.com/26537112/79399590-bc6d1280-7fbe-11ea-88ea-8944c3644dfe.jpg)

![결제하기](https://user-images.githubusercontent.com/26537112/79399580-b5de9b00-7fbe-11ea-91a1-aa397b9022cd.jpg)



< 애플리케이션 구성 >

- Main
    - Splash
    - Login
        - Sign Up
        - Admin ~~(삭제)~~
- QR Scan
- Waiting, Web View
- Ordering
- Payment


< 사용 기술 >

- Firebase : Google
    - Auth
    - Realtime DataBase (NoSQL)
    - Cloud Message
- Android
    - UI/UX & Layout Design
    - List Adapter Pattern
    - Notification (Background Service)
- QR code : ZXing
- Payment API : KG 이니시스
