package com.example.mybooks.login;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.mybooks.R;
import com.example.mybooks.databinding.ActivityLoginBinding;

public class login_Activity extends AppCompatActivity {

    private final String TAG=this.getClass().getSimpleName();
    private ActivityLoginBinding binding; // findviewbyid 대신 binding하면 객체만으로 바로 id접근 가능


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot()); // layout자체를 set해주는게 아니라 binding객체를 set해줌


        binding.etLoginPw.setText("set password"); // test
        binding.tvLoginForgetPw.setPaintFlags( binding.tvUnderlineJoin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG); // 밑줄(xml상으로 하면 번거로움)
        binding.tvUnderlineJoin.setPaintFlags( binding.tvUnderlineJoin.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);


        // 비번잊으셨나요?
        binding.tvLoginForgetPw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        // 회원가입하러 가기
        binding.tvUnderlineJoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(login_Activity.this, join_Activity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // 현재화면빼고 아래액티비티 지움
                startActivity(intent);
                Log.e(TAG, "startActivity() \n"+TAG+" -> join_Activity.class ");

            }
        });


    } // ~onCreate()


}