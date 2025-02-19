package pratice.user_management.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pratice.user_management.domain.dto.UserCreateDTO;
import pratice.user_management.domain.dto.UserDTO;
import pratice.user_management.service.UserService;

import java.util.List;
import java.util.Map;

@RestController     // REST API 컨트롤러 선언
@RequestMapping("/api/users")    // 모든 엔드포인트가 "/user 로 시작
@RequiredArgsConstructor    // 생성자 자동 생성
public class UserController {
    private final UserService service;

    /**
     * 회원 전체 조회 (GET /users)
     *
     * @return 모든 사용자 DTO 리스트
     */
    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = service.getAllUsers();
        return ResponseEntity.ok(users);
    }


    /**
     * 특정 회원 조회 (GET /users/{id})
     *
     * @param id 조회할 사용자 ID
     * @return 조회된 사용자 DTO
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getUser(@PathVariable Long id) {
        UserDTO user = service.getUser(id);

        // 올바른 값이 아닌 경우, 근데 이건 알아서 걸러지므로 생략
        /*
        if(id == 0) {
            throw new IllegalArgumentException("Invalid user ID");
        }
        */

        return ResponseEntity.ok(user);
    }


    /**
     * 특정 회원 생성 (POST /users)
     *
     * @param userCreateDTO 회원가입 요청 데이터
     * @return 생성된 사용자 정보
     */
    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        service.createUser(userCreateDTO);
        return ResponseEntity.ok("User created successfully!");
    }

    /**
     * 특정 사용자 삭제 (DELETE /users/{id})
     *
     * @param id 삭제할 사용자 ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.ok("User deleted successfully!");
    }

    /**
     * 특정 사용자 정보 수정 (PUT /users/{id})
     *
     * @param id            수정할 사용자 ID
     * @param userCreateDTO 수정할 데이터 (DTO)
     * @return 업데이트된 사용자 정보
     */
    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO updatedUser = service.updateUser(id, userCreateDTO);
        return ResponseEntity.ok(updatedUser);
    }
}

/*
어노테이션 정리
    @Controller
        Spring MVC의 전통적인 컨트롤러
        View(화면) 반환이 기본 목적
        주로 웹 페이지를 반환할 때 사용
        String을 반환하면 해당 이름의 View를 찾아 렌더링
        사용목적
            @Controller: 웹 페이지(View) 반환
    @ResController
        @Controller + @ResponseBody의 조합
        데이터(JSON/XML) 반환이 기본 목적
        REST API 구현에 특화
        모든 메서드에 @ResponseBody가 자동으로 적용
        사용목적
            @RestController: REST API 구현

HTTP Method 어노테이션
    @GetMapping
        - HTTP GET 요청을 처리
        - 데이터 조회 작업에 사용
    
    @PostMapping
        - HTTP POST 요청을 처리
        - 새로운 리소스 생성 작업에 사용
    
    @PutMapping
        - HTTP PUT 요청을 처리
        - 리소스 전체 수정 작업에 사용
    
    @DeleteMapping
        - HTTP DELETE 요청을 처리
        - 리소스 삭제 작업에 사용

파라미터 관련 어노테이션
    @PathVariable
    - URL 경로의 일부를 변수로 받을 때 사용
    - URL 경로에서 {}로 묶인 부분의 값을 가져옴
        URL: /users/123
        @GetMapping("/users/{id}")
        public User getUser(@PathVariable Long id) { }
    @RequestPram
    - URL 의 쿼리 파라미터를 받을 때 사용
    - URL에서 ?뒤에 오는 key=value 형태의 파라미터를 가져옴
        URL: /users?name=john
        @GetMapping("/users")
        public List<User> searchUsers(@RequestParam String name) { ... }
    @RequestBody
    - HTTP 요청 본문을 자바 객체로 변환
        @PostMapping("/users")
        public User createUser(@RequestBody User user) { ... }
    @RequestHeader
    - HTTP 요청 헤더 값을 가져올 때 사용
        @GetMapping("/users")
        public User getUser(@RequestHeader("Authorization") String token) { ... }

*/

/*
ResponseEntity
    Spring에서 HTTP 응답을 다룰 때 사용하는 클래스
    응답 데이터 + HTTP 상태 코드를 함께 반환할 수 있음
    컨트롤러에서 API 응답을 보낼 때 유용

기본적인 ResponseEntity의 활용 방법
    HTTP 200 OK 응답
    return ResponseEntity.ok(user);

    HTTP 201 Created 응답
    return ResponseEntity.status(HttpStatus.CREATED).body("User created successfully!");

    HTTP 400 Bad Request 응답
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid user data");

    HTTP 404 Not Found 응답
    return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");

    HTTP 500 Internal Server Error 응답
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Server error occurred");


예시
    매핑
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUser(@PathVariable Long id) {
        UserDTO user = userService.getUserById(id);
        return ResponseEntity.ok(user); // ⭐ HTTP 200 OK + 유저 데이터 반환
    }
    HTTP 응답
        헤더
            HTTP/1.1 200 OK
            Content-Type: application/json
        json
            {
                "email": "test@example.com",
                "username": "testuser",
                "phone": "010-1234-5678",
                "createdAt": "2024-02-19T12:00:00"
            }



    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody UserCreateDTO userCreateDTO) {
        userService.createUser(userCreateDTO);
        return ResponseEntity.ok("User created successfully!"); // ⭐ HTTP 200 OK + 메시지 반환
    }
    HTTP 응답
        헤더
            HTTP/1.1 201 Created
            Content-Type: text/plain
        json
            "User created successfully!"

*/

/*
주요 HTTP 상태 코드 정리
    1xx (정보 응답)
        100 Continue → 요청 일부를 수신했으며, 계속 요청을 진행해도 됨.
        101 Switching Protocols → 클라이언트가 요청한 프로토콜로 변경됨.
    2xx (성공)
        200 OK → 요청이 정상적으로 처리됨.
        201 Created → 요청이 성공적으로 수행되었으며, 새로운 리소스가 생성됨.
        204 No Content → 요청이 성공했으나, 응답할 콘텐츠가 없음.
    3xx (리다이렉션)
        301 Moved Permanently → 리소스가 영구적으로 이동됨.
        302 Found → 리소스가 임시적으로 이동됨.
        304 Not Modified → 캐시된 버전을 사용하도록 클라이언트에게 요청.
    4xx (클라이언트 오류)
        400 Bad Request → 잘못된 요청 (문법 오류, 잘못된 파라미터).
        401 Unauthorized → 인증이 필요함 (로그인이 필요).
        403 Forbidden → 권한이 없어 접근 불가.
        404 Not Found → 요청한 리소스를 찾을 수 없음.
        405 Method Not Allowed → 요청한 메서드가 허용되지 않음.
        429 Too Many Requests → 너무 많은 요청을 보냄 (Rate Limit 초과).
    5xx (서버 오류)
        500 Internal Server Error → 서버 내부 오류 (일반적인 서버 문제).
        502 Bad Gateway → 게이트웨이 또는 프록시 서버가 잘못된 응답을 받음.
        503 Service Unavailable → 서버 과부하 또는 유지보수 중.
        504 Gateway Timeout → 서버가 응답을 기다리다 타임아웃 발생.

*/

