package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * A class that implements the logic of the player
 */
public class Player implements Drawable{

    protected Point point;
    protected Paint paint;
    protected int size;

    /**
     * Instantiates a new Player.
     *
     * @param start the starting point
     * @param size  the playing field size
     */
    public Player(Point start, int size) {
        this.paint = getPaint();
        this.point = start;
        this.size = size;
    }

    public Player() {
    }

    /**
     * Static method for creating Paint
     */
    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.BLACK);
        return paint;
    }

    /**
     * Move the player to a given point
     *
     * @param x the x coordinate where the user wants to step
     * @param y the y coordinate where the user wants to step
     */
    public void moveTo(int x, int y) {
        point.x = x;
        point.y = y;
    }

    /**
     * Gets point.
     *
     * @return the point the user is currently at
     */
    public Point getPoint() {
        return point;
    }

    /**
     * Gets x the user is currently at
     *
     * @return the x the user is currently at
     */
    public int getX() {
        return point.x;
    }

    /**
     * Gets y the user is currently at
     *
     * @return the y the user is currently at
     */
    public int getY() {
        return point.y;
    }

    /**
     * Overriding the draw method
     */
    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (float) (rect.right - rect.left) / size;
        canvas.drawRect(rect.left + point.x * cellSize,
                        rect.top +  point.y * cellSize,
                       rect.left +  point.x * cellSize + cellSize,
                     rect.top +  point.y * cellSize + cellSize, paint);
    }
}
