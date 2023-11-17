package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.UserRole;

public interface RoleValidationService {
    boolean isValid(UserRole role);
}
