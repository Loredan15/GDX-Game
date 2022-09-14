package ru.maxol;


import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

public class MyInputProcessor implements InputProcessor {

    Player player;

    public MyInputProcessor(Player player) {
        this.player = player;
    }

    private String outString = "";

    public String getOutString() {
        return outString;
    }

    @Override
    public boolean keyDown(int keycode) {

        switch (keycode) {
            case Input.Keys.D:
                player.setLeftMove(true);
                break;
            case Input.Keys.A:
                player.setRightMove(true);
                break;
        }
//        if (!outString.contains(Input.Keys.toString(keycode))) {
//            outString += Input.Keys.toString(keycode);
//
//        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {

        switch (keycode) {
            case Input.Keys.D:
                player.setLeftMove(false);
                break;
            case Input.Keys.A:
                player.setRightMove(false);
                break;
        }

//        if (outString.contains(Input.Keys.toString(keycode))) {
//            outString = outString.replace(Input.Keys.toString(keycode), "");
//        }
        return true;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
