package ch.hearc.swissbox.counter;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import ch.hearc.swissbox.R;

public class CounterActivity extends AppCompatActivity {

    //UI
    private ImageButton mButtonPlus;
    private ImageButton mButtonMinus;
    private Button mButtonReset;

    private TextView mTextCounter;

    //Tools
    private static final int MAX_VALUE = 9999;

    private Animation animationUp;
    private Animation animationDown;

    //Listener
    private OnClickListener buttonListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.button_plus:
                    increment();
                    break;
                case R.id.button_minus:
                    decrement();
                    break;
                case R.id.button_reset:
                    reset();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counter);

        mButtonPlus = (ImageButton) findViewById(R.id.button_plus);
        mButtonPlus.setOnClickListener(buttonListener);

        mButtonMinus = (ImageButton) findViewById(R.id.button_minus);
        mButtonMinus.setOnClickListener(buttonListener);

        mButtonReset = (Button) findViewById(R.id.button_reset);
        mButtonReset.setOnClickListener(buttonListener);

        mTextCounter = (TextView) findViewById(R.id.text_counter);

        animationUp = AnimationUtils.loadAnimation(this, R.anim.counter_up);
        animationUp.reset();

        animationDown = AnimationUtils.loadAnimation(this, R.anim.counter_down);
        animationDown.reset();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("counterValue", mTextCounter.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        String savedValue = savedInstanceState.getString("counterValue");
        mTextCounter.setText(savedValue);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();

        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_DOWN) {
                    increment();
                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    decrement();
                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    private void increment() {
        int value = Integer.parseInt(mTextCounter.getText().toString());

        if (value < MAX_VALUE) {
            runAnimationUp();
            mTextCounter.setText(String.valueOf(value + 1));
        }
    }

    private void decrement() {
        int value = Integer.parseInt(mTextCounter.getText().toString());

        if (value > 0) {
            runAnimationDown();

            mTextCounter.setText(String.valueOf(value - 1));
        }
    }

    private void runAnimationDown() {
        mTextCounter.clearAnimation();
        mTextCounter.startAnimation(animationDown);
    }

    private void reset() {
        mTextCounter.setText(String.valueOf(0));
    }

    private void runAnimationUp() {
        mTextCounter.clearAnimation();
        mTextCounter.startAnimation(animationUp);
    }
}
