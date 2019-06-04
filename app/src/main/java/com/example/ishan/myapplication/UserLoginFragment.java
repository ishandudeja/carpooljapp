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
import android.widget.TextView;
import android.widget.Toast;


public class UserLoginFragment extends Fragment {
    EditText txtUName;
    EditText txtPwd;
    Button btnLogin;
    DBHelper db;
    Button btnLogout;
    TextView textViewTitle;
    TextView textView11;
    TextView textView12;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_login, container, false);
        txtUName = (EditText) rootView.findViewById(R.id.editUserName);

        txtPwd = (EditText) rootView.findViewById(R.id.editPassword);

        btnLogin = (Button) rootView.findViewById(R.id.btnLogin);
        btnLogout = rootView.findViewById(R.id.btnLogout);

        textViewTitle = rootView.findViewById(R.id.textViewTitle);
        textView11 = rootView.findViewById(R.id.textView11);
        textView12 = rootView.findViewById(R.id.textView12);

        btnLogout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                logoutUser(v);
            }
        });
        btnLogin.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                buttonClicked(v);
            }
        });
        db = new DBHelper(getContext(), null, null, 1);
        SharedPreferences loginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        int user_id = loginData.getInt("user_id", 0);
        if (user_id > 0) {
            txtUName.setVisibility(View.GONE);

            txtPwd.setVisibility(View.GONE);
            btnLogin.setVisibility(View.GONE);
            textView11.setVisibility(View.GONE);
            textView12.setVisibility(View.GONE);
            textViewTitle.setVisibility(View.GONE);
            btnLogout.setVisibility(View.VISIBLE);
        } else {
            txtUName.setVisibility(View.VISIBLE);

            txtPwd.setVisibility(View.VISIBLE);
            btnLogin.setVisibility(View.VISIBLE);
            btnLogout.setVisibility(View.GONE);
            textView11.setVisibility(View.VISIBLE);
            textView12.setVisibility(View.VISIBLE);
            textViewTitle.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    public void buttonClicked(View view) {


        Users user = db.LoginUser(txtUName.getText().toString(), txtPwd.getText().toString());
        if (user != null) {

            SharedPreferences loginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = loginData.edit();
            editor.putString("userName", user.get_name());
            editor.putInt("user_id", user.get_id());
            editor.putString("email", user.get_email());
            editor.apply();

            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
            a_builder.setMessage("login success").show();
        } else {
            AlertDialog.Builder a_builder = new AlertDialog.Builder(getContext());
            a_builder.setMessage("login fail!").show();
        }

        Log.i("search", "login click");
    }

    public void logoutUser(View view) {
        SharedPreferences loginData = getActivity().getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = loginData.edit();
        editor.putString("userName","");
        editor.putInt("user_id",0);
        editor.putString("email","");
        editor.apply();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
        Toast.makeText(getContext(),"LogOut Successfully",Toast.LENGTH_LONG).show();
    }
}
