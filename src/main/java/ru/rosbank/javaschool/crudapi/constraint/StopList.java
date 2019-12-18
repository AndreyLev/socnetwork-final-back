package ru.rosbank.javaschool.crudapi.constraint;

import ru.rosbank.javaschool.crudapi.validator.StopListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = StopListValidator.class)
public @interface StopList {
  String message() default "{javax.validation.constraints.StopList.message}";
  Class<?>[] groups() default { };
  Class<? extends Payload>[] payload() default { };
  String[] value() default {};
}
