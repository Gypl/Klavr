package com.company.klavr.screen.register;

import com.company.klavr.screen.register.*;
import io.jmix.core.DataManager;
import io.jmix.core.LoadContext;
import com.company.klavr.entity.User;
import io.jmix.core.querycondition.PropertyCondition;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UserExistsValidator implements ConstraintValidator<CheckUserExists, String> {
    @Override
    public void initialize(CheckUserExists constraintAnnotation) {}
    @Autowired
    private DataManager dataManager;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        List<User> userList = dataManager.load(User.class).condition(PropertyCondition.equal("login", value.toLowerCase()))
                .list();
        long existing = userList.size();
        return existing < 1;
    }
}
