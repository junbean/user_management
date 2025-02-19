package pratice.user_management.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pratice.user_management.domain.dto.ErrorResponseDTO;

import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 사용자가 존재하지 않음 (404)
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.NOT_FOUND.value(),
                "Not exist user",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    // 잘못된 요청 (400)
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponseDTO> handleIllegalArgument(IllegalArgumentException ex, HttpServletRequest request) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    // 내부 서버 오류 (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGeneralException(Exception ex, HttpServletRequest request) {
        ErrorResponseDTO response = new ErrorResponseDTO(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}



/*
예외 대응 방법

존재하지 않는 데이터에 대한 예외 처리
    1. ResponseEntity<?>를 사용하여 모든 응답을 처리
        - 기본적으로 Json을 UserDTO를 반환한다고 가정
        - 하지만 특정id에 대한 값이 없어서 반환할 객체가 존재하지 않음(NOT FOUND)
        - NOT FOUND에 대한 예외를 처리해야함
        - 반환객체를 ResponseEntity<?>롤 지정해서 만약 예외가 발생하면 json형식으로 예외 메세지를 반환하도록 설정
    2. ExceptionHandler를 활용하여 예외 처리 분리
        - 이거 쓰는게 바람직하다
        - 설명은 아래 첨부


@ExceptionHandler란?
    Spring에서 전역적으로 예외를 처리할 수 있는 기능
    컨트롤러에서 예외 발생 시, 직접 try-catch를 하지 않고 자동으로 예외를 처리 가능
    REST API에서 JSON 형식으로 에러 메시지를 반환하는 데 유용

@ExceptionHandler를 활용한 예외 처리 방법
    // ❗ 사용자 정의 예외 클래스 (UserNotFoundException)
    public class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

에러 메세지 응답 DTO구현




이전 코드 - ResponseEntity<?>

@ExceptionHandler(UserNotFoundException.class)
public ResponseEntity<?> handleUserNotFound(UserNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of("error", ex.getMessage(), "status", 404));
}

// 잘못된 요청 (400)
@ExceptionHandler(IllegalArgumentException.class)
public ResponseEntity<?> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(Map.of("error", ex.getMessage(), "status", 400));
}

// 내부 서버 오류 (500)
@ExceptionHandler(Exception.class)
public ResponseEntity<?> handleGeneralException(Exception ex) {
    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Map.of("error", "Internal server error", "status", 500));
}

*/
