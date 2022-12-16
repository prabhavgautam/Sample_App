package com.example.android.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class ImageActivity extends AppCompatActivity {

    ImageView imageView;
    Button next, previous;

    int i = 0;
    private int[] textureArrayWin = {R.drawable.images, R.drawable.image3, R.drawable.a};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);

        imageView = findViewById(R.id.activity_image_imageView);
        next = findViewById(R.id.activity_image_Next);
        previous = findViewById(R.id.activity_image_Previous);

        if (i==0){
            previous.setVisibility(View.GONE);
        }

        if (i==2){
            next.setVisibility(View.GONE);
        }

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(textureArrayWin[i]);
                i--;
                    if (i == 0) {
                        previous.setVisibility(View.GONE);
                    } else {
                        previous.setVisibility(View.VISIBLE);
                    }

                    if (i == 2) {
                        next.setVisibility(View.GONE);
                    } else {
                        next.setVisibility(View.VISIBLE);
                    }
                }

        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageView.setImageResource(textureArrayWin[i]);
                 i++;
                    if (i == 0) {
                        previous.setVisibility(View.GONE);
                    } else {
                        previous.setVisibility(View.VISIBLE);
                    }

                    if (i == 2) {
                        next.setVisibility(View.GONE);
                    } else {
                        next.setVisibility(View.VISIBLE);
                    }
                }

        });
    }
}