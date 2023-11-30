package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

/**
 * The class that contains the exit point from the maze
 */
public class ExitPoint implements Drawable {
    private int size;
    private Point point;
    private Paint paint;

    /**
     * Instantiates a new Exit point.
     *
     * @param point the point
     * @param size  the size
     */
    public ExitPoint(Point point, int size) {
        this.point = point;
        paint = getPaint();
        this.size = size;
    }

    /**
     * Static method for creating Paint
     */
    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.GREEN);
        return paint;
    }

    /**
     * Gets point at which the exit from the maze is located. Always (1, 1).
     *
     * @return the point at which the exit from the maze is located
     */
    public Point getPoint() {
        return point;
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