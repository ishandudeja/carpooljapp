package com.example.ishan.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;


public class UserRegistrationFragment extends Fragment {
    EditText txtUName;
    EditText txtEmail;
    EditText txtpwd;
    EditText txtConPwd;
    Button btnSave;
    DBHelper db;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        db = new DBHelper(getContext(), null, null, 1);

        View rootView = inflater.inflate(R.layout.fragment_user_registration, container, false);
        txtUName = (EditText) rootView.findViewById(R.id.editUserNameR);
        txtEmail = (EditText) rootView.findViewById(R.id.editEmailR);
        txtpwd = (EditText) rootView.findViewById(R.id.editPasswordR);
        txtConPwd = (EditText) rootView.findViewById(R.id.editConformPwdR);
        btnSave = (Button) rootView.findViewById(R.id.btnSaveR);

        btnSave.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });

        return rootView;
    }

    public void buttonClicked(View view) {
        Users user = new Users();//("ishan","ishan@gmail.com","asdf");

        Log.i("search", txtUName.getText().toString());
        user.set_name(txtUName.getText().toString());
        user.set_email(txtEmail.getText().toString());
        user.set_password(txtpwd.getText().toString());
        if (!db.isUserExist(txtUName.getText().toString())) {
            long user_id = 0;
            try {
                user_id = db.createUser(user);
            } catch (Exception e) {
            }
            if (user_id > 0) {


                SharedPreferences loginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = loginData.edit();
                editor.putString("userName", user.get_name());
                editor.putInt("user_id", (int) user_id);
                editor.putString("email", user.get_email());
                editor.apply();


                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();

                AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
                a_builder.setMessage("your are registered successfully").show();
            }

            Log.i("search", Long.toString(user_id));
        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
            a_builder.setMessage("User Name exist").show();
        }

    }

}
