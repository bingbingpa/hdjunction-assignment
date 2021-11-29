# [에이치디정션](https://www.hdjunction.co.kr) 백엔드 개발자 채용 온라인 과제

### 개발환경

- os: Ubuntu 18.04.5 LTS
- java: openjdk 11.0.11
- springBoot 2.6.0
  - spring web
  - spring data jpa
  - spring restdocs
  - queryDsl
- db: h2 memory
- test: JUnit5

### 프로젝트 구조

### API 문서

### 요구사항 정리

- [X] Hospital(병원), Patient(환자), Visit(환자방문) Entity 정의
  - (옵션)API 구현중 추가로 필요한 칼럼이 있다면 자유롭게 추가
    - created_date, modified_date
  - (옵션) 코드, 코드그룹 Entity 구현
  - JpaRepository 를 상속 받아서 PatientRepository, VisitRepository 생성
- [ ] API
  - 환자 등록
    - 환자등록번호는 **병원별로 중복되지 않도록 서버에서 생성**
      - 환자번호는 등록일 yyyy + leftPad(6 - 숫자.length ) 형태로 등록한다.
  - 환자 수정
  - 환자 삭제
  - **환자 조회**
    - 환자 id 를 이용해 한 환자의 정보를 조회. 환자 Entity 의 모든 속성과 내원 정보를 목록으로 함께 조회
  - **환자 목록 조회**
    - 이름, 환자등록번호, 생년월일로 동적 조회
    - pageNo 는 1부터 시작
    - 총건수, 페이지시작번호 - 페이지마지막번호, 페이지 정보를 보여줘야한다.
- [ ] spring restdocs 를 이용한 문서화
- [ ] validation
  - global Exception 처리
  - dto validation
    - 전화 번호, 생일
- [ ] response
  - 성별 코드명으로 보여주기
  - 날짜 yyyy-mm-dd 형태로 보여주기
  - 전화번호 xxx-xxxx-xxxx 형태로 보여주기