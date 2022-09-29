package ru.maxol.utils;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.math.Vector2;

public class MyInputProcessorV2 implements InputProcessor {
    private Vector2 out;

    public MyInputProcessorV2() {
        out = new Vector2();
    }

    public Vector2 getOut() {
        return out;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.A -> out.add(-0.25f, 0);
            case Input.Keys.D -> out.add(0.25f, 0);
            case Input.Keys.W -> out.add(0, -0.5f);
            case Input.Keys.SPACE -> out.add(0, 3f);
        }

        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Input.Keys.A -> out.set(0, out.y);
            case Input.Keys.D -> out.set(0, out.y);
            case Input.Keys.W -> out.set(out.x, 0);
            case Input.Keys.SPACE -> out.set(out.x, 0);
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
