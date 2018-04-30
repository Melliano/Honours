package com.mygdx.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Dialog;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

public class MyGdxGame extends Game {

	public Texture bodyImg, wheelImg, newBodyImg, backgroundImg;
	public Rectangle testCrash;
	public Sprite playerFrontWheel, playerBackWheel, playerCarSprite, backgroundSprite, staticSprite;
	public SpriteBatch batch;
	public BitmapFont font;
	double lowestDiff, score;
	int yDiff, xDiff;
	public Car playerCar;
	int leftValue, rightValue, centreValue, forwardValue, stopValue, reverseValue;
	int value, counter, scanValue, spaceValue;
	Dialog dialog, dialogStatic;
	Stage stage;
	TextButton button, scanButton, resetButton;
	Skin skin;
	MainMenuScreen mainMenuScreen;
	MainScreen mainScreen;
	double totalDiff = 0;
	double rotation;
	boolean isCarClicked;
	public com.badlogic.gdx.math.Polygon testPolygon, carSpacePoly, carSpacePolyTwo, carSpacePolyThree, carSpacePolyFour,
			carSpacePolyFive, carSpacePolySix, carSpacePolySeven, carSpacePolyEight, carSpacePolyNine, carSpacePolyTen;
	public ShapeRenderer shapeRenderer;
	ArrayList<StaticCar> staticCarList;
	ArrayList<ParkingSpace> polygonsList, takenSpacesList, freeSpacesList;

	public void create() {
		mainMenuScreen = new MainMenuScreen(this);
		mainScreen = new MainScreen(this);
		setScreen(mainMenuScreen);
		value = 0;
		spaceValue = 0;
		shapeRenderer = new ShapeRenderer();
		isCarClicked = false;

		backgroundImg = new Texture(Gdx.files.internal("test.png"));
		wheelImg = new Texture(Gdx.files.internal("tut1.png"));
		bodyImg = new Texture(Gdx.files.internal("blank-1299404_640.png"));
		font = new BitmapFont();

		batch = new SpriteBatch();
		backgroundSprite = new Sprite(backgroundImg);
		playerFrontWheel = new Sprite(wheelImg);
		playerBackWheel = new Sprite(wheelImg);
		playerCarSprite = new Sprite(bodyImg);
		staticSprite = new Sprite(bodyImg);

		playerCarSprite.setOrigin(playerCarSprite.getWidth() / 2, playerCarSprite.getHeight() / 2);
		playerCarSprite.setSize(120, 50);
		backgroundSprite.setSize(1280, 720);
		staticSprite.setSize(120, 50);
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
				polygonsList.get(i).getSpacePoly().setRotation(360);
			}
			else if (polygonsList.get(i).x == 1200){
				polygonsList.get(i).getSpacePoly().setRotation(180);
			}
		}

		carSpacePolyTwo = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyTwo.setOrigin(12, 12);

		carSpacePolyThree = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyThree.setOrigin(12, 12);

		carSpacePolyFour = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyFour.setOrigin(12, 12);

		carSpacePolyFive = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyFive.setOrigin(12, 12);

		carSpacePolySix = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolySix.setOrigin(12, 12);

		carSpacePolySeven = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolySeven.setOrigin(12, 12);

		carSpacePolyEight = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyEight.setOrigin(12, 12);

		carSpacePolyNine = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyNine.setOrigin(12, 12);

		carSpacePolyTen = new com.badlogic.gdx.math.Polygon(new float[]{0, 0, 25, 0, 25, 25, 0, 25});
		carSpacePolyTen.setOrigin(12, 12);

		playerCar = new Car(playerCarSprite, playerFrontWheel, playerBackWheel);

		yDiff = 0;
		xDiff = 0;

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
		//Reset car position
		resetButton = new TextButton("Reset position", skin, "default");
		resetButton.setWidth(200);
		resetButton.setHeight(50);
		resetButton.setX(0);
		resetButton.setY(280);
		resetButton.setColor(Color.RED);

		staticCarList = new ArrayList<StaticCar>(Arrays.asList(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 200, 200)));

		dialog = new Dialog("Click where you would like the car placed!", skin);
		dialogStatic = new Dialog(" - Click Escape to delete car \n - Drag and Drop car to move \n - User arrow keys to rotate", skin);
		dialogStatic.show(stage);
		counter = 0;
		//Constructing first scenario
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarSprite.getHeight(), 165 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarSprite.getHeight(), 255 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarSprite.getHeight(), 355 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 690 - (int) playerCarSprite.getHeight(), 445 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarSprite.getHeight(), 165 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarSprite.getHeight(), 255 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarSprite.getHeight(), 445 - (int) (playerCarSprite.getHeight() / 4)));
		staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, 1200 - (int) playerCarSprite.getHeight(), 545 - (int) (playerCarSprite.getHeight() / 4)));

		button.addListener(new ClickListener() {
			@Override
			public void clicked(InputEvent event, float x, float y) {
				dialog.show(stage);
				dialog.setX(500);
				dialog.setY(200);
				Timer.Task schedule = Timer.schedule(new Timer.Task() {
					@Override
					public void run() {
						spaceValue = 0;
						staticCarList.add(new StaticCar(staticSprite, playerFrontWheel, playerBackWheel, (int) (Gdx.input.getX() - (staticSprite.getWidth() / 2)), (int) (720 - Gdx.input.getY() - (staticSprite.getHeight() / 2))));
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
					}
				}, 1);
			}
		});
		resetButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				playerCar.carLocation.x = 900;
				playerCar.carLocation.y = 50;
				playerCar.setCarSpeed(0);
				playerCar.setCarHeading(-55.0f);
				playerCar.setSteerAngle(0);

			}
		});
		stage.addActor(button);
		stage.addActor(scanButton);
		stage.addActor(resetButton);
	}

	public double distanceToSpace(){

		int xCoord;
		int yCoord;
		double distance;


		xCoord = (int) (freeSpacesList.get(counter).x + 12.5);
		yCoord = (int) (freeSpacesList.get(counter).y + 12.5);

		yDiff = (int) (yCoord - (playerCar.getCentrePoly().getY() + playerCarSprite.getHeight() / 2));
		xDiff = (int) (xCoord - (playerCar.getCentrePoly().getX() + playerCarSprite.getWidth() / 2));

		distance = Math.sqrt(Math.pow((xCoord - (playerCar.getCentrePoly().getX() + (playerCarSprite.getWidth() / 2))), 2) +
				Math.pow(((playerCar.getCentrePoly().getY() + playerCarSprite.getHeight() / 2 )- yCoord), 2));
		rotation = freeSpacesList.get(counter).getSpacePoly().getRotation();

		return distance;
	}

	public void updatePlayerCar() {

		if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
			if (playerCar.getCarSpeed() < playerCar.maxSpeed)
				if (playerCar.getCarSpeed() < 0){
					playerCar.setCarSpeed(0);
				}
				else {
					playerCar.setCarSpeed(50);
				}
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			if (playerCar.getCarSpeed() <= 0){
				playerCar.setCarSpeed( -50);
			}
			if (playerCar.getCarSpeed() > 0){
				playerCar.setCarSpeed(0);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			if (playerCar.getSteerAngle() >= (-playerCar.maxSteerAngle) && playerCar.getSteerAngle() < playerCar.maxSteerAngle - 0.024 ) {
				playerCar.setSteerAngle((playerCar.getSteerAngle() + (float) 0.024));;
				System.out.println(playerCar.getSteerAngle() + " , " + playerCar.maxSteerAngle);
			}
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			if (playerCar.getSteerAngle() > (-playerCar.maxSteerAngle + 0.024) && playerCar.getSteerAngle() <= playerCar.maxSteerAngle) {
				playerCar.setSteerAngle((playerCar.getSteerAngle() - (float) 0.024));
				System.out.println(playerCar.getSteerAngle() + " , " + playerCar.maxSteerAngle);
			}
		}
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
		lowestDiff = 0;
		counter = 0;
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

			totalDiff = Math.sqrt(Math.pow((xCoord - (playerCar.getCentrePoly().getX())), 2) +
					Math.pow((playerCar.getCentrePoly().getY() - yCoord), 2));

			System.out.println("Total diff for " + i + " " + totalDiff);
			if (lowestDiff == 0) {
				lowestDiff = totalDiff;
				System.out.println("First lowest");
				counter = i;
			}
			if (totalDiff < lowestDiff) {
				lowestDiff = totalDiff;
				System.out.println("New Lowest Point");
				counter = i;
			}
		}
		System.out.println(totalDiff);
		spaceValue = 1;
		scanValue = 0;
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
