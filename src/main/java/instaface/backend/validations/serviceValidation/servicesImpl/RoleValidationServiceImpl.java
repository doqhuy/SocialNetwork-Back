package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.UserRole;
import instaface.backend.validations.serviceValidation.services.RoleValidationService;
import org.springframework.stereotype.Component;

@Component
public class RoleValidationServiceImpl implements RoleValidationService {
    @Override
    public boolean isValid(UserRole role) {
        return role != null;
    }
}
