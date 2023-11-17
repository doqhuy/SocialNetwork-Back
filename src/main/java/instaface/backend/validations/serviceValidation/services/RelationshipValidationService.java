package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.Relationship;

public interface RelationshipValidationService {
    boolean isValid(Relationship relationship);
}
