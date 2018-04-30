package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.neuroph.core.NeuralNetwork;


public class MainScreen implements Screen {

    MyGdxGame game;
    Skin skin;
    Stage stage;
    int recordValue, exampleCounter;
    double distToSpacevalue;
    String behindAlert, frontAlert, leftAlert, rightAlert, FILE_HEADER, COMMA_DELIMITER, NEW_LINE_SEPERATOR;
    int angle_diff, minAngleDiff, maxAngleDiff, minDistance, maxDistance;
    NeuralNetwork neuralNetwork, neuralNetworkRight, neuralNetworkCombined;
    double minDistData, maxDistData, maxAngleData,
            minSpeedData, maxSpeedData, minAngleData, leftMinDistData, leftMaxDistData,
            leftMinAngleData, leftMaxAngleData,
            minTimeTaken, maxTimetaken, minXdata, minYData, maxXData, maxYData, minXLeftdata,
            minYLeftData, maxXLeftData, maxYLeftData, minXCombined, maxXCombined,
            minYCombined, maxYCombined;
    double normAngleDiff, normXDiff, normYDiff;
    double [] networkOutput;
    boolean range;
    long timeElapsed, timeStart;
    double scoreGlobal;

    FileWriter pw, scores;
    double normleftXDiff, normleftYDiff;
    BitmapFont font;

    public MainScreen(MyGdxGame game){
        this.game = game;
        exampleCounter = 0;
        range = false;
        font = new BitmapFont();
        behindAlert = "Nothing Detected";
        frontAlert = "Nothing Detected";
        leftAlert = "Nothing Detected";
        rightAlert = "Nothing Detected";
        FILE_HEADER = "x_distance, y_distance, angle_Diff, steering_angle, speed ";
        COMMA_DELIMITER = ",";
        NEW_LINE_SEPERATOR = "\n";
        distToSpacevalue = 0;
        recordValue = 0;
        minAngleDiff = 0;
        maxAngleDiff = 5;
        minTimeTaken = 3;
        maxTimetaken = 25;
        leftMinDistData = 12.989888;
        leftMaxDistData = 289.567505262;
        leftMinAngleData = -92.0;
        leftMaxAngleData = 3;
        minXLeftdata = -386.0;
        maxXLeftData = 37.0;
        minYLeftData = -73.0;
        maxYLeftData = 236.0;
        minDistance = 0;
        maxDistance = 15;
        minXdata = -25.0;
        maxXData = 411;
        minYData = -82.0; //-72
        maxYData = 237.0; //230
        leftMinAngleData = -180.0;
        leftMaxAngleData = 179.0;
        minAngleData = -180.0; //-40
        maxAngleData = 179.0; //174
        minXCombined = -386.0;
        maxXCombined = 411.0;
        minYCombined = -82.0;
        maxYCombined = 237.0;
        scoreGlobal = 0;
        minDistData = 0.1905336634;
        maxDistData = 433.6030935639;
        minSpeedData = 0;
        maxSpeedData = 50;
        neuralNetworkCombined = NeuralNetwork.load("C:\\Users\\Melliano\\Documents\\NetBeansProjects\\Test\\Neural Networks\\NewNeuralNetwork1.nnet");
        neuralNetwork = NeuralNetwork.load("C:\\Users\\Melliano\\Documents\\NetBeansProjects\\Test\\Neural Networks\\NewNeuralNetwork204.nnet");
        neuralNetworkRight = NeuralNetwork.load("C:\\Users\\Melliano\\Documents\\NetBeansProjects\\Test\\Neural Networks\\NewNeuralNetwork202.nnet"); // 105 OG
        try {
            System.out.println("Tried");
            pw = new FileWriter("C:\\Users\\Melliano\\Documents\\TestRefactored.csv", true);
            scores = new FileWriter("C:\\Users\\Melliano\\Documents\\RightNNScoresv4", true);
            pw.append(FILE_HEADER);
            pw.append(NEW_LINE_SEPERATOR);
            System.out.println("Appended");
        } catch (IOException e) {
            e.printStackTrace();
        }
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
       // Gdx.input.setInputProcessor(game.stage);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        game.batch.begin();
        game.backgroundSprite.draw(game.batch);
        game.playerCarSprite.setX(game.playerCar.carLocation.x - (game.playerCar.wheelBase / 2));
        game.playerCarSprite.setY(game.playerCar.carLocation.y - (game.playerCarSprite.getHeight()/2));
        game.playerCarSprite.setRotation((float) Math.toDegrees(game.playerCar.getCarHeading()));
        game.playerCarSprite.draw(game.batch);
        game.shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        game.shapeRenderer.setColor(Color.RED);
        game.shapeRenderer.polygon(game.playerCar.getBoundingPoly().getTransformedVertices());
        game.shapeRenderer.polygon(game.playerCar.getLeftWheelBoundingPoly().getTransformedVertices());
        game.shapeRenderer.polygon(game.playerCar.getCentrePoly().getTransformedVertices());

        for (int i = 0; i < game.polygonsList.size(); i ++){
            game.shapeRenderer.polygon(game.polygonsList.get(i).getSpacePoly().getTransformedVertices());
        }
        game.testPolygon.setPosition(game.testCrash.x, game.testCrash.y);
        game.font.draw(game.batch,  "In range: " + range + "\nCoordinates of car : " + game.playerCar.carLocation.x + " , " + game.playerCar.carLocation.y + "\nSpeed of car: " + game.playerCar.getCarSpeed()
                + "\nCar heading: " + (Math.toDegrees(-game.playerCar.getCarHeading()) + 180)  + "\nSteer Angle: " + Math.toDegrees(game.playerCar.getSteerAngle())
                    + "\nDistance to nearest space: " + distToSpacevalue + " Space Rotation: " + game.rotation + " Angle difference: " + angle_diff + " \nRecording : "  + recordValue
                        + " Example: " + exampleCounter + "\nScore: " + scoreGlobal ,10, 680);
        game.updatePlayerCar();
        collisionCheck();
        editStaticCar();
        if (game.scanValue == 1){
            game.scanSpace();
        }
        if (game.spaceValue == 1){
            System.out.println("Distance to nearest space " + " " + game.distanceToSpace());
            distToSpacevalue = game.distanceToSpace();
            angle_diff = angleDifference((Math.toDegrees(-game.playerCar.getCarHeading()) + 180), game.rotation);
            checkParkingRange();
            if (game.rotation == 180){
                game.shapeRenderer.rect((float) (game.freeSpacesList.get(game.counter).x - (game.playerCarSprite.getWidth() * 3.5)),
                        game.freeSpacesList.get(game.counter).y - game.playerCarSprite.getWidth() * 2,
                        (float) (game.playerCarSprite.getWidth() * 2.5), (game.playerCarSprite.getWidth() * 2) + 25);
            }
            else if(game.rotation == 360){
                game.shapeRenderer.rect((float) (game.freeSpacesList.get(game.counter).x + (game.playerCarSprite.getWidth())),
                        game.freeSpacesList.get(game.counter).y - game.playerCarSprite.getWidth() * 2,
                        (float) (game.playerCarSprite.getWidth() * 2.5), (game.playerCarSprite.getWidth() * 2) + 25);
            }
        }
        if (game.value == 1){
           //editStaticCar();
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
            recordValue = 0;
            timeElapsed = TimeUtils.timeSinceMillis(timeStart) / 1000;
            System.out.println("ELAPSED" + timeElapsed);
            System.out.println("SCORE: " + calculateScore(game.xDiff, game.yDiff, timeElapsed, angle_diff));
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.H)) {
            recordValue = 1;
            exampleCounter++;
            game.playerCar.setCarSpeed(50);
            timeStart = TimeUtils.millis();
        }
        if (recordValue == 1){
            try {
                recordData();
                normalizeInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        game.batch.end();
        game.shapeRenderer.end();
        stage.addActor(game.resetButton);
        stage.addActor(game.scanButton);
        stage.addActor(game.button);
        stage.addActor(game.dialogStatic);
        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    public void collisionCheck(){
        for (int i = 1; i < game.staticCarList.size(); i++){
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getBoundingPoly())){
                System.out.println("Collision");
                game.playerCar.setCarSpeed(0);
            }
        }
    }

    public void normalizeInput(){

        if (game.rotation == 360){
            //Left hand parking
            normleftXDiff = ((game.xDiff - minXLeftdata) / (maxXLeftData - minXLeftdata));
            normleftYDiff = ((game.yDiff - minYLeftData) / (maxYLeftData - minYLeftData));
            normAngleDiff = ((angle_diff - leftMinAngleData) / (leftMaxAngleData - leftMinAngleData));

            neuralNetwork.setInput(normleftXDiff, normleftYDiff, normAngleDiff);
            neuralNetwork.calculate();
            networkOutput = neuralNetwork.getOutput();
            game.playerCar.setSteerAngle((float) ((networkOutput[0] * 1.2) - 0.6));

            if (networkOutput[1] < 0.2){
                game.playerCar.setCarSpeed(-50);
            }
            else if(networkOutput[1] > 0.8){
                game.playerCar.setCarSpeed(50);
            }
            else {
                game.playerCar.setCarSpeed(0);
            }
        }
        if (game.rotation == 180){
            normXDiff = ((game.xDiff - minXdata) / (maxXData - minXdata));
            normYDiff = ((game.yDiff - minYData) / (maxYData - minYData));
            normAngleDiff = ((angle_diff - minAngleData) / (maxAngleData - minAngleData));

            neuralNetworkRight.setInput(normXDiff, normYDiff, normAngleDiff);
            neuralNetworkRight.calculate();
            networkOutput = neuralNetworkRight.getOutput();
            game.playerCar.setSteerAngle((float) ((networkOutput[0] * 1.2) - 0.6));
            if (networkOutput[1] < 0.2){
                game.playerCar.setCarSpeed(-50);
            }
            else if(networkOutput[1] > 0.8){
                game.playerCar.setCarSpeed(50);
            }
            else {
                game.playerCar.setCarSpeed(0);
            }

        }
        System.out.println("angle " + game.playerCar.getSteerAngle() + " output " + networkOutput[0]);
        System.out.println("speed" + networkOutput[1]);

    }

    public double calculateScore(int xDistance,int yDistance, double timeTaken, double angle){

        double score;
        double distance;
        distance = Math.abs(xDistance + yDistance); // Negative to positive
        angle = Math.abs(angle); // Negative to positive
        System.out.println(timeTaken + " , "+  distance + " , "+  angle);

        distance =  ((distance - minDistance) / (maxDistance - minDistance));
        timeTaken =  ((timeTaken - minTimeTaken) / (maxTimetaken - minTimeTaken));
        angle = ((angle - minAngleDiff) / (maxAngleDiff - minAngleDiff));

        System.out.println("Distance : "+ distance + " , " + "time :" + timeTaken + " , " + "angle " + angle);

        distance = (int) (distance * 1.35);
        timeTaken = timeTaken * 0.3;
        angle = angle * 1.35;

        score = distance + timeTaken + angle;
        score = score / 3;
        score = 1 - score;
        score = score * 100;

        System.out.println("SCOOOOOORE" + score);
        game.score = score;
        try {
            scores.append(String.valueOf(score));
            scores.append(NEW_LINE_SEPERATOR);
            scores.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        scoreGlobal = score;
        return score;
    }

    public void checkParkingRange(){
        if (game.playerCar.getCentrePoly().getY() + 12.5 > game.freeSpacesList.get(game.counter).y - (game.playerCarSprite.getWidth() * 2)
                && game.playerCar.getCentrePoly().getY() < game.freeSpacesList.get(game.counter).y + 12.5 ){
           range = true;
           game.shapeRenderer.setColor(Color.GREEN);
        }
        else {
            range = false;
        }
        game.shapeRenderer.polygon(game.freeSpacesList.get(game.counter).getSpacePoly().getTransformedVertices());
        game.shapeRenderer.setColor(Color.GREEN);
    }

    public void recordData() throws IOException {
        //System.out.println("Recording data");
        //Inputs -- 2
        pw.append(String.valueOf(game.xDiff));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.yDiff));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(angle_diff));
        pw.append(COMMA_DELIMITER);
        // Outputs -- 6
        pw.append(String.valueOf(((double)game.playerCar.getSteerAngle())));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf((double)game.playerCar.getCarSpeed()));
        //New Frame
        pw.append(NEW_LINE_SEPERATOR);
        pw.flush();
    }

    public int angleDifference(double carAngle, double spaceAngle){
        int difference = 0;
        difference = (int) (spaceAngle - carAngle);
        difference = (difference + 180) % 360 - 180;
        return difference;
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
        game.shapeRenderer.setColor(Color.RED);
        game.isCarClicked = false;
        // Drag and Drop Implementation
        for (int i = 1; i < game.staticCarList.size(); i++){
            game.shapeRenderer.polygon(game.staticCarList.get(i).getStaticBoundingPoly().getTransformedVertices());
            if (game.staticCarList.get(i).getStaticBoundingPoly().contains((int) (Gdx.input.getX()), (int) (720 - Gdx.input.getY()))){
                game.isCarClicked = true;
                System.out.println("Car Clicked");
                System.out.println(game.staticCarList.size() + "Size");
                rotateStaticCar(i);
                game.dialogStatic.setX(game.staticCarList.get(i).staticCarLocation.x - (game.playerCarSprite.getWidth() / 4));
                game.dialogStatic.setY(game.staticCarList.get(i).staticCarLocation.y - (game.playerCarSprite.getHeight()) );
                System.out.println(game.staticCarList.get(i).staticCarLocation.x + " < " + game.staticCarList.get(i).staticCarLocation.y);
                game.dialogStatic.setVisible(true);
                if (Gdx.input.isTouched()){
                    System.out.println("Drag car");
                    game.staticCarList.get(i).staticCarLocation.x = (Gdx.input.getX() - (game.staticSprite.getWidth() / 2));
                    game.staticCarList.get(i).staticCarLocation.y = (720 - Gdx.input.getY() - (game.staticSprite.getHeight() / 2));
                    game.staticCarList.get(i).getStaticBoundingPoly().setPosition(game.staticCarList.get(i).staticCarLocation.x, game.staticCarList.get(i).staticCarLocation.y);
                }
                else {
                    game.dialogStatic.setVisible(false);
                }
                if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)){
                    System.out.println("Car Deleted");
                    game.staticCarList.remove(i);
                    game.dialogStatic.setVisible(false);
                }
            }
            else {
                game.isCarClicked = false;
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
        game.stage.dispose();
    }

}
