package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

/**
 * The class that implements the logic of the game
 */
public class GameManager extends GestureDetector.SimpleOnGestureListener {

    private List<Drawable> drawables = new ArrayList<>();
    private View view;
    private Player player;
    private Maze maze;
    private ExitPoint exitPoint;
    private Rect rect = new Rect();
    private int screenSize;

    /**
     * Instantiates a new Game manager.
     *
     * @param mazeSize the maze size
     */
    public GameManager(int mazeSize) {
        createManager(mazeSize);
    }

    /**
     * Create manager.
     *
     * @param mazeSize the maze size
     */
    void createManager(int mazeSize) {
        drawables.clear();
        maze = new Maze(mazeSize);
        player = new Player(maze.getStart(), mazeSize);
        exitPoint = new ExitPoint(maze.getEnd(), mazeSize);
        drawables.add(maze);
        drawables.add(player);
        drawables.add(exitPoint);
    }

    /**
     * A method that tracks the user's swipe and moves the player
     */
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        int diffX = Math.round(e2.getX() - e1.getX()),
              diffY = Math.round(e2.getY() - e1.getY());
        if (Math.abs(diffX) > Math.abs(diffY)) {
            diffX = diffX > 0 ? 1 : -1;
            diffY = 0;
        } else {
            diffX = 0;
            diffY = diffY > 0 ? 1 : -1;
        }
        int stepX = player.getX();
        int stepY = player.getY();

        while (maze.canPlayerGoTo(stepX + diffX, stepY + diffY)) {
            stepX += diffX;
            stepY += diffY;
            if (diffX != 0) {
                if (maze.canPlayerGoTo(stepX, stepY + 1)
                        || maze.canPlayerGoTo(stepX, stepY - 1)) {
                    break;
                }
            }
            if (diffY != 0) {
                if (maze.canPlayerGoTo(stepX + 1, stepY)
                        || maze.canPlayerGoTo(stepX - 1, stepY)) {
                    break;
                }
            }
        }
        player.moveTo(stepX, stepY);
        if (exitPoint.getPoint().equals(player.getPoint())) {
            createManager(maze.getSize() + 6);
        }
        view.invalidate();

        return super.onFling(e1, e2, velocityX, velocityY);
    }

    /**
     * A method that implements the "Template Method" design pattern. We do not know what kind of elements we are drawing. Since the elements are inheritors of Drawable
     *
     * @param canvas the canvas
     */
    public void draw(Canvas canvas) {
        for (Drawable drawableItem : drawables) {
            drawableItem.draw(canvas, rect);
        }
    }

    /**
     * Sets view.
     *
     * @param view the view
     */
    public void setView(View view) {
        this.view = view;
    }

    /**
     * Sets screen size.
     *
     * @param width  the width
     * @param height the height
     */
    public void setScreenSize(int width, int height) {
        screenSize = Math.min(width, height);
        rect.set( (width - screenSize) / 2,
                  (height - screenSize) / 2,
                  (width + screenSize) / 2,
                  (height + screenSize) / 2);
    }
}
