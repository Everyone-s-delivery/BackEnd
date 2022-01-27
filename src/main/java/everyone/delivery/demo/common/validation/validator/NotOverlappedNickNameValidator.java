package everyone.delivery.demo.common.validation.validator;

import everyone.delivery.demo.common.validation.annotaion.NotOverlappedNickName;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Slf4j
@Component
@AllArgsConstructor
public class NotOverlappedNickNameValidator implements ConstraintValidator<NotOverlappedNickName,String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String nickName, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserEntity> byNickName = userRepository.findByNickName(nickName);
        if(byNickName.isPresent()){
            log.error("nickName overlap. email: {}", nickName);
            return false;   //존재한다 => email은 중복된다 => 검증 오류
        }
        else
            return true;
    }
}
