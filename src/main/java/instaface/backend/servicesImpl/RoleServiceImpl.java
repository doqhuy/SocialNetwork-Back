package instaface.backend.servicesImpl;

import instaface.backend.domain.entities.UserRole;
import instaface.backend.repositories.RoleRepository;
import instaface.backend.services.RoleService;
import instaface.backend.validations.serviceValidation.services.RoleValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static instaface.backend.utils.constants.ResponseMessageConstants.SERVER_ERROR_MESSAGE;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleValidationService roleValidation;

    @Autowired
    public RoleServiceImpl(RoleRepository roleRepository, RoleValidationService roleValidation) {
        this.roleRepository = roleRepository;
        this.roleValidation = roleValidation;
    }

    @Override
    public boolean persist(UserRole role) throws Exception {
        if(!roleValidation.isValid(role)){
            throw new Exception(SERVER_ERROR_MESSAGE);
        }

        return this.roleRepository.save(role) != null;
    }

    @Override
    public UserRole getByName(String name) {
        return this.roleRepository.findByAuthority(name);
    }
}
