package ru.maxol;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	MyAnimation animation;
	MyAnimation animationRunner;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("badlogic.jpg");
		animation = new MyAnimation("explosion_v2.png", 8, 6, 30);
		animationRunner = new MyAnimation("runner.png", 10, 3, 30);
	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);
		float x = Gdx.input.getX();
		float y = Gdx.graphics.getHeight() - Gdx.input.getY();
		batch.begin();
		batch.draw(img, 0, 0);
//		batch.draw(img, x, y);
		batch.draw(animation.getFrame(), x - animation.getFrame().getRegionHeight() / 2, y - animation.getFrame().getRegionWidth() / 2);
		batch.draw(animationRunner.getFrame(), 0,0);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		animation.dispose();
	}
}
