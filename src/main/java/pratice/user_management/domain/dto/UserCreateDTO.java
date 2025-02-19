package pratice.user_management.domain.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserCreateDTO {
    private String email;
    private String password;
    private String username;
    private String phone;

    public UserCreateDTO(String email, String password, String username, String phone) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }
}
