package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Picture;
import instaface.backend.validations.serviceValidation.services.PictureValidationService;
import org.springframework.stereotype.Component;

@Component
public class PictureValidationServiceImpl implements PictureValidationService {
    @Override
    public boolean isValid(Picture picture) {
        return picture != null;
    }
}
