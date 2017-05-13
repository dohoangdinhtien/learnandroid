package service.example.com.learnservice;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

/**
 * Created by asus on 4/22/2017.
 */

public class CalculatorService extends Service {

    /** interface for clients that bind */
    private final IBinder mBinder = new CalculatorBinder();

    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    public class CalculatorBinder extends Binder {
        CalculatorService getService() {
            return CalculatorService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind Started", Toast.LENGTH_LONG).show();
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "CalculatorService Unbind", Toast.LENGTH_LONG).show();
        return mAllowRebind;
    }

    /** Called when a client is binding to the service with bindService()*/
    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, "onRebind() Started", Toast.LENGTH_LONG).show();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        int c = this.addTwoNumber(10, 20);
        Toast.makeText(this, "Result: " + c, Toast.LENGTH_LONG).show();

        // https://developer.android.com/reference/android/app/Service.html
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "CalculatorService Destroyed", Toast.LENGTH_LONG).show();
    }

    public int addTwoNumber(int a, int b) {
        return a + b;
    }
}
