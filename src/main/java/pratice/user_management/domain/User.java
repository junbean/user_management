package pratice.user_management.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(length = 20)
    private String phone;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    public User(String email, ) {

    }

}

/*
어노테이션 정리

@Entity
    JPA가 해당 클래스를 엔티티로 인식하게 함
    데이터베이스 테이블과 매핑되는 클래스임을 나타냄

@Table(name = "users")
    매핑할 테이블의 이름을 지정
    클래스명과 테이블명이 다를 경우 사용

@Getter
    모든 필드의 getter 메서드를 자동 생성

@Setter
    모든 필드의 setter 메서드 자동 생성

@NoArgsConstructor(access = AccessLevel.PROTECTED)
    파라미터가 없는 기본 생성자 생성
    protected 접근 제한자로 설정

@Id
    해당 필드가 기본키(pk)임을 나타냄

@GeneratedValue(strategy = GenerationType.IDENTITY)
    기본키 생성 전략을 설정
    IDENTITY는 데이터베이스의 AUTO_INCREMENT를 사용

@Column(nullable = false, length = 50, unique = true)
    데이터베이스 테이블의 컬럼과 관련된 설정을 지정할 때 사용
    nullable : 이 필드는 NULL 값을 허용하지 않음
    length : 문자열 컬럼의 최대 길이를 지정
    unique : 해당 컬럼의 값은 유일해야 함
    name : 데이터베이스 컬럼명 지정 (기본값은 필드명)
    insertable : INSERT 가능 여부 (기본값 true)
    updateable : UPDATE 가능 여부 (기본값 true)
    columDefintion : 컬럼 정의를 직접 지정
    percision : 숫자형 데이터의 전체 자릿수
    scale : 숫자형 데이터의 소수점 자릿수

@PrePersist
    엔티티가 저장되기 전에 실행될 메서드 지정
    생성 시간 자동 설정에 사용
    
@PreUpdate
    엔티티가 업데이트 되기 전에 실행될 메서드 지정
    수정 시간 자동 설정에 사용

*/