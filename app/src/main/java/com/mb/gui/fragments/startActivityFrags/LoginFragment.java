package com.mb.gui.fragments.startActivityFrags;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.ActionBar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mb.R;
import com.mb.gui.activities.StartActivity;
import com.mb.model.User;
import com.mb.resource.DatabaseHelper;

public class LoginFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String ARG_PARAM3 = "param3";

    private TextView newaccount_TextView, forgotpassword_TextView;
    private CheckBox rememeber_CheckBox;
    private Button login_Button;
    private EditText username_EditText, password_EditText;
    private DatabaseHelper databaseHelper;

    private NewUserFragment newUserFragment;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean mParam3;
    private StartActivity rootActivity;
    private ProgressDialog progressDialog;
    private int rememberuser = 1;

    private static final String PREFS_NAME = "MyBookmarksProfile";
    private static final String PREF_USERNAME = "username";
    private static final String PREF_PASSWORD = "password";

    public LoginFragment() {
    }

    /**
     * @param param1 Parameter 1. username
     * @param param2 Parameter 2. password
     * @return A new instance of fragment LoginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2, boolean mParam3) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        args.putBoolean(ARG_PARAM3,mParam3);

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.newUserFragment = new NewUserFragment();
        this.databaseHelper = new DatabaseHelper(getActivity());
        log("Login fragment onCreate methode.");

    }

    private  ActionBar bar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        log("Login fragment onCreateView methode.");
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        TextView logo = (TextView)view.findViewById(R.id.textView_Logo);
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),
                R.anim.fadin);
        logo.setAnimation(animation);

        this.forgotpassword_TextView = (TextView) view.findViewById(R.id.textView_forgotpassLoginFrag);
        this.rememeber_CheckBox = (CheckBox) view.findViewById(R.id.checkBox_remember);
        this.login_Button = (Button) view.findViewById(R.id.button_loginLoginFrag);

        this.username_EditText = (EditText) view.findViewById(R.id.editText_usernameLoginFrag);
        this.password_EditText = (EditText) view.findViewById(R.id.editText_passwordLoginFrag);
        this.newaccount_TextView = (TextView) view.findViewById(R.id.textView_newaccountLoginFrag);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
            mParam3 = getArguments().getBoolean(ARG_PARAM3);
            username_EditText.setText(mParam1);
            password_EditText.setText(mParam2);
            this.rememeber_CheckBox.setChecked(mParam3);
        }

        if (bar!=null){
            bar.hide();
        }


        this.login_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startHomeActivity();
            }
        });

        this.newaccount_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bar.setTitle("New user");
                bar.show();
                FragmentTransaction fragmentTransaction =  getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.layout_Startactivity_fragmentcontainer,newUserFragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        this.rememeber_CheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remembercheckboxOnClicked();
            }
        });


        return view;
    }

    private void remembercheckboxOnClicked() {
        if (rememeber_CheckBox.isChecked()) {
            Toast.makeText(getActivity(), "The username and pass wil be remembered!", Toast.LENGTH_SHORT).show();
            rememberuser = 1;

        } else {
            Toast.makeText(getActivity(), "Not gona remember!", Toast.LENGTH_SHORT).show();
            rememberuser = 0;
        }


    }

    private void startHomeActivity() {

        final String username = username_EditText.getText().toString().trim();
        final String pass = password_EditText.getText().toString().trim();

        if (username.equals("") || username.equals(null)) {
            username_EditText.setError("Username required.");
            return;
        } else if (pass.equals("") || pass.equals(null)) {
            password_EditText.setError("Password required.");
            return;
        } else {
            new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                boolean result = false;
                try{
                    User user = databaseHelper.getUser(username);
                    if (!user.equals(null)) {

                        if (user.getUser_name().equals(username) &&
                                pass.equals(user.getPassword())) {
                            result = true;
                            if (rememberuser == 1){
                                getActivity().getSharedPreferences(PREFS_NAME,getActivity().getBaseContext().MODE_PRIVATE)
                                        .edit()
                                        .putString(PREF_USERNAME, user.getUser_name())
                                        .putString(PREF_PASSWORD, user.getPassword())
                                        .commit();
                            } else {

                                getActivity().getSharedPreferences(PREFS_NAME,getActivity().getBaseContext().MODE_PRIVATE)
                                        .edit()
                                        .putString(PREF_USERNAME, "")
                                        .putString(PREF_PASSWORD, "")
                                        .commit();

                            }
                            //
                            Intent intent = new Intent("com.mb.gui.activities.HomeActivity");
                            intent.putExtra("currentuser", user);
                            startActivity(intent);

                        } else {

                            Toast.makeText(getActivity(), "Username & password is NOT correct!", Toast.LENGTH_SHORT).show();
                        }
                    }

                }catch (RuntimeException e){
                    log("Eception in asyntask.");

                }



                return null;
            }

            @Override
            protected void onPreExecute() {
                progressDialog = ProgressDialog.show(getView().getContext(),"Signing in.","Please waite...");
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                progressDialog.dismiss();
                super.onPostExecute(aVoid);
            }
        }.execute();

        }

    }


    @Override
    public void onStop() {
        super.onStop();
        log("Login fragment onStop methode.");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("Login fragment onResume methode.");
    }

    @Override
    public void onStart() {
        super.onStart();
        log("Login fragment onStart methode.");
    }



    private void log(String msg){
        Log.i("msg","[Login Fragment]:[ "+msg+" ]");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        log("Login fragment onAttach methode. The context: "+context.getPackageCodePath());
    }

    @Override
    public void onDetach() {
        super.onDetach();
        log("Login fragment onDetach methode. The context: ");
    }

    public void setRootActivity(StartActivity rootActivity) {
        this.rootActivity = rootActivity;
    }

    public void setBar(ActionBar bar) {
        this.bar = bar;
    }


}
