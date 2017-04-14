package com.amitsin6h.rssreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {

    TextView tvTitle, tvDescriptions;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Details.this, MainActivity.class);
                startActivity(intent);

            }
        });

        tvTitle = (TextView) findViewById(R.id.tvTitle);
        imageView = (ImageView) findViewById(R.id.detailImage);
        tvDescriptions = (TextView) findViewById(R.id.details);
        Bundle bundle = getIntent().getExtras();
        tvTitle.setText(bundle.getString("Title"));
        Picasso.with(this).load(bundle.getString("Image")).into(imageView);
        tvDescriptions.setText(bundle.getString("Descriptions"));
    }
}
