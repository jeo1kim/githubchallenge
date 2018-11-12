package com.example.euiwonkim.githubchallenge;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Card View is used to show list of pull requests
 *
 * This class executes the api call to the github given a hard coded url.
 *
 * default github repo link: "https://api.github.com/repos/torvalds/linux/pulls"
 */
public class CardFragment extends Fragment {

    private ArrayList<PullRequest> listitems;
    private RecyclerView MyRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //TODO: make string formatter given just a repo link.
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

    /**
     * Adapter
     */
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

        /**
         * set the cards views.
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(final MyViewHolder holder, final int position) {

            holder.titleText.setText(list.get(position).getTitle());
            String imageUrl = list.get(position).getImageUrl();

            new DownloadImageTask(holder.coverImage).execute(imageUrl);

            holder.userName.setText(" By "+list.get(position).getUserId());
            holder.pullNumber.setText("#"+list.get(position).getId());
            holder.showDiff.setText("VIEW DIFF");

            // click listener to send to diff activity
            holder.showDiff.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent diffViewIntent = new Intent(getActivity(), DiffViewActivity.class);
                    // run asynctask to get fetch the diff
                    list.get(position).fetchDiff();
                    // call individual diff call and pass it to activity for performance.
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

    // viewholder to recycle views.
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView titleText;
        public ImageView coverImage;
        public TextView userName;
        public TextView pullNumber;
        public TextView showDiff;

        public MyViewHolder(View v) {
            super(v);
            titleText = v.findViewById(R.id.titleTextView);
            coverImage = v.findViewById(R.id.coverImageView);
            userName = v.findViewById(R.id.user_name);
            pullNumber = v.findViewById(R.id.pull_number);
            showDiff = v.findViewById(R.id.show_diff);
        }
    }

    /**
     *  AsyncTask to handle user avat image processing on the fragment.
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView coverImage;
        public DownloadImageTask(ImageView coverImage) {
            this.coverImage = coverImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap bmp = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                bmp = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return bmp;
        }

        protected void onPostExecute(Bitmap result) {
            coverImage.setImageBitmap(result);
        }
    }
}
