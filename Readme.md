# 예약 서비스 (백엔드위주)
- 예약 서비스는 카테고리 별로 관람을 예약을 도와주는 서비스입니다.   
- 총 4가치 카테고리(전시, 뮤지컬, 콘서트, 클래식, 연극)가 있습니다.  
- 개발은 다음과 같이 크게 5가지로 구현을 합니다.    


    1. 메인페이지
    2. 각 항목의 상세페이지
    3. 예약하기
    4. 나의 예매 내역 확인
    5. 한줄평등록

해당 프로젝트는 부스트코스 웹 강의에서 진행한 과제입니다.  
html, css는 부스트코스에서 제공되었습니다.

## 기술스택 
Spring Framework, MySql, Git  
Spring Boot 전환 

## ERD 
![connectdb](https://github.com/hj0328/Resorvation-System/assets/24749457/92c88c6a-63ee-4973-abf2-2febedff2480)


## 추가 기능 
위 예제만으로는 아쉬워 개인적으로 추가작업을 진행하고 있습니다.

### 도메인 추가 
1. 사용자 도메인을 추가하였습니다.
   - 사용자 도메인
     - 사용자 등급에 따라 BASIC, VIP, VVIP 적용. 사용자가 총 예약을 10회, 20회 했을 때 마다 등급이 올라갑니다.   
     - 혜택: 할인? 무료 쿠폰? 
   -

### 기능 적용
1. Spring Boot 적용
2. Junit5 유닛테스트 추가 (진행중)
3. api 예외처리 처리 로직 추가하여 500에러 400에러로 변경하여 리턴

유닛 테스트 추가 (진행중)  
JPA 적용(진행중)

 

