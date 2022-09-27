package ru.maxol.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MainMenuScreen implements Screen {
    Game game;
    Texture background, startButton;
    SpriteBatch batch;
    Rectangle rectangle;
    int x;

    public MainMenuScreen(Game game) {
        this.game = game;
        background = new Texture("background3.jpg");
        startButton = new Texture("Start-button.png");
        x = Gdx.graphics.getWidth()/2 - startButton.getWidth()/2;
        rectangle = new Rectangle(x, 100, startButton.getWidth(), startButton.getHeight());
        batch = new SpriteBatch();

    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.draw(startButton, x, 100);
        batch.end();

        if (Gdx.input.isTouched()) {
            if (rectangle.contains(Gdx.input.getX(), Gdx.graphics.getHeight() - Gdx.input.getY())) {
                dispose();
                game.setScreen(new GameScreen(game));
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
        background.dispose();
        startButton.dispose();
        batch.dispose();
    }
}
