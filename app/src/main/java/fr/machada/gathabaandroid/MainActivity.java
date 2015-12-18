package fr.machada.gathabaandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import de.greenrobot.event.EventBus;
import fr.machada.gathabaandroid.controllers.RepoDetailsActivity;
import fr.machada.gathabaandroid.controllers.UserNameDialogFragment;
import fr.machada.gathabaandroid.event.OnFollowRepoEvent;
import fr.machada.gathabaandroid.event.SeeRepoDetailsEvent;
import fr.machada.gathabaandroid.event.SettingsEvent;
import fr.machada.gathabaandroid.model.BundleKeys;
import fr.machada.gathabaandroid.model.PreferencesKeys;
import fr.machada.gathabaandroid.model.Repo;
import fr.machada.gathabaandroid.service.GitHubService;
import io.realm.Realm;
import io.realm.RealmObject;
import io.realm.RealmResults;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String API_BASE = "https://api.github.com";
    Retrofit mRetrofit;
    GitHubService mService;
    Call<List<Repo>> mRepos;
    private Realm mRealm;
    private List<Repo> mRepoList;
    private RepoListAdapter mAdapter;
    private RecyclerView mRecycler;
    static final int REPO_REQUEST = 1;  // The request code
    private Repo mRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setLogo(R.mipmap.ic_launcher);
        setSupportActionBar(toolbar);
        
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showUserNameDialogBox();
            }
        });

        instantiateBDD();

        prepareRequest();

        instantiateListView();
    }

    private void instantiateListView() {
        // set creator
        mRecycler = (RecyclerView) findViewById(R.id.repoRecycler);
        mAdapter = new RepoListAdapter(getRepoList());

        mRecycler.setAdapter(mAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(getApplicationContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecycler.setLayoutManager(manager);
    }

    private void deleteRepo(Repo repo) {
        mRealm.beginTransaction();
        RealmResults<Repo> repoToRemove = mRealm.where(Repo.class).equalTo("id", repo.getId()).findAll();
        if (repoToRemove.size() > 0)
            repoToRemove.get(0).removeFromRealm();
        mRealm.commitTransaction();
    }

    private void registerRepo(Repo repo) {
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(repo);
        mRealm.commitTransaction();
    }


    private void instantiateBDD() {
        mRealm = Realm.getInstance(this);
    }

    private void executeRequest(final boolean firstTime) {
        mRepoList = new ArrayList<>();
        mRepos.enqueue(new Callback<List<Repo>>() {

            @Override
            public void onResponse(Response<List<Repo>> response, Retrofit retrofit) {
                Toast.makeText(getApplicationContext(), String.format("%s %s", getString(R.string.repositories_retrieved_for_username), getUsername()), Toast.LENGTH_SHORT).show();
                List<Repo> result = response.body();
                if (firstTime) {
                    for (Repo r : result)
                        mRepoList.add(r);
                    mAdapter.notifyDataSetChanged();
                } else {
                    for (Repo r : result) {
                        RealmResults<Repo> resultQuery = mRealm.where(Repo.class).equalTo("id", r.getId()).findAll();
                        if (resultQuery.size() == 0)
                            mRepoList.add(r);
                    }
                    refreshListView();
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("%s", getString(R.string.wrong_username_or_not_connected)), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void constructRequest() {
        mRepos = mService.listRepos(getUsername());
    }

    private String getUsername() {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(PreferencesKeys.USERNAME, "machadacosta");
    }

    private Gson getGsonConverterAdaptedToReaml() {
        Gson gson = new GsonBuilder()
                .setExclusionStrategies(new ExclusionStrategy() {
                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        return f.getDeclaringClass().equals(RealmObject.class);
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                })
                .create();
        return gson;
    }


    private void prepareRequest() {
        Gson gson = getGsonConverterAdaptedToReaml();

        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_BASE)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        mService = mRetrofit.create(GitHubService.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            showUserNameDialogBox();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showUserNameDialogBox() {
        new UserNameDialogFragment().show(getFragmentManager(), null);
    }


    public void onEvent(SettingsEvent event) {
        registerUsernameInPreferences(event.getUsername());
        constructRequest();
        executeRequest(false);
    }

    public void onEvent(OnFollowRepoEvent event) {
        if (event.getToFollow())
            registerRepo(event.getRepo());
        else
            deleteRepo(event.getRepo());
    }

    public void onEvent(SeeRepoDetailsEvent event) {
        mRepo = event.getRepo();
        Intent intent = new Intent(getApplicationContext(), RepoDetailsActivity.class);
        Repo clonedRepo = new Repo(mRepo);
        intent.putExtra(BundleKeys.REPO, (Serializable) clonedRepo);
        startActivityForResult(intent, REPO_REQUEST);
    }

    private void refreshListView() {
        mAdapter.addRepoList(mRepoList);
        mAdapter.notifyDataSetChanged();
    }

    private void registerUsernameInPreferences(String username) {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(PreferencesKeys.USERNAME, username);
        editor.commit();
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    protected void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }


    public List<Repo> getRepoList() {
        RealmResults<Repo> repos = mRealm.where(Repo.class).findAll();
        List<Repo> repoList = new ArrayList();
        for (Repo repo : repos) {
            repo.setIsFollowed(true);
            repoList.add(repo);
        }
        if (repoList.size() > 0)
            return repoList;
        else {
            constructRequest();
            executeRequest(true);
            return mRepoList;
        }
    }
}
