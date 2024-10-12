package md.practice.ecommerceapi.dto;

import lombok.Getter;
import lombok.Setter;
import md.practice.ecommerceapi.enums.Role;

@Getter @Setter
public class UserDTO {

    private String username;
    private String password;
    private Role role;
}
