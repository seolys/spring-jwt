### 프로젝트 설명
- Springboot2, Spring-Security, JPA, JWT활용하여 인증 구현.
- 봄이네집 유튜브를 보면서 모든소스를 Kotlin으로 클론코딩... 
- 봄이네집 : https://www.youtube.com/playlist?list=PLcsqrv8NxApXzHViDU2fB1ew7KoLoaB02

---

## 강의해주신 내용 메모..

### AuthenticationManager(인증관리자) : AuthenticationProvider 주머니

- Builder 패턴으로 구현
- 등록된 Authentication Provider들에 접근하는 유일한 객체
- 단순 인터페이스에 불과하다. 내장 구현체 : ProviderManager
- AuthenticationManager를 구현해서 쓰지말자. Pivotal 기술자보다 더 잘 만들 자신이 없으면..
- 구현해서 쓰라고 넣어준 인터페이스가 아니다.

### AuthenticationProvider(인증공급자) : 진짜 인증이 일어나는곳

- 인증 '전' 객체를 받아 인증 가능 여부를 체크한 후, 예외를 던지던지 인증 '후' 객체를 만들어 돌려준다.
- 구현하라고 넣어준 인터페이스다.
- 필요에 맞게 정교하게 구현하고 인증관리자에 등록시키자.

### 인증객체란?

- Authentication 클래스의 모든 서브클래스.
  - UsernamePasswordAuthenticationToken
    - principal : 인증의 주체가되는 객체, userId
    - credential : 패스워드
    - ※ 인증 전 객체 : 파라메터 2개만 있는 생성자(principal, credential)
    - ※ 인증 후 객체 : 파라메터 3개 생성자(principal, credential, 권한)

### 구현내용

- 요청을 받아낼 필터(AbstractAuthenticationFilter)
  - 로그인방법만큼 생성
  - Form, SSO 각각 있을경우 각각 필터 구현
- Manager에 등록시킬 Auth Provider
- AJAX방식이라면 인증 정보를 담을 DTO
- 각 인증에 따른 추가 구현체. 기본적으로 성공/실패 핸들러.
- 소셜 인즈으이 경우 각 소셜 공급자 규격에 맞는 DTO와 HTTP request객체.(RestTemplate)
- 인증 시도 / 인증 성공시에 각각 사용할 Authentication 객체.

### 로그인 프로세스
- 1. 클라이언트가 외부서비스에 SSO 인증요청.
- 2. 외부서비스에서 토큰 수신
- 3. 토큰으로 서버(서비스) 호출
- 4. 서버는 외부서비스에 토큰유효성 체크
- 5. 유효한것이 확인되면, 클라이언트에 JWT토큰 발행
- 6. 이후 서버에 호출할때 JWT토큰을 제시.

### 유저 객체 설계
- 유저인증을 위해 필요한 정보, 서비스 제공을 위한 필요한 만큼만 저장한다.
- 비밀번호를 비롯한 민감 정보는 암호화하는것이 원칙이다.(BCryptPasswordEncoder)
- 소셜 회원도 담을 수 있게 확장성있게 구현한다.
- @ElementCollection, Enum 등을 활용하자
