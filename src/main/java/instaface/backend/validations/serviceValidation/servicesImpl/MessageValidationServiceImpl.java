package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.models.bindingModels.message.MessageCreateBindingModel;
import instaface.backend.validations.serviceValidation.services.MessageValidationService;
import org.springframework.stereotype.Component;

@Component
public class MessageValidationServiceImpl implements MessageValidationService {

    @Override
    public boolean isValid(MessageCreateBindingModel messageCreateBindingModel) {
        return messageCreateBindingModel != null;
    }
}
