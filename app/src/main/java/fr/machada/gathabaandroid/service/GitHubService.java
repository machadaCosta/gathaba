package fr.machada.gathabaandroid.service;

import java.util.List;

import fr.machada.gathabaandroid.model.Repo;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by macha on 24/11/15.
 */
public interface GitHubService {

    @GET("/users/{user}/repos")
    Call<List<Repo>> listRepos(@Path("user") String user);
}