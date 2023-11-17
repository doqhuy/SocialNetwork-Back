package instaface.backend.validations.serviceValidation.services;

import instaface.backend.domain.entities.User;
import instaface.backend.domain.models.bindingModels.user.UserRegisterBindingModel;
import instaface.backend.domain.models.bindingModels.user.UserUpdateBindingModel;
import instaface.backend.domain.models.serviceModels.UserServiceModel;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserValidationService {
    boolean isValid(User user);

    boolean isValid(UserServiceModel userServiceModel);

    boolean isValid(UserRegisterBindingModel userRegisterBindingModel);

    boolean isValid(String firstParam, String secondParam);

    boolean isValid(UserUpdateBindingModel userUpdateBindingModel);

    boolean isValid(UserDetails userData);
}
