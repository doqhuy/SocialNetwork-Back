package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Relationship;
import instaface.backend.validations.serviceValidation.services.RelationshipValidationService;
import org.springframework.stereotype.Component;

@Component
public class RelationshipValidationServiceImpl implements RelationshipValidationService {
    @Override
    public boolean isValid(Relationship relationship) {
        return relationship != null;
    }
}
