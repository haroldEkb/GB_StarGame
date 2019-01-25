package geekbrains;

import com.badlogic.gdx.Game;

import geekbrains.screen.MenuScreen;

public class MyStarGame extends Game {

    @Override
    public void create() {
        setScreen(new MenuScreen(this));
    }
}
