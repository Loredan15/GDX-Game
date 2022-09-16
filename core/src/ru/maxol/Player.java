package ru.maxol;

import com.badlogic.gdx.Gdx;

public class Player {
    boolean leftMove;
    boolean rightMove;
    boolean upMove;
    boolean jump;
    boolean stay;
    float x = 5;
    float y = 95;



    void updateMotion() {
        if (leftMove) {
            x += 50 * Gdx.graphics.getDeltaTime();
        }
        if (rightMove) {
            x -= 50 * Gdx.graphics.getDeltaTime();
        }
        if (jump){
            //TODO Вот тут вопрос. Как корректно плавно изменять координату У, что бы реализовать прыжок ?
            for (int i = 0; i < 200; i++) {
                if (i < 100) y += 50 * Gdx.graphics.getDeltaTime();
                if (i > 101) y -= 50 * Gdx.graphics.getDeltaTime();
                // В лог выводится корректно все, но по факту персонаж остается в высшей точке
                Gdx.app.log("Move", String.valueOf(y));
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
