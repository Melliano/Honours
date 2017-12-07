package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/**
 * Created by Callum on 02/12/2017.
 */
public class Wheel {

    private World world;
    Car car;
    Body body;
    Sprite sprite;
    Texture wheel;

    public Wheel(Car car){
        car  = car;
    }

}
