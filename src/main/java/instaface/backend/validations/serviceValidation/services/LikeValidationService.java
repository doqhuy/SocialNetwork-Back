package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.Like;

public interface LikeValidationService {
    boolean isValid(Like like);
}
