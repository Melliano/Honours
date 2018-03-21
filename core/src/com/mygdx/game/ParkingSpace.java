package com.mygdx.game;

import com.badlogic.gdx.math.Polygon;

import java.awt.*;

public class ParkingSpace {

    int x, y;
    Polygon spacePoly;

    public ParkingSpace(int x, int y){
        this.x = x;
        this.y = y;

        spacePoly = new Polygon(new float[]{0, 0, 25, 0, 25, 25 , 0 , 25});
        spacePoly.setOrigin(12, 12);
        spacePoly.setPosition(x, y);
    }

    public Polygon getSpacePoly() {
        return spacePoly;
    }
}
