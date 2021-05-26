package cx.rain.mc.fabric.rainauth.data.bean;

public class BeanConfig {
    private String language = "zh_cn";
    private int secondsToDisconnect = 120;

    public int getSecondsToDisconnect() {
        return secondsToDisconnect;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setSecondsToDisconnect(int secondsToDisconnect) {
        this.secondsToDisconnect = secondsToDisconnect;
    }
}
