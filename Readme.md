# 예약 서비스 (백엔드)
- 예약 서비스는 콘서트와 같은 관람상품 예약을 도와주는 사이드 프로젝트입

## 기술스택 
- Java 8
- Spring Boot 2.7.15
- JPA
- Spring Data JPA
- MySql 8

<!-- 
## 개발 차수
- 1차
    - 예약 서비스 기능에 집중 
    - 데이터베이스 이상문제로 재설계 필요
    - Spring Framework, JdbcTemplate 기반
- 2차 
    - 데이터의 이상문제 해결과 확장성을 고려하여 데이터베이스 재설계 
    - 재설계 후 변화 
        - 갱신 이상  
        AS-IS: 장소 정보 수정 시 중복 데이터로 인해 데이터 모순이 발생   
        TO-BE: 장소 정보를 한 튜플만 수정하면 모순이 없도록 설계 수정
        - 삭제 이상  
        
        TO-BE: 장소 정보를 한 튜플만 수정하면 완료하도록 설계
    - JPA를 적용하면서 더 객체지향스럽게 전체적으로 수정 
    - Spring Boot, JPA 기반 
-->

### 개발 기간 
- 2024.02.19 ~ 2024.03.02 설계
- 2024.03.03 ~ 2024.03.13 구현


## 주요기능 

## ERD 
<img width="100%" alt="image" src="https://github.com/hj0328/Reservation-System/assets/24749457/9786e870-79dc-4f4a-b0c0-540bce13f24e">

### 테이블 
1. category
    - 제품의 카테고리(클래식, 뮤지컬, 전시 등)
2. product
    - 제품 소개 및 정보
3. product_price
    - 제품의 좌석별 가격
4. product_seat_schedule
    - 제품 좌석별 예약 시간과 예약 수량
    - (Ex. 뮤지컬 P1 A좌석의 관람 시간, 예약 수량  
    - 뮤지컬 P1 VIP좌석의 관람시간, 예약 수량 등등)
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

- Product을 매번 예매 순서에 따라 정렬된 상태로 조회하는 것은 DB에 부담을 주게 된다. 
- 따라서 스케줄러를 이용해 일정 주기에 따라 실시간 인기 product를 캐싱한다.  
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

<!-- 
### 2. 동시성 문제 해결 - Pessimisitc Lock
- 다수의 사용자가 동시에 상품 예약 시 좌석 수가 제대로 계산되지 않는 현상 해결 -->

                                  
