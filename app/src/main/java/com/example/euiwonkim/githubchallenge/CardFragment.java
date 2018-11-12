package com.example.euiwonkim.githubchallenge;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class CardFragment extends Fragment {

    ArrayList<PullRequest> listitems;
    RecyclerView MyRecyclerView;
    List<PullRequest> pullRequests;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        String githubRepoURL = "https://api.github.com/repos/torvalds/linux/pulls";
        listitems = getGithubRequest(githubRepoURL);


        getActivity().setTitle("Open Pull Requests");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_card, container, false);
        MyRecyclerView = (RecyclerView) view.findViewById(R.id.cardView);
        MyRecyclerView.setHasFixedSize(true);
        LinearLayoutManager MyLayoutManager = new LinearLayoutManager(getActivity());
        MyLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        if (listitems.size() > 0 & MyRecyclerView != null) {
            MyRecyclerView.setAdapter(new MyAdapter(listitems));
        }
        MyRecyclerView.setLayoutManager(MyLayoutManager);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    /**
     * Uses Asynctaks in GithubGetRequest class
     * @param
     * @return List of pull request on the github repo
     */
    protected ArrayList<PullRequest> getGithubRequest(String githubUrl) {

        // Asynctask
        GIthubGetRequest githubGetRequest = new GIthubGetRequest();
        JSONObject result = null;
        // pull requests list
        ArrayList<PullRequest> pullRequests = new ArrayList<>();

        try {
            pullRequests = (ArrayList<PullRequest>) githubGetRequest.execute(githubUrl).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return pullRequests;

    }


    public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private ArrayList<PullRequest> list;

        public MyAdapter(ArrayList<PullRequest> Data) {
            list = Data;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.pull_request_list, parent, false);
            MyViewHolder holder = new MyViewHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.titleText.setText(list.get(position).getTitle());
            String imageUrl = list.get(position).getImageUrl();
            //holder.coverImage.setImageResource( imageUrl);
            //holder.coverImageView.setTag(list.get(position).getImageResourceId());
            holder.userName.setText(list.get(position).getUserId());
            holder.pullNumber.setText(list.get(position).getId());
            holder.showDiff.setText("View Diff");

            holder.showDiff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent diffViewIntent = new Intent(getActivity(), DiffViewActivity.class);
                    // run asynctask to get fetch the diff
                    list.get(position).fetchDiff();
                    diffViewIntent.putExtra("diff", list.get(position).getDiff());
                    startActivity(diffViewIntent);
                }
            });

        }

       @Override
        public int getItemCount() {
            return list.size();
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public ImageView coverImage;
        public TextView userName;
        public TextView pullNumber;
        public TextView showDiff;

        public MyViewHolder(View v) {
            super(v);
            titleText = (TextView) v.findViewById(R.id.titleTextView);
            coverImage = (ImageView) v.findViewById(R.id.coverImageView);
            userName = v.findViewById(R.id.user_name);
            pullNumber = v.findViewById(R.id.pull_number);
            showDiff = v.findViewById(R.id.show_diff);
        }
    }
}
