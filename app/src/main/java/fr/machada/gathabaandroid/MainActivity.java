package fr.machada.gathabaandroid;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import fr.machada.gathabaandroid.model.Repo;
import fr.machada.gathabaandroid.service.GitHubService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity {

    private static final String API_BASE = "https://api.github.com";
    private static final String TAG = "MAIN ACTIVITY";
    Retrofit mRetrofit;
    GitHubService mService;
    Call<List<Repo>> mRepos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        prepareRequest();
        constructRequest();
        executeRequest();
    }

    private void executeRequest() {
        mRepos.enqueue(new Callback<List<Repo>>() {

            @Override
            public void onResponse(Response<List<Repo>> response, Retrofit retrofit) {
                Toast.makeText(getApplicationContext(), String.format("OK"), Toast.LENGTH_SHORT).show();
                List<Repo> allRepos = response.body();
                for (int i = 0; i < allRepos.size(); i++)
                    Log.i(TAG, String.format(" Un repo : %s", allRepos.get(i)));
            }

            @Override
            public void onFailure(Throwable t) {
                Toast.makeText(getApplicationContext(), String.format("KO"), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void constructRequest() {
        mRepos = mService.listRepos("machadacosta");

    }


    private void prepareRequest() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(API_BASE)
                .addConverterFactory(GsonConverterFactory.create())
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
