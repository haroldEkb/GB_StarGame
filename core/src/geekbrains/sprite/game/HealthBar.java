package geekbrains.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.base.Sprite;

public class HealthBar extends Sprite {

    private float initialWidth = 0.4f;

    public HealthBar(TextureAtlas atlas){
        super(atlas.findRegion("bar"));
        setBottom(0.435f);
        setHeight(0.04f);
        setWidth(initialWidth);
    }

    public float getInitialWidth() {
        return initialWidth;
    }

    public void start(){
        setWidth(initialWidth);
        setLeft(-getHalfWidth()+0.02f);
    }
}
