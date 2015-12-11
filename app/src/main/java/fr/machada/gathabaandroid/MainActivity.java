package fr.machada.gathabaandroid;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
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
    private SwipeMenuListView mListView;

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
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {
                // add to menu
                switch (menu.getViewType()) {
                    case 0:
                        menu.addMenuItem(getMenuItem(false));
                        break;
                    case 1:
                        menu.addMenuItem(getMenuItem(true));
                        break;
                }
            }
        };

        // set creator
        mListView = (SwipeMenuListView) findViewById(R.id.listView);
        mAdapter = new RepoListAdapter(getRepoList());
        mListView.setAdapter(mAdapter);
        mListView.setMenuCreator(creator);

        mListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch (index) {
                    case 0:
                        Repo r = mAdapter.getItem(position);
                        if (!r.isFollowed()) {
                            // follow is on way
                            followItem(position);
                        } else {
                            // delete from realm db
                            deleteItem(position);
                        }
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RepoDetailsActivity.class);
                Repo repo = mAdapter.getItem(position);

                Repo clonedRepo = new Repo();
                clonedRepo.setName(repo.getName());
                clonedRepo.setFull_name(repo.getFull_name());
                clonedRepo.setId(repo.getId());
                clonedRepo.setIsFollowed(repo.isFollowed());

                intent.putExtra(BundleKeys.REPO, (Serializable) clonedRepo);
                startActivity(intent);
            }
        });
    }

    private void deleteItem(int position) {
        mRealm.beginTransaction();
        RealmResults<Repo> repoToRemove = mRealm.where(Repo.class).equalTo("id", mAdapter.getItemId(position)).findAll();
        if (repoToRemove.size() > 0)
            repoToRemove.get(0).removeFromRealm();
        mRealm.commitTransaction();
        //delete from adapter
        mAdapter.remove(position);
        mAdapter.notifyDataSetChanged();
    }

    private void followItem(int position) {
        Repo repo = mAdapter.getItem(position);
        mRealm.beginTransaction();
        mRealm.copyToRealmOrUpdate(repo);
        mRealm.commitTransaction();
        //refresh adapter and listView
        repo.setIsFollowed(true);
        mAdapter.notifyDataSetChanged();
        mListView.setAdapter(mAdapter);
    }

    private SwipeMenuItem getMenuItem(boolean isNew) {
        // create "follow" item
        SwipeMenuItem openItem = new SwipeMenuItem(
                getApplicationContext());
        if (isNew) {
            // set item background
            openItem.setBackground(R.color.colorGreen);
            // set item title
            openItem.setTitle(R.string.follow);
        } else {
            openItem.setBackground(R.color.colorRed);
            openItem.setTitle(R.string.unfollow);
        }
        // set item width
        openItem.setWidth(Utils.dp2px(getApplicationContext(), 100));
        // set item title fontsize
        openItem.setTitleSize(18);
        // set item title font color
        openItem.setTitleColor(Color.WHITE);
        return openItem;
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
