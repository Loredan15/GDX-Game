package ru.maxol;

import com.badlogic.gdx.Gdx;

public class Player {
    boolean leftMove;
    boolean rightMove;
    float x;
    float y;

    void updateMotion() {
        if (leftMove) {
            x += 50 * Gdx.graphics.getDeltaTime();
        }
        if (rightMove) {
            x -= 50 * Gdx.graphics.getDeltaTime();
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

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isLeftMove() {
        return leftMove;
    }

    public boolean isRightMove() {
        return rightMove;
    }
}
