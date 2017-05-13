package helloworld.example.com.helloworldexample;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by asus on 4/22/2017.
 */

public class LocalService extends Service {
    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();
    /** indicates whether onRebind should be used */
    boolean mAllowRebind;

    public class LocalBinder extends Binder {
        LocalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LocalService.this;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "LocalService Started", Toast.LENGTH_LONG).show();
        return mBinder;
    }

    /** Called when all clients have unbound with unbindService() */
    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "LocalService Unbind", Toast.LENGTH_LONG).show();
        return mAllowRebind;
    }

    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }

}
