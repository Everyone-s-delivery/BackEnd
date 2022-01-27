package everyone.delivery.demo.common.validation.annotaion;

import everyone.delivery.demo.common.validation.validator.NotDuplicatedValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)          //파라미터에 붙을 수 있다는 의미
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NotDuplicatedValidator.class)
public @interface NotDuplicated {
}
