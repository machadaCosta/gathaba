package fr.machada.gathabaandroid.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by macha on 24/11/15.
 */
public class Repo extends RealmObject {

    @PrimaryKey
    int id;
    String name;

    public Repo(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
