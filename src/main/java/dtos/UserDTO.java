package dtos;

import entities.Role;
import entities.User;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class UserDTO {


    private String user_name;
    private String user_pass;
    private String address;
    private String phone;
    private String email;
    private String birthYear;
    private String gender;
    private List<String> roles = new ArrayList<>();

    // Constructors, getters, and setters

    public UserDTO(User user) {
        this.user_name = user.getUser_name();
        this.user_pass = user.getUserPass();
        this.address = user.getAddress();
        this.phone = user.getPhone();
        this.email = user.getEmail();
        this.birthYear = user.getBirthYear();
        this.gender = user.getGender();
        for (Role role : user.getRoleList()) {
            this.roles.add(role.getRoleName());
        }
    }

    // Getters and Setters

    // Other methods (if any)
}
