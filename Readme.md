# 예약 서비스 (백엔드)
- 예약 서비스는 콘서트와 같은 관람상품 예약을 도와주는 사이드 프로젝트

### 1차 개발
- SKLL | Java, Spring Framework, JdbcTemplate, MySQL
- 관람 상품 예약 프로젝트 개발
- Spring Framework 4.3 → Spring Boot 2.7 전환

### 2차 개발
- SKLL | Java, Spring Boot, JPA, MySQL
- JdbcTempate에서 JPA로 변경
- 이상 현상 방지를 위해 ERD 재설계
    - 관람 좌석 등급 기능을 추가하며 관람 장소 좌석 테이블의 N:M 관계를 1:N 관계를 분리
    - ‘제품 예약 인원 수’ 속성을 추가하여 예약 인원 수 조회마다 수행한 연산 작업 제거
- 인기 예약 상품에 로컬 캐시 적용을 통해 응답속도 85% 향상
    - 1만 건의 목 데이터 생성을 통해 테스트 진행
- 동시 예약이 몰릴 경우 발생할 수 있는 동시성 이슈 해결

<!--
### 개발 기간 
- 2024.02.19 ~ 2024.03.02 설계
- 2024.03.03 ~ 2024.03.13 구현
-->

## Postman API Docs

<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/a61f231c-c9ac-4c3d-b282-2dfb7ef329bf">

- https://documenter.getpostman.com/view/15521816/2sA35A6QEr

## ERD 
<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/9786e870-79dc-4f4a-b0c0-540bce13f24e">

### 테이블 
1. category
    - 제품의 카테고리(클래식, 뮤지컬, 전시 등)
2. product
    - 제품 소개 및 정보
3. product_price
    - 제품 (좌석별) 가격
        - 예) A 콘서트 R석 1만원 / VIP 석 2만원
4. product_seat_schedule
    - 제품의 예약 시간과 총 예약 수량
        - 예) A 콘서트 24.1.1일자 1시에 R석 총 10석 예약 
5. place
    - 관람 장소  
    - 관람 장소의 좌석 수, 좌석 종류, 주소
6. member
    - 사용자 정보 
7. reservation
    - 사용자 예약 정보 
8. reservation_price
    - 예약 시 가격과 좌석 종류 

## 최적화 

### 1-1. 로컬 캐싱

<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/aec7c472-522d-4b2e-9a3d-c223e94f1f2c">

- Product을 Group by를 통해 매번 예매 순서에 따라 정렬된 상태로 조회하는 것은 DB에 부담을 주게 된다. 
- 따라서 실시간 인기 product를 캐싱하여 DB 부하를 줄이고 응답을 빠르게 처리하도록 캐싱한다.
    - 사용자가 요청 시 DB를 거치지 않고 메모리에서 빠르게 조회가 가능하게 된다. 

### 1-2. 예약/예약 취소 시 로컬 캐시에 반영

<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/6f113e28-43e5-41d6-98cb-24549605ae45">


- 캐시는 정렬된 상태를 유지하고 있기 때문에 product의 예약/예약 취소 시 insertion sort로 재정렬하여 디비 조회 요청을 줄일 수 있다.

### 1-3. 응답 속도 비교

- 환경
    - product 데이터 10000건
    - product_seat_schedule 데이터 10000건 (예약 수 정보)

<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/560f0e81-2795-4c0f-872b-934bbb324774">

- 캐싱 적용 전 응답 속도: 약 90ms
---  

<img width="100%" alt="로컬 캐싱 적용 후" src="https://github.com/hj0328/Reservation-System/assets/24749457/bfb3275a-210f-494a-90da-2e077510a21b">

- 캐싱 적용 후 응답 속도: 약 14ms
- 응답 속도가 90ms에서 14ms로 빨라졌을 때 성능은 약 84.44% 향상
    - 기준: Improvement Percentage Formula
    - 성능 향상(%) = (이전 응답 시간 - 현재 응답 시간 / 이전 응답 시간)* 100  

### 1-4. 장단점  
  
- 장점 
    - 디비를 거치지 않아도되어 빠르게 조회가 가능하다. 
- 단점 
    - 다중 was 서버 구조가 된다면 예약/예약 취소 시 캐시에 반영이 어려워 캐시 서버를 두는 것같은 다른 방법이 필요하다. 

코드: https://github.com/hj0328/Reservation-System/commit/68cedcee0a95852618e0ad7fd01aeac8761de356


### 2. 동시성 문제 해결 - Pessimisitc Lock
- 예약 시스템 특성상 다수의 고객이 동시에 예약 요청을 할 수 있기 때문에 동시에 예약 요청이 들어와도 안전한 처리가 필요

- 테스트 결과 다수의 사용자가 동시에 상품 예약 시 총 예약 좌석 수가 제대로 계산되지 않는 현상 
 
<img width="100%" alt="스크린샷 2024-03-23 오후 7 46 51" src="https://github.com/hj0328/Reservation-System/assets/24749457/17332cd1-e2f1-4c05-9afc-43b9da68ee05">

- 예약 요청 로직은 위와 같다. 

<img width="100%" src="https://github.com/hj0328/Reservation-System/assets/24749457/ef0726fb-d5e7-446f-8a9d-13a0f084906e">

- 동시 요청이 들어왔을 때 4, 5번째 로직에서 동시성 문제 발생 
- 따라서 4번 조회 로직은 먼저 실행된 트랜잭션의 update 요청 이후에 수행되어야 한다. 

<img width="819" alt="스크린샷 2024-03-23 오후 7 35 56" src="https://github.com/hj0328/Reservation-System/assets/24749457/8edc1e61-e593-4134-9a2d-e3faaca7022a">

- 비관적 락(pessimistic lock)을 이용하여 DB 테이블에 락 거는 방법을 변경한다.
- 4번 로직에서 테이블 row를 조회할 때 쓰기와 같은 수준의 Exclusive Lock을 건다.
- 그러면 이전 트랜잭션의 update 요청이 끝나야 4번 로직에서 lock 을 얻어 작업을 수행할 수 있다.


### 2-1. 장단점  
  
- 장점 
    - 디비를 거치지 않아도되어 빠르게 조회가 가능하다. 
- 단점 
    - 다중 was 서버 구조가 된다면 예약/예약 취소 시 캐시에 반영이 어려워 캐시 서버를 두는 것같은 다른 방법이 필요하다. 
