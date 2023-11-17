package instaface.backend.testUtils;

import instaface.backend.domain.entities.User;
import instaface.backend.domain.entities.UserRole;
import instaface.backend.domain.models.bindingModels.user.UserRegisterBindingModel;
import instaface.backend.domain.models.bindingModels.user.UserUpdateBindingModel;
import instaface.backend.domain.models.serviceModels.UserServiceModel;
import instaface.backend.domain.models.viewModels.user.UserCreateViewModel;
import instaface.backend.domain.models.viewModels.user.UserDetailsViewModel;
import instaface.backend.domain.models.viewModels.user.UserEditViewModel;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class UsersUtils {
    public static User createUser() {
        return new User() {{
            setId("1");
            setPassword("1234");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyenvana");
            setEmail("nguyenvana@gmail.com");
            setBirthDay("01/01/2000");
            setGender("Nam");
            setInfo("Hello");
            setCity("TP. Hồ Chí Minh");
            setAddress("Thủ Đức");
            setProfilePicUrl("profilePic");
            setBackgroundImageUrl("backgroundPic");
            setOnline(false);
        }};
    }

    public static List<User> getUsers(int count) {
        return IntStream.range(0, count)
                .mapToObj(index -> new User() {{
                    setId(String.valueOf(index + 1));
                    setPassword("1234");
                    setFirstName("Nguyen " + index);
                    setLastName("A " + index);
                    setUsername("nguyenvana " + index);
                    setEmail("nguyenvana " + index + " @gmail.com");
                    setBirthDay("01/01/2000");
                    setGender("Nam");
                    setInfo("Hello");
                    setCity("TP. Hồ Chí Minh");
                    setAddress("Thủ Đức");
                    setProfilePicUrl("profilePic " + index);
                    setBackgroundImageUrl("backgroundPic " + index);
                    setOnline(false);
                }})
                .collect(Collectors.toList());
    }

    public static List<UserServiceModel> getUserServiceModels(int count, UserRole role) {
        LocalDateTime time = LocalDateTime.now();

        return IntStream.range(0, count)
                .mapToObj(index -> new UserServiceModel() {{
                    setId(String.valueOf(index + 1));
                    setPassword("1234");
                    setFirstName("Nguyen " + index);
                    setLastName("A " + index);
                    setUsername("nguyenvana " + index);
                    setEmail("nguyenvana " + index + " @gmail.com");
                    setBirthDay("01/01/2000");
                    setGender("Nam");
                    setInfo("Hello");
                    setCity("TP. Hồ Chí Minh");
                    setAddress("Thủ Đức");
                    setProfilePicUrl("profilePic " + index);
                    setBackgroundImageUrl("backgroundPic " + index);
                    setAuthorities(new HashSet<>(Arrays.asList(role)));
                    setOnline(false);
                }})
                .collect(Collectors.toList());
    }

    public static UserRegisterBindingModel getUserRegisterBindingModel() {
        return new UserRegisterBindingModel() {{
            setPassword("1234");
            setConfirmPassword("1234");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyevana");
            setEmail("nguyevana@gmail.com");
            setBirthDay("01/01/2000");
            setGender("Nam");
            setGender("Nam");
            setCity("TP. Hồ Chí Minh");
            setAddress("Thủ Đức");
            setProfilePicUrl("profilePic");
            setBackgroundImageUrl("backgroundPic");
        }};
    }

    public static UserCreateViewModel getUserCreateViewModel() {
        return new UserCreateViewModel() {{
            setId("1");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyenvana");
            setEmail("nguyenvana@gmail.com");
            setBirthDay("01/01/2000");
            setGender("Nam");
            setInfo("Hello");
            setCity("TP. Hồ Chí Minh");
            setAddress("Thủ Đức");
        }};
    }

    public static UserDetailsViewModel getUserDetailsViewModel(UserRole role) {
        return new UserDetailsViewModel() {{
            setId("1");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyenvana");
            setEmail("nguyenvana@gmail.com");
            setCity("TP. Hồ Chí Minh");
            setAddress("Thủ Đức");
            setProfilePicUrl("profilePic");
            setBackgroundImageUrl("backgroundPic");
            setAuthorities(new HashSet<>(Arrays.asList(role)));
        }};
    }

    public static UserEditViewModel getUserEditViewModel() {
        return new UserEditViewModel() {{
            setId("1");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyenvana");
            setEmail("nguyenvana@gmail.com");
            setCity("TP. Hồ Chí Minh");
            setAddress("Thủ Đức");
            setProfilePicUrl("profilePic");
            setBackgroundImageUrl("backgroundPic");
        }};
    }

    public static UserUpdateBindingModel getUserUpdateBindingModel() {
        return new UserUpdateBindingModel() {{
            setId("1");
            setFirstName("Nguyen");
            setLastName("A");
            setUsername("nguyenvana");
            setEmail("nguyenvana@gmail.com");
            setCity("TP. Hồ Chí Minh");
            setBirthDay("01/01/2000");
            setGender("Nam");
            //setInfo("Hello");
            setAddress("Thủ Đức");
            setProfilePicUrl("profilePic");
            setBackgroundImageUrl("backgroundPic");
        }};
    }
}

