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

    private static final float ENEMY_MEDIUM_HEIGHT = 0.15f;
    private static final float ENEMY_MEDIUM_BULLET_HEIGHT = 0.02f;
    private static final float ENEMY_MEDIUM_BULLET_VY = -0.5f;
    private static final int ENEMY_MEDIUM_DAMAGE = 2;
    private static final float ENEMY_MEDIUM_RELOAD_INTERVAL = 0.3f;
    private static final int ENEMY_MEDIUM_HP = 10;

    private static final float ENEMY_LARGE_HEIGHT = 0.2f;
    private static final float ENEMY_LARGE_BULLET_HEIGHT = 0.03f;
    private static final float ENEMY_LARGE_BULLET_VY = -0.5f;
    private static final int ENEMY_LARGE_DAMAGE = 5;
    private static final float ENEMY_LARGE_RELOAD_INTERVAL = 0.5f;
    private static final int ENEMY_LARGE_HP = 20;

    private Vector2 enemySmallV = new Vector2(0, -0.2f);
    private Vector2 enemyMediumV = new Vector2(0, -0.1f);
    private Vector2 enemyLargeV = new Vector2(0, -0.05f);

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
            switch (id){
                case 0:
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
                    break;
                case 1:
                    enemy.set(
                            enemyShips[id],
                            enemyMediumV,
                            bulletRegion,
                            ENEMY_MEDIUM_BULLET_HEIGHT,
                            ENEMY_MEDIUM_BULLET_VY,
                            ENEMY_MEDIUM_DAMAGE,
                            ENEMY_MEDIUM_RELOAD_INTERVAL,
                            ENEMY_MEDIUM_HEIGHT,
                            ENEMY_MEDIUM_HP,
                            worldBounds
                    );
                    break;
                case 2:
                    enemy.set(
                            enemyShips[id],
                            enemyLargeV,
                            bulletRegion,
                            ENEMY_LARGE_BULLET_HEIGHT,
                            ENEMY_LARGE_BULLET_VY,
                            ENEMY_LARGE_DAMAGE,
                            ENEMY_LARGE_RELOAD_INTERVAL,
                            ENEMY_LARGE_HEIGHT,
                            ENEMY_LARGE_HP,
                            worldBounds
                    );
                    break;
            }


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
