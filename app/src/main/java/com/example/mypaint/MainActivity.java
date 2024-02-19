package com.example.mypaint;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.view.PointerIcon;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.view.LayoutInflater;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

import java.io.File;
import java.io.FileOutputStream;

public class MainActivity extends Activity {

    int i;
    private int clickCount = 0;
    RadioButton radioButton;
    SketchView sketchView;
    RadioGroup radioGroup_ColorPalette;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_activity_main);
        sketchView = findViewById(R.id.sketchView);
        radioGroup_ColorPalette = findViewById(R.id.radio_group);
        createColorPalette();

        ImageButton color_button = (ImageButton) findViewById(R.id.color_palette);
        color_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickCount++;

                if (clickCount % 2 == 0) {
                    radioGroup_ColorPalette.setVisibility(View.GONE);
                } else {
                    radioGroup_ColorPalette.setVisibility(View.VISIBLE);
                }
            }
        });

        ImageButton undo_button = (ImageButton) findViewById(R.id.undo);
        undo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sketchView.undo();
            }
        });

    }

    public void createColorPalette() {
        String[] colors_array = getResources().getStringArray(R.array.colors);

        for (i = 0; i < colors_array.length; i++) {
            View colorPalette = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_radio_button, null, false);
            radioButton = (RadioButton) colorPalette.getRootView();
            radioButton.setId(i);

            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(28, 28, 28, 28);
            radioButton.setLayoutParams(params);
            radioButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(colors_array[i])));
            radioGroup_ColorPalette.addView(radioButton);

            radioGroup_ColorPalette.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup group, int checkedId) {
                    sketchView.setCurrentColor(Color.parseColor(colors_array[checkedId]));
                }
            });
        }
    }
}
