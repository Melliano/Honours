package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sun.applet.Main;

import java.awt.*;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;

public class MyGdxGame extends Game {
	public Texture bodyImg, wheelImg, wheelImg2, newBodyImg, backgroundImg;
	public Rectangle testCrash;
	public Sprite playerCarSprite, playerFrontWheel, playerBackWheel, playerCarNewSprite, backgroundSprite, staticSprite, staticSprite2, staticSprite3, staticSprite4, staticSprite5;
	public SpriteBatch batch;
	public BitmapFont font;
	private Table table;
	private OrthographicCamera camera;
	private Vector2 velocity;
	public CarTwo playerCar;
	int value, counter;
	Dialog dialog;
	Stage stage;
	TextButton button;
	TextButton.TextButtonStyle style;
	Skin skin;
	TextureAtlas atlas;
	MainMenuScreen mainMenuScreen;
	MainScreen mainScreen;
	StaticCar staticCar;
	boolean isCarClicked;
	public com.badlogic.gdx.math.Polygon testPolygon;
	public ShapeRenderer shapeRenderer;
	private float trackX, trackY, speed, delta, midXPos, midYPos;
	private float acc, friction, rotation, rotationStep, topVelocity;
	ArrayList<StaticCar> staticCarList;

	public void create () {
		mainMenuScreen = new MainMenuScreen(this);
		mainScreen = new MainScreen(this);
		setScreen(mainMenuScreen);
		value = 0;
		shapeRenderer = new ShapeRenderer();
		isCarClicked = false;

		backgroundImg = new Texture(Gdx.files.internal("Background.png"));
		//bodyImg = new Texture(Gdx.files.internal("Grey.png"));
		wheelImg = new Texture(Gdx.files.internal("tut1.png"));
		bodyImg = new Texture(Gdx.files.internal("blank-1299404_640.png"));
		font = new BitmapFont();

		batch = new SpriteBatch();
		backgroundSprite = new Sprite(backgroundImg);
		playerCarSprite = new Sprite(bodyImg);
		playerFrontWheel = new Sprite(wheelImg);
		playerBackWheel = new Sprite(wheelImg);
		playerCarNewSprite = new Sprite(bodyImg);
		staticSprite = new Sprite(bodyImg);

		delta = 1/60;

		playerCarNewSprite.setOrigin(playerCarNewSprite.getWidth() / 2, playerCarNewSprite.getHeight() / 2);
		playerCarNewSprite.setSize(120, 50);
		backgroundSprite.setSize(1280, 720);
		staticSprite.setSize(120, 50);
		font.setColor(Color.RED);

		testCrash = new Rectangle(20, 30, 20 , 20);
		testPolygon = new com.badlogic.gdx.math.Polygon(new float[] {0, 0 , testCrash.width, 0 , testCrash.width, testCrash.height, 0, testCrash.height});
		testPolygon.setOrigin(testCrash.width/2 , testCrash.height/2);


		playerCar = new CarTwo(playerCarNewSprite, playerFrontWheel, playerBackWheel);
		this.
		acc = 0.2f;
		friction = 0.01f;

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new ScreenViewport());
		button = new TextButton("Add Car",skin,"default");
		button.setWidth(200);
		button.setHeight(50);
		button.setX(0);
		button.setY(400);
		button.setColor(Color.RED);

		staticCarList =  new ArrayList<StaticCar>(Arrays.asList(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 200, 200)));

		dialog = new Dialog("Click Where you would like the car placed!",skin);
		dialog.setColor(Color.RED);
		counter = 0;
		button.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.show(stage);
				dialog.setX(360);
				dialog.setY(200);
				counter += 1;
				Timer.Task schedule = Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, (int) (Gdx.input.getX() - (staticSprite.getWidth() / 2)), (int) (720 - Gdx.input.getY() - (staticSprite.getHeight() / 2))));
						//staticCar = new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, (int) (Gdx.input.getX() - (staticSprite.getWidth() / 2)), (int) (720 - Gdx.input.getY() - (staticSprite.getHeight() / 2)));
						value = 1;
						dialog.hide();
						System.out.println(counter);
					}
				}, 1);
			}
		});
		stage.addActor(button);


		Gdx.input.setInputProcessor(stage);
	}

	/*
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
		shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
		shapeRenderer.setColor(Color.RED);
		shapeRenderer.rect(testCrash.x, testCrash.y, testCrash.width, testCrash.height);
		shapeRenderer.polygon(playerCar.getBoundingPoly().getTransformedVertices());
		testPolygon.setPosition(testCrash.x, testCrash.y);

		font.draw(batch, "Coordinates of car : " + playerCar.carLocation.x + " , " + playerCar.carLocation.y + "\nSpeed of car: " + playerCar.getCarSpeed()
							 + "\nCar heading: " + (Math.toDegrees(playerCar.getCarHeading())+ 90) + "\nSteer Angle: " + Math.toDegrees(playerCar.getSteerAngle()),10, 680);
		//batch.draw(playerCarSprite, playerCar.carLocation.x, playerCar.carLocation.y);
		batch.draw(playerBackWheel, playerCar.backWheelLoc.x, playerCar.backWheelLoc.y, 20 , 10);
		batch.draw(playerFrontWheel, playerCar.frontWheelLoc.x, playerCar.frontWheelLoc.y, 20, 10);
		updatePlayerCar();
		if (Intersector.overlapConvexPolygons(testPolygon, playerCar.getBoundingPoly())){
			System.out.println("COLLISION");
			playerCar.setCarSpeed(0);
		}
		batch.end();
		shapeRenderer.end();
	}
	*/

	public void updatePlayerCar() {

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
