package geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import geekbrains.base.SpritesPool;
import geekbrains.sprite.game.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private Sound shootSound;
    private BulletPool bulletPool;
    private TextureAtlas atlas;

    public EnemyPool(TextureAtlas atlas, BulletPool bulletPool) {
        this.atlas = atlas;
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(atlas, shootSound, bulletPool);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
