package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import geekbrains.base.BaseScreen;
import geekbrains.base.Sprite;
import geekbrains.math.Rect;
import geekbrains.sprite.Star;
import geekbrains.pool.BulletPool;
import geekbrains.pool.EnemyPool;
import geekbrains.pool.ExplosionPool;
import geekbrains.sprite.Background;
import geekbrains.sprite.game.Bullet;
import geekbrains.sprite.game.EnemyShip;
import geekbrains.sprite.game.MainShip;
import geekbrains.sprite.game.NewGameButton;
import geekbrains.utils.EnemyEmitter;

public class GameScreen extends BaseScreen {
    private Texture bgr;
    private TextureAtlas atlas;
    private Background background;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;


    private enum GameState {OVER, ON}
    private GameState state;
    private Sprite gameOver;
    private NewGameButton newGame;

    @Override
    public void show() {
        super.show();
        state = GameState.ON;
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
        music.play();
        music.setLooping(true);
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgr = new Texture("textures/background-1.jpg");
        gameOver = new Sprite(atlas.findRegion("message_game_over"));
        background = new Background(new TextureRegion(bgr));
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        bulletPool = new BulletPool();
        explosionPool = new ExplosionPool(atlas);
        mainShip = new MainShip(atlas, bulletPool, explosionPool);
        enemyPool = new EnemyPool(atlas ,bulletPool, explosionPool, mainShip);
        enemyEmitter = new EnemyEmitter(atlas, enemyPool, worldBounds);
        newGame = new NewGameButton(atlas, this);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        deleteAllDestroyed();
        draw(delta);
    }

    private void update(float delta){
        for (Star aStar : star) {
            aStar.update(delta);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.update(delta);
        } else if (state != GameState.OVER){
            gameOver();
        }
        if (state == GameState.ON){
            enemyPool.updateActiveSprites(delta);
            bulletPool.updateActiveSprites(delta);
            explosionPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta);
        }
    }

    private void checkCollisions(){
        List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
        for (EnemyShip enemy:enemyShipList) {
            if (enemy.isDestroyed()){
                continue;
            }
            float minDist = enemy.getHalfWidth() + mainShip.getHalfWidth();
            if (enemy.pos.dst2(mainShip.pos) < minDist * minDist) {
                enemy.destroy();
                mainShip.damage(enemy.getDamage());
                return;
            }
        }

        List<Bullet> bulletList = bulletPool.getActiveObjects();
        for (Bullet bullet:bulletList) {
            if (bullet.getOwner() == mainShip || bullet.isDestroyed()){
                continue;
            }
            if (mainShip.isBulletCollision(bullet)){
                mainShip.damage(bullet.getDamage());
                bullet.destroy();
            }
        }

        for (EnemyShip enemy:enemyShipList) {
            if (enemy.isDestroyed()){
                continue;
            }
            for (Bullet bullet:bulletList) {
                if (bullet.getOwner() != mainShip || bullet.isDestroyed()){
                    continue;
                }
                if (enemy.isBulletCollision(bullet)){
                    enemy.damage(mainShip.getDamage());
                    bullet.destroy();
                }
            }
        }
    }

    private void deleteAllDestroyed(){
        bulletPool.freeAllDestroyedActiveSprites();
        explosionPool.freeAllDestroyedActiveSprites();
        enemyPool.freeAllDestroyedActiveSprites();
    }

    private void draw(float delta){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if (!mainShip.isDestroyed()) {
            mainShip.draw(batch);
        }
        if (state == GameState.ON){
            bulletPool.drawActiveSprites(batch);
            explosionPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
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
        if (state == GameState.ON) {
            mainShip.keyDown(keycode);
        }
        return super.keyDown(keycode);
    }

    @Override
    public boolean keyUp(int keycode) {
        if (state == GameState.ON) {
            mainShip.keyUp(keycode);
        }
        return super.keyUp(keycode);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer){
        if (state == GameState.ON) {
            mainShip.touchDown(touch, pointer);
        } else {
            newGame.touchDown(touch, pointer);
        }
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer){
        if (state == GameState.ON) {
            mainShip.touchUp(touch, pointer);
        } else {
            newGame.touchUp(touch, pointer);
        }
        return super.touchUp(touch, pointer);
    }

    private void gameOver(){
        state = GameState.OVER;
        enemyPool.freeAllActiveObjects();
        bulletPool.freeAllActiveObjects();
        explosionPool.freeAllActiveObjects();
        gameOver.pos.set(0, 0.1f);
        gameOver.setHeight(0.15f);
        gameOver.setWidth(0.6f);
        music.stop();
    }

    public void gameOn() {
        state = GameState.ON;
        mainShip.recreate();
        mainShip.setHp(100);
        mainShip.setLeft(-mainShip.getHalfWidth());
        music.play();
        music.setLooping(true);
    }
}
