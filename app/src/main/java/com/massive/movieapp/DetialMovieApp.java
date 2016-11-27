package com.massive.movieapp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.massive.movieapp.db.MovieDb;
import com.massive.movieapp.utils.Constants;
import com.pierfrancescosoffritti.youtubeplayer.AbstractYouTubeListener;
import com.pierfrancescosoffritti.youtubeplayer.YouTubePlayerView;
import com.squareup.picasso.Picasso;

import fm.jiecao.jcvideoplayer_lib.JCVideoPlayer;


public class DetialMovieApp extends Fragment {
    private MovieDb MyInfo;
    private TextView Title, ovewView, relsedData;
    private RatingBar Rate;
    private ImageView imageView;
    private MovieDb dataBase;
    YouTubePlayerView youTubePlayerView ;
    // private FullscreenVideoLayout showVideo;
    public static String MovieTrailers = Constants.baseYoutube+"movie/";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detials, container, false);
        Title = (TextView) rootView.findViewById(R.id.original_title_tv);
        ovewView = (TextView) rootView.findViewById(R.id.OverViewShow);
        relsedData = (TextView) rootView.findViewById(R.id.ReleaseDataView);
        Rate = (RatingBar) rootView.findViewById(R.id.ratingBar);
        imageView = (ImageView) rootView.findViewById(R.id.detialImgview);
        youTubePlayerView  = (YouTubePlayerView) rootView.findViewById(R.id.youtube_player_view);
        youTubePlayerView.initialize(new AbstractYouTubeListener() {
            @Override
            public void onReady() {
                youTubePlayerView.loadVideo("6JYIGclVQdw", 0);
            }
        }, true);
        //showVideo=(FullscreenVideoLayout) rootView.findViewById(R.id.videoView);
       // showVideo.setActivity(getActivity());


        //jcVideoPlayerStandard.thumbImageView.setThumbInCustomProject("http://p.qpic.cn/videoyun/0/2449_43b6f696980311e59ed467f22794e792_1/640");

        MyInfo = (MovieDb) getArguments().getSerializable(FragmentForActivity.MOVIE_KEY);
        if ((null != MyInfo)) { //set all details in my second fragment
            Title.setText(MyInfo.getTitle());
            ovewView.setText(MyInfo.getOverview());
            relsedData.setText(MyInfo.getDate());
            Rate.setRating((Float.valueOf(MyInfo.getVote())) / 2);
            String videoUri = MovieTrailers+MyInfo.getMovieID()+"/videos?";

//            jcVideoPlayerStandard.setUp("http://www.youtube.com/watch?v=9Tk7yCj2kgE"
//                    , JCVideoPlayerStandard.SCREEN_LAYOUT_LIST, "嫂子闭眼睛");
            if (MyInfo.getPoster_image() != null) {
                Picasso.with(getActivity()).load(Constants.PosterUrl + MyInfo.getPoster_image()).into(imageView);
            } else {
                imageView.setImageResource(R.drawable.noimage); // as default image
            }
        }
        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();
        JCVideoPlayer.releaseAllVideos();
    }

    @Override
    public void onDestroy() {
        if(isRemoving()){
            if (JCVideoPlayer.backPress()) {
                return;
            }
        }
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        if(isRemoving()){
            if (JCVideoPlayer.backPress()) {
                return;
            }
        }
        super.onDetach();
    }
}

