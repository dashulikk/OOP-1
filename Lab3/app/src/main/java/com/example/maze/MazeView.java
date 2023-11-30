package com.example.maze;

import android.content.Context;
import android.graphics.Canvas;
import android.view.View;

/**
 * The type Maze view.
 */
public class MazeView extends View {
    private GameManager gameManager;

    /**
     * Instantiates a new Maze view.
     *
     * @param context     the context
     * @param gameManager the game manager
     */
    public MazeView(Context context, GameManager gameManager) {
        super(context);
        this.gameManager = gameManager;
        gameManager.setView(this);
    }

    /**
     * Overriding the onDraw method of the View class
     */
    @Override
    protected void onDraw(Canvas canvas) {
        gameManager.draw(canvas);
    }

    /**
     * Overriding the onSizeChanged method of the View class
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        gameManager.setScreenSize(w, h);
    }
}
