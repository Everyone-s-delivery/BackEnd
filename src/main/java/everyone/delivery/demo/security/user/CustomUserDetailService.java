package everyone.delivery.demo.security.user;
import everyone.delivery.demo.common.exception.ClientDataValidationException;
import everyone.delivery.demo.security.user.dtos.BasicUserDto;
import everyone.delivery.demo.security.user.dtos.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class CustomUserDetailService implements UserDetailsService {

	@Autowired
    private UserRepository userRepository;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	/**
	 * 전체 사용자 리스트 리턴
	 * @return
	 * **/
	public List<UserDto> getList(){
		List<UserEntity> userEntityList = userRepository.findAll();
		if(userEntityList.size() == 0)
			throw new ClientDataValidationException("There is no corresponding information.");

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
		if(userId < 0)
			throw new ClientDataValidationException("userId cannot be minus.");
		UserEntity userEntity = userRepository.findByuserId(userId);
		if(userEntity == null)
			throw new ClientDataValidationException("There is no corresponding information for userId.");
		return convertEntityToDto(userEntity);
	}

	/**
	 * {email}에 해당하는 사용자 리턴
	 * @param email
	 * @return
	 * **/
	public UserDto getByEmail(String email) {
		if(email == null)
			throw new ClientDataValidationException("email cannot be null.");
		UserEntity userEntity = userRepository.findByEmail(email);
		if(userEntity == null)
			throw new ClientDataValidationException("There is no corresponding information for email.");
		return convertEntityToDto(userEntity);
	}

//	/**
//	 * 하나의 사용자 등록
//	 * @param userDto
//	 * @return
//	 * **/
//	public Long save(BasicUserDto userDto) {
//		if(userDto.getEmail() == null || userDto.getPassword() == null || userDto.getRoles() == null)
//			throw new ClientDataValidationException("Not enough user data.");
//
//		UserEntity userEntity = userDto.toEntity();
//		userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
//		userRepository.save(userEntity);
//		return userEntity.getUserId();
//	}
	
	/**
	 * {userId}에 해당하는 사용자 수정
	 * @param userDto
	 * @return
	 * **/
	public UserDto update(Long userId, BasicUserDto userDto) {
		if(userId <= 0)
			throw new ClientDataValidationException("userId cannot be minus.");

		if(userRepository.findByuserId(userId) ==null)
			throw new ClientDataValidationException("There is no corresponding information for userId.");

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
		if(userId <= 0) 
			throw new ClientDataValidationException("userId cannot be minus.");
		UserEntity userEntity = userRepository.findByuserId(userId);
		if(userEntity ==null)
			throw new ClientDataValidationException("There is no corresponding information for userId.");
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