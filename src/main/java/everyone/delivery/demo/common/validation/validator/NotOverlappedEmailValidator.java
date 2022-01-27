package everyone.delivery.demo.common.validation.validator;

import everyone.delivery.demo.common.validation.annotaion.NotOverlappedEmail;
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
public class NotOverlappedEmailValidator implements ConstraintValidator<NotOverlappedEmail,String> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserEntity> byEmail = userRepository.findByEmail(email);
        if(byEmail.isPresent()){
            log.error("email overlap. email: {}", email);
            return false;   //존재한다 => email은 중복된다 => 검증 오류
        }
        else
            return true;
    }
}
