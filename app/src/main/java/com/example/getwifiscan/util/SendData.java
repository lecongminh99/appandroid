package com.example.getwifiscan.util;
import android.app.AppComponentFactory;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.getwifiscan.util.WifiHelper;
import com.example.getwifiscan.util.PreferenceKeyConst;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

public class SendData extends AppCompatActivity {
//    DatabaseReference reff;
//    SharedPreferences sharedPreferences;
//    Member member;
//    @Override
//    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
//        super.onCreate(savedInstanceState, persistentState);
//        sharedPreferences = getSharedPreferences(PreferenceKeyConst.APP_PREF, Context.MODE_PRIVATE);
//        //reff = sharedPreferences.getString(PreferenceKeyConst.TEMP_DATA, "null");
//        reff = FirebaseDatabase.getInstance().getReference().child("RSS:");
//        member = new Member();
//        String rss;
//        rss = sharedPreferences.getString(PreferenceKeyConst.TEMP_DATA, "null");
//        while(rss != "null"){
//            String hint = sharedPreferences.getString(PreferenceKeyConst.TEMP_DATA, "null").trim();
//            member.getRss(hint);
//            reff.push().setValue(member);
//            SharedPreferences.Editor editor = sharedPreferences.edit();
////            editor.remove(PreferenceKeyConst.TEMP_DATA);
//        }
//    }

}
