package geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import geekbrains.math.Rect;
import geekbrains.math.Rnd;
import geekbrains.pool.EnemyPool;
import geekbrains.sprite.game.EnemyShip;

public class EnemyEmitter {

    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.5f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 0.2f;
    private static final int ENEMY_SMALL_HP = 1;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private TextureRegion[][] enemyShips;

    private TextureRegion bulletRegion;

    private float generateInterval = 2f;
    private float generateTimer;

    private EnemyPool enemyPool;

    private Rect worldBounds;

    public EnemyEmitter(TextureAtlas atlas, EnemyPool enemyPool, Rect worldBounds) {
        this.enemyPool = enemyPool;
        this.enemyShips = new TextureRegion[3][2];
        for (int i = 0; i < 3; i++) {
            TextureRegion textureRegion = atlas.findRegion("enemy" + i);
            this.enemyShips[i] = Regions.split(textureRegion, 1,2,2);
        }
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer >= generateInterval) {
            generateTimer = 0f;
            int id = genShipID();
            EnemyShip enemy = enemyPool.obtain();
            enemy.set(
                    enemyShips[id],
                    enemySmallV,
                    bulletRegion,
                    ENEMY_SMALL_BULLET_HEIGHT,
                    ENEMY_SMALL_BULLET_VY,
                    ENEMY_SMALL_DAMAGE,
                    ENEMY_SMALL_RELOAD_INTERVAL,
                    ENEMY_SMALL_HEIGHT,
                    ENEMY_SMALL_HP,
                    worldBounds
            );

            enemy.pos.x = Rnd.nextFloat(worldBounds.getLeft() + enemy.getHalfWidth(), worldBounds.getRight() - enemy.getHalfWidth());
            enemy.setBottom(worldBounds.getTop());
        }
    }

    private int genShipID(){
        int i = Rnd.nextInt(0,100);
        if (i<50) return 0;
        else if(i > 85) return 2;
        else return 1;
    }
}
