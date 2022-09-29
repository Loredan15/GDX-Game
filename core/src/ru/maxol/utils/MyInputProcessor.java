package ru.maxol.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import ru.maxol.unit.Player;

public class MyInputProcessor implements InputProcessor {

    Player player;

    public MyInputProcessor(Player player) {
        this.player = player;
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
            case Input.Keys.W:
                player.setUpMoveMove(true);
                break;
            case Input.Keys.SPACE:
                player.setJump(true);
                break;
            default:
                player.setStay(true);
        }
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
            case Input.Keys.W:
                player.setUpMoveMove(false);
                break;
            case Input.Keys.SPACE:
                player.setJump(false);
                break;
            default:
                player.setStay(true);
        }
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
