package instaface.backend.validations.serviceValidation.servicesImpl;

import instaface.backend.domain.entities.UserRole;
import instaface.backend.validations.serviceValidation.services.RoleValidationService;
import instaface.backend.testUtils.RolesUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RoleValidationServiceTests {
    private RoleValidationService roleValidationService;

    @Before
    public void setupTest(){
        roleValidationService = new RoleValidationServiceImpl();
    }

    @Test
    public void isValid_whenValid_true(){
        UserRole userRole = RolesUtils.createUserRole();
        boolean result = roleValidationService.isValid(userRole);
        assertTrue(result);
    }

    @Test
    public void isValid_whenNull_false(){
        UserRole userRole =  null;
        boolean result = roleValidationService.isValid(userRole);
        assertFalse(result);
    }
}
