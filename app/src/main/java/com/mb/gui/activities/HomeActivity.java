package com.mb.gui.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.mb.R;
import com.mb.gui.GUIInterface;
import com.mb.gui.fragments.homeActivityFrags.BackupFragment;
import com.mb.gui.fragments.homeActivityFrags.BookmarksFragment;
import com.mb.gui.fragments.homeActivityFrags.HomeFragment;
import com.mb.gui.fragments.homeActivityFrags.NewItemFragment;
import com.mb.gui.fragments.homeActivityFrags.PeopleFragment;
import com.mb.model.User;
import com.mb.resource.DatabaseHelper;

import java.util.Hashtable;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private HomeFragment homeFragment;
    private HomeActivitHelper helper;
    private NewItemFragment newItemFragment;
    private BookmarksFragment bookmarksFragment;
    private BackupFragment backupFragment;
    private PeopleFragment peopleFragment;
    private DatabaseHelper databaseHelper;

    private FloatingActionButton fab;
    private User systemUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        this.databaseHelper = new DatabaseHelper(this);

        homeFragment = HomeFragment.newInstance("", "");
        helper = new HomeActivitHelper();
        newItemFragment = new NewItemFragment();
        newItemFragment.setStartParams(getSupportActionBar(), fab);


        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out,
                R.animator.slide_left_in, R.animator.slide_left_out);
        fragmentTransaction.replace(R.id.homeActivity_fragmentcontainer, homeFragment);
        fragmentTransaction.commit();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                helper.changeFragment(newItemFragment, "newItemFragment");
            }
        });

        this.systemUser = (User) getIntent().getSerializableExtra("currentuser");

        log(this.systemUser.toString());
    }

    private void log(String msg){
        Log.i("msg","[Home Activity]:[ "+msg+" ]");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_bookmarks) {

        } else if (id == R.id.nav_onMap) {

        } else if (id == R.id.nav_backuprestore) {

        } else if (id == R.id.nav_people) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private class HomeActivitHelper implements GUIInterface {
        private Hashtable<String, Fragment> backstackTable;
        private Fragment fragmentToShow;
        private String fragmentTag;

        public HomeActivitHelper() {
            backstackTable = new Hashtable<>();
        }

        @Override
        public void changeFragment(Fragment fragment, String tag) {

            if (!fragment.isVisible()) {
                this.fragmentToShow = fragment;
                this.fragmentTag = tag;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out,
                                R.animator.slide_left_in, R.animator.slide_left_out);

                        if (!backstackTable.containsKey(fragmentTag)) {
                            backstackTable.put(fragmentTag, fragmentToShow);
                            fragmentTransaction.replace(R.id.homeActivity_fragmentcontainer, fragmentToShow);
                            fragmentTransaction.addToBackStack(fragmentTag);

                        } else {
                            fragmentManager.popBackStack(fragmentTag, 0);
                            backstackTable.remove(fragmentTag);
                        }

                        fragmentTransaction.commit();

                    }
                });

                thread.start();

            }
        }
    }
}
