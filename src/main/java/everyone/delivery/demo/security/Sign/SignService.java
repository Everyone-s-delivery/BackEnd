package everyone.delivery.demo.security.Sign;

import everyone.delivery.demo.Response.CommonResult;
import everyone.delivery.demo.Response.ResponseService;
import everyone.delivery.demo.Response.SingleResult;
import everyone.delivery.demo.exception.SignFailedException;
import everyone.delivery.demo.security.JWT.JwtTokenProvider;
import everyone.delivery.demo.security.Sign.model.SignInResult;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import everyone.delivery.demo.security.user.UserRole;
import everyone.delivery.demo.security.user.dtos.UserDto;
import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SignService {

    private final UserRepository userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final ResponseService responseService;
    private final PasswordEncoder passwordEncoder;


    /***
     * 로그인
     * @param email
     * @param password
     * @return
     */
    public SingleResult<SignInResult> signin(String email, String password) {
        UserEntity findedUserEntity = userRepository.findByEmail(email);
        if(findedUserEntity == null )
            throw new SignFailedException("login fail, check email");

        if (!passwordEncoder.matches(password, findedUserEntity.getPassword()))
            throw new SignFailedException("login fail, check password");

        return responseService.getSingleResult(new SignInResult(
                jwtTokenProvider.createToken(String.valueOf(findedUserEntity.getEmail()), findedUserEntity.getRoles()),findedUserEntity.getUserId()));
    }

    /***
     * 회원가입
     * @param userDto
     * @return
     */
    public CommonResult signup(UserDto userDto) {
        if(userRepository.findByEmail(userDto.getEmail()) != null)
            throw new SignFailedException("email overlap");

        List<UserRole> roles = new ArrayList<>();
        roles.add(UserRole.ROLE_PARTICIPANTS);
        roles.add(UserRole.ROLE_RECRUITER);
        if(userDto.getEmail().equals("admin"))
            roles.add(UserRole.ROLE_ADMIN);

        UserEntity userEntity = UserEntity.builder()
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .address(userDto.getAddress())
                .interestedAddress(userDto.getInterestedAddress())
                .roles(roles).build();

        userEntity = userRepository.save(userEntity);
        return responseService.getSingleResult(userEntity);
    }
}
