package geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.base.SpritesPool;
import geekbrains.sprite.game.EnemyShip;
import geekbrains.sprite.game.MainShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private Sound shootSound;
    private BulletPool bulletPool;
    private TextureAtlas atlas;
    private ExplosionPool explosionPool;
    private MainShip mainShip;

    public EnemyPool(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, MainShip mainShip) {
        this.atlas = atlas;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
        this.bulletPool = bulletPool;
        this.explosionPool = explosionPool;
        this.mainShip = mainShip;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, shootSound, bulletPool, explosionPool, mainShip);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }

}
