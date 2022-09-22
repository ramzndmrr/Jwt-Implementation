package accountManagement.entities;

import lombok.Data;

@Data
public class UserRequestForRegister {
	 private String firstName;
	    private String lastName;
	    private String userName;
	    private String password;
}
