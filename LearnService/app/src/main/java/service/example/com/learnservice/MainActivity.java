package service.example.com.learnservice;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    boolean mBound = false;
    boolean mStartFlag = false;
    CalculatorService mService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void checkBound(View view) {
        CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
        mBound = checkBox.isChecked();
    }

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            CalculatorService.CalculatorBinder binder = (CalculatorService.CalculatorBinder) service;
            mService = binder.getService();
            mBound = true;
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            checkBox.setChecked(mBound);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            CheckBox checkBox = (CheckBox) findViewById(R.id.checkBox);
            checkBox.setChecked(mBound);
        }
    };

    public void onCalculator(View view) {
        if (mBound) {
            int c = mService.addTwoNumber(10, 15);
            Toast.makeText(this, "number: " + c, Toast.LENGTH_LONG).show();
        }
    }

    public void startButtonClick(View view) {
        Button startStopBtn = (Button) findViewById(R.id.startStopButton);
        if (!mBound) {
            if (!mStartFlag) {
                startService(new Intent(getBaseContext(), CalculatorService.class));
                startStopBtn.setText("Stop Service");
                mStartFlag = true;
            } else {
                stopService(new Intent(getBaseContext(), CalculatorService.class));
                startStopBtn.setText("Start Service");
                mStartFlag = false;
            }
        } else {
            if (!mStartFlag) {
                bindService(new Intent(getBaseContext(), CalculatorService.class), mConnection, Context.BIND_AUTO_CREATE);
                startStopBtn.setText("Stop Bind Service");
                mStartFlag = true;
            } else {
                unbindService(mConnection);
                startStopBtn.setText("Start Bind Service");
                mStartFlag = false;
            }
        }
    }

    public void switchMessageActivity(View v) {
        Intent intent = new Intent(this, ActivityMessenger.class);
        startActivity(intent);
    }
}
