package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.Post;
import instaface.backend.domain.models.bindingModels.post.PostCreateBindingModel;

public interface PostValidationService {
    boolean isValid(Post post);

    boolean isValid(PostCreateBindingModel postCreateBindingModel);
}
