package geekbrains.pool;

import geekbrains.base.SpritesPool;
import geekbrains.sprite.game.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    protected Bullet newObject() {
        return new Bullet();
    }
}