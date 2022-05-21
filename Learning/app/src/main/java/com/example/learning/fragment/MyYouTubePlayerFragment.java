package com.example.learning.fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.learning.R;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MyYouTubePlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MyYouTubePlayerFragment extends YouTubePlayerSupportFragmentX {

    private static final String GOOGLE_API_KEY = "AIzaSyB71SQHoRtgrcjLPighaSpsEooalaDLGpY";

    private String currentVideoID = "video_id";
    private YouTubePlayer activePlayer;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MyYouTubePlayerFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment MyYouTubePlayerFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MyYouTubePlayerFragment newInstance(String videoId) {
        MyYouTubePlayerFragment fragment = new MyYouTubePlayerFragment();
        Bundle args = new Bundle();
        args.putString("videoId", videoId);
        fragment.setArguments(args);
        fragment.init();
        return fragment;
    }

    private void init() {
        initialize(GOOGLE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
                activePlayer = player;
                activePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
                if (!wasRestored) {
                    activePlayer.loadVideo(getArguments().getString("videoId"), 0);
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                Toast.makeText(getContext(), "Video loading failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    @Override
//    public void onYouTubeVideoPaused() {
//        activePlayer.pause();
//    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        return inflater.inflate(R.layout.fragment_my_you_tube_player, container, false);
//    }
}