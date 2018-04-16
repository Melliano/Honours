package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.TimeUtils;

import java.io.FileWriter;
import java.io.IOException;

import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.neuroph.core.NeuralNetwork;


public class MainScreen implements Screen {

    MyGdxGame game;
    StaticCar staticCar;
    Window pause;
    Skin skin;
    Stage stage;
    TextButton button;
    int recordValue, leftValue, rightValue, exampleCounter;
    double distToSpacevalue;
    String behindAlert, frontAlert, leftAlert, rightAlert, FILE_HEADER, COMMA_DELIMITER, NEW_LINE_SEPERATOR;
    Polygon carSpace;
    boolean carClicked;
    int angle_diff, minAngleDiff, maxAngleDiff, minDistance, maxDistance;
    NeuralNetwork neuralNetwork, neuralNetworkRight;
    double minDistData, maxDistData, minSpaceData, maxSpaceData, minAngle, maxAngleData,
            minSpeedData, maxSpeedData, minAngleData, leftMinDistData, leftMaxDistData, leftMinAngleData, leftMaxAngleData,
            minTimeTaken, maxTimetaken, minXdata, minYData, maxXData, maxYData;
    double normDist, normSpace, normAngleDiff, normSpeed, normAngle, normXDiff, normYDiff;
    double [] networkOutput;
    boolean range;
    long timeElapsed, timeStart;

    FileWriter pw, scores;
    public MainScreen(MyGdxGame game){
        this.game = game;
        exampleCounter = 0;
        range = false;
        behindAlert = "Nothing Detected";
        frontAlert = "Nothing Detected";
        leftAlert = "Nothing Detected";
        rightAlert = "Nothing Detected";
        FILE_HEADER = "distance, angle_diff, steer_angle, forward, stop, backwards";
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
        //minDistData = 44.3736887355;
        //maxDistData = 341.8017974071;
        minDistance = 0;
        maxDistance = 15;
        minXdata = -25.0;
        maxXData = 411;
        minYData = -82.0; //-72
        maxYData = 237.0; //230
        minAngleData = -179.0; //-40
        maxAngleData = 179.0; //174
        //minAngleData = -7.0;
        //maxAngleData = 88.0;
        minDistData = 0.1905336634;
        maxDistData = 433.6030935639;
        minSpeedData = 0;
        maxSpeedData = 50;
        neuralNetwork = NeuralNetwork.load("C:\\Users\\Melliano\\Documents\\NetBeansProjects\\Test\\Neural Networks\\NewNeuralNetwork119.nnet");
        neuralNetworkRight = NeuralNetwork.load("C:\\Users\\Melliano\\Documents\\NetBeansProjects\\Test\\Neural Networks\\NewNeuralNetwork202.nnet"); // 105 OG
        //neuralNetwork.setInput(0.884, 0, 0.35);
        //neuralNetwork.calculate();
        //double[] networkOutput = neuralNetwork.getOutput();
        //System.out.println(networkOutput.length)
        //System.out.println("output " + networkOutput[0] + " " + networkOutput[1] + " ");
        try {
            System.out.println("Tried");
            pw = new FileWriter("C:\\Users\\Melliano\\Documents\\RightNetworkv8.csv", true);
            scores = new FileWriter("C:\\Users\\Melliano\\Documents\\RightNNScoresv3", true);
            pw.append(FILE_HEADER);
            pw.append(NEW_LINE_SEPERATOR);
            System.out.println("Appended");
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show() {
       // Gdx.input.setInputProcessor(game.stage);
        Gdx.input.setInputProcessor(stage);
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
        //game.shapeRenderer.rect(game.testCrash.x, game.testCrash.y, game.testCrash.width, game.testCrash.height);
        game.shapeRenderer.polygon(game.playerCar.getBoundingPoly().getTransformedVertices());
        //game.shapeRenderer.polygon(game.playerCar.getSensorBoundingPoly().getTransformedVertices());
        //game.shapeRenderer.polygon(game.playerCar.getLeftBoundingPoly().getTransformedVertices());
        //game.shapeRenderer.polygon(game.playerCar.getRightBoundingPoly().getTransformedVertices());
        //game.shapeRenderer.polygon(game.playerCar.getFrontBoundingPoly().getTransformedVertices());
        //game.shapeRenderer.polygon(game.playerCar.getBackBoundingPoly().getTransformedVertices());
        game.shapeRenderer.polygon(game.playerCar.getLeftWheelBoundingPoly().getTransformedVertices());
        //Car Spaces Testing
        /*
        game.shapeRenderer.polygon(game.carSpacePoly.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyTwo.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyThree.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyFour.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyFive.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolySix.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolySeven.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyEight.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyNine.getTransformedVertices());
        game.shapeRenderer.polygon(game.carSpacePolyTen.getTransformedVertices());
        */
        game.shapeRenderer.polygon(game.playerCar.getCentrePoly().getTransformedVertices());

        //game.shapeRenderer.polygon(game.playerCar.getLeftWheelPoly().getTransformedVertices());

        for (int i = 0; i < game.polygonsList.size(); i ++){
            game.shapeRenderer.polygon(game.polygonsList.get(i).getSpacePoly().getTransformedVertices());
        }
        //game.playerCar.frontSensor.draw(game.batch);
        game.testPolygon.setPosition(game.testCrash.x, game.testCrash.y);
        /*
        game.carSpacePoly.setPosition(690, 165);
        game.carSpacePolyTwo.setPosition(690, 255);
        game.carSpacePolyThree.setPosition(690, 355);
        game.carSpacePolyFour.setPosition(690, 445);
        game.carSpacePolyFive.setPosition(690, 545);
        game.carSpacePolySix.setPosition( 1200, 165);
        game.carSpacePolySeven.setPosition(1200, 255);
        game.carSpacePolyEight.setPosition(1200, 355);
        game.carSpacePolyNine.setPosition(1200, 445);
        game.carSpacePolyTen.setPosition(1200, 545);
        */
        //sensorCheck();
        game.font.draw(game.batch,  "In range: " + range + "\nCoordinates of car : " + game.playerCar.carLocation.x + " , " + game.playerCar.carLocation.y + "\nSpeed of car: " + game.playerCar.getCarSpeed()
                + "\nCar heading: " + (Math.toDegrees(-game.playerCar.getCarHeading()) + 180)  + "\nSteer Angle: " + Math.toDegrees(game.playerCar.getSteerAngle())
                    + "\nDistance to nearest space: " + distToSpacevalue + " Space Rotation: " + game.rotation + " Angle difference: " + angle_diff + " \nRecording : "  + recordValue
                        + " Example: " + exampleCounter ,10, 680);
        //batch.draw(playerCarSprite, playerCar.carLocation.x, playerCar.carLocation.y);
        //game.batch.draw(game.playerBackWheel, game.playerCar.backWheelLoc.x, game.playerCar.backWheelLoc.y, 20 , 10);
        //game.batch.draw(game.playerFrontWheel, game.playerCar.frontWheelLoc.x, game.playerCar.frontWheelLoc.y, 20, 10);
        game.updatePlayerCar();
        collisionCheck();
        sensorCheck();
        editStaticCar();
        //System.out.println("SCORE " + calculateScore(50, 5, 0));
        if (game.scanValue == 1){
            game.scanSpace();
        }
        if (game.spaceValue == 1){
            System.out.println("Distance to nearest space " + " " + game.distanceToSpace());
            distToSpacevalue = game.distanceToSpace();
            angle_diff = angleDifference((Math.toDegrees(-game.playerCar.getCarHeading()) + 180), game.rotation);
            checkParkingRange();
            game.shapeRenderer.rect((float) (game.freeSpacesList.get(game.counter).x - (game.playerCarNewSprite.getWidth() * 3.5)),
                    game.freeSpacesList.get(game.counter).y - game.playerCarNewSprite.getWidth() * 2,
                    (float) (game.playerCarNewSprite.getWidth() * 2.5), (game.playerCarNewSprite.getWidth() * 2) + 25);
        }
        //System.out.println("Distance to closest free space " + " " + game.lowestDiff + "\nThe space is  - " + game.counter);
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
        //game.stage.act(Gdx.graphics.getDeltaTime());
        //game.stage.draw();
//        game.stage.dispose();
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

        double firstAngleDiff = (angle_diff - minAngleData);
        double secondAngleDiff = (maxAngleData - minAngleData);

        /*
        normDist = ((distToSpacevalue - minDistData) / (maxDistData - minDistData));
        normSpace = ((game.rotation - minSpaceData) / (maxSpaceData - minSpaceData));
        normAngleDiff = ((angle_diff - minAngleData) / (maxAngleData - minAngleData));
        */
        if (game.rotation == 360){
            //Left hand parking
            normDist = ((distToSpacevalue - leftMinDistData) / (leftMaxDistData - leftMinDistData));
            normAngleDiff = ((angle_diff - leftMinAngleData) / (leftMaxAngleData - leftMinAngleData));

            neuralNetwork.setInput(normDist, normAngleDiff);
            networkOutput = neuralNetwork.getOutput();
            neuralNetwork.calculate();
            game.playerCar.setSteerAngle((float) ((networkOutput[0] * 0.57)));
            game.playerCar.setCarSpeed((float) ((networkOutput[1] * 100)) - 50);
            /*
            if (networkOutput[1] < 0.2){
                game.playerCar.setCarSpeed(0);
            }
            else if(networkOutput[1] > 0.8){
                game.playerCar.setCarSpeed(50);
            }
            */
        }
        if (game.rotation == 180){
            //normDist = ((distToSpacevalue - minDistData) / (maxDistData - minDistData));
            normXDiff = ((game.xDiff - minXdata) / (maxXData - minXdata));
            normYDiff = ((game.yDiff - minYData) / (maxYData - minYData));
            normAngleDiff = ((angle_diff - minAngleData) / (maxAngleData - minAngleData));

            neuralNetworkRight.setInput(normXDiff, normYDiff, normAngleDiff);
            neuralNetworkRight.calculate();
            networkOutput = neuralNetworkRight.getOutput();
            game.playerCar.setSteerAngle((float) ((networkOutput[0] * 1.2) - 0.6));
            //game.playerCar.setCarSpeed((float) ((networkOutput[1] * 100)) - 50);
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
        /*
        normDist = ((distToSpacevalue - leftMinDistData) / (leftMaxDistData - leftMinDistData));
        normAngleDiff = ((angle_diff - leftMinAngleData) / (leftMaxAngleData - leftMinAngleData));

        System.out.println();
        System.out.println("first " + angle_diff + ", " + firstAngleDiff + " second " + secondAngleDiff);
        System.out.println(normAngleDiff);
        //normHeading = ((Math.toDegrees(-game.playerCar.getCarHeading() + 90) - minHeadingData) / (maxHeadingData - minHeadingData));
        normSpeed = ((game.playerCar.getCarSpeed()
                - minSpeedData) / (maxSpeedData - minSpeedData));
        normAngle = ((Math.toDegrees(game.playerCar.getSteerAngle()) - minAngleData) / (maxAngleData - minAngleData));

        System.out.println("input 1 " + normDist + " input 2 " + normAngleDiff);
        neuralNetwork.setInput(normDist, normAngleDiff);
        //System.out.println("Input = " + normDist +  " , " + normAngleDiff);
        neuralNetwork.calculate();
        networkOutput = neuralNetwork.getOutput();
        */
        //System.out.println("First " + firstHeading + " Second" + secondHeading);
        //System.out.println(normHeading);
        //System.out.println((Math.toDegrees(-game.playerCar.getCarHeading() + 90) - minHeadingData));
        //System.out.println(maxHeadingData - minHeadingData);
        //System.out.println(normDist + " " + normSpace + " " + normHeading);
        //game.playerCar.setSteerAngle((float) (networkOutput[0] / 120));

        //Outputs 0.67 -- change for differenet networks
        //game.playerCar.setSteerAngle((float) ((networkOutput[0] * 0.67) - 0.6));
        //game.playerCar.setSteerAngle((float) ((networkOutput[0] * 0.57)));
        System.out.println("angle " + game.playerCar.getSteerAngle() + " output " + networkOutput[0]);
        System.out.println("speed" + networkOutput[1]);


        //Stopping car
        //Forward Neuron
        /*
        if (networkOutput[1] < 0.2){
            game.playerCar.setCarSpeed(0);
        }
        else if(networkOutput[1] > 0.8){
            game.playerCar.setCarSpeed(50);
        }
        /*
        //Stop Neuron
        if (networkOutput[2] < 0.2){
            //Nothing
        }
        else if (networkOutput[2] >= 0.8){
            game.playerCar.setCarSpeed(0);
        }

        //Back neuron
        if (networkOutput[3] <= 0.2){
            // Nothing
        }
        else if (networkOutput[3] >= 0.8){
            game.playerCar.setCarSpeed(-50);
        }
        /*
        //left neuron
        if (networkOutput[3] <= 0.2){
            //Nothing
        }
        else if (networkOutput[3] >= 0.8){
            game.playerCar.setSteerAngle(0.60f);
        }

        //Centre neuron
        if (networkOutput[4] <= 0.2){
            //nothing
        }
        else if (networkOutput[4] >= 0.8){
            game.playerCar.setSteerAngle(0);
        }

        //Right neuron
        if (networkOutput[5] <= 0.2){
            //Nothing
        }
        else if (networkOutput[5] >= 0.8){
            game.playerCar.setSteerAngle(-0.60f);
        }
        */
        //System.out.println(networkOutput[0] + " , " + networkOutput[1] + " , " + networkOutput[2] + " , " + networkOutput[3] + " , " + networkOutput[4] + " , " + networkOutput[5]);
    }

    public double calculateScore(int xDistance,int yDistance, double timeTaken, double angle){

        double score;
        int distance;
        distance = Math.abs(xDistance + yDistance);
        angle = Math.abs(angle); // Negative to
        System.out.println(timeTaken + " , "+  distance + " , "+  angle);
        /*
        if (game.rotation == 360){
            distance = distance - 25;
        }
        else if (game.rotation == 180){
            distance = distance - 50;
        }
        */
        //distance = distance - 25; // - 50 for right network
        System.out.println("ABS DISTANCE " + distance);

        distance =  (((distance) - minDistance) / (maxDistance - minDistance));
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
        return score;
    }

    public void checkParkingRange(){
        if (game.playerCar.getCentrePoly().getY() + 12.5 > game.freeSpacesList.get(game.counter).y - (game.playerCarNewSprite.getWidth() * 2)
                && game.playerCar.getCentrePoly().getY() < game.freeSpacesList.get(game.counter).y + 12.5 ){
           range = true;
           game.shapeRenderer.setColor(Color.GREEN);
        }
        else {
            range = false;
        }
    }

    public void recordData() throws IOException {
        //System.out.println("Recording data");
        //Inputs -- 2
        pw.append(String.valueOf(game.xDiff));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.yDiff));
       // pw.append(String.valueOf(distToSpacevalue));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(angle_diff));
        pw.append(COMMA_DELIMITER); // (Math.toDegrees(-game.playerCar.getCarHeading()) + 180)
        // Outputs -- 6
        pw.append(String.valueOf(((double)game.playerCar.getSteerAngle())));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf((double)game.playerCar.getCarSpeed()));
        /*
        pw.append(String.valueOf(game.forwardValue));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.stopValue));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.reverseValue));
        pw.append(COMMA_DELIMITER);
        /*
        pw.append(String.valueOf(game.leftValue));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.centreValue));
        pw.append(COMMA_DELIMITER);
        pw.append(String.valueOf(game.rightValue));
        */
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

    public void sensorCheck(){
        int backChecker = 0;
        int frontChecker = 0;
        int leftChecker = 0;
        int rightChecker = 0;
        //System.out.println(game.staticCarList.size() +  "SIZE");
        for (int i = 0; i < game.staticCarList.size(); i++){
            //System.out.println(i);
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getBackBoundingPoly())){
                //System.out.println("CAR DETECTED BEHIND");
                behindAlert = "CAR DETECTED BEHIND";
                backChecker = 1;
            }
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getLeftBoundingPoly())){
                //System.out.println("CAR DETECTED TO THE LEFT");
                leftAlert = "CAR DETECTED TO THE LEFT";
                leftChecker = 1;
            }
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getRightBoundingPoly())){
                rightAlert = "CAR DETECTED TO THE RIGHT";
                rightChecker = 1;
            }
            if (Intersector.overlapConvexPolygons(game.staticCarList.get(i).getStaticBoundingPoly(), game.playerCar.getFrontBoundingPoly())){
                frontAlert = "CAR DETECTED IN FRONT";
                frontChecker = 1;
            }
        }
        if (backChecker < 1){
            behindAlert = "Nothing Detected";
        }
        if (frontChecker < 1){
            frontAlert = "Nothing Detected";
        }
        if (leftChecker < 1){
            leftAlert = "Nothing Detected";
        }
        if (rightChecker < 1){
            rightAlert = "Nothing Detected";
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
        // Drag and Drop Implementation
        for (int i = 1; i < game.staticCarList.size(); i++){
            game.shapeRenderer.polygon(game.staticCarList.get(i).getStaticBoundingPoly().getTransformedVertices());
            if (game.staticCarList.get(i).getStaticBoundingPoly().contains((int) (Gdx.input.getX()), (int) (720 - Gdx.input.getY()))){
                game.isCarClicked = true;
                System.out.println("Car Clicked");
                System.out.println(game.staticCarList.size() + "Size");
                //System.out.println("Can rotate...");
                rotateStaticCar(i);
                game.dialogStatic.setX(game.staticCarList.get(i).staticCarLocation.x - (game.playerCarNewSprite.getWidth() / 4));
                game.dialogStatic.setY(game.staticCarList.get(i).staticCarLocation.y - (game.playerCarNewSprite.getHeight()) );
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
                //System.out.println("Car not clicked");
                //game.dialogStatic.setVisible(false);
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
