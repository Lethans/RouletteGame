package com.example.android.roulette;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String[] sectors = {"32 Red", "15 Black",
            "19 Red", "4 Black", "21 Red", "2 Black", "25 Red", "17 Black", "34 Red",
            "6 Black", "27 Red", "13 Black", "36 Red", "11 Black", "30 Red", "8 Black",
            "23 Red", "10 Black", "5 Red", "24 Black", "16 Red", "33 Black",
            "1 Red", "20 Black", "14 Red", "31 Black", "9 Red", "22 Black",
            "18 Red", "29 Black", "7 Red", "28 Black", "12 Red", "35 Black",
            "3 Red", "26 Black", "Zero"
    };
    @BindView(R.id.spinBtn)
    Button spinBtn;
    @BindView(R.id.resultTv)
    TextView resultTv;
    @BindView(R.id.wheel)
    ImageView wheel;
    @BindView(R.id.triangle)
    ImageView triangle;
    private static final Random RANDOM = new Random();
    private int degree = 0, degreeOld = 0;
    private static final float HALF_SECTOR = 360f / 37f / 2f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.spinBtn)
    public void spin(View v) {
        degreeOld = degree % 360;
        degree = RANDOM.nextInt(360) + 720;
        RotateAnimation rotateAnim = new RotateAnimation(degreeOld, degree,
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        rotateAnim.setDuration(3600);
        rotateAnim.setFillAfter(true);
        rotateAnim.setInterpolator(new DecelerateInterpolator());
        rotateAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                resultTv.setText("");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                resultTv.setText(getSector(360 - (degree % 360)));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        wheel.startAnimation(rotateAnim);
    }

    private String getSector(int degrees) {
        int i = 0;
        String text = null;
        do {
            float start = HALF_SECTOR * (i * 2 + 1);
            float end = HALF_SECTOR * (i * 2 + 3);

            if (degrees >= start && degrees < end) {
                text = sectors[i];
            }
            i++;
        } while (text == null && i < sectors.length);
        return text;
    }
}
