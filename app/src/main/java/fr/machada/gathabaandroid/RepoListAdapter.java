package fr.machada.gathabaandroid;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.io.Serializable;
import java.util.List;

import de.greenrobot.event.EventBus;
import fr.machada.gathabaandroid.controllers.RepoDetailsActivity;
import fr.machada.gathabaandroid.event.OnFollowRepoEvent;
import fr.machada.gathabaandroid.event.SeeRepoDetailsEvent;
import fr.machada.gathabaandroid.model.BundleKeys;
import fr.machada.gathabaandroid.model.Repo;

/**
 * Created by macha on 08/12/15.
 */
public class RepoListAdapter extends RecyclerView.Adapter {

    private List<Repo> mRepos;
    private Context mContext;

    public RepoListAdapter(List<Repo> repos) {
        mRepos = repos;
    }


    @Override
    public long getItemId(int position) {
        return mRepos.get(position).getId();
    }

    @Override
    public int getItemCount() {
        return mRepos.size();
    }


    public void remove(int position) {
        mRepos.remove(position);
    }

    public void addRepoList(List<Repo> repoList) {
        for (Repo r : repoList)
            mRepos.add(r);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_list_repo, parent, false);
        RecyclerView.ViewHolder vh = new RepoViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Repo repo = mRepos.get(position);
        populateRepo((RepoViewHolder) holder, repo);
    }

    private void populateRepo(final RepoViewHolder holder, final Repo repo) {
        holder.textRepoTitle.setText(repo.getName());
        holder.textRepoUser.setText(repo.getFull_name());
        if (repo.isFollowed()) {
            holder.repoFollow.setVisibility(View.GONE);
            holder.repoUnfollow.setVisibility(View.VISIBLE);
        }
        holder.repoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new SeeRepoDetailsEvent(repo));
            }
        });

        holder.repoFollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new OnFollowRepoEvent(repo));
                holder.repoFollow.setVisibility(View.GONE);
                holder.repoUnfollow.setVisibility(View.VISIBLE);
                repo.setIsFollowed(true);
            }
        });

        holder.repoUnfollow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repo.setIsFollowed(false);
                mRepos.remove(repo);
                EventBus.getDefault().post(new OnFollowRepoEvent(repo, false));
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        if (mRepos.get(position).isFollowed())
            return 0;
        else
            return 1;
    }

    private class RepoViewHolder extends RecyclerView.ViewHolder {
        TextView textRepoTitle;
        TextView textRepoUser;
        TextView repoUnfollow;
        TextView repoFollow;
        View repoView;

        public RepoViewHolder(View view) {
            super(view);
            textRepoTitle = (TextView) view.findViewById(R.id.textRepoTitle);
            textRepoUser = (TextView) view.findViewById(R.id.textRepoUser);
            repoFollow = (TextView) view.findViewById(R.id.repoFollow);
            repoUnfollow = (TextView) view.findViewById(R.id.repoUnfollow);
            repoView = view.findViewById(R.id.repo);

        }
    }
}



