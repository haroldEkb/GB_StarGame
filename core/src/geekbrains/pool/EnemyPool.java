package geekbrains.pool;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;

import geekbrains.base.SpritesPool;
import geekbrains.sprite.game.EnemyShip;

public class EnemyPool extends SpritesPool<EnemyShip> {

    private Sound shootSound;
    private BulletPool bulletPool;

    public EnemyPool(BulletPool bulletPool) {
        this.shootSound = Gdx.audio.newSound(Gdx.files.internal("sound/shot.wav"));
        this.bulletPool = bulletPool;
    }

    @Override
    protected EnemyShip newObject() {
        return new EnemyShip(shootSound, bulletPool);
    }

    @Override
    public void dispose() {
        super.dispose();
        shootSound.dispose();
    }
}
