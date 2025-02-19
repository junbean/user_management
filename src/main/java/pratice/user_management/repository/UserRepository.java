package pratice.user_management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pratice.user_management.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    // 비밀번호가 존재하는지 확인
    Optional<User> findByPassword(String password);

}

/*
JpaRepository에서 기본적으로 제공하는 주요 메서드들
    조회
    findById(ID)      // 단일 조회
    findAll()         // 전체 조회
    existsById(ID)    // 존재 여부 확인
    count()           // 개수 조회

    저장 & 수정
    save(Entity)      // 저장 및 수정
    saveAll(Iterable) // 다수 저장

    삭제
    delete(Entity)    // 삭제
    deleteById(ID)    // ID로 삭제
    deleteAll()       // 전체 삭제

JpaRepository에서 추가적인 메서드 정의 방법
키워드
    단일 조건
    Optional<User> findByEmail(String email);
    List<User> findByUsername(String username);
    List<User> findByAge(int age);

    다중 조건
    And: 여러 조건을 모두 만족
    Or: 여러 조건 중 하나라도 만족
    List<User> findByUsernameAndAge(String username, int age);
    List<User> findByUsernameOrEmail(String username, String email);

    비교 연산
    GreaterThan: 초과
    LessThan: 미만
    Between: 범위 내
    List<User> findByAgeGreaterThan(int age);
    List<User> findByAgeLessThan(int age);
    List<User> findByAgeBetween(int start, int end);

    Like 검색
    Like: 포함
    StartsWith: ~로 시작
    EndsWith: ~로 끝남
    List<User> findByUsernameLike(String username);
    List<User> findByUsernameStartsWith(String username);

    null 체크
    IsNull: null인 경우
    IsNotNull: null이 아닌 경우
    List<User> findByEmailIsNull();
    List<User> findByEmailIsNotNull();

    OrderBy: 정렬
    List<User> findByOrderByAgeAsc();     // 나이 오름차순
    List<User> findByOrderByAgeDesc();    // 나이 내림차순


@Query를 사용한 커스텀 쿼리도 가능
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmailCustom(@Param("email") String email);
*/

/*
어노테이션 정리

리포지토리 관련 기본 어노테이션

    @Repository
    - 해당 클래스가 Repository임을 명시
    - Spring Data JPA 사용시 생략 가능 (JpaRepository 상속시 자동 인식)

    @NoRepositoryBean
    - 실제 Repository가 아닌 중간 Repository 인터페이스임을 명시
    - 공통 인터페이스 작성시 사용

쿼리 메서드 관련 어노테이션

    @Query
    - 직접 쿼리를 작성할 때 사용
        @Query("SELECT u FROM User u WHERE u.email = ?1")
        User findByEmail(String email);

    @Param
    - 쿼리의 파라미터에 이름을 붙일 때 사용
    - 파라미터의 이름이 쿼리 내의 이름이 달라도 @Param()으로 동일하게 만들면 성립된다
        @Query("SELECT u FROM User u WHERE u.email = :email")
        User findByEmail(@Param("email") String email);

    @Modifying
    - UPDATE, DELETE 쿼리 실행시 사용
        @Modifying
        @Query("UPDATE User u SET u.name = :name WHERE u.id = :id")
        int updateUsername(@Param("id") Long id, @Param("name") String name);

트랜잭션 관련 어노테이션

    @Transactional
    - 트랜잭션 처리가 필요한 메서드에 사용
    - 주로 Service 계층에서 사용하지만, Repository에서도 사용 가능

    @Lock
    - 데이터베이스 락을 걸 때 사용
        @Lock(LockModeType.PESSIMISTIC_WRITE)
        Optional<User> findById(Long id);

*/


/*
JPQL
*/

/*
JpaRepository<User, Long> 정리

첫 번째 파라미터 User
    Entity의 타입을 지정
    즉, 이 Repository가 어떤 Entity를 다룰 것인지를 명시
    여기서는 User 엔티티를 다루는 Repository라는 의미

두 번째 파라미터 Long
    Entity의 PK(ID)의 타입을 지정
    User 에티티의 @Id로 지정된 필드의 파입
    여기서는 User의 id 필드가 Long 타입이라는 의미

*/

/*
application.properties - jpa 설정 정리

spring.jpa.hibernate.ddl-auto=update
    create: 애플리케이션 시작 시 테이블을 삭제하고 새로 생성
    create-drop: create와 같지만 애플리케이션 종료 시 테이블 삭제
    update: 엔티티와 테이블이 다른 경우 테이블 수정 (안전한 변경만 수행)
    validate: 엔티티와 테이블이 일치하는지 검사만 수행
    none: 아무 작업도 하지 않음

# SQL 쿼리를 콘솔에 출력
spring.jpa.show-sql=true

# SQL 쿼리를 보기 좋게 포맷팅
spring.jpa.properties.hibernate.format_sql=true

# SQL 쿼리의 파라미터 값 출력
logging.level.org.hibernate.type.descriptor.sql=trace
*/