package everyone.delivery.demo.common.validation.validator;

import everyone.delivery.demo.common.validation.annotaion.NotDuplicated;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotDuplicatedValidator implements ConstraintValidator<NotDuplicated,String> {


    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }
}
