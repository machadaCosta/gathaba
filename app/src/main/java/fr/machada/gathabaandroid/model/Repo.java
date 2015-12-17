package fr.machada.gathabaandroid.model;

import java.io.Serializable;
import java.util.Date;

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
    private String description;
    private String created_at;
    private String pushed_at;
    private int size;
    private String language;
    private int stargazers_count;
    private int watchers_count;

    @Ignore
    private boolean isFollowed;

    public Repo() {
    }

    public Repo(Repo repo) {
        this.id = repo.getId();
        this.name = repo.getName();
        this.full_name = repo.getFull_name();
        this.description = repo.getDescription();
        this.created_at = repo.getCreated_at();
        this.pushed_at = repo.getPushed_at();
        this.size = repo.getSize();
        this.language = repo.getLanguage();
        this.stargazers_count = repo.getStargazers_count();
        this.watchers_count = repo.getWatchers_count();
        this.isFollowed = repo.isFollowed();
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getPushed_at() {
        return pushed_at;
    }

    public void setPushed_at(String pushed_at) {
        this.pushed_at = pushed_at;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getStargazers_count() {
        return stargazers_count;
    }

    public void setStargazers_count(int stargazers_count) {
        this.stargazers_count = stargazers_count;
    }

    public int getWatchers_count() {
        return watchers_count;
    }

    public void setWatchers_count(int watchers_count) {
        this.watchers_count = watchers_count;
    }
}
