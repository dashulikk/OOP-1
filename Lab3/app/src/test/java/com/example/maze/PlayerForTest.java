package com.example.maze;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;

public class PlayerForTest extends Player {

    public PlayerForTest(Point start, int size) {
        this.point = start;
        this.size = size;
        this.paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }
}
