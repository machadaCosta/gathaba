package fr.machada.gathabaandroid.controllers;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
            textRepoDate.setText(r.getCreated_at().substring(0, 10));

            TextView textRepoPath = (TextView) findViewById(R.id.textRepoPath);
            textRepoPath.setText(r.getFull_name());

            TextView textRepoDesc = (TextView) findViewById(R.id.textRepoDesc);
            textRepoDesc.setText(r.getDescription());

            TextView textRepoSize = (TextView) findViewById(R.id.textRepoSize);
            textRepoSize.setText(String.valueOf(r.getSize()));

            TextView textRepoLang = (TextView) findViewById(R.id.textRepoLang);
            textRepoLang.setText(r.getLanguage());

            TextView textRepoLastDate = (TextView) findViewById(R.id.textRepoLastDate);
            textRepoLastDate.setText(r.getPushed_at().substring(0, 10));

            TextView textRepoStar = (TextView) findViewById(R.id.textRepoStar);
            textRepoStar.setText(String.valueOf(r.getStargazers_count()));


            if (r.isFollowed()) {
                findViewById(R.id.repoDetailsButtonNegatif).setVisibility(View.VISIBLE);
                findViewById(R.id.repoDetailsButtonPositif).setVisibility(View.GONE);
            }

        }

    }

    public void unfollowRepo(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(BundleKeys.FOLLOW, false);
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }

    public void followRepo(View view) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(BundleKeys.FOLLOW, true);
        setResult(Activity.RESULT_OK, returnIntent);
        this.finish();
    }
}
