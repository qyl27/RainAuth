package cx.rain.mc.fabric.rainauth.data.bean;

import java.util.*;

public class BeanData {
    private Map<UUID, BeanUser> users = new HashMap<>();

    public Map<UUID, BeanUser> getUsers() {
        return users;
    }

    public void setUsers(Map<UUID, BeanUser> users) {
        this.users = users;
    }
}
