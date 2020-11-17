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
