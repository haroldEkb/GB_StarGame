package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import geekbrains.base.BaseScreen;
import geekbrains.math.Rect;
import geekbrains.pool.BulletPool;
import geekbrains.pool.EnemyPool;
import geekbrains.pool.ExplosionPool;
import geekbrains.sprite.Background;
import geekbrains.sprite.game.Bullet;
import geekbrains.sprite.game.MainShip;
import geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private Texture bgr;
    private TextureAtlas atlas;
    private Background background;
    private Bullet.Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;

    @Override
    public void show() {
        super.show();
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
        music.play();
        music.setLooping(true);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgr = new Texture("textures/background-1.jpg");
        background = new Background(new TextureRegion(bgr));
        star = new Bullet.Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Bullet.Star(atlas);
        }
        bulletPool = new BulletPool();
        mainShip = new MainShip(atlas, bulletPool);
        explosionPool = new ExplosionPool(atlas);
        enemyPool = new EnemyPool(bulletPool);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        deleteAllDestroyed();
        draw(delta);
    }

    public void update(float delta){
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        explosionPool.updateActiveSprites(delta);
        mainShip.update(delta);
        enemyPool.updateActiveSprites(delta);
        enemyEmitter.generate(delta);
    }

    public void deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    public void draw(float delta){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        mainShip.draw(batch);
        bulletPool.drawActiveSprites(batch);
        explosionPool.drawActiveSprites(batch);
        enemyPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
        mainShip.resize(worldBounds);
    }
    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        batch.dispose();
        atlas.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyPool.dispose();
        super.dispose();
        mainShip.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        mainShip.keyDown(keycode);
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        mainShip.keyUp(keycode);
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer){
        mainShip.touchDown(touch, pointer);
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer){
        mainShip.touchUp(touch, pointer);
        return super.touchUp(touch, pointer);
    }
}
