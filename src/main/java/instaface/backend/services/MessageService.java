package instaface.backend.services;

import instaface.backend.domain.models.bindingModels.message.MessageCreateBindingModel;
import instaface.backend.domain.models.serviceModels.MessageServiceModel;
import instaface.backend.domain.models.viewModels.message.MessageFriendsViewModel;

import java.util.List;

public interface MessageService {

    MessageServiceModel createMessage(MessageCreateBindingModel messageCreateBindingModel, String loggedInUsername) throws Exception;

    List<MessageServiceModel> getAllMessages(String loggedInUsername, String chatUserId);

    List<MessageFriendsViewModel> getAllFriendMessages(String loggedInUsername);
}
