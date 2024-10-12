package md.practice.ecommerceapi.service;

import md.practice.ecommerceapi.dto.UserDTO;
import md.practice.ecommerceapi.entity.User;

public interface AuthenticationService {
    void signup(UserDTO input);

    User authenticate(UserDTO input);
}
