package pratice.user_management.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;

@Getter
public class ErrorResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX", timezone = "UTC")
    private final ZonedDateTime timestamp;
    //private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String path;

    public ErrorResponseDTO(int status, String error, String path) {
        //this.timestamp = LocalDateTime.now();
        this.timestamp = ZonedDateTime.now(ZoneOffset.UTC);
        this.status = status;
        this.error = error;
        this.path = path;
    }
}

/*
예시

{
  "timestamp": "2023-04-16T07:28:34.039+00:00", # 예외 발생 시간
  "status": 500, # HTTP 상태 코드
  "error": "Internal Server Error", # 예외 유형
  "path": "/api/articles/123" # 예외가 발생한 요청 경로
}



public class ErrorResponseDTO {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ", timezone = "UTC")
    private final LocalDateTime timestamp;
    private final int status;
    private final String error;
    private final String message;
    private final String path;

    public ErrorResponseDTO(int status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }
}
*/