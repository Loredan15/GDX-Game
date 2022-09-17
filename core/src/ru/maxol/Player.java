package ru.maxol;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class Player {
    boolean leftMove;
    boolean rightMove;
    boolean upMove;
    boolean jump;
    boolean stay;
    float x = 5;
    float y = 95;


    void updateMotion(Body body) {
        if (leftMove) {
            body.applyForceToCenter(new Vector2(1000000f, 0f), true);
        }
        if (rightMove) {
            body.applyForceToCenter(new Vector2(-1000000f, 0f), true);
        }
        if (jump) {
            float y1 = getY();
            for (int i = 0; i < 200; i++) {
                if (i < 100) y += 2 * Gdx.graphics.getDeltaTime();
                if (y - y1 >= 200)
                y -= 45 * Gdx.graphics.getDeltaTime();
                body.applyForceToCenter(new Vector2(0, 10000f), true);
                continue;
            }
        }
    }

    public void setLeftMove(boolean t) {
        if (rightMove && t) rightMove = false;
        leftMove = t;
    }

    public void setRightMove(boolean t) {
        if (leftMove && t) leftMove = false;
        rightMove = t;
    }

    public void setJump(boolean jump) {
        this.jump = jump;
    }

    public boolean isJump() {
        return jump;
    }

    public void setUpMoveMove(boolean t) {
        if (stay && t) stay = false;
        upMove = t;
    }

    public boolean isUpMove() {
        return upMove;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public void setStay(boolean stay) {
        this.stay = stay;
    }

    public boolean isRightMove() {
        return rightMove;
    }
}
