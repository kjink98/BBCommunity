# BBCommunity
[개인프로젝트] 스프링으로 구현한 CRUD 게시판

<!-- contents -->
<details open="open">
  <summary>Contents</summary>
  <ol>
    <li>
      <a href="#개요">개요</a>
    </li>
    <li>
      <a href="#내용">내용</a>
    </li>
    <li><a href="#구현-기능">구현 기능</a>
      <ul>
        <li><a href="#main">메인 게시판</a></li>
        <li><a href="#register">회원가입</a></li>
        <li><a href="#login">로그인</a></li>
        <li><a href="#myInfo">내 정보</a></li>
        <li><a href="#admin">관리자 페이지</a></li>
        <li><a href="#all">전체 게시판</a></li>
        <li><a href="#free">자유 게시판</a></li>
        <li><a href="#notice">공지사항 게시판</a></li>
        <li><a href="#write">글쓰기</a></li>
        <li><a href="#detail">게시글 상세 조회</a></li>
      </ul>
    </li>
  </ol>
</details>

------------

# 📝개요

* 프로젝트 명 : BBCommunity

* 일정 : 2023년 12월 21일 ~ 2024년 01월 04일

* 개발 목적 : 야구 관련 정보를 공유하고 소통할 수 있는 커뮤니티 사이트 제작

* 개발 인원 : 1명
   
* 개발 환경
  - **O/S** : Windows 11
  - **Server** : Apache-tomcat-9.0
  - **IDE** : STS4
  - **Database** : MariaDB
  - **Programming Language** : JAVA
  - **Framework** : SpringBoot 3.1.5
  - **FrontEnd** : Bootstrap, jQuery
  - **Templete Engine** : Tymeleaf
  - **Version management** : Git
------------

# 📝내용

* 구현 기능
  - 커뮤니티 게시판 CRUD
  - 관리자 기능 (회원 관리)


* DB 설계<br>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/4472bfb3-1a0c-4014-9652-66a5733a2212)

------------

# 📝구현 기능

## 메인 페이지

 1. <h3 id="main">메인 페이지</h3>
 
![메인페이지](https://github.com/kjink98/BBCommunity/assets/113023365/8142a3c0-67e0-434b-a772-3aa3e516a7ab)


  **메인 페이지**
   
  * 구현 기능 설명
    - 페이징 처리
    - 게시글 목록 (글번호, 게시판종류, 제목, 작성자, 날짜, 조회수)
    - 각 게시판으로 이동
    - 회원가입, 로그인 페이지로 이동

------------


## 회원가입, 로그인

1. <h3 id="register">회원가입</h3>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/5964c0e7-d83f-4179-bc1a-489654993515)


**회원가입**

 * 구현 기능 설명
    - 회원 가입 페이지
    - 비밀번호 체크
    - 이메일 중복 검사

------------

2. <h3 id="login">로그인</h3>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/8c1e20b9-1f34-4ad3-8a29-34390780866b)

**로그인**

 * 구현 기능 설명
    - 로그인
    - 소셜 로그인

### 로그인 완료
![image](https://github.com/kjink98/BBCommunity/assets/113023365/d6fd6fd6-0cd1-41a0-880f-95eedd5c1a0d)

------------

3. <h3 id="myInfo">내 정보</h3>
## 일반 유저 권한
![image](https://github.com/kjink98/BBCommunity/assets/113023365/ae9cddd8-c125-46c0-843d-989bec9de71b)

## 관리자 권한
![image](https://github.com/kjink98/BBCommunity/assets/113023365/a3fad042-4589-4e57-8663-97e440776c8f)


**내 정보**

 * 구현 기능 설명
    - 내 정보 확인(이름, 이메일, 성별, 별명, 가입일, 역할)
    - 비밀번호 변경, 내 정보 변경, 탈퇴하기
    - 관리자 페이지 이동 가능
------------

4. <h3 id="admin">관리자 페이지</h3>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/8c1e20b9-1f34-4ad3-8a29-34390780866b)

**관리자 페이지**

 * 구현 기능 설명
    - 역할 수정
    - 회원 탈퇴
    - 사용자 확인(아이디, 이메일, 이름, 닉네임, 성별, 등록일, 권한)
------------
## 게시판


1. <h3 id="all">전체 게시판 조회</h3>

![image](https://github.com/kjink98/BBCommunity/assets/113023365/b3c940f4-a13f-4eb1-a827-b7e5d239584f)


  **전체 게시판**
  
  * 구현 기능 설명
    - 전체 게시판 글 목록 조회
    - 클릭 시, 게시글 상세조회 페이지로 넘어감
    - 페이징 처리
    - 자유게시판 글쓰기
    - 게시글 검색

------------


2. <h3 id="free">자유 게시판 조회</h3>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/322f81d2-7cc9-4531-98ef-f4c4534f6d44)


  **자유 게시판**
  
  * 구현 기능 설명
    - 자유 게시판 글 목록 조회
    - 클릭 시, 게시글 상세조회 페이지로 넘어감
    - 페이징 처리
    - 자유게시판 글쓰기

------------

3. <h3 id="notice">공지사항 게시판 조회</h3>
### 관리자 권한
![image](https://github.com/kjink98/BBCommunity/assets/113023365/9fcc2d30-455b-463e-9856-9db8e44109ec)

### 일반 사용자 권한
![image](https://github.com/kjink98/BBCommunity/assets/113023365/b03feba5-8af8-4d6a-bb42-81964c9f6f96)

  **공지사항 게시판**
  
  * 구현 기능 설명
    - 공지사항 게시판 글 목록 조회
    - 클릭 시, 게시글 상세조회 페이지로 넘어감
    - 페이징 처리
    - 관리자 권한을 가진 유저만 글쓰기 가능

------------


4. <h3 id="write">글쓰기</h3>

![image](https://github.com/kjink98/BBCommunity/assets/113023365/43100767-b755-44e4-a838-292be44163e7)

  **글쓰기**
  
  * 구현 기능 설명
    - 글쓰기 (제목, 내용)  
      
   ------------

   
5. <h3 id="detail">게시글 상세 조회 페이지</h3>
![image](https://github.com/kjink98/BBCommunity/assets/113023365/09c4511f-ae3b-4680-9225-30bdb960d8fb)


  **게시글 상세 조회 페이지**
  
  * 구현 기능 설명
    - 게시글 상세보기(제목, 작성자, 등록일, 조회수, 내용)
    - 게시글 수정, 삭제 가능
    - 댓글 목록(작성자, 작성일, 내용)
    - 댓글 작성
    - 댓글 삭제

------------
# 감사합니다
    
<p align="center">
