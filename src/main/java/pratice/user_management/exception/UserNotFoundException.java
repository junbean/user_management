package pratice.user_management.exception;

/**
 * 사용자를 찾을 수 없을 때 발생하는 예외
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}


/*
사용자 정의 예외
    RuntimeException을 상속받아 Unchecked Exception으로 동작
    예외 메시지를 생성자에서 받아서 설정 가능
    컨트롤러나 서비스에서 throw new UserNotFoundException("User not found")로 사용

*/
