package bd.com.mcc.memoryleaketest;

import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.lang.ref.WeakReference;

/**
 * Created by user on 2/16/16.
 */
public class MyThread extends AsyncTask<Void, Void, Void> {


    private Context mContext; // you may need context here for several purpose
    private WeakReference<MyInterface> myInterface; // this is the trick to avoid memory leak, it will release object as early as your Activity finish
    private Button button; // don's pass a view component to a non activity class

    public MyThread(Context context, WeakReference<MyInterface> myInterface, Button button) {
        this.mContext = context.getApplicationContext(); // use getApplicationContext() instead of actual context
        this.myInterface = myInterface; // use weak reference instead of actual object
        //this.button = button; // don't use this (strictly)
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        if(myInterface.get() != null) {
            myInterface.get().onTaskStart();
        }
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {
            // do network operation or whatever you want to execute in background
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);


        // task completion callback to post data into UI
        if(myInterface.get() != null) {
            myInterface.get().onTaskCompleted();
        }
    }

}
