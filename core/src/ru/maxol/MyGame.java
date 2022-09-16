package ru.maxol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
    SpriteBatch batch;
    MyAtlasAnim animRun, animStay, animUp, animJump, tmpA;
    MyInputProcessor inputProcessor;
    Player player;
    private Music music;
    private Sound runSound;
    Texture background;

    @Override
    public void create() {
        Gdx.app.log("Game", "Start");
        batch = new SpriteBatch();
        background = new Texture("background.png");
        animRun = new MyAtlasAnim("atlas/sonic.atlas", "sonic-run", 7);
        animStay = new MyAtlasAnim("atlas/sonic.atlas", "sonic-stay", 7);
        animUp = new MyAtlasAnim("atlas/sonic.atlas", "sonic-up", 7);
        animJump = new MyAtlasAnim("atlas/sonic-jump.atlas", "sonic-jump", 7);
        player = new Player();
        inputProcessor = new MyInputProcessor(player);
        Gdx.input.setInputProcessor(inputProcessor);
        music = Gdx.audio.newMusic(Gdx.files.internal("Green Hill Zone MP3.mp3"));
        music.setVolume(0.05f);
        music.setLooping(true);
        music.play();
        runSound = Gdx.audio.newSound(Gdx.files.internal("19557.mp3"));

    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        batch.begin();
        batch.draw(background, 0, 0);
        movePlayer();
        batch.end();
    }

    private void movePlayer() {
        tmpA = animStay;
        tmpA.setTime(Gdx.graphics.getDeltaTime());
        player.updateMotion();
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            runSound.play();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            runSound.play();
        }
        if (player.isLeftMove()) {
            tmpA = animRun;
            if (tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else if (player.isRightMove()) {
            tmpA = animRun;
            if (!tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else if (player.isUpMove()) {
            tmpA = animUp;
//            if (!tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else if (player.isJump()) {
            tmpA = animJump;
//            if (!tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
        } else {
            tmpA = animStay;
            runSound.stop();
        }
        batch.draw(tmpA.getFrame(), player.getX(), player.getY());
    }

    @Override
    public void dispose() {
        batch.dispose();
        animRun.dispose();
        animStay.dispose();
        animUp.dispose();
        animJump.dispose();
        music.dispose();
    }
}
