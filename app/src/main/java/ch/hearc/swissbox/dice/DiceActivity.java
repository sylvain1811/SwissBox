package ch.hearc.swissbox.dice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.util.Random;

import ch.hearc.swissbox.R;

public class DiceActivity extends AppCompatActivity {

    public static final String TAG = "DiceActivity";

    //UI
    private Button mButtonD4;
    private Button mButtonD6;
    private Button mButtonD8;
    private Button mButtonD10;
    private Button mButtonD12;
    private Button mButtonD20;
    private Button mButtonGenerate;

    private NumberPicker mPickerMin;
    private NumberPicker mPickerMax;

    private AlertDialog.Builder mBuilder;

    //Tools
    private Random mRandom;
    private int mMinValue;
    private int mMaxValue;

    //Listener
    private View.OnClickListener buttonListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_d4:
                    showRandomValue(1, 4);
                    break;
                case R.id.button_d6:
                    showRandomValue(1, 6);
                    break;
                case R.id.button_d8:
                    showRandomValue(1, 8);
                    break;
                case R.id.button_d10:
                    showRandomValue(1, 10);
                    break;
                case R.id.button_d12:
                    showRandomValue(1, 12);
                    break;
                case R.id.button_d20:
                    showRandomValue(1, 20);
                    break;
                case R.id.button_generate:
                    showRandomValue(mMinValue, mMaxValue);
                    break;

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dice);

        mButtonD4 = (Button) findViewById(R.id.button_d4);
        mButtonD4.setOnClickListener(buttonListener);

        mButtonD6 = (Button) findViewById(R.id.button_d6);
        mButtonD6.setOnClickListener(buttonListener);

        mButtonD8 = (Button) findViewById(R.id.button_d8);
        mButtonD8.setOnClickListener(buttonListener);

        mButtonD10 = (Button) findViewById(R.id.button_d10);
        mButtonD10.setOnClickListener(buttonListener);

        mButtonD12 = (Button) findViewById(R.id.button_d12);
        mButtonD12.setOnClickListener(buttonListener);

        mButtonD20 = (Button) findViewById(R.id.button_d20);
        mButtonD20.setOnClickListener(buttonListener);

        mButtonGenerate = (Button) findViewById(R.id.button_generate);
        mButtonGenerate.setOnClickListener(buttonListener);

        mPickerMin = (NumberPicker) findViewById(R.id.picker_min);
        mPickerMin.setMinValue(1);
        mPickerMin.setMaxValue(100);
        mPickerMin.setValue(1);
        mMinValue = 1;
        mPickerMin.setWrapSelectorWheel(true);
        mPickerMin.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal < mMaxValue) {
                    mMinValue = newVal;
                } else {
                    picker.setValue(oldVal);
                }
            }
        });

        mPickerMax = (NumberPicker) findViewById(R.id.picker_max);
        mPickerMin.setMinValue(1);
        mPickerMax.setMaxValue(100);
        mPickerMax.setValue(100);
        mMaxValue = 100;
        mPickerMax.setWrapSelectorWheel(true);
        mPickerMax.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                if (newVal > mMinValue) {
                    mMaxValue = newVal;
                } else {
                    picker.setValue(oldVal);
                }
            }
        });

        mRandom = new Random();

        mBuilder = new AlertDialog.Builder(this, android.R.style.Theme_Material_Dialog_Alert);
        mBuilder.setTitle("Result");
        mBuilder.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //Nothing
            }
        });
        mBuilder.setIcon(android.R.drawable.ic_dialog_info);

    }

    private void showRandomValue(int min, int max) {
        int randValue = mRandom.nextInt(max - min + 1) + min;

        TextView msg = new TextView(this);
        msg.setText(String.valueOf(randValue));
        msg.setGravity(Gravity.CENTER_HORIZONTAL);
        msg.setTextColor(Color.WHITE);
        mBuilder.setView(msg);

        mBuilder.show();
    }
}
