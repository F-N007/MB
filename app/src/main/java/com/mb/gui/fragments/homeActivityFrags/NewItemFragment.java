package com.mb.gui.fragments.homeActivityFrags;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.mb.R;
import com.mb.gui.GUIInterface;
import com.mb.gui.fragments.sharedFragments.WebFragment;

public class NewItemFragment extends Fragment implements GUIInterface{
    private ActionBar actionBar;
    private FloatingActionButton floatingActionButton;
    private WebFragment webFragment;

    private String itemLink = "http://www.google.dk";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_new_item, container, false);
        webFragment = WebFragment.newInstance(itemLink);
        Button webButton = (Button)view.findViewById(R.id.button_itemsearchongoogle);
        webButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(webFragment,"webFragment");
            }
        });

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (actionBar !=null){
            actionBar.hide();
            floatingActionButton.setImageResource(R.drawable.ic_save);
//            floatingActionButton.setVisibility(View.GONE);
        }
    }

    public void setStartParams(ActionBar bar, FloatingActionButton actionButton){
        this.actionBar = bar;
        this.floatingActionButton = actionButton;
    }

    @Override
    public void onStop() {
        if (actionBar !=null){
            actionBar.show();
//            floatingActionButton.setVisibility(View.VISIBLE);
 }
        super.onStop();
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

    @Override
    public void onResume() {
        getView().findViewById(R.id.layout_NewitemFragmentContainer).setVisibility(View.GONE);
        super.onResume();
    }
}
