package everyone.delivery.demo.security.Sign;

import everyone.delivery.demo.common.exception.LogicalRuntimeException;
import everyone.delivery.demo.common.exception.error.UserError;
import everyone.delivery.demo.security.JWT.JwtTokenProvider;
import everyone.delivery.demo.security.Sign.model.TokenResult;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import everyone.delivery.demo.security.user.UserRole;
import everyone.delivery.demo.security.user.dtos.BasicUserDto;
import everyone.delivery.demo.security.user.dtos.UserDto;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class SignService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;


    /***
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public TokenResult signin(String email, String password) {
        UserEntity findedUserEntity = userRepository.findByEmail(email);
        if(findedUserEntity == null ) {
            log.error("login fail, check email. email: {}", email);
            throw new LogicalRuntimeException(UserError.LOGIN_FAIL_EMAIL);
        }

        if (!passwordEncoder.matches(password, findedUserEntity.getPassword())){
            log.error("login fail, check password. password: {}", password);
            throw new LogicalRuntimeException(UserError.LOGIN_FAIL_PASSWORD);
        }

        return new TokenResult(
                jwtTokenProvider.createToken(String.valueOf(findedUserEntity.getEmail()), findedUserEntity.getRoles()),findedUserEntity.getUserId());
    }

    /***
     * 회원가입
     * @param basicUserDto
     * @return
     */
    public UserDto signup(BasicUserDto basicUserDto) {
        if(userRepository.findByEmail(basicUserDto.getEmail()) != null){
            log.error("email overlap. email: {}", basicUserDto.getEmail());
            throw new LogicalRuntimeException(UserError.SIGNUP_FAIL_EMAIL_OVERLAP);
        }

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_PARTICIPANTS);
        roles.add(UserRole.ROLE_RECRUITER);
        if(basicUserDto.getEmail().equals("admin@admin.com"))
            roles.add(UserRole.ROLE_ADMIN);

        UserEntity userEntity = UserEntity.builder()
                .email(basicUserDto.getEmail())
                .password(passwordEncoder.encode(basicUserDto.getPassword()))
                .address(basicUserDto.getAddress())
                .roles(roles).build();

        userEntity = userRepository.save(userEntity);
        return userEntity.toDTO();
    }
}
