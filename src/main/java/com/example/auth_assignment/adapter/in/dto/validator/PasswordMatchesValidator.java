package com.example.auth_assignment.adapter.in.dto.validator;

import com.example.auth_assignment.adapter.in.dto.RegisterRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, RegisterRequest> {

    @Override
    public boolean isValid(RegisterRequest request, ConstraintValidatorContext context) {
        if (request.getPassword() == null || request.getConfirmPassword() == null) {
            return false;
        }
        boolean isMatch = request.getPassword().equals(request.getConfirmPassword());
        if (!isMatch) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords do not match")
                    .addPropertyNode("confirmPassword") // ชี้ไปยัง field ที่ผิด
                    .addConstraintViolation();
        }
        return isMatch;
    }
}
