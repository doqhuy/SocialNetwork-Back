package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Like;
import instaface.backend.domain.entities.Post;
import instaface.backend.domain.entities.User;
import instaface.backend.validations.serviceValidation.services.LikeValidationService;
import instaface.backend.testUtils.LikesUtils;
import instaface.backend.testUtils.PostsUtils;
import instaface.backend.testUtils.UsersUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class LikeValidationServiceTests {
    private LikeValidationService likeValidationService;

    @Before
    public void setupTest() {
        likeValidationService = new LikeValidationServiceImpl();
    }

    @Test
    public void isValid_whenValid_true() {
        User user = UsersUtils.createUser();
        Post post = PostsUtils.createPost(user, user);
        Like like = LikesUtils.createLike(user, post);

        boolean result = likeValidationService.isValid(like);
        assertTrue(result);
    }

    @Test
    public void isValid_whenNull_false() {
        Like like = null;
        boolean result = likeValidationService.isValid(like);
        assertFalse(result);
    }
}
