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
public class CarTwo {

    Sprite car, frontWheel, backWheel, frontSensor;
    Vector2 carLocation, backWheelLoc, frontWheelLoc;
    private float carSpeed, steerAngle,  dt;
    private Polygon boundingPoly, sensorBoundingPoly, leftBoundingPoly, rightBoundingPoly, frontBoundingPoly, backBoundingPoly, leftWheelBoundingPoly, leftWheelPoly, centrePoly;
    private Rectangle bounds, sensorBounds, leftBounds, rightBounds, frontBounds, backBounds, leftWheelBounds;
    float carHeading, maxSteerAngle, minSteerAngle, maxSpeed, wheelBase;

    public CarTwo(Sprite car, Sprite frontWheel, Sprite backWheel, Sprite frontSensor){

        this.car = car;
        this.frontWheel = frontWheel;
        this.backWheel = backWheel;
        this.frontSensor = frontSensor;
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

        sensorBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y - car.getHeight()), car.getWidth() + car.getWidth(),
                    car.getHeight() + car.getHeight());
        sensorBoundingPoly = new Polygon(new float[]{(-car.getWidth()), (-car.getHeight()), sensorBounds.width, (-car.getHeight()), sensorBounds.width, sensorBounds.height, (-car.getWidth()), sensorBounds.height});
        sensorBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        leftBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y - car.getHeight()), car.getWidth(), car.getHeight());
        leftBoundingPoly = new Polygon(new float[]{0, car.getWidth(), leftBounds.width, car.getWidth() , leftBounds.width, leftBounds.height, 0, leftBounds.height});
        leftBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        rightBounds = new Rectangle((carLocation.x + car.getWidth()), (carLocation.y + car.getHeight()), car.getWidth(), car.getHeight());
        rightBoundingPoly = new Polygon(new float[]{0, -car.getWidth() + 50, rightBounds.width, -car.getWidth()+50 , rightBounds.width, 0, 0, 0});
        rightBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        frontBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y + car.getHeight()), car.getWidth(), car.getHeight());
        frontBoundingPoly = new Polygon(new float[]{frontBounds.width , -frontBounds.width+car.getHeight(),  frontBounds.width * 1.5f  ,-frontBounds.width + car.getHeight() , frontBounds.width * 1.5f ,car.getWidth() , frontBounds.width, car.getWidth()});
        frontBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        backBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y + car.getHeight()), car.getWidth(), car.getHeight());
        backBoundingPoly = new Polygon(new float[]{-backBounds.width / 2, -frontBounds.width + car.getHeight(), 0 , -frontBounds.width + car.getHeight(), 0, frontBounds.width , -backBounds.width / 2, frontBounds.width});
        backBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);

        leftWheelBounds = new Rectangle((carLocation.x - car.getWidth()), (carLocation.y - car.getHeight()), car.getWidth(), car.getHeight());
        leftWheelBoundingPoly = new Polygon(new float[]{leftWheelBounds.width - 20 , leftWheelBounds.height /2 + 5 , leftWheelBounds.width, leftWheelBounds.height /2 + 5,
                leftWheelBounds.width , leftWheelBounds.height /2 - 5, leftWheelBounds.width - 20, leftWheelBounds.height / 2 - 5});
        leftWheelBoundingPoly.setOrigin(bounds.width / 2, bounds.height / 2);
        //leftWheelBoundingPoly.setOrigin(leftWheelBounds.width - 20, 5);
        System.out.println(leftWheelBoundingPoly.getOriginX() + " " + leftWheelBoundingPoly.getOriginY());

        leftWheelPoly = new Polygon(new float[]{leftWheelBounds.width - 30 , 0 , leftWheelBounds.width - 10, 0, leftWheelBounds.width - 10 ,
                10, leftWheelBounds.width - 30, 10});
        leftWheelPoly.setOrigin(bounds.width - 20, 5);

        centrePoly = new Polygon(new float[]{(bounds.width / 2) + 12, bounds.height / 2 + 12, bounds.width / 2 - 12, bounds.height / 2 + 12, (bounds.width / 2) - 12, bounds.height / 2 -  12, (bounds.width / 2) + 12 , bounds.height / 2 - 12});
        centrePoly.setOrigin(bounds.width / 2, bounds.height / 2);
        //lol
        //System.out.println(leftWheelPoly.getOriginX() + " " + leftWheelPoly.getOriginY());

        frontSensor.setX(carLocation.x);
        frontSensor.setY(carLocation.y);
        //frontSensor.setOrigin(getBoundingPoly().getX(), getBoundingPoly().getY());

        carSpeed = 0f;
        maxSteerAngle = 0.60f;
        minSteerAngle = -0.60f;
        maxSpeed = 150f;
        carHeading = -55.0f;
        steerAngle = 0f;
        wheelBase = car.getWidth() - 70;

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
        boundingPoly.setPosition((carLocation.x -  (wheelBase /2)),(carLocation.y - car.getHeight()/2));
        //boundingPoly.setRotation((float)Math.toDegrees(getSteerAngle()) + (float)Math.toDegrees(carHeading));
        boundingPoly.setRotation((float)Math.toDegrees(carHeading));
        sensorBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight()/2));
        sensorBoundingPoly.setRotation((float)Math.toDegrees(carHeading));
        leftBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() / 2));
        leftBoundingPoly.setRotation((float)Math.toDegrees(carHeading));
        rightBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() / 2));
        rightBoundingPoly.setRotation((float)Math.toDegrees(carHeading));
        frontBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() / 2));
        frontBoundingPoly.setRotation((float)Math.toDegrees(carHeading));
        backBoundingPoly.setPosition((carLocation.x - wheelBase / 2), (carLocation.y - car.getHeight() / 2));
        backBoundingPoly.setRotation((float)Math.toDegrees(carHeading));

        leftWheelBoundingPoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() /2));
        leftWheelBoundingPoly.setRotation((float)Math.toDegrees(getSteerAngle()) + (float)Math.toDegrees(carHeading));

        centrePoly.setRotation((float)Math.toDegrees(carHeading));
        centrePoly.setPosition((carLocation.x - (wheelBase / 2)), (carLocation.y - car.getHeight() /2));
        //leftWheelPoly.setPosition(leftWheelBoundingPoly.getX(), leftWheelBoundingPoly.getY());
        //leftWheelPoly.setRotation((float)Math.toDegrees(getSteerAngle()) + (float)Math.toDegrees(carHeading));
        //frontSensor.setPosition(boundingPoly.getX() + car.getWidth(), boundingPoly.getY() - (car.getHeight() / 2));
        frontSensor.setRotation((float)Math.toDegrees(carHeading) + 90);

        //System.out.println(leftWheelPoly.getOriginX() + " " +  leftWheelPoly.getOriginY() + "HERE");

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

    public Polygon getBoundingPoly(){
            return boundingPoly;
    }

    public Polygon getSensorBoundingPoly(){
        return sensorBoundingPoly;
    }

    public Polygon getLeftBoundingPoly(){
        return leftBoundingPoly;
    }

    public Polygon getRightBoundingPoly(){
        return rightBoundingPoly;
    }

    public Polygon getFrontBoundingPoly(){
        return frontBoundingPoly;
    }

    public Polygon getBackBoundingPoly(){
        return backBoundingPoly;
    }

    public Polygon getLeftWheelBoundingPoly(){
        return  leftWheelBoundingPoly;
    }

    public Polygon getCentrePoly(){
        return centrePoly;
    }

    public Polygon getLeftWheelPoly(){
        return  leftWheelPoly;
    }

    public void rotateLeftWheel(){
        leftWheelBoundingPoly.rotate(5);
    }
}
