package ru.javawebinar.topjava.repository.jdbc;

import ru.javawebinar.topjava.model.AbstractBaseEntity;

import javax.validation.*;
import java.util.Set;

public class JdbcValidationEntity {

    private final static ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    private final static Validator validator = validatorFactory.getValidator();

    protected static void validationEntity(AbstractBaseEntity entity) {
        Set<ConstraintViolation<AbstractBaseEntity>> validate = validator.validate(entity);
        if (validate.size() != 0) {
            throw new ConstraintViolationException(validate);
        }
    }
}
