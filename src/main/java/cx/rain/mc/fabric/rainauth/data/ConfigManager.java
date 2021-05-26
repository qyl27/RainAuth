package cx.rain.mc.fabric.rainauth.data;

import com.google.gson.Gson;
import cx.rain.mc.fabric.rainauth.data.bean.BeanConfig;
import cx.rain.mc.fabric.rainauth.data.bean.BeanData;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class ConfigManager {
    private final Gson gson = new Gson();

    private BeanConfig config;
    private final File configFile = new File("data/rainmods/rainauth/config.json");

    public ConfigManager() {
        try {
            internalInit();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new RuntimeException("Cannot init config.");
        }
    }

    private void internalInit() throws IOException {
        if (!configFile.exists()) {
            if (!configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }

            if (!configFile.createNewFile()) {
                throw new RuntimeException("Cannot create data file.");
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
    }

    private void saveConfig() throws IOException {
        FileWriter writer = new FileWriter(configFile);
        writer.write(gson.toJson(config));
        writer.flush();
        writer.close();
    }

    public void save() {
        try {
            saveConfig();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public BeanConfig getConfig() {
        return config;
    }
}
