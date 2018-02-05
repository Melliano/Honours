package com.mygdx.game;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class StaticCar {

    Sprite staticCar, frontWheel, backWheel;
    Vector2 staticCarLocation;
    private Polygon staticBoundingPoly;
    private Rectangle staticBounds;
    float carHeading, wheelBase;
    int x, y, instance;

    public StaticCar(Sprite staticCar, Sprite frontWheel, Sprite backWheel, int x, int y){

        instance = 1;
        this.staticCar = staticCar;
        staticCarLocation = new Vector2(x, y);
        this.frontWheel = frontWheel;
        this.backWheel = backWheel;
        this.staticCar.setOrigin(this.staticCar.getWidth() / 2, this.staticCar.getHeight() / 2);

        wheelBase =  staticCar.getWidth() - 40;

        staticBounds = new Rectangle((staticCarLocation.x - (wheelBase / 2)) , (staticCarLocation.y - staticCar.getHeight()/2) , staticCar.getWidth(), staticCar.getHeight());
        staticBoundingPoly = new Polygon(new float[]{0, 0, staticBounds.width, 0, staticBounds.width, staticBounds.height, 0, staticBounds.height});
        staticBoundingPoly.setOrigin(staticBounds.width / 2, staticBounds.height / 2);

        carHeading = 0.0f;

        staticBoundingPoly.setPosition(staticCarLocation.x, staticCarLocation.y);

    }

    //TBD
    public void userMove(){

    }

    public Sprite getStaticCar() {
        return staticCar;
    }

    public void setStaticCar(Sprite staticCar) {
        this.staticCar = staticCar;
    }

    public Sprite getFrontWheel() {
        return frontWheel;
    }

    public void setFrontWheel(Sprite frontWheel) {
        this.frontWheel = frontWheel;
    }

    public Sprite getBackWheel() {
        return backWheel;
    }

    public void setBackWheel(Sprite backWheel) {
        this.backWheel = backWheel;
    }

    public Vector2 getStaticCarLocation() {
        return staticCarLocation;
    }

    public void setStaticCarLocation(Vector2 staticCarLocation) {
        this.staticCarLocation = staticCarLocation;
    }

    public Polygon getStaticBoundingPoly() {
        return staticBoundingPoly;
    }

    public void setStaticBoundingPoly(Polygon staticBoundingPoly) {
        this.staticBoundingPoly = staticBoundingPoly;
    }

    public Rectangle getStaticBounds() {
        return staticBounds;
    }

    public void setStaticBounds(Rectangle staticBounds) {
        this.staticBounds = staticBounds;
    }

    public float getCarHeading() {
        return carHeading;
    }

    public void setCarHeading(float carHeading) {
        this.carHeading = carHeading;
    }

    public float getWheelBase() {
        return wheelBase;
    }

    public void setWheelBase(float wheelBase) {
        this.wheelBase = wheelBase;
    }

    public void setInstance(int value){
        this.instance = value;
    }

    public int getInstance(){
        return instance;
    }
}

