package ru.maxol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	MyAnimation animation;
	MyAnimation animationRunner;
	MyAtlasAnim animRun, animStay, tmpA;
	MyInputProcessor inputProcessor;
	float x,y;

	Player player;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
//		img = new Texture("badlogic.jpg");
//		animation = new MyAnimation("explosion_v2.png", 8, 6, 30);
//		animationRunner = new MyAnimation("runner.png", 10, 3, 30);
		animRun = new MyAtlasAnim("atlas/gb/unnamed.atlas", "run", 7);
		animStay = new MyAtlasAnim("atlas/gb/unnamed.atlas", "stand", 7);
		player = new Player();
		inputProcessor = new MyInputProcessor(player);
		Gdx.input.setInputProcessor(inputProcessor);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		tmpA = animStay;

		batch.begin();

		tmpA.setTime(Gdx.graphics.getDeltaTime());
		player.updateMotion();
		if (player.isLeftMove()){
			tmpA = animRun;
			if (tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
		} else if (player.isRightMove()){
			tmpA = animRun;
			if (!tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
		}else{
			tmpA = animStay;
//			if (tmpA.getFrame().isFlipX()) tmpA.getFrame().flip(true, false);
			if (!tmpA.getFrame().isFlipX()) {
				tmpA.getFrame().flip(true, false);
			}else{
				tmpA.getFrame().flip(false, false);
			}
		}
		batch.draw(tmpA.getFrame(), player.getX(), player.getY());
		batch.end();

	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		animation.dispose();
		animRun.dispose();
		animStay.dispose();
	}
}
