package cx.rain.mc.fabric.rainauth.data.bean;

import java.util.UUID;

public class BeanUser {
    private String username;
    private String passwordHashed;

    public BeanUser(String name, String password) {
        username = name;
        passwordHashed = password;
    }

    public String getPasswordHashed() {
        return passwordHashed;
    }

    public String getUsername() {
        return username;
    }
}
