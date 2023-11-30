package com.example.maze;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;



public class PlayerTest {

    private PlayerForTest player;

    @Before
    public void setUp() throws Exception {
        player = new PlayerForTest(new Point(1, 1), 10);
    }

    @Test
    public void playerTest() {
        assertEquals(player.getPoint().x, player.getX());
        assertEquals(player.getPoint().y, player.getY());
        player.moveTo(2, 4);
        assertEquals(player.getPoint().x, player.getX());
        assertEquals(player.getPoint().y, player.getY());
    }
}