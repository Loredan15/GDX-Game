package ru.maxol.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {

    Texture texture;
    TextureRegion[][] region;
    Animation<TextureRegion> animation;
    float time;

    public MyAnimation(String pathToFile, int col, int row, float fps) {
        texture = new Texture(pathToFile);
        region = TextureRegion.split(texture, texture.getWidth() / col, texture.getHeight() / row);
        TextureRegion[] regions = new TextureRegion[col * row];
        int count = 0;
        for (TextureRegion[] textureRegions : region) {
            for (TextureRegion textureRegion : textureRegions) {
                regions[count++] = textureRegion;
            }
        }
        animation = new Animation<>(1 / fps, regions);
    }

    public TextureRegion getFrame(){
        time += Gdx.graphics.getDeltaTime();
        return animation.getKeyFrame(time, true);
    }

    public void dispose(){
        texture.dispose();
    }
}
