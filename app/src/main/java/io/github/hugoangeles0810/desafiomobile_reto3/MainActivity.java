package io.github.hugoangeles0810.desafiomobile_reto3;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleGroup;
import io.github.hugoangeles0810.desafiomobile_reto3.bubble.BubbleView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BubbleGroup bubbleGroup = (BubbleGroup) findViewById(R.id.bubble_group);
        bubbleGroup.setOnBubbleClickListener(new BubbleGroup.OnBubbleClickListener() {
            @Override
            public void onBubbleClick(int index, BubbleView bubble) {
                Toast.makeText(getApplicationContext(),
                        String.format("Index: %d \n Title: %s \n Clicked!", index, bubble.getText()),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
