package com.xxx.mianshiya.user.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.RecordComponent;

public class PasswordConfirmedValidator implements ConstraintValidator<PasswordConfirmed, Object> {

    private String passwordField;
    private String confirmField;

    @Override
    public void initialize(PasswordConfirmed constraintAnnotation) {
        this.passwordField = constraintAnnotation.passwordField();
        this.confirmField = constraintAnnotation.confirmField();

        if (passwordField == null || confirmField == null ||
                passwordField.equals(confirmField)) {
            throw new IllegalArgumentException("@PasswordConfirmed: passwordField and confirmField must be different and not null");
        }
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            String password = getFieldValue(value, passwordField);
            String confirmPassword = getFieldValue(value, confirmField);
            if (password == null || confirmPassword == null) {
                return false;
            }
            return password.equals(confirmPassword);
        } catch (Exception e) {
            // 反射失败
            return false;
        }
    }

    private String getFieldValue(Object obj, String fieldName) throws Exception {
        Class<?> clazz = obj.getClass();
        // 支持 record 类型
        if (clazz.isRecord()) {
            for (RecordComponent rc : clazz.getRecordComponents()) {
                if (rc.getName().equals(fieldName)) {
                    return (String) rc.getAccessor().invoke(obj);
                }
            }
        } else {
            var field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            return (String) field.get(obj);
        }
        throw new NoSuchFieldException("Field '" + fieldName + "' not found in " + clazz.getName());
    }
}
