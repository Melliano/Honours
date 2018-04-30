package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.w3c.dom.css.Rect;

/**
 * Created by Callum on 03/12/2017.
 */
public class Car {

    Sprite car, frontWheel, backWheel;
    Vector2 carLocation, backWheelLoc, frontWheelLoc;
    private float carSpeed, steerAngle,  dt;
    private Polygon boundingPoly, leftWheelBoundingPoly, centrePoly;
    private Rectangle bounds, leftWheelBounds;
    float carHeading, maxSteerAngle, minSteerAngle, maxSpeed, wheelBase;

    public Car(Sprite car, Sprite frontWheel, Sprite backWheel){

        this.car = car;
        this.frontWheel = frontWheel;
        this.backWheel = backWheel;
        wheelBase = car.getWidth() - 80;
        carLocation = new Vector2(900,50);
        this.car.setOrigin(this.car.getWidth()/2, this.car.getHeight()/2);
        backWheelLoc = new Vector2();
        frontWheelLoc = new Vector2();

        backWheel.setX(backWheelLoc.x);
        backWheel.setY(backWheelLoc.y);

        frontWheel.setX(frontWheelLoc.x);
        frontWheel.setY(frontWheelLoc.y);

        bounds = new Rectangle((carLocation.x -  (wheelBase /2)), (carLocation.y - car.getHeight()/2) , car.getWidth(), car.getHeight());
        boundingPoly = new Polygon(new float[]{0, 0, bounds.width, 0 , bounds.width, bounds.height, 0, bounds.height});
        boundingPoly.setOrigin(bounds.width/2, bounds.height/2);

        leftWheelBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y - car.getHeight()), car.getWidth(), car.getHeight());
        leftWheelBoundingPoly = new Polygon(new float[]{leftWheelBounds.width - 20 , leftWheelBounds.height /2 + 5 , leftWheelBounds.width, leftWheelBounds.height /2 + 5,
                leftWheelBounds.width , leftWheelBounds.height /2 - 5, leftWheelBounds.width - 20, leftWheelBounds.height / 2 - 5});
        leftWheelBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        centrePoly = new Polygon(new float[]{(bounds.width / 2) + 12, bounds.height / 2 + 12, bounds.width / 2 - 12, bounds.height / 2 + 12, (bounds.width / 2) - 12, bounds.height / 2 -  12, (bounds.width / 2) + 12 , bounds.height / 2 - 12});
        centrePoly.setOrigin(bounds.width / 2, bounds.height / 2);

        carSpeed = 0f;
        maxSteerAngle = 0.6000001f;
        minSteerAngle = -0.6000001f;
        maxSpeed = 50f;
        carHeading = -55.0f;
        steerAngle = 0f;
        wheelBase = car.getWidth() - 70;

        dt = 1/60f;
    }

    public void move(){

        frontWheelLoc.x = carLocation.x + wheelBase/2 * (float) Math.cos(carHeading);
        frontWheelLoc.y = carLocation.y + wheelBase/2 * (float) Math.sin(carHeading);
        backWheelLoc.x = carLocation.x - wheelBase/2 * (float) Math.cos(carHeading);
        backWheelLoc.y = carLocation.y - wheelBase/2 * (float)Math.sin(carHeading);
        backWheelLoc.x += carSpeed * dt * (float) Math.cos(carHeading);
        backWheelLoc.y += carSpeed * dt * (float) Math.sin(carHeading);
        frontWheelLoc.x += carSpeed * dt * (float) Math.cos(carHeading + steerAngle);
        frontWheelLoc.y += carSpeed * dt * (float)Math.sin(carHeading + steerAngle);

        carLocation.x = (frontWheelLoc.x + backWheelLoc.x) / 2;
        carLocation.y = (frontWheelLoc.y + backWheelLoc.y) / 2;

        carHeading = MathUtils.atan2(frontWheelLoc.y - backWheelLoc.y, frontWheelLoc.x - backWheelLoc.x);
        boundingPoly.setPosition((carLocation.x -  (wheelBase /2)),(carLocation.y - car.getHeight()/2));
        boundingPoly.setRotation((float)Math.toDegrees(carHeading));

        leftWheelBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() /2));
        leftWheelBoundingPoly.setRotation((float)Math.toDegrees(getSteerAngle()) + (float)Math.toDegrees(carHeading));

        centrePoly.setRotation((float)Math.toDegrees(carHeading));
        centrePoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() /2));
    }

    public float getCarSpeed() {
        return carSpeed;
    }

    public void setCarSpeed(float carSpeed) {
        this.carSpeed = carSpeed;
    }

    public float getSteerAngle() {
        return steerAngle;
    }

    public void setSteerAngle(float steerAngle) {
        this.steerAngle = steerAngle;
    }

    public float getCarHeading() {
        return carHeading;
    }

    public void setCarHeading(float carHeading) {
        this.carHeading = carHeading;
    }

    public Polygon getBoundingPoly(){
            return boundingPoly;
    }

    public Polygon getLeftWheelBoundingPoly(){
        return  leftWheelBoundingPoly;
    }

    public Polygon getCentrePoly(){
        return centrePoly;
    }

}
