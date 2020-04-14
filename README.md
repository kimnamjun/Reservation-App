# QR 코드를 이용한 예약 앱


## < 개요 >

- QR 코드를 촬영하여 식당을 예약할 수 있는 안드로이드 모바일 앱
- 이메일로 로그인, 웹 뷰 지원, 알림 및 주문 기능, 이니시스 결제

![시작 화면](C:/Users/user/Desktop/메인.jpg)
![예약 시스템](C:/Users/user/Desktop/예약.jpg)
![알림 서비스](C:/Users/user/Desktop/알림.jpg)
![주문하기](C:/Users/user/Desktop/주문.jpg)
![결제하기](C:/Users/user/Desktop/결제.jpg)


< 애플리케이션 구성 >

> Main
>> Splash
>> Login
>>> Sign Up
>>> Admin ~~(삭제)~~
> QR Scan
> Waiting, Web View
> Ordering
> Payment


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
