package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.validations.serviceValidation.services.LikeValidationService;
import instaface.backend.domain.entities.Like;
import org.springframework.stereotype.Component;

@Component
public class LikeValidationServiceImpl implements LikeValidationService {
    @Override
    public boolean isValid(Like like) {
        return like != null;
    }
}
