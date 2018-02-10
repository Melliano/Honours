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
        collisionCheck();
        if (game.value == 1){
            editStaticCar();
        }
        game.batch.end();
        game.shapeRenderer.end();
        game.stage.act(Gdx.graphics.getDeltaTime());
        game.stage.draw();
    }

    public void collisionCheck(){
        for (int i = 1; i < game.staticCarList.size(); i++){
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getBoundingPoly())){
                System.out.println("Collision");
                game.playerCar.setCarSpeed(0);
            }
        }
    }

    public void rotateStaticCar(int carNum){
            if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)){
                System.out.println("ROTATING " + game.staticCarList.get(carNum).getCarHeading());
                game.staticCarList.get(carNum).setCarHeading(game.staticCarList.get(carNum).getCarHeading() + 15);
                game.staticCarList.get(carNum).staticCar.setRotation(game.staticCarList.get(carNum).getCarHeading());
                game.staticCarList.get(carNum).getStaticBoundingPoly().setRotation(game.staticCarList.get(carNum).getCarHeading());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)){
                game.staticCarList.get(carNum).setCarHeading(game.staticCarList.get(carNum).getCarHeading() - 15);
                game.staticCarList.get(carNum).staticCar.setRotation(game.staticCarList.get(carNum).getCarHeading());
                game.staticCarList.get(carNum).getStaticBoundingPoly().setRotation(game.staticCarList.get(carNum).getCarHeading());
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
                System.out.println("Rotating stopped");
                game.isCarClicked = false;
            }
    }

    public void editStaticCar(){
        for (int i = 1; i < game.staticCarList.size(); i++){
            game.staticCarList.get(i).staticCar.setX(game.staticCarList.get(i).staticCarLocation.x);
            game.staticCarList.get(i).staticCar.setY(game.staticCarList.get(i).staticCarLocation.y);
            game.staticCarList.get(i).staticCar.setRotation(game.staticCarList.get(i).getCarHeading());
            game.staticCarList.get(i).staticCar.draw(game.batch);
        }
        /*
        game.staticCarList.get(1).staticCar.setX(game.staticCarList.get(1).staticCarLocation.x);
        game.staticCarList.get(1).staticCar.setY(game.staticCarList.get(1).staticCarLocation.y);
        game.staticCarList.get(1).staticCar.setRotation(game.staticCarList.get(1).getCarHeading());
        //game.staticSprite.setX(game.staticCar.staticCarLocation.x);
        //game.staticSprite.setY(game.staticCar.staticCarLocation.y);
       // game.staticSprite.setRotation(game.staticCar.getCarHeading());
       // game.staticSprite.draw(game.batch);
        game.staticCarList.get(1).staticCar.draw(game.batch);
        */
        //.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.isCarClicked = false;
        for (int i = 1; i < game.staticCarList.size(); i++){
            game.shapeRenderer.polygon(game.staticCarList.get(i).getStaticBoundingPoly().getTransformedVertices());
            if (game.staticCarList.get(i).getStaticBoundingPoly().contains((int) (Gdx.input.getX()), (int) (720 - Gdx.input.getY()))){
                game.isCarClicked = true;
                System.out.println("Car Clicked");
                //System.out.println("Can rotate...");
                rotateStaticCar(i);
                if (Gdx.input.isTouched()){
                    System.out.println("Drag car");
                    game.staticCarList.get(i).staticCarLocation.x = (Gdx.input.getX() - (game.staticSprite.getWidth() / 2));
                    game.staticCarList.get(i).staticCarLocation.y = (720 - Gdx.input.getY() - (game.staticSprite.getHeight() / 2));
                    game.staticCarList.get(i).getStaticBoundingPoly().setPosition(game.staticCarList.get(i).staticCarLocation.x, game.staticCarList.get(i).staticCarLocation.y);
                }
            }
            else {
                game.isCarClicked = false;
                System.out.println("Car not clicked");
            }
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
