package everyone.delivery.demo.common.validation.validator;

import everyone.delivery.demo.common.validation.annotaion.MustExistUserId;
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
public class MustExistUserIdValidator implements ConstraintValidator<MustExistUserId,Long> {

    private UserRepository userRepository;

    @Override
    public boolean isValid(Long userId, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserEntity> byId = userRepository.findById(userId);
        if(!byId.isPresent()){
            log.error("This user does not exist. userId: {}", userId);
            return false;   //존재하지 않는다
        }
        else
            return true;
    }
}
