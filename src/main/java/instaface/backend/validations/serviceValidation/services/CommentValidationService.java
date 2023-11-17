package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.Comment;
import instaface.backend.domain.models.bindingModels.comment.CommentCreateBindingModel;

public interface CommentValidationService {
    boolean isValid(Comment comment);

    boolean isValid(CommentCreateBindingModel commentCreateBindingModel);
}
