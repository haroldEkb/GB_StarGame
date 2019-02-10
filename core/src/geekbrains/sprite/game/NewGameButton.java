package geekbrains.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.math.Rect;
import geekbrains.screen.GameScreen;
import geekbrains.screen.MenuScreen;
import geekbrains.sprite.ScaledTouchUpButton;

public class NewGameButton extends ScaledTouchUpButton {

    private Rect worldBounds;
    private GameScreen gameScreen;

    public NewGameButton(TextureAtlas atlas, GameScreen screen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = screen;
        setHeight(0.1f);
        setWidth(0.4f);
        setTop(-0.1f);
    }

    @Override
    public void action() {
        gameScreen.gameOn();
    }
}
