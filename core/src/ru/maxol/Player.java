package ru.maxol;

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
            body.setLinearVelocity(100f, body.getLinearVelocity().y);
        }
        if (rightMove) {
            body.setLinearVelocity(-100f, body.getLinearVelocity().y);
        }
        if (jump) {
            body.setLinearVelocity(body.getLinearVelocity().x, 100f);
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
