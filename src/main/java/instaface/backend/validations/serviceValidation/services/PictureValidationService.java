package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.Picture;

public interface PictureValidationService {
    boolean isValid(Picture picture);
}
