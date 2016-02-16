package bd.com.mcc.memoryleaketest;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.squareup.leakcanary.RefWatcher;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements MyInterface {

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button  = (Button) findViewById(R.id.button);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // finish the activity to analyze memory leak
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        // start background task or network operation
        new MyThread(MainActivity.this, new WeakReference<MyInterface>(MainActivity.this), button).execute();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        /**
         * <trace leak>
         * It will show a popup after close the Activity if memory leak happen with this activity
         * nothing will be shown otherwise
          */

        RefWatcher refWatcher = App.getRefWatcher(MainActivity.this);
        refWatcher.watch(MainActivity.this);
    }

    @Override
    public void onTaskStart() {
        Toast.makeText(MainActivity.this, "Starting task", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTaskCompleted() {
        Toast.makeText(MainActivity.this, "Task completed", Toast.LENGTH_SHORT).show();
    }


}
