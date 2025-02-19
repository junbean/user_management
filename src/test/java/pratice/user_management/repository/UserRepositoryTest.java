package pratice.user_management.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import pratice.user_management.domain.entity.User;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    /**
     * 🔹 1. 사용자 생성 테스트 (Create)
     */
    @Test
    @DisplayName("사용자를 저장하면 데이터베이스에 정상적으로 저장되어야 한다.")
    void saveUser_ShouldStoreUserInDatabase() {
        // ✅ Given (테스트 데이터 생성)
        User user = User.builder()
                .email("test@example.com")
                .password("securePassword")
                .username("testuser")
                .phone("010-1234-5678")
                .build();

        // ✅ When (DB 저장)
        User savedUser = userRepository.save(user);

        // ✅ Then (검증)
        assertThat(savedUser.getId()).isNotNull(); // ID가 자동 생성되었는지 확인
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    /**
     * 🔹 2. 사용자 조회 테스트 (Read)
     */
    @Test
    @DisplayName("이메일을 통해 사용자를 조회할 수 있어야 한다.")
    void findUserByEmail_ShouldReturnUser_WhenUserExists() {
        // ✅ Given (테스트 데이터 저장)
        User user = User.builder()
                .email("test@example.com")
                .password("securePassword")
                .username("testuser")
                .phone("010-1234-5678")
                .build();
        userRepository.save(user);

        // ✅ When (이메일로 사용자 검색)
        Optional<User> foundUser = userRepository.findByEmail("test@example.com");

        // ✅ Then (검증)
        assertThat(foundUser).isPresent(); // 사용자 존재 여부 확인
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    /**
     * 🔹 3. 사용자 전체 조회 테스트 (Read All)
     */
    @Test
    @DisplayName("모든 사용자를 조회할 수 있어야 한다.")
    void findAllUsers_ShouldReturnListOfUsers() {
        // ✅ Given (테스트 데이터 저장)
        User user1 = User.builder()
                .email("user1@example.com")
                .password("password1")
                .username("User One")
                .phone("010-1111-1111")
                .build();

        User user2 = User.builder()
                .email("user2@example.com")
                .password("password2")
                .username("User Two")
                .phone("010-2222-2222")
                .build();

        userRepository.save(user1);
        userRepository.save(user2);

        // ✅ When (모든 사용자 조회)
        List<User> users = userRepository.findAll();

        // ✅ Then (검증)
        assertThat(users).hasSize(2); // 데이터 개수 확인
    }

    /**
     * 🔹 4. 사용자 정보 수정 테스트 (Update)
     */
    @Test
    @DisplayName("사용자 정보를 수정할 수 있어야 한다.")
    void updateUser_ShouldUpdateUserInfo() {
        // ✅ Given (테스트 데이터 저장)
        User user = User.builder()
                .email("test@example.com")
                .password("securePassword")
                .username("Old Username")
                .phone("010-1234-5678")
                .build();
        User savedUser = userRepository.save(user);

        // ✅ When (사용자 정보 변경)
        savedUser.setUsername("New Username");
        savedUser.setPhone("010-9999-9999");
        userRepository.save(savedUser); // 변경된 내용 저장

        // ✅ Then (변경된 내용 검증)
        Optional<User> updatedUser = userRepository.findByEmail("test@example.com");
        assertThat(updatedUser).isPresent();
        assertThat(updatedUser.get().getUsername()).isEqualTo("New Username");
        assertThat(updatedUser.get().getPhone()).isEqualTo("010-9999-9999");
    }

    /**
     * 🔹 5. 사용자 삭제 테스트 (Delete)
     */
    @Test
    @DisplayName("사용자를 삭제하면 데이터베이스에서 삭제되어야 한다.")
    void deleteUser_ShouldRemoveUserFromDatabase() {
        // ✅ Given (테스트 데이터 저장)
        User user = User.builder()
                .email("test@example.com")
                .password("securePassword")
                .username("testuser")
                .phone("010-1234-5678")
                .build();
        userRepository.save(user);

        // ✅ When (사용자 삭제)
        userRepository.delete(user);

        // ✅ Then (삭제 검증)
        Optional<User> deletedUser = userRepository.findByEmail("test@example.com");
        assertThat(deletedUser).isEmpty(); // 데이터가 삭제되었는지 확인
    }
}

/*
Ctrl + Shift + T = 클래스의 테스트 클래스를 만들어줌
*/