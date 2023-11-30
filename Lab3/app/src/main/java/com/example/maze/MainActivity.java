package com.example.maze;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private MazeView view;
    private GestureDetector gestureDetector;

    /**
     * Overriding the onCreate method of the AppCompatActivity class
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GameManager gameManager = new GameManager(5);
        view = new MazeView(this, gameManager);
        gestureDetector = new GestureDetector(this, gameManager);
        setContentView(view);
    }

    /**
     * Overriding the onTouchEvent method of the AppCompatActivity class
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }
}