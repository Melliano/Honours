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

    public StaticCar(Sprite staticCar, Sprite frontWheel, Sprite backWheel, int x, int y){

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

    public Polygon getStaticBoundingPoly() {
        return staticBoundingPoly;
    }

    public float getCarHeading() {
        return carHeading;
    }

    public void setCarHeading(float carHeading) {
        this.carHeading = carHeading;
    }

}

