package ru.rosbank.javaschool.crudapi.validator;

import ru.rosbank.javaschool.crudapi.constraint.Email;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EmailValidator implements ConstraintValidator<Email, String> {


    @Override
    public boolean isValid(String emailValue, ConstraintValidatorContext constraintValidatorContext) {
        if (emailValue == null) {
            return true;
        }

        Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher mather = pattern.matcher(emailValue);
        return mather.matches();
    }
}
