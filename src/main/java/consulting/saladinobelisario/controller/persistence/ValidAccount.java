package consulting.saladinobelisario.controller.persistence;

import java.io.Serializable;

public class ValidAccount implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public ValidAccount(String address, String password) {
        this.address = address;
        this.password = password;
    }
    private String address;
    public String getAddress() {
        return address;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
    private String password;

}
