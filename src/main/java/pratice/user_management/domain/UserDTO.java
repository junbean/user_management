package pratice.user_management.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// API 출력용
@Getter
@NoArgsConstructor
public class UserDTO {
    private String email;
    private String username;
    private String phone;
    private LocalDateTime createdAt;

    public UserDTO(String email, String username, String phone, LocalDateTime createdAt) {
        this.email = email;
        this.username = username;
        this.phone = phone;
        this.createdAt = createdAt;
    }
}


/*
DTO (Data Transfer Object)
    데이터를 전달하는 객체(클래스)
    간단하게, 데이터를 담아서 이동시키는 박스
    스프링에서는 보통 컨트롤러(Controller) ↔ 서비스(Service) ↔ 리포지토리(Repository) 사이에서 데이터를 주고받을 때 DTO를 사용

사용하는 이유
    불필요한 데이터 제외 →
        엔티티(Entity)는 데이터베이스와 연결된 클래스라 필드가 많아.
        하지만 클라이언트(프론트엔드)나 다른 계층에는 꼭 필요한 데이터만 보내야 해!
            → 🚫 Entity를 직접 넘기면 위험함!
            → ✅ DTO를 만들어서 필요한 데이터만 골라서 전달!

    보안 강화 →
        예를 들어, 유저 비밀번호 같은 민감한 정보를 클라이언트에 보내면 안 되겠지?
            → DTO를 사용해서 중요한 정보는 제외할 수 있어!

    계층 분리 & 유지보수성 증가 →
        엔티티 구조가 바뀌어도 DTO만 잘 수정하면 되니까 코드 관리가 쉬워짐!

사용 예제

    서비스 계층
        @Service
        public class UserService {
            private final UserRepository userRepository;

            public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
            }

            // User 엔티티를 UserDTO로 변환하는 메서드
            private UserDTO convertToDTO(User user) {
                return new UserDTO(user.getName(), user.getEmail());
            }

            // 모든 사용자 가져오기 (DTO로 변환 후 반환)
            public List<UserDTO> getAllUsers() {
                List<User> users = userRepository.findAll();
                return users.stream()
                        .map(this::convertToDTO) // 엔티티를 DTO로 변환
                        .collect(Collectors.toList());
            }

            // 특정 사용자 가져오기 (DTO로 변환 후 반환)
            public UserDTO getUserById(Long id) {
                User user = userRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("User not found"));
                return convertToDTO(user);
            }
        }
    도메인
        @Entity
        @Table(name = "users") // users 테이블과 매핑
        public class User {
            @Id
            @GeneratedValue(strategy = GenerationType.IDENTITY)
            private Long id;  // 기본 키
            private String name;
            private String email;
            private String password; // ❌ 클라이언트에 노출하면 안 됨!

            // 기본 생성자 (JPA용)
            public User() {}

            public User(String name, String email, String password) {
                this.name = name;
                this.email = email;
                this.password = password;
            }

            // Getter & Setter
            public Long getId() { return id; }
            public String getName() { return name; }
            public String getEmail() { return email; }
            public String getPassword() { return password; }

            public void setId(Long id) { this.id = id; }
            public void setName(String name) { this.name = name; }
            public void setEmail(String email) { this.email = email; }
            public void setPassword(String password) { this.password = password; }
        }
    DTO
        public class UserDTO {
            private String name;
            private String email;

            public UserDTO(String name, String email) {
                this.name = name;
                this.email = email;
            }

            // Getter & Setter
            public String getName() { return name; }
            public String getEmail() { return email; }

            public void setName(String name) { this.name = name; }
            public void setEmail(String email) { this.email = email; }
        }

*/