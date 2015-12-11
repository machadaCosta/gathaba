package fr.machada.gathabaandroid.model;

import java.io.Serializable;

import io.realm.RealmObject;
import io.realm.annotations.Ignore;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macha on 24/11/15.
 */
public class Repo extends RealmObject implements Serializable {

    @PrimaryKey
    private int id;
    private String name;
    private String full_name;

    @Ignore
    private boolean isFollowed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public boolean isFollowed() {
        return isFollowed;
    }

    public void setIsFollowed(boolean isNew) {
        this.isFollowed = isNew;
    }
}
