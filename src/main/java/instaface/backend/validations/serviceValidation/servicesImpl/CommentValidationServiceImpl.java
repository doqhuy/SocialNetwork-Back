package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Comment;
import instaface.backend.domain.models.bindingModels.comment.CommentCreateBindingModel;
import instaface.backend.validations.serviceValidation.services.CommentValidationService;
import org.springframework.stereotype.Component;

@Component
public class CommentValidationServiceImpl implements CommentValidationService {
    @Override
    public boolean isValid(Comment comment) {
        return comment != null;
    }

    @Override
    public boolean isValid(CommentCreateBindingModel commentCreateBindingModel) {
        return commentCreateBindingModel != null;
    }
}
