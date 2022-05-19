package com.example.learning.fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learning.R;
import com.example.learning.controller.MainActivity;
import com.example.learning.model.Theme;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragmentX;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetailsThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetailsThemeFragment extends Fragment {

    private ListThemeFragment.OnFragmentInteractionListener mListener;

    private ImageView imageTheme;
    private TextView nameTheme;
    private TextView descriptionTheme;
    private View btnLearn;
    private View btnPractice;

    private Theme theme;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetailsThemeFragment() {
        // Required empty public constructor
    }

    public DetailsThemeFragment(Theme theme) {
        setTheme(theme);
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetailsThemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetailsThemeFragment newInstance(String param1, String param2) {
        DetailsThemeFragment fragment = new DetailsThemeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details_theme, container, false);

        if(mListener != null) {
            mListener.onFragmentInteractionChangeTitle(getTheme().getName());
        }

        imageTheme = rootView.findViewById(R.id.imageDetailsTheme);
        nameTheme = rootView.findViewById(R.id.textDetailsTheme);
        descriptionTheme = rootView.findViewById(R.id.txtThemeDescription);
        btnLearn = rootView.findViewById(R.id.btnLearn);
        btnPractice = rootView.findViewById(R.id.btnPractice);

        imageTheme.setImageResource(getContext().getResources().getIdentifier(getTheme().getImage().split("\\.")[0], "drawable", getContext().getPackageName()));
        nameTheme.setText(getTheme().getName());
        descriptionTheme.setText(getTheme().getDescription());


//        "qAHMCZBwYo4"
        MyYouTubePlayerFragment youTubeFragment = MyYouTubePlayerFragment.newInstance(getTheme().getUrlVideo());
        ((MainActivity)getContext()).getSupportFragmentManager().beginTransaction().replace(R.id.youtube_video_container, youTubeFragment).commit();

        ImageView imageBtnLearn = (ImageView) btnLearn.findViewById(R.id.imageBtnActionTheme);
        imageBtnLearn.setImageResource(R.drawable.cap);
        TextView textBtnLearn = (TextView) btnLearn.findViewById(R.id.textBtnActionTheme);
        textBtnLearn.setText("Apprendre");

        ImageView imageBtnPractice = (ImageView) btnPractice.findViewById(R.id.imageBtnActionTheme);
        imageBtnPractice.setImageResource(R.drawable.pencil);
        TextView textBtnPractice = (TextView) btnPractice.findViewById(R.id.textBtnActionTheme);
        textBtnPractice.setText("S'exercer");

        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(context instanceof ListThemeFragment.OnFragmentInteractionListener) {
            mListener = (ListThemeFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteractionChangeTitle(String title);
    }

    public Theme getTheme() {
        return theme;
    }

    public void setTheme(Theme theme) {
        this.theme = theme;
    }
}