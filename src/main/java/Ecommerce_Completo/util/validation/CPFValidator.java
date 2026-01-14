package Ecommerce_Completo.util.validation;

import Ecommerce_Completo.util.ValidaCPF;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CPFValidator implements ConstraintValidator<CPF, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return ValidaCPF.isCPF(value);
    }
}
