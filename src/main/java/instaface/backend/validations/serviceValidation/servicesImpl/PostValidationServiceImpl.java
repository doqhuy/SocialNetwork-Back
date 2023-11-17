package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Post;
import instaface.backend.domain.models.bindingModels.post.PostCreateBindingModel;
import instaface.backend.validations.serviceValidation.services.PostValidationService;
import org.springframework.stereotype.Component;

@Component
public class PostValidationServiceImpl implements PostValidationService {
    @Override
    public boolean isValid(Post post) {
        return post != null;
    }

    @Override
    public boolean isValid(PostCreateBindingModel postCreateBindingModel) {
        return postCreateBindingModel != null;
    }
}
