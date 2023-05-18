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
    MEMBER ||--o{ BOARD : make
    MEMBER ||--o{ BOARD_BOOKMARK : make
    BOARD ||--|| BOARD_COUNT : count

    MEMBER {
        int member_id PK
        string login_id
        string password
        string nickname
        date created_at
        date last_modified_at
        int student_number
        string user_fixture_url
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

    BOARD_BOOKMARK {
        int board_bookmark_id PK
        int member_id FK
        int board_id FK
        date created_at
    }

    BOARD {
        int board_id PK
        int member_id FK
        string title 
        string image_url
        string description
        boolean is_deleted
    }

    BOARD_COUNT {
        int board_count_id PK
        int board_id FK
        int board_count
    }
```


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
