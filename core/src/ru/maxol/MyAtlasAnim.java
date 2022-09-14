package ru.maxol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAtlasAnim {
    TextureAtlas atlas;
    Animation<TextureAtlas.AtlasRegion> anm;
    private float time;

    public MyAtlasAnim(String atlas, String name, float fps) {
        time = 1;
        this.atlas = new TextureAtlas(atlas);
        anm = new Animation<>(1 / fps, this.atlas.findRegions(name));
        anm.setPlayMode(Animation.PlayMode.LOOP);
    }

    public TextureRegion getFrame(){
        time += Gdx.graphics.getDeltaTime();
        return anm.getKeyFrame(time, true);
    }

    public TextureRegion draw() {
//        time += Gdx.graphics.getDeltaTime();
        return anm.getKeyFrame(time);
    }

    public void setTime(float dT) {
        time += dT;
    }

    public void dispose() {
        this.atlas.dispose();
    }

}
