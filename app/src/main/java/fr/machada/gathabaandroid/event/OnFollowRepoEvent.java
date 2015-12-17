package fr.machada.gathabaandroid.event;

import fr.machada.gathabaandroid.model.Repo;

/**
 * Created by macha on 17/12/15.
 */
public class OnFollowRepoEvent {
    public final Repo repo;
    public final Boolean toFollow;

    public OnFollowRepoEvent(Repo repo) {
        this.repo = repo;
        this.toFollow = true;
    }

    public OnFollowRepoEvent(Repo repo, boolean b) {
        this.repo = repo;
        this.toFollow =false;
    }

    public Repo getRepo() {
        return repo;
    }

    public Boolean getToFollow() {
        return toFollow;
    }
}
