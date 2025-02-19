package pratice.user_management.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pratice.user_management.domain.User;
import pratice.user_management.domain.UserCreateDTO;
import pratice.user_management.domain.UserDTO;
import pratice.user_management.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository repository;

    /**
     * 모든 사용자 조회 (Entity -> DTO 변화 후 반환)
     */
    public List<UserDTO> getAllUsers() {
        return repository.findAll().stream()
                .map(user -> new UserDTO(user.getEmail(), user.getUsername(), user.getPhone(), user.getCreatedAt()))
                .collect(Collectors.toList());
    }

    /**
     * 특정 사용자 조회(id) (Entity -> DTO 변환 후 반환)
     */
    public UserDTO getUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return new UserDTO(user.getEmail(), user.getUsername(), user.getPhone(), user.getCreatedAt());
    }

    /**
     * 회원가입 (DTO -> Entity 변환 후 저장)
     */
    public void createUser(UserCreateDTO userCreateDTO) {
        User user = User.builder()
                .email(userCreateDTO.getEmail())
                .password(userCreateDTO.getPassword()) // 비밀번호는 나중에 암호화!
                .username(userCreateDTO.getUsername())
                .phone(userCreateDTO.getPhone())
                .build();

        repository.save(user);
    }

    /**
     * 특정 사용자 삭제
     */
    public void deleteUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        repository.delete(user);
    }

    /**
     * 특정 사용자 정보 수정(Entity -> Entity 변경 후 저장)
     */
    public UserDTO updateUser(Long id, UserCreateDTO userCreateDTO) {
        User existingUser = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.updateProfile(
                userCreateDTO.getEmail(),
                userCreateDTO.getUsername(),
                userCreateDTO.getPhone()
        );

        existingUser.updatePassword(userCreateDTO.getPassword());

        return new UserDTO(existingUser.getEmail(), existingUser.getUsername(), existingUser.getPhone(), existingUser.getCreatedAt());
    }
}



/*
Service 어노테이션 정리

기본적인 어노테이션
    @Service
        - 해당 클래스가 서비스 계층임을 나타냄
        - 스프링의 컴포넌트 스캔 대상이 됨
    @Autowired
        - 의존성 주입을 위해 사용
        - 생성자 주입, 필드 주입, 세터 주입에 사용 가능

생성자 관련(Lombok)
    @RequiredArgsConstructor
        - final이나 @NonNull이 붙은 필드에 대한 생성자 자동 생성
    @AllArgsConstructor
        - 모든 필드를 파라미터로 받는 생성자 생성
    @NoArgsConstructor
        - 파라미터가 없는 기본 생성자 생성

트랜잭션 관련
    @Transactional
        - 해당 메서드를 트랜잭션으로 처리
        - 클래스 레벨에도 적용 가능
        - 옵션 예시:
          @Transactional(readOnly = true)  // 읽기 전용
          @Transactional(rollbackFor = Exception.class)  // 특정 예외 발생 시 롤백

예외 처리
    @ExceptionHandler
        - 특정 예외를 처리하는 메서드 지정
    @ResponseStatus
        - HTTP 응답 상태 코드 지정
*/

/*
의존성 주입의 방식
    필드 주입
        코드가 간단함
        단점: 의존성을 숨기게 됨
        단점: 테스트가 어려움
        단점: final 선언 불가 (불변성 보장 못함)
            @Service
            public class UserService {
                @Autowired
                private UserRepository userRepository;  // 필드에 직접 주입
            }

    세터 주입
        선택적 의존성에 유용
        setter를 통해 의존성을 바꿀 수 있음
        단점: 실행 중에 의존성이 바뀔 수 있음 (불안정)
        단점: final 선언 불가
            @Service
            public class UserService {
                private UserRepository userRepository;

                @Autowired
                public void setUserRepository(UserRepository userRepository) {
                    this.userRepository = userRepository;
                }
            }

    생성자 주입
        컴파일 시점에 의존성 확인 가능
        필수적인 의존성 보장
        final 선언 가능 (불변성)
        순환 참조 감지 가능
        테스트 용이
            @Service
            public class UserService {
                private final UserRepository userRepository;

                @Autowired  // 생성자가 1개면 생략 가능
                public UserService(UserRepository userRepository) {
                    this.userRepository = userRepository;
                }
            }

    Lombok을 사용한 생성자 주입
        생성자 주입의 장점을 모두 가짐
        코드가 매우 간단
        final 필드를 위한 생성자 자동 생성
            @Service
            @RequiredArgsConstructor
            public class UserService {
                private final UserRepository userRepository;
                private final EmailService emailService;
            }

*/


/*
Optional이란?

null 여부를 명시적으로 표현
null 체크를 강제
null일 경우의 처리를 보다 우아하게 지원

이메일로 사용자를 찾을 때 두 가지 상황이 발생할 수 있다
- 해당 이메일을 가진 사용자가 존재
- 해당 이메일을 가진 사용자가 없음

Optional의 장점
- Null 체크를 보다 명시적이고 안전하게 처리
- Optional이 제공하는 다양한 메서드 활용 가능
    - ifPresent(): 값이 있을 때만 실행
    - orElse(): 값이 없을 때 대체값 반환
    - orElseThrow(): 값이 없을 때 예외 발생
- NullPointerException 예방

사용 방법 예시
    Optional 사용 안함
        User user = userRepository.findByEmail("test@test.com");
        if (user == null) {  // null 체크 필요 }
    Optional 사용함
        userRepository.findByEmail("test@test.com")
            .ifPresent(user -> { // 사용자가 존재할 때 처리 });
        // 또는
        User user = userRepository.findByEmail("test@test.com")
            .orElseThrow(() -> new RuntimeException("사용자가 없습니다"));

JpaRepository에서의 Optional
    JpaRepository에서는 메서드의 특성에 따라 Optional을 반환하기도 함
    Optional로 감싸서 반환하는 경우 - 결과가 없을 수 있는 단일 객체 조회 - Optional 사용
        Optional<User> findById(Long id);                    // ID로 단일 조회
        Optional<User> findByEmail(String email);            // 이메일로 단일 조회
        Optional<User> findByEmailAndUsername(String email, String username);  // 조건으로 단일 조회
    Optional 없이 반환하는 경우 - 컬렉션 반환은 Optional을 사용하지 않고 빈 컬렉션 반환 - 저장, 삭제 등의 작업은 Optional을 사용 안함
        List<User> findAll();                               // 전체 조회
        List<User> findByAge(int age);                     // 나이로 다중 조회
        List<User> findByUsernameLike(String username);    // 이름으로 다중 조회
        User save(User user);                              // 저장/수정
        void delete(User user);                            // 삭제

Optional이 제공하는 주요 메서드
    // 생성 예시
    Optional<String> empty = Optional.empty();
    Optional<String> normal = Optional.of("hello");      // null 불가
    Optional<String> nullable = Optional.ofNullable(someString);  // null 가능

    // 값 접근 예시
    if (optional.isPresent()) {
        // 값이 있을 때 처리
    }
    if (optional.isEmpty()) {  // Java 11+
        // 값이 없을 때 처리
    }

    // Optional을 제거하고 객체 반환
    repository.findById(id).get()

    // 조건부 동작 예시
    optional.ifPresent(value -> System.out.println(value));
    optional.ifPresentOrElse(
        value -> System.out.println(value),
        () -> System.out.println("값이 없습니다")
    );

    // 값 변환 예시
    Optional<Integer> length = optional.map(String::length);
    Optional<User> user = optional.flatMap(this::findUser);
    Optional<String> filtered = optional.filter(str -> str.length() > 5);

    // 값 반환 예시
    String result1 = optional.orElse("기본값");
    String result2 = optional.orElseGet(() -> generateDefault());
    String result3 = optional.orElseThrow(() -> new NotFoundException("값을 찾을 수 없습니다"));

실무에서 자주 사용되는 패턴
    // Repository 메서드에서
    public Optional<User> findByEmail(String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    // Service 메서드에서
    public User getUser(String email) {
        return userRepository.findByEmail(email)
            .filter(user -> user.isActive())
            .orElseThrow(() -> new UserNotFoundException("User not found: " + email));
    }

    // 중첩된 객체 안전하게 접근
    public String getUserCity(Long userId) {
        return userRepository.findById(userId)
            .map(User::getAddress)
            .map(Address::getCity)
            .orElse("Unknown");
    }
*/

/*
 Stream 개념과 특징
    개념
        데이터의 흐름을 표현하는 인터페이스
        컬렉션의 요소를 내부 반복자를 이용해서 처리
        한 번 사용하면 재사용 불가능 (일회용)
        원본 데이터를 변경하지 않음
    특징
        // 1. 선언형 프로그래밍
        List<String> filtered = list.stream()
            .filter(s -> s.startsWith("a"))
            .collect(Collectors.toList());

        // 2. 내부 반복
        // 기존 방식
        for(String s : list) {
            if(s.startsWith("a")) {
                filtered.add(s);
            }
        }

        // 3. 지연 연산 (lazy evaluation)
        Stream<String> stream = list.stream()
            .filter(s -> {
                System.out.println("필터링: " + s);
                return s.startsWith("a");
            }); // 이 시점에서는 실행되지 않음
        stream.collect(Collectors.toList()); // 이때 실제 실행

*/