# Calenkit

## Project Summary
- 일정을 등록하고 다른 사람들과 공유할 수 있는 실시간 일정 공유 웹 애플리케이션입니다.
- 로그인 프로세스 구현과 메일 전송 기능, 이미지 업로드 기능을 구현하였으며, 실시간 일정 공유 기능 구현에 일부 기여하였습니다.
- Spring Boot를 사용해 본 첫 프로젝트이며, OAuth2.0이라는 생소한 기술을 사용해볼 수 있었습니다.
- Google Slides, Notion 등을 이용하여 팀원들과 함께 구체적인 설계를 진행해볼 수 있었으며 브랜치 전략을 수립하고 커밋 규칙을 정하는 등의 팀 프로젝트 경험을 해볼 수 있었습니다.

## 👨‍👨‍👧‍👦Team Member
- 김민재 : 일정 캘린더 출력, 일정 추가 삭제 및 수정, 휴지통, 즐겨찾기, 검색, 무한 스크롤 기능
- 라재희 : 일정 메인 및 상세 레이아웃, 일정 초대 및 공유, 일정 내용 변경 실시간 반영, 푸시 알림
- 이진규 : 회원 개인정보 관리(마이페이지), 회원 관리(관리자페이지)
- 오세욱 : 카카오 소셜로그인, 회원가입, 이메일 전송 기능, 이미지 저장 기능

## 📙WIKI
- calenkit의 부가적 설명은 WIKI를 이용했습니다.
- [calenkit WIKI](https://github.com/effourt/calenkit/wiki) 내용을 참고해주세요.

## 🛠️Skill
<p>
  <img alt="html5" src="https://img.shields.io/badge/-HTML5-E34F26?style=flat-square&logo=html5&logoColor=white" />
  <img alt="css3" src="https://img.shields.io/badge/css3-1572B6?style=flat&logo=css3&logoColor=white"/>
  <img alt="bootstrap" src="https://img.shields.io/badge/-bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white" />
  <img alt="javascript" src="https://img.shields.io/badge/Javascript-F7DF1E?style=flat&logo=javascript&logoColor=white"/>
  <img alt="jQuery" src="https://img.shields.io/badge/jQuery-0769AD?style=flat&logo=jQuery&logoColor=white"/>
  <img alt="thymeleaf" src="https://img.shields.io/badge/thymeleaf-005F0F?style=flat&logo=thymeleaf&logoColor=white"/>
</p>
<p> 
  <img alt="java" src="https://img.shields.io/badge/-java-3A75AF?style=flat-square&logo=java&logoColor=white" />
  <img alt="spring" src="https://img.shields.io/badge/-spring-6DB33F?style=flat-square&logo=spring&logoColor=white" />
  <img alt="springboot" src="https://img.shields.io/badge/-springboot-6DB33F?style=flat-square&logo=springboot&logoColor=white" />
  <img alt="gradle" src="https://img.shields.io/badge/-gradle-02303A?style=flat-square&logo=gradle&logoColor=white" />
</p>
<p>
  <img alt="mysql" src="https://img.shields.io/badge/-mysql-4479A1?style=flat-square&logo=mysql&logoColor=white" />
  <img alt="mybatis3" src="https://img.shields.io/badge/-mybatis3-211A1A?style=flat-square&logo=mybatis3&logoColor=white" />
</p>
<p>
  <img alt="apacheTomcat" src="https://img.shields.io/badge/-apacheTomcat-F8DC75?style=flat-square&logo=apacheTomcat&logoColor=white" />
  <img alt="github" src="https://img.shields.io/badge/-github-181717?style=flat-square&logo=github&logoColor=white" />
</p>

## 🛠️Team Collaboration Tool
- Github
- Notion
- Google Slide
- diagrams.net
- ERD Cloud
- Discord
- Code with Me (IntelliJ)

## ✔Git Branch Policy
![image](https://github.com/effourt/calenkit/assets/107570140/7b22ea6d-92df-4ee8-9b50-ebb5738cf13a)
- **Github-Flow** 전략과 유사함
- 작업은 user-branch에서만 이루어지며 develop, main 브랜치로 직접 접근 불가능
- 새로운 브랜치는 develop을 기준으로 생성하여 작업 진행
- 브랜치는 로컬에 commit하고 정기적으로 user-branch로 push
- 정해진 회의시간에 코드리뷰를 진행하고 pull-request를 생성해 develop 브랜치로 병합
- 병합은 rebase-merge로, 충돌이 있을 경우 merge로 진행
- develop 브랜치에 병합 후 user-branch로 pull 받아 기능 테스트

## ✔Git Commit Message Rule
- **[FEAT]** 문서 내 기능 추가, 변경, 수정이 발생한 경우
- **[DESIGN]** 문서 내 코드 수정(CSS) 발생한 경우 (기능 영향 있음)
- **[FIX]** 문서 내 코드 버그 수정 발생한 경우
- **[DOCS]** 문서의 추가, 삭제, 변경
- **[STYLE]** 코드 가독성을 위한 수정(주석, 들여쓰기 등) 발생한 경우 (기능 영향 없음)
- **[REFACTOR]** 효율 증가를 위한 수정 (기능 영향 없음)
- **[TEST]** 테스트 코드 추가 삭제 변경 - 테스트 코드 관련 모든 변경사항들
- **[ETC]** 기타 애매한 경우

## ✔ERD Diagram
![ERD](https://github.com/BeefCutlet/calenkit/assets/77325024/2547d1cf-8841-4506-a98b-832f87be8a6b)


## 담당 파트

### Interceptor를 이용한 인증 프로세스 구현
- 특정 요청을 제외한 대부분의 요청들은 로그인을 한 뒤에 이용할 수 있고, 관리자 계정과 일반 회원 계정은 서로 다른 페이지에만 접근 가능하도록 설계하였습니다. 그래서 매 요청마다 로그인이 되어있는지, 권한이 있는지를 체크할 필요가 있었습니다.
- 요청마다 로그인 상태 및 권한을 자동으로 검사하기 위해서 Spring Interceptor를 활용하여 인증이 필요한 요청이 들어왔을 때, 요청을 가로채서 인증 및 인가를 처리하는 프로세스를 만들었습니다.
- 일반 유저와 관리자 권한을 구별하여야 했기 때문에 인터셉터를 분리하여 권한마다 다른 인터셉터가 동작하도록 하였습니다.


### 메일 전송 기능 구현
- 로그인 시에 일부 로그인 형태(로그인 코드를 이용하여 로그인, 회원가입 후 로그인)에서는 로그인 또는 회원가입을 위한 코드를 이메일로 전송하여 인증을 하도록 하였습니다.
- 이메일 전송을 처리하기 위한 방법으로 JavaMailSender 라이브러리와 Google SMTP를 이용하여 로그인을 시도할 때 비밀번호 대신 입력할 로그인 코드를 메일로 전송하는 기능을 구현하였습니다.
- 이메일 전송은 다른 로직에 비해 속도가 현저히 느렸기 때문에 로그인 코드를 발급하는 코드와 같이 요청 처리를 하게 되면, 이메일 전송이 완료되기까지 응답이 지연되는 문제가 생겼습니다.
- 이를 해결하기 위하여 클라이언트에서 로그인 코드를 요청할 때, 이메일 전송 요청을 비동기식으로 전송하도록 구현하여 메일 전송으로 인해 3초까지 지연된 응답이 1초 내로 응답될 수 있도록 하였습니다.


### 이미지 저장 기능 구현
- 회원가입 시, 혹은 프로필 수정 시에 프로필 이미지를 설정하고 저장하는 기능을 구현하였습니다.
- 그러나 배포한 뒤에는 로컬에 이미지를 계속 저장하다보면 서버의 저장공간 부족해질 가능성이 있었고, 관리도 불편하였습니다. 그래서 외부 저장소에 이미지를 저장하고 웹페이지를 렌더링할 때 불러올 필요가 있었습니다.
- 해결 방법으로 외부 저장소인 Amazon S3에 이미지를 저장하고 해당 이미지 경로에서 이미지를 찾아서 보여줄 수 있도록 구현하였습니다.


### 카카오 소셜 로그인 기능 구현을 구현
- 카카오 소셜 로그인 기능을 구현하기 위해서는 카카오 인증 서버와 통신하여 AccessKey를 받고, 카카오 리소스 서버와 통신하여 사용자의 정보를 받아올 수 있어야 합니다.
- 기존 서버의 API로는 외부와 통신이 불가능하였고, 서버 외부의 API와 통신할 수 있는 방법을 찾아야했습니다.
- OpenFeign은 인터페이스를 만들기만 하면 외부의 API를 쉽게 호출할 수 있다는 장점이 있었습니다.
- OpenFeign을 사용하여 Kakao 인증 서버 및 리소스 서버 API를 호출하여 회원의 정보를 받아오고, 해당 정보를 바탕으로 세션 설정 및 회원가입을 진행하도록 구현할 수 있었습니다.
- 다만, OpenFeign을 사용하여 외부 API를 호출하면, API 스펙을 직접 구현해야 했기에 인증 서버와 리소스 서버 API 호출용 클래스와 DTO까지 총 6개의 클래스가 필요했습니다. 이로 인해, 관리해야 할 클래스가 많아서 로직 파악이 복잡해졌습니다.
- 문제를 해결하고자 OAuth2.0 인증을 보다 쉽게 할 수 있는 방법을 찾아보았고, OAuth Client 라이브러리를 이용하면 OAuth2.0 인증을 위한 클래스를 제공받아 서비스 클래스 하나로 인증을 할 수 있다는 사실을 알게되었습니다. OAuth Client를 이용한 인증으로 리팩토링 함으로써 인증을 위한 6개의 클래스가 1개의 클래스로 줄어들어 유지보수의 효율성을 높일 수 있었습니다.


## 🗓️구현

### ✅ 로그인
![image](https://github.com/effourt/calenkit/assets/107570140/730a6def-88b8-479b-8975-66f8dbbe75d0)
- 카카오 소셜 로그인
![image](https://github.com/effourt/calenkit/assets/107570140/3fba3d1e-3bea-4f09-b474-011fd749614e)
- 회원 정보가 존재하고 비밀번호가 존재할 경우, 비밀번호로 로그인
![image](https://github.com/effourt/calenkit/assets/107570140/ecaf251a-888e-4d01-a76b-8b3de6cb0e9c)
- 회원 정보가 존재하고 비밀번호가 존재하지 않을 경우, 로그인 코드로 로그인
    - 로그인 코드는 입력한 이메일로 전송
![image](https://github.com/effourt/calenkit/assets/107570140/13a3e22d-f3df-4c36-bede-3d56b1876c12)
- 회원 정보가 존재하지 않을 경우, 회원가입 코드로 로그인
    - 회원가입 코드는 입력한 이메일로 전송
![image](https://github.com/effourt/calenkit/assets/107570140/6909ec90-cf6b-41ac-bd08-1d38261831c7)
![image](https://github.com/effourt/calenkit/assets/107570140/7bc2e19f-7177-42a1-a1c3-b721cfa4a1e5)
### ✅ 회원가입
- 프로필 사진, 닉네임, 비밀번호 설정 가능
![image](https://github.com/effourt/calenkit/assets/107570140/e4de6081-5200-4f98-9786-e7f911e34225)
### ✅ 메인페이지 (🚨로그인 회원만 접속 가능)
![image](https://github.com/effourt/calenkit/assets/107570140/e5dc31b4-9cf2-4e6e-8078-03e7ff70c5a6)
- 검색 기능 - 일정 검색(필터링 - 제목, 내용)
![image](https://github.com/effourt/calenkit/assets/107570140/94cfd757-6b68-4451-b84a-9f4fd6a5d6e4)
![image](https://github.com/effourt/calenkit/assets/107570140/02756530-b4b3-490f-b71f-219352cfa42f)
- 검색 기능 - 휴지통 검색
![image](https://github.com/effourt/calenkit/assets/107570140/3a5ce77d-49d5-49bb-8206-be8b83e2ee71)


### ✅ 마이페이지 (🚨로그인 회원만 접속 가능)
![image](https://github.com/effourt/calenkit/assets/107570140/69d3e6e1-4551-477c-bd2d-f20107029498)
- 비밀번호 변경
![image](https://github.com/effourt/calenkit/assets/107570140/cc9c3e32-202f-4a7b-b4e4-563bda38b19b)
### ✅ 일정 상세페이지 (🚨로그인 회원만 접속 가능)
- 휴지통 이동 / 메인가기 / 일정 공유 / 즐겨찾기
![image](https://github.com/effourt/calenkit/assets/107570140/57f7fa98-3c39-404f-8df7-2775d821dc80)
- 제목, 시작 날짜 및 종료 날짜 변경
![image](https://github.com/effourt/calenkit/assets/107570140/edfe445d-dcd7-4af5-ace8-7a12007f9430)
- 진행 상태 변경
![image](https://github.com/effourt/calenkit/assets/107570140/a903967e-4970-41aa-8da3-f828dab53b15)
- 내용 작성 및 변경 (실시간 공유)
![image](https://github.com/effourt/calenkit/assets/107570140/51eb67b6-849f-48fe-b774-b1616f4f2044)
- 일정 초대 (메일 발송 및 푸시 알림)
![image](https://github.com/effourt/calenkit/assets/107570140/45e3652c-fd46-4720-918f-80f62d5caed2)
![image](https://github.com/effourt/calenkit/assets/107570140/a950d7c2-6cd6-45aa-afda-090b67ba59c6)
![image](https://github.com/effourt/calenkit/assets/107570140/a58a9a4a-b5ce-4204-b7fb-4a1c272b5a16)
- 일정 참가
![image](https://github.com/effourt/calenkit/assets/107570140/9535d6dc-8516-4b5c-9830-b4ca749b50fd)
![image](https://github.com/effourt/calenkit/assets/107570140/3754fcfd-6a18-4c55-969a-1b7854cf0efb)
![image](https://github.com/effourt/calenkit/assets/107570140/6bd78552-affc-44be-b94c-b38881a1f2b5)
- 권한 변경(푸시 알림)
![image](https://github.com/effourt/calenkit/assets/107570140/ab9c8a84-13c1-4f29-9c82-d6ac97a94a0e)

### ✅ 관리자페이지 (🚨관리자만 접속 가능)
- 회원 상태 변경
    - 탈퇴회원, 일반회원, 휴면회원, 관리자로 회원 상태 변경 가능
![image](https://github.com/effourt/calenkit/assets/107570140/ce8345fc-f5b2-4d0a-9876-e76b6c11b23d)
- 회원 검색
    - ID의 앞자리가 일치하는 회원 검색
![image](https://github.com/effourt/calenkit/assets/107570140/717e1dca-747b-4a55-801f-b7beb9d7491e)
- 회원 정보 삭제
    - [선택 회원 삭제] 버튼 클릭 시 DB에서 회원 정보 완전 삭제
![image](https://github.com/effourt/calenkit/assets/107570140/6f0f32e5-ee54-4bef-ac17-d72100e1cae9)


