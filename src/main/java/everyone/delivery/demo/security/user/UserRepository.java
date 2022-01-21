package everyone.delivery.demo.security.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 *
 * @author gshgsh0831
 * **/
public interface UserRepository extends JpaRepository<UserEntity,Long> {
	
	List<UserEntity> findAll();
	UserEntity findByuserId(Long userId);
	UserEntity findByEmail(String email);


	/***
	 * 업데이트 기능도 있음(동일한 키가 있으면 업데이트)
	 * @param userEntity
	 * @return
	 */
	UserEntity save(UserEntity userEntity);
	

	void deleteByUserId(Long userId);
}