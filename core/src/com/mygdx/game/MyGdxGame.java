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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import sun.applet.Main;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.MGF1ParameterSpec;
import java.util.ArrayList;
import java.util.Arrays;

public class MyGdxGame extends Game {
	public Texture bodyImg, wheelImg, frontSensorImg, newBodyImg, backgroundImg;
	public Rectangle testCrash;
	public Sprite playerCarSprite, playerFrontWheel, playerBackWheel, playerCarNewSprite, backgroundSprite, staticSprite, frontSensorSprite;
	public SpriteBatch batch;
	public BitmapFont font;
	private int screenWidth, ScreenHeight;
	private Table table;
	private OrthographicCamera camera;
	private Vector2 velocity;
	double lowestDiff;
	public CarTwo playerCar;
	int leftValue, rightValue, centreValue, forwardValue, stopValue, reverseValue;
	int value, counter, scanValue, spaceValue;
	Dialog dialog, dialogStatic;
	Stage stage;
	TextButton button, scanButton;
	TextButton.TextButtonStyle style;
	Skin skin;
	TextureAtlas atlas;
	MainMenuScreen mainMenuScreen;
	MainScreen mainScreen;
	StaticCar staticCar;
	double totalDiff = 0;
	double rotation;
	boolean isCarClicked;
	public com.badlogic.gdx.math.Polygon testPolygon, carSpacePoly, carSpacePolyTwo, carSpacePolyThree, carSpacePolyFour,
			carSpacePolyFive, carSpacePolySix, carSpacePolySeven, carSpacePolyEight, carSpacePolyNine, carSpacePolyTen;
	public ShapeRenderer shapeRenderer;
	private float trackX, trackY, speed, delta, midXPos, midYPos;
	private float acc, friction, rotationStep, topVelocity;
	ArrayList<StaticCar> staticCarList;
	ArrayList<ParkingSpace> polygonsList, takenSpacesList, freeSpacesList;
	FileWriter pw;
	ParkingSpace[] array;


	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		mainScreen = new MainScreen(this);
		setScreen(mainMenuScreen);
		value = 0;
		spaceValue = 0;
		shapeRenderer = new ShapeRenderer();
		isCarClicked = false;


		backgroundImg = new Texture(Gdx.files.internal("test.png"));
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
		frontSensorSprite = new Sprite(wheelImg);

		delta = 1 / 60;
		playerCarNewSprite.setOrigin(playerCarNewSprite.getWidth() / 2, playerCarNewSprite.getHeight() / 2);
		playerCarNewSprite.setSize(120, 50);
		backgroundSprite.setSize(1280, 720);
		staticSprite.setSize(120, 50);
		frontSensorSprite.setSize(5, 50);
		font.setColor(Color.RED);

		polygonsList = new ArrayList<ParkingSpace>();
		takenSpacesList = new ArrayList<ParkingSpace>();
		freeSpacesList = new ArrayList<ParkingSpace>();
		testCrash = new Rectangle(20, 30, 20, 20);
		testPolygon = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, testCrash.width, 0, testCrash.width, testCrash.height, 0, testCrash.height});
		testPolygon.setOrigin(testCrash.width / 2, testCrash.height / 2);

		carSpacePoly = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePoly.setOrigin(12, 12);
		polygonsList.add(new ParkingSpace(690, 165));
		polygonsList.add(new ParkingSpace(690, 255));
		polygonsList.add(new ParkingSpace(690, 355));
		polygonsList.add(new ParkingSpace(690, 445));
		polygonsList.add(new ParkingSpace(690, 545));
		polygonsList.add(new ParkingSpace(1200, 165));
		polygonsList.add(new ParkingSpace(1200, 255));
		polygonsList.add(new ParkingSpace(1200, 355));
		polygonsList.add(new ParkingSpace(1200, 445));
		polygonsList.add(new ParkingSpace(1200, 545));
		for (int i = 0; i < polygonsList.size(); i++){
			if (polygonsList.get(i).x == 690){
				polygonsList.get(i).getSpacePoly().setRotation(270);
			}
			else if (polygonsList.get(i).x == 1200){
				polygonsList.get(i).getSpacePoly().setRotation(90);
			}
		}

		carSpacePolyTwo = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyTwo.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyTwo);

		carSpacePolyThree = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyThree.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyThree);

		carSpacePolyFour = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyFour.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyFour);

		carSpacePolyFive = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyFive.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyFive);

		carSpacePolySix = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolySix.setOrigin(12, 12);
		//polygonsList.add(carSpacePolySix);

		carSpacePolySeven = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolySeven.setOrigin(12, 12);
		//polygonsList.add(carSpacePolySeven);

		carSpacePolyEight = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyEight.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyEight);

		carSpacePolyNine = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyNine.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyNine);

		carSpacePolyTen = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyTen.setOrigin(12, 12);
		//polygonsList.add(carSpacePolyTen);

		playerCar = new CarTwo(playerCarNewSprite, playerFrontWheel, playerBackWheel, frontSensorSprite);
		this.
				acc = 0.2f;
		friction = 0.01f;

		skin = new Skin(Gdx.files.internal("uiskin.json"));
		stage = new Stage(new ScreenViewport());
		//Add Car Button
		button = new TextButton("Add Car", skin, "default");
		button.setWidth(200);
		button.setHeight(50);
		button.setX(0);
		button.setY(400);
		button.setColor(Color.RED);
		//Scan for Space Button
		scanButton = new TextButton("Scan for Space", skin, "default");
		scanButton.setWidth(200);
		scanButton.setHeight(50);
		scanButton.setX(0);
		scanButton.setY(340);
		scanButton.setColor(Color.RED);

		staticCarList = new ArrayList<StaticCar>(Arrays.asList(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 200, 200)));

		dialog = new Dialog("Click where you would like the car placed!", skin);
		dialogStatic = new Dialog(" - Click Escape to delete car \n - Drag and Drop car to move \n - User arrow keys to rotate", skin);
		dialogStatic.show(stage);
		counter = 0;
		//Constructing first scenario
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarNewSprite.getHeight(), 165 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarNewSprite.getHeight(), 255 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarNewSprite.getHeight(), 355 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarNewSprite.getHeight(), 445 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarNewSprite.getHeight(), 165 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarNewSprite.getHeight(), 255 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarNewSprite.getHeight(), 445 - (int) (playerCarNewSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarNewSprite.getHeight(), 545 - (int) (playerCarNewSprite.getHeight() / 4)));

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.show(stage);
				dialog.setX(500);
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
		scanButton.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				// Scan for space method call
				Timer.Task schedule = Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						scanValue = 1;
						//scanSpace();
					}
				}, 1);
			}
		});
		stage.addActor(button);
		stage.addActor(scanButton);
		Gdx.input.setInputProcessor(stage);
	}

	public double distanceToSpace(){

		int xCoord;
		int yCoord;
		double distance;

		xCoord = freeSpacesList.get(counter).x;
		yCoord = freeSpacesList.get(counter).y;

		distance = Math.sqrt(Math.pow((xCoord - (playerCar.getCentrePoly().getX())), 2) +
				Math.pow((playerCar.getCentrePoly().getY() - yCoord), 2));
		rotation = freeSpacesList.get(counter).getSpacePoly().getRotation();
		//System.out.println("The rotation of space " + counter + " is " + rotation);

		return distance;
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

	//TBD : Perhaps increment speed???
	public void updatePlayerCar() {

		final float subtractSteer = 0.25f;
		float subtractSteer2 = -0.2f;

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (playerCar.getCarSpeed() < playerCar.maxSpeed)
				if (playerCar.getCarSpeed() < 0){
					playerCar.setCarSpeed(0);
				}
				else {
					playerCar.setCarSpeed(50);
				}
				//playerCar.setCarSpeed(playerCar.getCarSpeed() + 1f);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (playerCar.getCarSpeed() <= 0){
				playerCar.setCarSpeed( -50);
			}
			if (playerCar.getCarSpeed() > 0){
				playerCar.setCarSpeed(0);
			}
			//playerCar.setCarSpeed((playerCar.getCarSpeed() - 1f));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
			if (playerCar.getSteerAngle() >= (-playerCar.maxSteerAngle) && playerCar.getSteerAngle() < playerCar.maxSteerAngle) {
				playerCar.setSteerAngle((playerCar.getSteerAngle() + 0.60f));
				//System.out.println(playerCar.getSteerAngle() + " " + playerCar.maxSteerAngle);
			}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
			if (playerCar.getSteerAngle() > (-playerCar.maxSteerAngle) && playerCar.getSteerAngle() <= playerCar.maxSteerAngle) {
				playerCar.setSteerAngle((playerCar.getSteerAngle() - 0.60f));
				//System.out.println(playerCar.getSteerAngle() + " " + (-playerCar.maxSteerAngle));
			}
		}
		/*
		if (!Gdx.input.isKeyPressed(Input.Keys.RIGHT) && !Gdx.input.isKeyPressed(Input.Keys.LEFT)){
			if (playerCar.getSteerAngle() > 0)
				//System.out.println(Math.toDegrees(playerCar.getSteerAngle()));
				playerCar.setSteerAngle(playerCar.getSteerAngle() - (0.4f));
			if (playerCar.getSteerAngle() < 0)
				//System.out.println(Math.toDegrees(playerCar.getSteerAngle()));
				playerCar.setSteerAngle(playerCar.getSteerAngle() + (0.4f));
		}
		*/
		/*
		if (!Gdx.input.isKeyPressed(Input.Keys.UP) && !Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			if (playerCar.getCarSpeed() > 0) {
				playerCar.setCarSpeed(playerCar.getCarSpeed() - 0.25f);
				//System.out.println("Current Speed " + playerCar.getCarSpeed());
			}
			if (playerCar.getCarSpeed() < 0) {
				playerCar.setCarSpeed(playerCar.getCarSpeed() + 0.25f);
				//System.out.println("Current Speed " + playerCar.getCarSpeed());
			}
		}
		*/
		if (playerCar.getSteerAngle() < 0){
			rightValue = 1;
			leftValue = 0;
			centreValue = 0;
		}
		else if (playerCar.getSteerAngle() > 0) {
			rightValue = 0;
			leftValue = 1;
			centreValue = 0;
		}
		else {
			rightValue = 0;
			leftValue = 0;
			centreValue = 1;
		}
		//Outputs for speed
		if (playerCar.getCarSpeed() > 0){
			forwardValue = 1;
			stopValue = 0;
			reverseValue = 0;
		}
		else if (playerCar.getCarSpeed() < 0){
			reverseValue = 1;
			stopValue = 0;
			forwardValue = 0;
		}
		else {
			stopValue = 1;
			forwardValue = 0;
			reverseValue = 0;
		}

		playerCar.move();
	}

	public void scanSpace() {
		float xCoord = 0;
		float yCoord = 0;
		float nearestX = 0;
		float nearestY = 0;
		float xDiff = 0;
		float yDiff = 0;
		lowestDiff = 0;
		counter = 0;
		int counterTwo = 0;
		takenSpacesList.clear();
		freeSpacesList.clear();
		scanValue = 1;
		for (int i = 0; i < polygonsList.size(); i++) {
			for (int x = 0; x < staticCarList.size(); x++) {
				if (Intersector.overlapConvexPolygons(staticCarList.get(x).getStaticBoundingPoly(), polygonsList.get(i).getSpacePoly())) {
					System.out.println("Space taken " + i);
					takenSpacesList.add(polygonsList.get(i));
					break;
				}
			}
		}
		for (int i = 0; i < polygonsList.size(); i++) {
			if (takenSpacesList.contains(polygonsList.get(i))) {
				System.out.println("Not free" + polygonsList.get(i));
			} else {
				System.out.println("Free at" + polygonsList.get(i));
				freeSpacesList.add(polygonsList.get(i));
			}
		}
		System.out.println(freeSpacesList);
		for (int i = 0; i < freeSpacesList.size(); i++) {
			xCoord = freeSpacesList.get(i).x;
			yCoord = freeSpacesList.get(i).y;

			xDiff = playerCar.getCentrePoly().getX() - xCoord;
			yDiff = playerCar.getCentrePoly().getY() - yCoord;

			totalDiff = Math.sqrt(Math.pow((xCoord - (playerCar.getCentrePoly().getX())), 2) +
					Math.pow((playerCar.getCentrePoly().getY() - yCoord), 2));

			System.out.println("Total diff for " + i + " " + totalDiff);
			if (lowestDiff == 0) {
				lowestDiff = totalDiff;
				System.out.println("First lowest");
				nearestY = yCoord;
				counter = i;
			}
			if (totalDiff < lowestDiff) {
				lowestDiff = totalDiff;
				System.out.println("New Lowest Point");
				nearestX = xCoord;
				nearestY = yCoord;
				counter = i;
			}
		}
		System.out.println(totalDiff);
		if (playerCar.getCentrePoly().getY() < nearestY - playerCarNewSprite.getWidth()){
			playerCar.setCarSpeed(50);
		}
		else {
			playerCar.setCarSpeed(0);
			//freeSpacesList.clear();
			scanValue = 0;
			return;
		}
		spaceValue = 1;
	}

	/*
	public void scanForSpace(){
		float xCoord = 0;
		float yCoord = 0;
		float xDiff = 0;
		float yDiff = 0;
		float lowestDiff;
		for (int i = 0; i < polygonsList.size(); i++){
			xCoord = polygonsList.get(i).getX();
			yCoord = polygonsList.get(i).getY();

			xDiff = (playerCar.getCentrePoly().getX() + 50) - xCoord;
			yDiff = (playerCar.getCentrePoly().getY() - yCoord);

			if (lowestDiff == null){
				lowestDiff = xDiff + yDiff;
			}
			else{
				if (xDiff + yDiff < lowestDiff){
					lowestDiff = xDiff + yDiff;
					System.out.println(lowestDiff);
				}
			}

		}
	}
	*/

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
