package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by Callum on 01/12/2017.
 */
public class Car {

    private Sprite car;
    private float speed, midXPos, midYPos;

    public Car(Sprite car){

        this.car = car;

        speed = 0;

        midXPos = car.getX() + car.getOriginX();
        midYPos = car.getY() + car.getOriginY();
    }

    public Vector2 getVelocity(float speed, float rotation){
        Vector2 vel = new Vector2();
        float vx = (float) Math.cos(Math.toRadians(rotation) * speed);
        float vy = (float) Math.sin(Math.toRadians(rotation) * speed);

        vel.x = vx;
        vel.y = vy;

        return vel;
    }

    public void move(){
        Vector2 vel = getVelocity(speed, car.getRotation());
        car.setX(car.getX() + vel.x);
        car.setY(car.getY() + vel.y);

        midXPos = car.getX() + car.getOriginX();
        midYPos = car.getY() + car.getOriginY();
    }

    public Sprite getSprite(){
        return car;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getMidXPos() {
        return midXPos;
    }

    public float getMidYPos() {
        return midYPos;
    }
}
