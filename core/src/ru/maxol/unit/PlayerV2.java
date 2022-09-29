package ru.maxol.unit;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import ru.maxol.enums.Actions;

import java.util.HashMap;

import static ru.maxol.utils.PhysX.PPM;

public class PlayerV2 {
    private HashMap<Actions, Animation<TextureRegion>> assets = new HashMap<>();
    private final float FPS = 1 / 7f;
    private float time;
    public static boolean canJump;
    private Animation<TextureRegion> baseAnimation;
    private boolean loop;
    private TextureAtlas atlas, jumpAtlas;
    private Body body;
    private Dir direction;

    public enum Dir {LEFT, RIGHT}

    private static final float scale = 1f;
    private float hitPoints, live;

    public PlayerV2(Body body) {
        this.body = body;
        atlas = new TextureAtlas("atlas/sonic.atlas");
        jumpAtlas = new TextureAtlas("atlas/sonic-jump.atlas");

        assets.put(Actions.STAND, new Animation<>(FPS, atlas.findRegions("sonic-stay")));
        assets.put(Actions.RUN, new Animation<>(FPS, atlas.findRegions("sonic-run")));
        assets.put(Actions.UP, new Animation<>(FPS, atlas.findRegions("sonic-up")));
        assets.put(Actions.JUMP, new Animation<>(FPS, jumpAtlas.findRegions("sonic-jump")));
        baseAnimation = assets.get(Actions.STAND);
        loop = true;
        direction = Dir.LEFT;
    }

    public static boolean isCanJump() {
        return canJump;
    }

    public static void setCanJump(boolean canJump) {
        PlayerV2.canJump = canJump;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public void setFPS(Vector2 vector, boolean onGround) {
        if (vector.x > 0.1f) setDirection(Dir.RIGHT);
        if (vector.x < -0.1f) setDirection(Dir.LEFT);
        float tmp = (float) (Math.sqrt(vector.x * vector.x + vector.y * vector.y)) * 10;
        setState(Actions.STAND);
        if (Math.abs(vector.x) > 0.25f && Math.abs(vector.y) < 10 && onGround) {
            setState(Actions.RUN);
            baseAnimation.setFrameDuration(1 / tmp);
        }
        if (Math.abs(vector.y) > 1 && canJump) {
            setState(Actions.JUMP);
            baseAnimation.setFrameDuration(FPS);
        }
    }

    public void setState(Actions state) {
        baseAnimation = assets.get(state);
        switch (state) {
            case STAND -> {
                loop = true;
                baseAnimation.setFrameDuration(FPS);
            }
            case JUMP -> loop = false;
            default -> loop = true;
        }
    }

    public TextureRegion getFrame() {
        if (time > baseAnimation.getAnimationDuration() && loop) time = 0;
        if (time > baseAnimation.getAnimationDuration()) time = 0;
        TextureRegion frame = baseAnimation.getKeyFrame(time);
        if (!frame.isFlipX() && direction == Dir.LEFT) frame.flip(true, false);
        if (frame.isFlipX() && direction == Dir.RIGHT) frame.flip(true, false);
        return frame;
    }

    public Rectangle getRectangle() {
        TextureRegion frame = baseAnimation.getKeyFrame(time);
        float x = body.getPosition().x * PPM - frame.getRegionWidth() / 2 / scale;
        float y = body.getPosition().y * PPM - frame.getRegionHeight() / 2 / scale;
        float w = frame.getRegionWidth() / PPM / scale;
        float h = frame.getRegionHeight() / PPM / scale;
        return new Rectangle(x, y, w, h);
    }

    public float setTime(float deltaTime) {
        time += deltaTime;
        return time;
    }

    public void dispose() {
        atlas.dispose();
        assets.clear();
        jumpAtlas.dispose();

    }
}
