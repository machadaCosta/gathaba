package fr.machada.gathabaandroid.event;

import fr.machada.gathabaandroid.model.Repo;

/**
 * Created by macha on 18/12/15.
 */
public class SeeRepoDetailsEvent {
    public final Repo repo;

    public SeeRepoDetailsEvent(Repo repo) {
        this.repo = repo;
    }

    public Repo getRepo() {
        return repo;
    }
}
