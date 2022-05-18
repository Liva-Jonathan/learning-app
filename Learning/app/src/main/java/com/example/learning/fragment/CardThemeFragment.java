package com.example.learning.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.learning.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardThemeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardThemeFragment extends Fragment {

    private CardView card;
    private ImageView imageTheme;
    private TextView textTheme;

    private String text;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardThemeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CardThemeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardThemeFragment newInstance() {
        CardThemeFragment fragment = new CardThemeFragment();
        Bundle args = new Bundle();
        args.putInt("layout_row", 0);
        args.putInt("layout_column", 1);
        args.putInt("layout_rowWeight", 1);
        args.putInt("layout_columnWeight", 1);
        fragment.setArguments(args);
        Log.d("FragArguments", String.valueOf(fragment.getArguments()));
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
        View rootView = inflater.inflate(R.layout.fragment_card_theme, container, false);

        card = (CardView) rootView.findViewById(R.id.cardTheme);
        imageTheme = (ImageView) rootView.findViewById(R.id.imageTheme);
        textTheme = (TextView) rootView.findViewById(R.id.textTheme);

        textTheme.setText(getText());

        return rootView;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}