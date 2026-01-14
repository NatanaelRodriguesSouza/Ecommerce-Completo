package Ecommerce_Completo.util.validation;

import Ecommerce_Completo.util.ValidaCNPJ;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CNPJValidator implements ConstraintValidator<CNPJ, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isBlank()) return true;
        return ValidaCNPJ.isCNPJ(value);
    }
}