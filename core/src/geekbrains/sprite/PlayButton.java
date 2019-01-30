package geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.math.Rect;
import geekbrains.screen.MenuScreen;
import geekbrains.screen.GameScreen;

public class PlayButton extends ScaledTouchUpButton {

    private Rect worldBounds;

    public PlayButton(TextureAtlas atlas, MenuScreen screen) {
        super(atlas.findRegion("btPlay"));
        setHeightProportion(0.3f);
        setTop(0.4f);
        this.screen = screen;
    }

    @Override
    public void action() {
        screen.getGame().setScreen(new GameScreen());
    }

    @Override
    public void resize(Rect bounds) {

    }
}
