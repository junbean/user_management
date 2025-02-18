package pratice.user_management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

//@RequestMapping("")
@Controller
public class UserController {

    /**
     * 메인 홈
     */
    @GetMapping("/")
    public String home() {
        return "home";
    }

    /**
     * 회원 전체 조회
     * localhost:8080/users
     */
    @GetMapping("/users")
    public String getAllUsers(Model model) {

        return "home";
    }

    /**
     * 특정 회원 조회
     * localhost:8080/users?id=1
     */
    @GetMapping("/users/{id}")
    public String getUser(@RequestParam int id) {

        return "user";
    }

    @PostMapping("/users")
    public User createUser(@RequestBody User user) {

    }

}

/*
어노테이션 정리

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
- 리소스 삭제 작업에 사

파라미터 관련 어노테이션
@PathVariable
- URL 경로의 일부를 변수로 받을 때 사용
- URL 경로에서 {}로 묶인 부분의 값을 가져옴

URL: /users/123
@GetMapping("/users/{id}")
public User getUser(@PathVariable Long id) {
    // id = 123
    return userService.findById(id);
}


@RequestPram
- URL 의 쿼리 파라미터를 받을 때 사용
- URL에서 ?뒤에 오는 key=value 형태의 파라미터를 가져옴

URL: /users?age=20
@GetMapping("/users")
public List<User> getUsersByAge(@RequestParam int age) {
    // age = 20
    return userService.findByAge(age);
}

*/