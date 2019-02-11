package geekbrains.sprite.game;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.base.Sprite;

public class StatusFrame extends Sprite {

    public StatusFrame(TextureAtlas atlas){
        super(atlas.findRegion("frame"));
        setHeightProportion(0.09f);
        setLeft(-getHalfWidth());
        setBottom(0.39f);
    }
}
