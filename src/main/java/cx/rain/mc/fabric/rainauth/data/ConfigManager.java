package cx.rain.mc.fabric.rainauth.data;

import com.google.gson.Gson;
import cx.rain.mc.fabric.rainauth.data.bean.BeanConfig;
import cx.rain.mc.fabric.rainauth.data.bean.BeanData;
import cx.rain.mc.fabric.rainauth.data.bean.BeanUser;

import javax.xml.bind.DatatypeConverter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.UUID;

public class ConfigManager {
    private final Gson gson = new Gson();

    private BeanConfig config;
    private final File configFile = new File("data/rainmods/rainauth/config.json");

    private BeanData data;
    private final File dataFile = new File("data/rainmods/rainauth/data.json");

    public ConfigManager() {
        try {
            internalInit();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot init auth data.");
        }
    }

    private void internalInit() throws IOException {
        if (!configFile.exists()) {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }

            if (!configFile.createNewFile()) {
                throw new RuntimeException("Cannot create config file.");
            }

            config = new BeanConfig();

            FileWriter writer = new FileWriter(configFile);
            writer.write(gson.toJson(config));
            writer.flush();
            writer.close();
        } else {
            FileReader reader = new FileReader(configFile);
            config = gson.fromJson(reader, BeanConfig.class);
            reader.close();
        }

        if (!dataFile.exists()) {
            if (!dataFile.getParentFile().exists()) {
                dataFile.getParentFile().mkdirs();
            }

            if (!dataFile.createNewFile()) {
                throw new RuntimeException("Cannot create data file.");
            }

            data = new BeanData();
            data.Users = new ArrayList<>();

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

    private void saveConfig() throws IOException {
        FileWriter writer = new FileWriter(configFile);
        writer.write(gson.toJson(config));
        writer.flush();
        writer.close();
    }

    private void saveData() throws IOException {
        FileWriter writer = new FileWriter(dataFile);
        writer.write(gson.toJson(data));
        writer.flush();
        writer.close();
    }

    private String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest((password + config.Salt).getBytes());
            return DatatypeConverter.printHexBinary(digest).toLowerCase();
        } catch (NoSuchAlgorithmException ignored) {
            return null;
        }
    }

    public boolean hasRegistered(String name, UUID uuid) {
        for (BeanUser u : data.Users) {
            if (u.Uuid == uuid) {
                return true;
            }
        }
        return false;
    }

    public void register(String name, UUID uuid, String password) {
        try {
            BeanUser user = new BeanUser();
            user.Username = name;
            user.Uuid = uuid;
            user.PasswordSalt = config.Salt;
            user.PasswordHashed = hashPassword(password);
            data.Users.add(user);
            saveData();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
