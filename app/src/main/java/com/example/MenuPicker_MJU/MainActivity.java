package com.example.MenuPicker_MJU;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import db.DBHelper;

public class MainActivity extends Activity {
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-2003914600650416~5042220845");

        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().addTestDevice("D14686101750E27D31737AB9E8B392DB").build();
        mAdView.loadAd(adRequest);
    }

    public void onClick_see(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("이거나 먹어라");

        DBHelper dbHelper = new DBHelper(getApplicationContext(), "MenuBook.db", null, 1);
        String result = dbHelper.getRandom();
        builder.setMessage(result);
        builder.setPositiveButton("ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    public void onClick_add(View v) {
        Intent intent = new Intent(this, AddMenuActivity.class);
        startActivity(intent);
    }

    public void onClick_list(View v) {
        Intent intent = new Intent(this, ViewListActivity.class);
        startActivity(intent);
    }
}
