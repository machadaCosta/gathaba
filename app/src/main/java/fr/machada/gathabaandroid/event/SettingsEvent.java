package fr.machada.gathabaandroid.event;

/**
 * Created by macha on 07/12/15.
 */
public class SettingsEvent {
    public final String username;

    public SettingsEvent(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
