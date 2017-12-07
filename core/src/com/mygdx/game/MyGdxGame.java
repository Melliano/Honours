package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.security.spec.MGF1ParameterSpec;

public class MyGdxGame extends ApplicationAdapter  {
	private Texture bodyImg, wheelImg, wheelImg2, newBodyImg, backgroundImg;
	private Rectangle car;
	private Sprite playerCarSprite, playerFrontWheel, playerBackWheel, playerCarNewSprite, backgroundSprite;
	private SpriteBatch batch;
	private BitmapFont font;
	private OrthographicCamera camera;
	private Vector2 velocity;
	private CarTwo playerCar;
	private float trackX, trackY, speed, delta, midXPos, midYPos;
	private float acc, friction, rotation, rotationStep, topVelocity;

	public void create () {
		float w = Gdx.graphics.getWidth();
		float h = Gdx.graphics.getHeight();

		backgroundImg = new Texture(Gdx.files.internal("Background.png"));
		bodyImg = new Texture(Gdx.files.internal("Grey.png"));
		wheelImg = new Texture(Gdx.files.internal("tut1.png"));
		newBodyImg = new Texture(Gdx.files.internal("blank-1299404_640.png"));
		font = new BitmapFont();

		batch = new SpriteBatch();
		backgroundSprite = new Sprite(backgroundImg);
		playerCarSprite = new Sprite(bodyImg);
		playerFrontWheel = new Sprite(wheelImg);
		playerBackWheel = new Sprite(wheelImg);
		playerCarNewSprite = new Sprite(newBodyImg);

		delta = 1/60;

		playerCarNewSprite.setOrigin(playerCarNewSprite.getWidth() / 2, playerCarNewSprite.getHeight() / 2);
		playerCarNewSprite.setSize(120, 50);
		backgroundSprite.setSize(1280, 720);
		font.setColor(Color.RED);

		playerCar = new CarTwo(playerCarNewSprite, playerFrontWheel, playerBackWheel);

		acc = 0.2f;
		friction = 0.01f;
	}

	@Override
	public void render () {

		//Gdx.gl.glClearColor(0, 0, 0.2f, 1);
		//Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();
		//batch.draw(backgroundSprite,0, 0);
		playerCarNewSprite.setX(playerCar.carLocation.x - (playerCar.wheelBase / 2));
		playerCarNewSprite.setY(playerCar.carLocation.y - (playerCarNewSprite.getHeight()/2));
		playerCarNewSprite.setRotation((float) Math.toDegrees(playerCar.getCarHeading()));
		//playerCarSprite.setRotation((float) Math.toDegrees(playerCar.getCarHeading()) + 90);
		//batch.draw(playerCarSprite, playerCar.carLocation.x , playerCar.carLocation.y, playerCarSprite.getOriginX(), playerCarSprite.getOriginY(),
				//playerCarSprite.getWidth(), playerCarSprite.getHeight(), 1, 1, playerCarSprite.getRotation());
		backgroundSprite.draw(batch);
		playerCarNewSprite.draw(batch);
		font.draw(batch, "Coordinates of car : " + playerCar.carLocation.x + " , " + playerCar.carLocation.y + "\nSpeed of car: " + playerCar.getCarSpeed()
							 + "\nCar heading: " + (Math.toDegrees(playerCar.getCarHeading())+ 90) + "\nSteer Angle: " + Math.toDegrees(playerCar.getSteerAngle()),10, 680 );
		//batch.draw(playerCarSprite, playerCar.carLocation.x, playerCar.carLocation.y);
		batch.draw(playerBackWheel, playerCar.backWheelLoc.x, playerCar.backWheelLoc.y, 20 , 10);
		batch.draw(playerFrontWheel, playerCar.frontWheelLoc.x, playerCar.frontWheelLoc.y, 20, 10);
		updatePlayerCar();
		batch.end();
	}


	private void updatePlayerCar() {

		final float subtractSteer = 0.25f ;
		float subtractSteer2 = -0.2f;

		if (Gdx.input.isKeyPressed(Input.Keys.UP)){
			if (playerCar.getCarSpeed() < playerCar.maxSpeed)
				playerCar.setCarSpeed(playerCar.getCarSpeed() + 0.5f);
		}
		if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			playerCar.setCarSpeed((playerCar.getCarSpeed() - 0.5f));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if (playerCar.getSteerAngle() > - playerCar.maxSteerAngle && playerCar.getSteerAngle() < playerCar.maxSteerAngle)
				playerCar.setSteerAngle((playerCar.getSteerAngle() + 0.2f));
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
			if (playerCar.getSteerAngle() > - playerCar.maxSteerAngle && playerCar.getSteerAngle() < playerCar.maxSteerAngle)
				playerCar.setSteerAngle((playerCar.getSteerAngle() - 0.2f));
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if (playerCar.getSteerAngle() > 0)
				//System.out.println(Math.toDegrees(playerCar.getSteerAngle()));
				playerCar.setSteerAngle(playerCar.getSteerAngle() - (0.4f));
			if (playerCar.getSteerAngle() < 0)
				//System.out.println(Math.toDegrees(playerCar.getSteerAngle()));
				playerCar.setSteerAngle(playerCar.getSteerAngle() + (0.4f));
		}
		if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)){
			if (playerCar.getCarSpeed() > 0)
				playerCar.setCarSpeed(playerCar.getCarSpeed() - 0.3f);
			if (playerCar.getCarSpeed() < 0)
				playerCar.setCarSpeed(playerCar.getCarSpeed() + 0.3f);
		}
		playerCar.move();
	}

	@Override
	public void dispose () {
		backgroundImg.dispose();
		newBodyImg.dispose();
		bodyImg.dispose();
		wheelImg.dispose();
		batch.dispose();
	}

	@Override
	public void resize(int width, int height){

	}


	@Override
	public void pause(){

	}

	@Override
	public void resume(){

	}
}
