package org.xdq.changebutton;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.xdq.library.ChanggeButtonLayout;
import org.xdq.library.listener.CenterClickListener;
import org.xdq.library.listener.SeparateClickListener;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChanggeButtonLayout buttonLayout = findViewById(R.id.one);
        buttonLayout.setSeparateClickListener(new SeparateClickListener() {
            @Override
            public void onCancle() {
                Toast.makeText(getApplicationContext(), "取消", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onConfirm() {
                Toast.makeText(getApplicationContext(), "确认", Toast.LENGTH_SHORT).show();
            }
        });

        buttonLayout.setCenterClickListener(new CenterClickListener() {
            @Override
            public void click() {
                Toast.makeText(getApplicationContext(), "执行任务", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
