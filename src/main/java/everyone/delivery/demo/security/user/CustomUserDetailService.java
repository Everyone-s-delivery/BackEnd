package everyone.delivery.demo.security.user;
import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.exception.error.CommonError;
import everyone.delivery.demo.security.user.dtos.BasicUserDto;
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
		UserEntity userEntity = userRepository.findByuserId(userId);
		if(userEntity == null){
			log.error("There is no corresponding information for userId. userId: {}", userId);
			throw new LogicalRuntimeException(CommonError.INVALID_DATA);
		}
		return convertEntityToDto(userEntity);
	}

	/**
	 * {email}에 해당하는 사용자 리턴
	 * @param email
	 * @return
	 * **/
	public UserDto getByEmail(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null){
			log.error("There is no corresponding information for email. email: {}", email);
			throw new LogicalRuntimeException(CommonError.INVALID_DATA);
		}
		return convertEntityToDto(userEntity);
	}


	
	/**
	 * {userId}에 해당하는 사용자 수정
	 * @param userDto
	 * @return
	 * **/
	public UserDto update(Long userId, BasicUserDto userDto) {
		if(userRepository.findByuserId(userId) ==null){
			log.error("There is no corresponding information for userId. userId: {}", userId);
			throw new LogicalRuntimeException(CommonError.INVALID_DATA);
		}

		UserEntity userEntity = userDto.toEntity();
		userEntity.setUserId(userId);
		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
		userEntity = userRepository.save(userEntity);
		return userEntity.toDTO();
	}
	
	
	/**
	 * {userId}에 해당하는 사용자 삭제
	 * @param userId
	 * @return
	 * **/
	@Transactional
	public UserDto delete(Long userId) {
		UserEntity userEntity = userRepository.findByuserId(userId);
		if(userEntity ==null){
			log.error("There is no corresponding information for userId. userId: {}", userId);
			throw new LogicalRuntimeException(CommonError.INVALID_DATA);
		}
		userRepository.deleteByUserId(userId);
		UserDto userDto = userEntity.toDTO();
		return userDto;
	}


    public UserDetails loadUserByUsername(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        return convertEntityToDto(userEntity);
    }
    
    
    private UserDto convertEntityToDto(UserEntity userEntity){
        return UserDto.builder()
        				.userId(userEntity.getUserId())
        				.email(userEntity.getEmail())
        				.password(userEntity.getPassword())
        				.roles(userEntity.getRoles())
						.address(userEntity.getAddress())
						.interestedAddress(userEntity.getInterestedAddress())
        				.regDate(userEntity.getRegDate())
						.updateDate(userEntity.getUpdateDate())
        				.build();
    }
}