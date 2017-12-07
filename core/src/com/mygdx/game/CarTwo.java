package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.org.apache.bcel.internal.generic.NEW;

/**
 * Created by Callum on 03/12/2017.
 */
public class CarTwo {

    Sprite car, frontWheel, backWheel;
    Vector2 carLocation, backWheelLoc, frontWheelLoc;
    private float carSpeed, steerAngle,  dt;
    float carHeading, maxSteerAngle, minSteerAngle, maxSpeed, wheelBase;

    public CarTwo(Sprite car, Sprite frontWheel, Sprite backWheel){

        this.car = car;
        this.frontWheel = frontWheel;
        this.backWheel = backWheel;

        carLocation = new Vector2(640,360);
        this.car.setOrigin(this.car.getWidth()/2, this.car.getHeight()/2);
        backWheelLoc = new Vector2();
        frontWheelLoc = new Vector2();

        backWheel.setX(backWheelLoc.x);
        backWheel.setY(backWheelLoc.y);

        frontWheel.setX(frontWheelLoc.x);
        frontWheel.setY(frontWheelLoc.y);

        carSpeed = 0f;
        maxSteerAngle = 0.65f;
        minSteerAngle = -0.8f;
        maxSpeed = 150f;
        carHeading = 0.0f;
        steerAngle = 0f;
        wheelBase = car.getWidth() - 40;

        dt = 1/60f;
    }

    public void move(){

        //backWheel.add(new Vector2((float)Math.cos(carHeading),(float)Math.sin(carHeading)).scl((carSpeed * dt)));
        //frontWheel.add(new Vector2((float) Math.cos(carHeading+steerAngle), (float) Math.sin(carHeading+steerAngle)).scl((carSpeed * dt)));

        frontWheelLoc.x = carLocation.x + wheelBase/2 * (float) Math.cos(carHeading);
        frontWheelLoc.y = carLocation.y + wheelBase/2 * (float) Math.sin(carHeading);
        backWheelLoc.x = carLocation.x - wheelBase/2 * (float) Math.cos(carHeading);
        backWheelLoc.y = carLocation.y - wheelBase/2 * (float)Math.sin(carHeading);
        backWheelLoc.x += carSpeed * dt * (float) Math.cos(carHeading);
        backWheelLoc.y += carSpeed * dt * (float) Math.sin(carHeading);
        frontWheelLoc.x += carSpeed * dt * (float) Math.cos(carHeading + steerAngle);
        frontWheelLoc.y += carSpeed * dt * (float)Math.sin(carHeading + steerAngle);

        /*
        frontWheel.setX(carLocation.x + wheelBase / 2 * (float) Math.sin(carHeading));
        frontWheel.setY(carLocation.y + wheelBase / 2 * (float) Math.cos(carHeading));

        backWheel.setX(carLocation.x - wheelBase / 2 * (float) Math.sin(carHeading));
        backWheel.setY(carLocation.x - wheelBase / 2 * (float) Math.sin(carHeading));
        backWheel.setX(backWheel.getX() + carSpeed * dt * (float) Math.sin(carHeading));
        backWheel.setY(backWheel.getY() + carSpeed * dt * (float) Math.cos(carHeading));

        frontWheel.setX(frontWheel.getX() + (carSpeed * dt * (float) Math.sin(carHeading + steerAngle)));
        frontWheel.setY(frontWheel.getY() + (carSpeed * dt * (float)Math.cos(carHeading+steerAngle)));

        */
        carLocation.x = (frontWheelLoc.x + backWheelLoc.x) / 2;
        carLocation.y = (frontWheelLoc.y + backWheelLoc.y) / 2;

        carHeading = MathUtils.atan2(frontWheelLoc.y - backWheelLoc.y, frontWheelLoc.x - backWheelLoc.x);
        ;
        //System.out.println(Math.toDegrees(maxSteerAngle));
        //System.out.println(Math.toDegrees(minSteerAngle));
        //System.out.println("current angle " +  Math.toDegrees(getSteerAngle()));

        //System.out.println(backWheel.getX() + "," + backWheel.getY() + " ----- " + backWheelLoc.x + "," + backWheelLoc.y);

        //System.out.println("Coordinates " + carLocation + "Steer angle : " + steerAngle + " Speed " + carSpeed + " Heading " + carHeading + " Wheel Base " + wheelBase + " Back Wheel " + backWheelLoc.x + ", " + backWheelLoc.y
         //                           + " Front Wheel " + frontWheelLoc.x +" , "+  frontWheelLoc.y);
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
}
