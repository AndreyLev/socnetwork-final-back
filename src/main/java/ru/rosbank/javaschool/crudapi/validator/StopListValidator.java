package ru.rosbank.javaschool.crudapi.validator;

import ru.rosbank.javaschool.crudapi.constraint.StopList;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class StopListValidator implements ConstraintValidator<StopList, String> {
   private String[] list;

   public void initialize(StopList constraint) {
      list = constraint.value();
   }

   public boolean isValid(String value, ConstraintValidatorContext context) {
      // by default for most validators
      if (value == null) {
         return true;
      }
      return Arrays.stream(list).noneMatch(o -> value.toLowerCase().contains(o.toLowerCase()));
   }
}
