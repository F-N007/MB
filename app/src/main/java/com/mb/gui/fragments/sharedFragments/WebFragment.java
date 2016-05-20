package com.mb.gui.fragments.sharedFragments;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.mb.R;
import com.mb.gui.GUIInterface;

public class WebFragment extends Fragment implements GUIInterface {
    private String linkChoosen;
    private ProgressBar webprogressbar;
    private static final String ARG_PARAM1 = "param1";

    private static WebView webView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_web, container, false);
        webView = (WebView)view.findViewById(R.id.webView_WebFrag);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webprogressbar = (ProgressBar)view.findViewById(R.id.progressBar_WebFrag);
        if (getArguments() != null) {
            linkChoosen = getArguments().getString(ARG_PARAM1);
        }
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                webprogressbar.setVisibility(View.GONE);

            }
        });
        webView.loadUrl(linkChoosen);
        return view;
    }




    public static WebFragment newInstance(String param1) {
        Bundle args = new Bundle();
        WebFragment fragment = new WebFragment();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void changeFragment(Fragment fragment, String tag) {
        getView().findViewById(R.id.layout_NewitemFragmentContainer).setVisibility(View.VISIBLE);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out,
                R.animator.slide_left_in, R.animator.slide_left_out);
        fragmentTransaction.replace(R.id.layout_NewitemFragmentContainer, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
