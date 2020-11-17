# CKI-Pass (코로나 전자출입명부)

해당 프로젝트는 졸업잘품으로 개발한 프로그램입니다.

"CKI-Pass"란 "Corona"에 기존 전자출입명부인 "KI-Pass"를 합쳐서 만든 용어입니다. 

주요 기능
- 로그인 기능 : 서버에 저장된 아이디와 비밀번호와 일치된 계정을 로그인
- 회원가입 기능 : 사용자의 아이디, 비밀번호, 이름 등을 입력받아 서버에 저장
- 아이디 및 비밀번호 찾기 기능 : 사용자가 아이디 혹은 비밀번호 분실 시 찾기 가능
- 인증기능
  1. 디바이스의 카메라를 실행하고 QR코드를 스캔하여 해당 위치와 시간을 서버에 저장
  2. 디바이스의 카메라를 실행하고 얼굴을 인식
- QR코드 생성 기능 : 디바이스의 현재 좌표와 매장명을 QR코드로 생성
- 주변 확진자 확인 기능 : Google Map상에서 확진자가 다녀간 장소와 시간을 확인
- 코로나 현황 확인 기능 : 일일 코로나 현황을 API를 통해 지역별, 날짜별 코로나 현황 확인
- 설정 기능 : 사용자의 계정을 수정하거나 탈퇴 등 가능

# Development environment
--------------
OS : Windows 10 64bit & Unbuntu 18.04.5 LTS

Server : Cafe24

IDE : Android Studio 4.1

Language : Java, PHP 7.4

DB : Maria DB 10.2 

DB Tool : HeidiSQL

DB Table

1. member
![member](https://user-images.githubusercontent.com/52353492/99325092-dfdf9f00-28b8-11eb-8c6a-e90aea3097e4.PNG)

2. location
![location](https://user-images.githubusercontent.com/52353492/99325098-e2da8f80-28b8-11eb-9978-26ae0b4767e3.PNG)

Etc : Putty


# Screenshots 
--------------
![noname01](https://user-images.githubusercontent.com/52353492/99207046-4eacf180-2800-11eb-945c-caaaca9e2b31.jpg)
![noname02](https://user-images.githubusercontent.com/52353492/99207047-4f458800-2800-11eb-8869-6a21ae30533f.jpg)
![AnyConv com__로그인문제](https://user-images.githubusercontent.com/52353492/99326018-ce979200-28ba-11eb-9f04-048b252c0a35.jpg)

![AnyConv com__메인](https://user-images.githubusercontent.com/52353492/99326026-d0f9ec00-28ba-11eb-9a75-06256b5dd4cf.jpg)
![noname03](https://user-images.githubusercontent.com/52353492/99207050-4fde1e80-2800-11eb-85d8-6d95ae279079.jpg)
![noname04](https://user-images.githubusercontent.com/52353492/99207051-5076b500-2800-11eb-998a-6c0e30db5e24.jpg)

![AnyConv com__qr](https://user-images.githubusercontent.com/52353492/99326031-d22b1900-28ba-11eb-9dfc-de17d9324f5d.jpg)
![noname05](https://user-images.githubusercontent.com/52353492/99207052-5076b500-2800-11eb-8ed9-932ab2aa61bb.jpg)
![noname06](https://user-images.githubusercontent.com/52353492/99207054-510f4b80-2800-11eb-9641-f6418abafa47.jpg)

![AnyConv com__마커클릭](https://user-images.githubusercontent.com/52353492/99326021-cfc8bf00-28ba-11eb-821c-83e345f234a9.jpg)
![noname07](https://user-images.githubusercontent.com/52353492/99207055-510f4b80-2800-11eb-8bbd-8936076362b2.jpg)
![AnyConv com__설정](https://user-images.githubusercontent.com/52353492/99326020-cf302880-28ba-11eb-9ff0-7b2e26300376.jpg)

![AnyConv com__관리자](https://user-images.githubusercontent.com/52353492/99326028-d1928280-28ba-11eb-8a73-05eaadf75a94.jpg)
![AnyConv com__qr만들기](https://user-images.githubusercontent.com/52353492/99326029-d1928280-28ba-11eb-91a3-c9a306c9bcf5.jpg)
![noname08](https://user-images.githubusercontent.com/52353492/99207056-51a7e200-2800-11eb-9b2b-15c377c212aa.jpg)
