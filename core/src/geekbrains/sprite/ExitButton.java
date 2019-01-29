package geekbrains.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.math.Rect;
import geekbrains.math.Rnd;

public class ExitButton extends ScaledTouchUpButton {

    private Rect worldBounds;

    public ExitButton(TextureAtlas atlas) {
        super(atlas.findRegion("btExit"));
        setHeightProportion(0.3f);
        setBottom(-0.3f);
    }

    @Override
    public void action() {
        Gdx.app.exit();
    }


    @Override
    public void resize(Rect bounds) {
        this.worldBounds = bounds;
        float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posX,posY);
    }


}
