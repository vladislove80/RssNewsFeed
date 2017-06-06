package jomedia.com.rssnewsfeed.ui.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import jomedia.com.rssnewsfeed.R;

public class BaseFragment extends Fragment {

    private ProgressBar mProgressBar;
    private RelativeLayout mContainerRelativeLayout;
    private TextView mNoDataTextView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base, container, false);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mContainerRelativeLayout = (RelativeLayout) view.findViewById(R.id.feed_container);
        mNoDataTextView = (TextView) view.findViewById(R.id.no_data_textview);
        return view;
    }

    public void hideProgressBar(){
        if (mProgressBar != null && mProgressBar.getVisibility() == View.VISIBLE) {
            mProgressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        hideProgressBar();
    }

    public void showNoDataTextView(String text) {
        mNoDataTextView.setVisibility(View.VISIBLE);
        mNoDataTextView.setText(text);
    }

    public void hideNoDataTextView() {
        mNoDataTextView.setVisibility(View.INVISIBLE);
    }

    public void addViewInContainer(ViewGroup view) {
        mContainerRelativeLayout.addView(view);
    }
}
