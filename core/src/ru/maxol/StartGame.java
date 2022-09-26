package ru.maxol;

import com.badlogic.gdx.Game;
import ru.maxol.screens.MainMenuScreen;

public class StartGame extends Game {
    @Override
    public void create() {
        setScreen(new MainMenuScreen(this));
    }
    @Override
    public void render() {super.render();}
}
