package fr.machada.gathabaandroid;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import fr.machada.gathabaandroid.model.Repo;

/**
 * Created by macha on 08/12/15.
 */
public class RepoListAdapter extends BaseAdapter {

    private List<Repo> mRepos;

    public RepoListAdapter(List<Repo> repos) {
        mRepos = repos;
    }

    @Override
    public int getCount() {
        return mRepos.size();
    }

    @Override
    public Repo getItem(int position) {
        return mRepos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(),
                    R.layout.item_list_repo, null);
            new ViewHolder(convertView);
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        Repo item = getItem(position);
        holder.textRepoTitle.setText(item.getName());
        holder.textRepoUser.setText(item.getFull_name());
        return convertView;
    }

    public void remove(int position) {
        mRepos.remove(position);
    }

    class ViewHolder {
        TextView textRepoTitle;
        TextView textRepoUser;

        public ViewHolder(View view) {
            textRepoTitle = (TextView) view.findViewById(R.id.textRepoTitle);
            textRepoUser = (TextView) view.findViewById(R.id.textRepoUser);
            view.setTag(this);
        }
    }

    public void addRepoList(List<Repo> repoList) {
        for (Repo r : repoList)
            mRepos.add(r);
    }

    @Override
    public int getItemViewType(int position) {
        if (mRepos.get(position).isFollowed())
            return 0;
        else
            return 1;
    }
}


