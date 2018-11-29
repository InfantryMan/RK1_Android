package com.rk6cooperation.rk1;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.button_add)
    Button buttonAdd;

    @BindView(R.id.button_remove)
    Button buttonRemove;

    @BindView(R.id.top_container)
    LinearLayout topContainer;

    @BindView(R.id.bottom_container)
    RelativeLayout bottomContainer;

    private final int MIN_NUMBER = 0;
    private final int MAX_NUMBER = 100;

    private Map<Integer, TextView> deleteNumbersList = new HashMap<>();

    public static final String TAG = "MyTag";
    private static int buttonId = 0;

    private final View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            TextView textView = (TextView) view;
            Integer id = view.getId();
            Log.d(TAG, Integer.toString(id));

            Integer color;

            if (deleteNumbersList.containsKey(id)) {
                deleteNumbersList.remove(id);
                color = ContextCompat.getColor(view.getContext(), R.color.colorBlack);
            } else {
                deleteNumbersList.put(id, (TextView) view);
                color = ContextCompat.getColor(view.getContext(), R.color.colorRed);
            }
            textView.setTextColor(color);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.button_add)
    void onLaunchAddClick() {
        //TextView numberTextView = new TextView(this, null, R.style.Numbers);
        TextView numberTextView = (TextView) getLayoutInflater().inflate(R.layout.template_textview, null);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );

        Integer margin =  Math.round(getResources().getDimension(R.dimen.number_margin));

        layoutParams.setMargins(0,0, margin, margin);

        int randomNumber = generateRandomNumber(MIN_NUMBER, MAX_NUMBER);

        numberTextView.setText( Integer.toString(randomNumber) );
        numberTextView.setId(buttonId);
        buttonId++;
        numberTextView.setOnClickListener(clickListener);

        topContainer.addView(numberTextView, layoutParams);
    }

    @OnClick(R.id.button_remove)
    void onLaunchRemoveClick() {
        for (Map.Entry<Integer, TextView> entry : deleteNumbersList.entrySet())
        {
            TextView numberTextView = entry.getValue();
            topContainer.removeView(numberTextView);
        }
    }

    // This function generates a random number from [min; max)
    int generateRandomNumber(int min, int max) {
        return ( min + (int)(Math.random() * (max - min)) );
    }
}
