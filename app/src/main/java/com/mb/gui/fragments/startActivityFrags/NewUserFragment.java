package com.mb.gui.fragments.startActivityFrags;


import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.mb.R;
import com.mb.model.User;
import com.mb.resource.DatabaseHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewUserFragment extends Fragment {

    private DatabaseHelper databaseHelper;
    private EditText name_EditText, lastname_EditText, username_EditText, pass_EditText,
            email_EditText, phone_EditText, comment_EditText;
    private ImageButton pickfromgallery_ImageButton, getPickfromcamera_ImageButton;
    private Button save_Button;
    private ImageView profilepic_ImageView;
    private String profilepic_path;
    private User newUser;
    private int fragmentContainer;
    private Fragment loginFragment;

    private int RESULT_OK = -1;
    private static int IMAGE_GALLERY_REQUEST = 1;


    public NewUserFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        log("New user fragment onCreateView methode.");
        View view = inflater.inflate(R.layout.fragment_new_user, container, false);
        this.name_EditText = (EditText) view.findViewById(R.id.editText_name);
        this.lastname_EditText = (EditText) view.findViewById(R.id.editText_lastname);
        this.username_EditText = (EditText) view.findViewById(R.id.editText_newacoutnusername);
        this.pass_EditText = (EditText) view.findViewById(R.id.editText_password);
        this.email_EditText = (EditText) view.findViewById(R.id.editText_email);
        this.phone_EditText = (EditText) view.findViewById(R.id.editText_phone);
        this.comment_EditText = (EditText) view.findViewById(R.id.editText_comment);
        this.pickfromgallery_ImageButton = (ImageButton) view.findViewById(R.id.imageButton_pickfromgallery);
        this.getPickfromcamera_ImageButton = (ImageButton) view.findViewById(R.id.imageButton_pickfromcamera);
        this.profilepic_ImageView = (ImageView) view.findViewById(R.id.imageView_profilepic);
        this.save_Button = (Button) view.findViewById(R.id.button_saveaccount);

        this.save_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNewAcount();
            }
        });

        this.pickfromgallery_ImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pickfromgallery();
            }
        });


        return view;
    }

    private void createNewAcount() {
        String name = name_EditText.getText().toString().trim(), lastname = lastname_EditText.getText().toString().trim(),
                username = username_EditText.getText().toString().trim(), password = pass_EditText.getText().toString().trim(),
                email = email_EditText.getText().toString().trim(), phone = phone_EditText.getText().toString().trim(),
                comment = comment_EditText.getText().toString().trim();

        if (name.equals("") || name.equals(null)) {
            name_EditText.setError("Name required.");
            return;
        } else if (lastname.equals("") || lastname.equals(null)) {
            lastname_EditText.setError("Lastname required.");
            return;
        } else if (username.equals("") || username.equals(null)) {
            username_EditText.setError("Username required.");
            return;
        } else if (password.equals("") || password.equals(null)) {
            pass_EditText.setError("Password required.");
            return;
        } else if (email.equals("") || email.equals(null)) {
            email_EditText.setError("Email required.");
            return;
        } else {

            newUser.setName(name);
            newUser.setLast_name(lastname);
            newUser.setUser_name(username);
            newUser.setPassword(password);
            newUser.setEmail(email);
            newUser.setTlf(Integer.parseInt(phone));
            newUser.setComments(comment);
            boolean res = false;
            AlertDialog alertDialog = new AlertDialog.Builder(getView().getContext()).create();
            res = databaseHelper.insertUser(newUser);
            if (res) {
                alertDialog.setTitle("Account created Successfully");
                alertDialog.setMessage("New account created successfully!");
                alertDialog.setIcon(R.drawable.ic_createdsucc);
                alertDialog.requestWindowFeature(Window.FEATURE_LEFT_ICON);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                getActivity().onBackPressed();
                            }
                        });
            } else {

                alertDialog.setMessage("User already exists!");
                alertDialog.setIcon(R.drawable.newaccouncreatefaild);
                alertDialog.requestWindowFeature(Window.FEATURE_RIGHT_ICON);
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

            }

            alertDialog.show();


        }

    }

    public void pickfromgallery() {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, IMAGE_GALLERY_REQUEST);
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String profileImagePath;
        if (resultCode == RESULT_OK)
            if (requestCode == IMAGE_GALLERY_REQUEST) {
                Uri imageuri = data.getData();
                profileImagePath = getRealPathFromURI_API11to18(this.getActivity(), imageuri);
                newUser.setProfile_pic(profileImagePath);
                Toast.makeText(getView().getContext(), "Image loaded.", Toast.LENGTH_SHORT).show();

                Bitmap bMap = BitmapFactory.decodeFile(profileImagePath);
                Bitmap bMapScaled = Bitmap.createScaledBitmap(bMap, profilepic_ImageView.getWidth(), profilepic_ImageView.getHeight(), true);
                profilepic_ImageView.setImageBitmap(bMapScaled);
            } else {
                Toast.makeText(getView().getContext(), "Unable to open image", Toast.LENGTH_SHORT).show();
            }

    }


    private static String getRealPathFromURI_API11to18(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        String result = null;
        CursorLoader cursorLoader = new CursorLoader(
                context,
                contentUri, proj, null, null, null);
        Cursor cursor = cursorLoader.loadInBackground();

        if (cursor != null) {
            int column_index =
                    cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            result = cursor.getString(column_index);
        }
        return result;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        databaseHelper = new DatabaseHelper(getActivity());
        this.newUser = new User();

    }


    @Override
    public void onStop() {
        super.onStop();
//        log("New user fragment onStop methode.");
    }

    @Override
    public void onResume() {
        super.onResume();
        log("New user fragment onResume methode.");
    }

    @Override
    public void onStart() {
        super.onStart();
//        log("New user fragment onStart methode.");
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

//        log("New user fragment onAttach methode. The context: "+context.getPackageCodePath());
    }

    @Override
    public void onDetach() {
        super.onDetach();
//        log("New user fragment onDetach methode. The context: ");
    }

    public void setContainer(int fragmentContainer, Fragment loginFragment) {
        this.loginFragment = loginFragment;
        this.fragmentContainer = fragmentContainer;
    }

    private void log(String msg) {
        Log.i("msg", "[ NewUserFragment: " + msg + " ]");
    }

    private NewUserFragment getThisInstance(){
        return this;
    }

}
