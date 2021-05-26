package cx.rain.mc.fabric.rainauth.data;

import com.google.gson.Gson;
import cx.rain.mc.fabric.rainauth.data.bean.BeanData;
import cx.rain.mc.fabric.rainauth.data.bean.BeanUser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class DataManager {
    private final Gson gson = new Gson();

    private BeanData data;
    private final File dataFile = new File("data/rainmods/rainauth/data.json");

    public DataManager() {
        try {
            internalInit();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot init auth data.");
        }
    }

    private void internalInit() throws IOException {
        if (!dataFile.exists()) {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }

            if (!dataFile.createNewFile()) {
                throw new RuntimeException("Cannot create data file.");
            }

            data = new BeanData();

            FileWriter writer = new FileWriter(dataFile);
            writer.write(gson.toJson(data));
            writer.flush();
            writer.close();
        } else {
            FileReader reader = new FileReader(dataFile);
            data = gson.fromJson(reader, BeanData.class);
            reader.close();
        }
    }

    private void saveData() throws IOException {
        FileWriter writer = new FileWriter(dataFile);
        writer.write(gson.toJson(data));
        writer.flush();
        writer.close();
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    private boolean checkPassword(String password, String hashed) {
        return BCrypt.checkpw(password, hashed);
    }

    public boolean hasRegistered(UUID uuid) {
       return data.getUsers().containsKey(uuid);
    }

    public void register(String name, UUID uuid, String password) {
        try {
            BeanUser user = new BeanUser(name, hashPassword(password));
            data.getUsers().put(uuid, user);
            saveData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public boolean login(String name, UUID uuid, String password) {
        if (!hasRegistered(uuid)) {
            return false;
        }

        return checkPassword(password, data.getUsers().get(uuid).getPasswordHashed());
    }
}
