package everyone.delivery.demo.security.user;
import everyone.delivery.demo.common.exception.ExceptionUtils;
import everyone.delivery.demo.security.user.dtos.CreateUserDto;
import everyone.delivery.demo.security.user.dtos.UpdateUserDto;
import everyone.delivery.demo.security.user.dtos.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	/**
	 * 전체 사용자 리스트 리턴
	 * 없으면 빈 리스트 리턴
	 * @return
	 * **/
	public List<UserDto> getList(){
		List<UserEntity> userEntityList = userRepository.findAll();
		List<UserDto> userDtoList = new ArrayList<>();
		for(UserEntity userEntity : userEntityList)
			userDtoList.add(this.convertEntityToDto(userEntity));
        
		return userDtoList;
	}
	
	/**
	 * {userId}에 해당하는 사용자 리턴
	 * @param userId
	 * @return
	 * **/
	public UserDto getById(Long userId) {
		Optional<UserEntity> userEntityOp = userRepository.findByUserId(userId);
		UserEntity userEntity = ExceptionUtils
				.ifNullThrowElseReturnVal(userEntityOp, "There is no corresponding information for userId. userId: {}",userId);

		return convertEntityToDto(userEntity);
	}

	/**
	 * {email}에 해당하는 사용자 리턴
	 * @param email
	 * @return
	 * **/
	public UserDto getByEmail(String email) {
		Optional<UserEntity> userEntityOp = userRepository.findByEmail(email);
		UserEntity userEntity = ExceptionUtils
				.ifNullThrowElseReturnVal(userEntityOp,"There is no corresponding information for email. email: {}", email);
		return convertEntityToDto(userEntity);
	}


	
	/**
	 * {userId}에 해당하는 사용자 수정
	 * @param updateUserDto
	 * @return
	 * **/
	public UserDto update(Long userId, UpdateUserDto updateUserDto) {
		Optional<UserEntity> userEntityOp = userRepository.findByUserId(userId);
		UserEntity userEntity = ExceptionUtils
				.ifNullThrowElseReturnVal(userEntityOp,"There is no corresponding information for userId. userId: {}", userId);

		userEntity.setEmail(updateUserDto.getEmail());
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity.setNickName(updateUserDto.getNickName());
		userEntity.setAddress(updateUserDto.getAddress());
		userEntity = userRepository.save(userEntity);
		return userEntity.toDTO();
	}
	
	
	/**
	 * {userId}에 해당하는 사용자 삭제
	 * @param userId
	 * @return
	 * **/
	@Transactional
	public Long delete(Long userId) {
		Optional<UserEntity> userEntityOp = userRepository.findByUserId(userId);
		ExceptionUtils
				.ifNullThrowElseReturnVal(userEntityOp,"There is no corresponding information for userId. userId: {}", userId);
		userRepository.deleteByUserId(userId);
		return userId;
	}


    public UserDetails loadUserByUsername(String email) {
        Optional<UserEntity> userEntityOp = userRepository.findByEmail(email);
		UserEntity userEntity = ExceptionUtils.ifNullThrowElseReturnVal(userEntityOp);
        return convertEntityToDto(userEntity);
    }
    
    
    private UserDto convertEntityToDto(UserEntity userEntity){
        return UserDto.builder()
        				.userId(userEntity.getUserId())
        				.email(userEntity.getEmail())
						.nickName(userEntity.getNickName())
        				.password(userEntity.getPassword())
        				.roles(userEntity.getRoles())
						.address(userEntity.getAddress())
        				.regDate(userEntity.getRegDate())
						.updateDate(userEntity.getUpdateDate())
        				.build();
    }
}