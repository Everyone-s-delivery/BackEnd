package everyone.delivery.demo.common.validation.validator;

import everyone.delivery.demo.common.validation.annotaion.NotDuplicatedEmail;
import everyone.delivery.demo.common.validation.annotaion.NotDuplicatedNickName;
import everyone.delivery.demo.security.user.UserEntity;
import everyone.delivery.demo.security.user.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

@Component
@AllArgsConstructor
public class NotDuplicatedNickNameValidator implements ConstraintValidator<NotDuplicatedNickName,String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String nickName, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserEntity> byNickName = userRepository.findByNickName(nickName);
        if(byNickName.isPresent())
            return false;   //존재한다 => email은 중복된다 => 검증 오류
        else
            return true;
    }
}
