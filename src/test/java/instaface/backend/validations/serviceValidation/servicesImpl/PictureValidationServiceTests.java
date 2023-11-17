package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.Picture;
import instaface.backend.domain.entities.User;
import instaface.backend.validations.serviceValidation.services.PictureValidationService;
import instaface.backend.testUtils.PictureUtils;
import instaface.backend.testUtils.UsersUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class PictureValidationServiceTests {
    private PictureValidationService pictureValidationService;

    @Before
    public void setupTest() {
        pictureValidationService = new PictureValidationServiceImpl();
    }

    @Test
    public void isValid_whenValid_true() {
        User user = UsersUtils.createUser();
        Picture picture = PictureUtils.createPicture(user);

        boolean result = pictureValidationService.isValid(picture);
        assertTrue(result);
    }

    @Test
    public void isValid_whenNull_false() {
        Picture picture = null;
        boolean result = pictureValidationService.isValid(picture);
        assertFalse(result);
    }
}
