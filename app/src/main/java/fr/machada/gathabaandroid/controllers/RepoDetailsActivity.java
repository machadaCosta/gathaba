package fr.machada.gathabaandroid.controllers;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import fr.machada.gathabaandroid.R;
import fr.machada.gathabaandroid.model.BundleKeys;
import fr.machada.gathabaandroid.model.Repo;

/**
 * Created by macha on 10/12/15.
 */
public class RepoDetailsActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo_details);

        Repo r = (Repo) getIntent().getSerializableExtra(BundleKeys.REPO);

        if (r != null) {

            TextView textRepoName = (TextView) findViewById(R.id.textRepoName);
            textRepoName.setText(r.getName());

            TextView textRepoDate = (TextView) findViewById(R.id.textRepoDate);
            textRepoDate.setText("12/12/30");

            TextView textRepoPath = (TextView) findViewById(R.id.textRepoPath);
            textRepoPath.setText(r.getFull_name());
        }

    }
}
