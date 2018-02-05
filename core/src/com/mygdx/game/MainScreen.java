package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.sun.org.apache.xpath.internal.SourceTree;

public class MainScreen implements Screen {

    MyGdxGame game;
    StaticCar staticCar;
    Window pause;
    Skin skin;
    Stage stage;
    TextButton button;
    boolean carClicked;
    public MainScreen(MyGdxGame game){
        this.game = game;
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        //Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        //Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        //batch.draw(backgroundSprite,0, 0);
        game.playerCarNewSprite.setX(game.playerCar.carLocation.x - (game.playerCar.wheelBase / 2));
        game.playerCarNewSprite.setY(game.playerCar.carLocation.y - (game.playerCarNewSprite.getHeight()/2));
        game.playerCarNewSprite.setRotation((float) Math.toDegrees(game.playerCar.getCarHeading()));
        //playerCarSprite.setRotation((float) Math.toDegrees(playerCar.getCarHeading()) + 90);
        //batch.draw(playerCarSprite, playerCar.carLocation.x , playerCar.carLocation.y, playerCarSprite.getOriginX(), playerCarSprite.getOriginY(),
        //playerCarSprite.getWidth(), playerCarSprite.getHeight(), 1, 1, playerCarSprite.getRotation());
        game.playerCarNewSprite.draw(game.batch);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.rect(game.testCrash.x, game.testCrash.y, game.testCrash.width, game.testCrash.height);
        game.shapeRenderer.polygon(game.playerCar.getBoundingPoly().getTransformedVertices());
        game.testPolygon.setPosition(game.testCrash.x, game.testCrash.y);

        game.font.draw(game.batch, "Coordinates of car : " + game.playerCar.carLocation.x + " , " + game.playerCar.carLocation.y + "\nSpeed of car: " + game.playerCar.getCarSpeed()
                + "\nCar heading: " + (Math.toDegrees(game.playerCar.getCarHeading())+ 90) + "\nSteer Angle: " + Math.toDegrees(game.playerCar.getSteerAngle()),10, 680);
        //batch.draw(playerCarSprite, playerCar.carLocation.x, playerCar.carLocation.y);
        game.batch.draw(game.playerBackWheel, game.playerCar.backWheelLoc.x, game.playerCar.backWheelLoc.y, 20 , 10);
        game.batch.draw(game.playerFrontWheel, game.playerCar.frontWheelLoc.x, game.playerCar.frontWheelLoc.y, 20, 10);
        game.updatePlayerCar();
        if (Intersector.overlapConvexPolygons(game.testPolygon, game.playerCar.getBoundingPoly())){
            System.out.println("COLLISION");
            game.playerCar.setCarSpeed(0);
        }
        if (game.value == 1){
            game.staticSprite.setX(game.staticCar.staticCarLocation.x);
            game.staticSprite.setY(game.staticCar.staticCarLocation.y);
            game.staticSprite.setRotation(game.staticCar.getCarHeading());
            game.staticSprite.draw(game.batch);
            //.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            game.shapeRenderer.setColor(Color.RED);
            game.shapeRenderer.polygon(game.staticCar.getStaticBoundingPoly().getTransformedVertices());
            if (Gdx.input.isTouched()){
                if (game.staticCar.getStaticBoundingPoly().contains((int) (Gdx.input.getX()), (int) (720 - Gdx.input.getY()))){
                    carClicked = true;
                    System.out.println("Car Clicked");
                }
            }
            if (carClicked = true){
                rotateStaticCar();
            }
        }
        game.batch.end();
        game.shapeRenderer.end();
        game.stage.act(Gdx.graphics.getDeltaTime());
        game.stage.draw();
    }

    public void rotateStaticCar(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
            System.out.println("ROTATING " + game.staticCar.getCarHeading());
            game.staticCar.setCarHeading(game.staticCar.getCarHeading() + 15);
            game.staticSprite.setRotation(game.staticCar.getCarHeading());
            game.staticCar.getStaticBoundingPoly().setRotation(game.staticCar.getCarHeading());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
            game.staticCar.setCarHeading(game.staticCar.getCarHeading() - 15);
            game.staticSprite.setRotation(game.staticCar.getCarHeading());
            game.staticCar.getStaticBoundingPoly().setRotation(game.staticCar.getCarHeading());
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            carClicked = false;
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
