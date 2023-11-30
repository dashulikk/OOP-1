package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;

/**
 * Maze class is implemented playing field
 */
public class Maze implements Drawable {
    private Paint wallPaint;
    private final boolean [][] array;
    private final int size;
    private int bestScore = 0;
    private Point start;
    private final Point end = new Point(1, 1);

    /**
     * Instantiates a new Maze
     *
     * @param size the size
     */
    public Maze(int size) {
        this.wallPaint = getPaint();
        this.size = size;
        this.array = new boolean[size][size];
        generateMaze();
    }

    /**
     * Static method for creating Paint
     */
    private static Paint getPaint() {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.LTGRAY);
        return paint;
    }

    /**
     * Method for filling the array (creating the playing field). Algorithm on the graph is used
     */
    private void generateMaze() {
        for (int i = 0; i < size; ++i) {
            for (int j = 0; j < size; ++j) {
                array[i][j] = i % 2 != 0 && j % 2 != 0
                        && i < size - 1 && j < size - 1;
            }
        }
        Random random = new Random();
        Stack<Point> stack = new Stack<>();
        stack.push(end);
        while (stack.size() > 0) {
            Point current = stack.peek();
            List<Point> unusedNeighbors = new LinkedList<>();
            //left
            if (current.x > 2) {
                if (!isUsedCell(current.x - 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x - 2, current.y));
                }
            }
            //top
            if (current.y > 2) {
                if (!isUsedCell(current.x, current.y - 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y - 2));
                }
            }
            //right
            if (current.x < size - 2) {
                if (!isUsedCell(current.x + 2, current.y)) {
                    unusedNeighbors.add(new Point(current.x + 2, current.y));
                }
            }
            //bottom
            if (current.y < size - 2) {
                if (!isUsedCell(current.x, current.y + 2)) {
                    unusedNeighbors.add(new Point(current.x, current.y + 2));
                }
            }
            if (unusedNeighbors.size() > 0) {
                int rnd = random.nextInt(unusedNeighbors.size());
                Point direction = unusedNeighbors.get(rnd);
                int diffX = (direction.x - current.x) / 2;
                int diffY = (direction.y - current.y) / 2;
                array[current.y + diffY][current.x + diffX] = true;
                stack.push(direction);
            } else {
                if (bestScore < stack.size()) {
                    bestScore = stack.size();
                    start = current;
                }
                stack.pop();
            }
        }
    }

    /**
     * Check if the user can go to this cell or if there is a wall (and it is forbidden)
     *
     * @param x the x coordinate where the user wants to step
     * @param y the y coordinate where the user wants to step
     * @return the boolean
     */
    public boolean canPlayerGoTo(int x, int y) {
        return array[y][x];
    }

    /**
     * Check if we have visited this cell before
     */
    private boolean isUsedCell(int x, int y) {
        if (x < 0 || y < 0 || x >= size - 1 || y >= size - 1) {
            return true;
        }
        return array[y - 1][x] // left
                || array[y][x - 1] // top
                || array[y + 1][x] // right
                || array[y][x + 1]; // bottom
    }

    /**
     * Overriding the draw method
     */
    @Override
    public void draw(Canvas canvas, Rect rect) {
        float cellSize = (float) (rect.right - rect.left) / size;
        for (short i = 0; i < size; i++) {
            for (short j = 0; j < size; j++) {
                if (!array[i][j]) {
                    float left = j*cellSize + rect.left;
                    float top = i*cellSize + rect.top;
                    canvas.drawRect(left, top, left + cellSize, top + cellSize, wallPaint);
                }
            }
        }
    }

    /**
     * Gets start point.
     *
     * @return the start point
     */
    public Point getStart() {
        return start;
    }

    /**
     * Gets end point.
     *
     * @return the end point
     */
    public Point getEnd() {
        return end;
    }

    /**
     * Gets playing field size
     *
     * @return playing field size
     */
    public int getSize() {
        return size;
    }
}