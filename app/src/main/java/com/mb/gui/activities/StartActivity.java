package com.mb.gui.activities;

import android.app.FragmentTransaction;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.R;
import com.mb.gui.fragments.startActivityFrags.LoginFragment;
import com.mb.gui.fragments.startActivityFrags.NewUserFragment;
import com.mb.resource.DatabaseHelper;

public class StartActivity extends AppCompatActivity {


    private static final String PREFS_NAME = "MyBookmarksProfile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";
    private int backstackTracker = 0;
    private TextView logo;
    private FrameLayout fragmentContainerLayout;

    private LoginFragment loginFragment;
    private NewUserFragment newUserFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        getSupportActionBar().setShowHideAnimationEnabled(true);
        getSupportActionBar().hide();

        SharedPreferences pref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        String username = pref.getString(PREF_USERNAME, null);
        String password = pref.getString(PREF_PASSWORD, null);

//        this.databaseHelper = new DatabaseHelper(this);

        this.loginFragment = LoginFragment.newInstance(username, password, true);
//        this.loginFragment.setRootActivity(this);
        this.loginFragment.setBar(getSupportActionBar());
        this.newUserFragment = new NewUserFragment();
        this.newUserFragment.setContainer(R.id.layout_Startactivity_fragmentcontainer,loginFragment);

        this.fragmentContainerLayout = (FrameLayout) findViewById(R.id.layout_Startactivity_fragmentcontainer);

        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.slide_left_in, R.animator.slide_left_out,
                R.animator.slide_left_in, R.animator.slide_left_out);
        fragmentTransaction.replace(R.id.layout_Startactivity_fragmentcontainer, loginFragment);
        fragmentTransaction.commit();


    }







    private void log(String msg){
        Log.i("msg","[Start Activity]:[ "+msg+" ]");
    }


    public void exiteOnClicked(View view) {
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    protected void onResume() {

      super.onResume();



    }

    @Override
    protected void onStart() {

        super.onStart();
        log("StartActivity on start.");
    }

    @Override
    protected void onStop() {
        super.onStop();

    }




}


//loginFragment = LoginFragment.newInstance(username,password,true);
//        loginFragment.setRootActivity(this);
//        newUserFragment = new NewUserFragment();
//
//        fragmentContainerid = R.id.layout_Startactivity_fragmentcontainer;
//
//        getFragmentManager().beginTransaction().setCustomAnimations();
//

//        this.fragmentsinstack++;
//        TextView view = (TextView)findViewById(R.id.textView_Logo);
////        view.animate().scaleXBy();
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.move);
//        view.setAnimation(animation);