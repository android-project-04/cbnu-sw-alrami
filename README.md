# cbnu-sw-alrami
[BE] 충북대학교 소프트웨어학과의 공지 알람 및 취업관련 컨텐츠, IT 소식 등을 알려주는 기능을 가지고있습니다.

<br/>


문서 -> 
http://ec2-3-39-25-103.ap-northeast-2.compute.amazonaws.com/docs/hello.html

도메인 주소 -> 
http://ec2-3-39-25-103.ap-northeast-2.compute.amazonaws.com

테스트 커버리지 -> 
http://ec2-3-39-25-103.ap-northeast-2.compute.amazonaws.com/docs/index.html

<br/>


## 사용 기술
- Cloud: AWS EC2, RDS
- Framework: Spring boot, Spring Security, Spring Rest Docs
- ORM: JPA, QueryDSL
- Test: JUnit5, RestAssured, Test Container
- CI/CD: Github actions, docker, slack
- DB: MySQL, Redis

<br/>

``` mermaid
mindmap
  root(cbnu sw alrami)
    커뮤니티
      (취업 커뮤니티)
        [글쓰기]
        [전체 목록]
        [글 읽기]
        [찜하기]
        [찜하기 취소]
      (자유 커뮤니티)
        [글쓰기]
        [전체 목록]
        [글 읽기]
        [찜하기]
        [찜하기 취소]
    회원
      (회원가입)
      (로그인)
      (비밀번호 재설정)
      (마이페이지)
        [회원 탈퇴]
        [비밀번호 재설정]
        [닉네임 변경]
    어드민
      (게시글 삭제)
      (회원 30일 정지)
    공지
      (학과 공지)
        [찜하기]
        [글 읽기]
        [찜하기 취소]
      (IT 관련 소식)
        [찜하기]
        [글 읽기]
        [찜하기 취소]
    즐겨찾기
      (찜한 글 보기)
        (필터링)
          [커뮤니티-자유]
          [커뮤니티-취업]
          [IT컨텐츠]
          [학과 공지]
      (찜하기 취소)
```

<br/>

## ERD

``` mermaid
erDiagram
    MEMBER ||--|| STOP_MEMBER : make
    MEMBER ||--o{ NOTIFICATION_BOOKMARK : make
    NOTIFICATION ||--o{ NOTIFICATION_BOOKMARK : make
    MEMBER ||--o{ COMMUNITY : make
    MEMBER ||--o{ COMMUNITY_BOOKMARK : make
    COMMUNITY ||--|| COMMUNITY_COUNT : count
    COMMUNITY |o--o{ COMMUNITY_BOOKMARK : make

    MEMBER {
        int member_id PK
        string login_id
        string password
        string nickname
        date created_at
        date last_modified_at
        int student_number
        string user_fixture_url
        string approval
        string role
        boolean is_deleted
    }

    STOP_MEMBER {
        int stop_member_id PK
        int member_id FK
        date created_at
        date last_stop_day
    }

    NOTIFICATION_BOOKMARK {
        int notification_bookmark_id PK
        int member_id FK
        int notification_id FK
        date created_at
    }

    NOTIFICATION {
        int notification_id PK
        string title
        string url
        date created_at
        date last_modified_at
    }

    COMMUNITY_BOOKMARK {
        int community_bookmark_id PK
        int member_id FK
        int community_id FK
        date created_at
    }

    COMMUNITY {
        int community_id PK
        int member_id FK
        string title 
        string image_url
        string description
        boolean is_deleted
        string community_type
    }

    COMMUNITY_COUNT {
        int community_count_id PK
        int community_id FK
        int count
    }
```

## 개발 방법
![image](https://github.com/android-project-04/cbnu-sw-alrami/assets/87268026/da33a242-91c2-40ca-81d5-3668d927cfdc)
- 인수테스트 주도 개발을 하며 개발 이전에 기능에 대한 기획에 집중할 수 있는 시간을 갖게 됩니다.
- 최대한 모킹을 하지 않고 런던파 스타일로 테스팅하였습니다.
- 1개에 인수테스트에 N개의 통합테스트와 단위테스트를 갖게 됩니다.
- 인수테스트 안에서 TDD가 이루어지어 요구사항 변경으로 기능의 변경이 있었는데 사이드 이펙트를 빠르게 확인하여 배포 이전에 수정할 수 있었습니다.

<br/>

## CI/CD
``` mermaid
stateDiagram-v2
    [*] --> Main_Branch_Merge

    Main_Branch_Merge --> 테스팅
    테스팅 --> API문서화
    API문서화 --> 빌드
    빌드 --> 도커_빌드
    도커_빌드 --> 도커_레파지토리리_푸쉬
    도커_레파지토리리_푸쉬 --> 서버_도커_pull
    서버_도커_pull --> 서버_도커_run
    서버_도커_run --> 슬랙_알람
```


## 조회수 기능
조회수의 카운트를 매번 DB로 카운팅하면 게시물을 읽는데 매번 게시물 row에 Update Lock이 걸리게 된다.

이는 Shared Lock과 다르게 동시 읽기가 불가능하여 성능 저하에 대한 이슈가 있다.

이를 방지하기 위해 Redis 메모리 DB에 조회수를 캐싱해 두었다가 스케줄러 서버에서 일정 시간에 한번씩 조회수를 업데이트 하는 방식을 사용하였습니다.

이로써 아무리 조회가 많아도 트랜잭션에 쓰기용 락으로 이슈가 생기지 않습니다.

+ 같은 회원이 하나의 게시물에 조회수를 여러번 올리지 않게 하기 위해도 redis를 통해 유저 검증을 쉽게 하였습니다.


<br/>

## 요구 사항

### <기본>

1. 회원가입
    1. 가입할 땐 학번과 비밀번호로 가입한다.
    2. 가입할 때 사용할 닉네임을 정한다.
    3. 가입할 때는 본인 학생증 사진을 같이 첨부한다.
    4. 운영자는 학생증 사진을 보고 승인 처리를 한다.
2. 로그인
   1. 학번과 비밀번호로 로그인한다.

1. 비밀번호 재설정
   1. 마이페이지에서 비밀번호를 재설정한다.

1. 회원탈퇴
   1. 마이페이지에서 회원탈퇴버튼을 누른다.

  b. 회원탈퇴를 누르면 회원정보가 삭제되고 로그인 페이지로 이동한다.

### <학과 공지>

1. 배치로 학과 공지를 스크롤해서 DB에 저장한다.
    1. 스크롤한 데이터가 원래 가지고 있던 데이터와 다르면 알림 기능을 수행한다.
2. 회원은 앱에서 공지를 조회한다.
    1. 필요한 공지는 즐겨찾기 할 수 있게한다.
    2. 커서 페이징 처리한다. 
        1. 페이지 당 게시글 개수는 추 후 정의
3. 공지 제목을 누르면 상세 페이지로 이동한다.

### <학과 취업 커뮤니티>

1. 회원은 학과의 취업커뮤니티 게시판에 글을 쓸 수 있다.
    1. 사진과 글을 첨부할 수 있다.
    2. 링크를 첨부할 수 도 있다.
    3. 자신이 작성한 게시글을 삭제할 수 있다.
2. 글은 페이징 처리로 게시글이 보여진다.
3. 게시글의 제목을 클릭하면 상세 페이지로 이동한다.
4. 게시글을 클릭하면 조회수가 상승한다.
5. 커뮤니티 페이지에서 게시글의 조회수도 볼 수 있다.
6. 커뮤니티 취지에 어긋나는 게시글은 신고를 할 수 있다.
    1. 신고 2회가 누적되면 운영자에게 신고에 대한 알림이 간다.
    2. 운영자는 해당 게시글로 이동 후 삭제가 가능하다.

### <즐겨 찾기>

1. 즐겨찾기한 게시물들을 볼 수 있는 페이지가 있다.
2. 회원은 게시글에서 즐겨찾기를 할 수 있다.
3. 회원은 즐겨찾기의 게시글을 즐겨찾기 해제할 수 있다.
4. 페이징 처리로 게시글이 보여진다.

### <마이페이지>

1. 자신의 정보를 관리할 수 있는 페이지이다.
2. 닉네임을 변경할 수 있다.
    1. 닉네임 변경은 한달에 한번만 가능하다.
3. 비밀번호 재설정이 가능하다.
4. 로그아웃이 가능하다.
    1. 로그아웃을 누르면 로그인 페이지로 이동한다.
5. 회원탈퇴가 가능하다.
