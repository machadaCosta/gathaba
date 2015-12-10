package fr.machada.gathabaandroid.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macha on 24/11/15.
 */
public class Repo extends RealmObject {

    @PrimaryKey
    private int id;
    private String name;
    private String full_name;

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
}
