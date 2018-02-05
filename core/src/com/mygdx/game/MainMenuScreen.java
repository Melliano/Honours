package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;

public class MainMenuScreen implements Screen {

    MyGdxGame game;
    private SpriteBatch batch;
    private Texture splash;
    private BitmapFont font;
    Stage stage;
    TextButton button;
    TextButton.TextButtonStyle style;
    Skin skin;
    TextureAtlas atlas;

     public MainMenuScreen(MyGdxGame game){
        this.game = game;
        font = new BitmapFont();
     }


     @Override
     public void render(float delta){

         Gdx.gl.glClearColor(0,0,0.2f,1 );
         Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

         batch.begin();
         font.draw(batch, "Welcome to NN simulator " + "\nHit space to continue", 500, 380);

         batch.end();
         if (Gdx.input.isKeyPressed(Input.Keys.SPACE)){
             System.out.println("HELLO THERE");;
             game.setScreen(new MainScreen(game));
         }
     }


    @Override
    public void pause() {

    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void show() {
        batch = new SpriteBatch();
    }

    @Override
    public void dispose() {

    }
}
