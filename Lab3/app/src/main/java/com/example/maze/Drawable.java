package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * The interface Drawable, so that during drawing we do not know what kind of object we are drawing
 */
public interface Drawable {
    /**
     * Method to be overridden in the inherited class.
     *
     * @param canvas the canvas
     * @param rect   the rect
     */
    void draw(Canvas canvas, Rect rect);
}
