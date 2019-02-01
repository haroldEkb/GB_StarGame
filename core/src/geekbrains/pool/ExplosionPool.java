package geekbrains.pool;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import geekbrains.base.Sprite;
import geekbrains.base.SpritesPool;
import geekbrains.sprite.game.Explosion;

public class ExplosionPool extends SpritesPool {

    private TextureRegion region;

    public ExplosionPool(TextureAtlas atlas){
        this.region = atlas.findRegion("explosion");
    }

    @Override
    protected Sprite newObject() {
        return new Explosion(region, 9, 9, 74);
    }
}
