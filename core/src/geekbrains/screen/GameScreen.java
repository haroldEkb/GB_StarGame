package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import geekbrains.base.BaseScreen;
import geekbrains.math.Rect;
import geekbrains.sprite.Star;
import geekbrains.pool.BulletPool;
import geekbrains.pool.EnemyPool;
import geekbrains.pool.ExplosionPool;
import geekbrains.sprite.Background;
import geekbrains.sprite.game.Bullet;
import geekbrains.sprite.game.EnemyShip;
import geekbrains.sprite.game.HealthBar;
import geekbrains.sprite.game.MainShip;
import geekbrains.sprite.game.MessageGameOver;
import geekbrains.sprite.game.NewGameButton;
import geekbrains.sprite.game.StatusFrame;
import geekbrains.utils.EnemyEmitter;
import geekbrains.utils.Font;

public class GameScreen extends BaseScreen {

    private static final String FRAGS = "Frags:";
    private static final String HP = "HP: ";
    private static final String LEVEL = "Lvl:";

    private StringBuilder sbFrags = new StringBuilder();
    private StringBuilder sbHp = new StringBuilder();
    private StringBuilder sbLevel = new StringBuilder();


    private Texture bgr;
    private TextureAtlas atlas;
    private TextureAtlas statusAtlas;
    private Background background;
    private Star star[];
    private MainShip mainShip;
    private BulletPool bulletPool;
    private Music music;
    private ExplosionPool explosionPool;
    private EnemyPool enemyPool;
    private EnemyEmitter enemyEmitter;
    private int frage;
    private Font font;


    private enum GameState {OVER, ON}
    private GameState state;
    private MessageGameOver gameOver;
    private NewGameButton newGame;
    private StatusFrame frame;
    private HealthBar bar;

    @Override
    public void show() {
        super.show();
        state = GameState.ON;
        music = Gdx.audio.newMusic(Gdx.files.internal("sound/music.mp3"));
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        statusAtlas = new TextureAtlas("textures/status.tpack");
        bgr = new Texture("textures/background-1.jpg");
        gameOver = new MessageGameOver(atlas);
        background = new Background(new TextureRegion(bgr));
        this.font = new Font("font/font.fnt", "font/font.png");
        font.setSize(0.02f);
        frame = new StatusFrame(statusAtlas);
        bar = new HealthBar(statusAtlas);

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
        gameOn();
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
        explosionPool.updateActiveSprites(delta);
        if (mainShip.isDestroyed()){
            gameOver();
        }else if (state == GameState.ON){
            mainShip.update(delta);
            enemyPool.updateActiveSprites(delta);
            bulletPool.updateActiveSprites(delta);
            enemyEmitter.generate(delta, frage);
        }
    }

    private void checkCollisions(){
        if (state == GameState.ON) {
            List<EnemyShip> enemyShipList = enemyPool.getActiveObjects();
            for (EnemyShip enemy : enemyShipList) {
                if (enemy.isDestroyed()) {
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
            for (Bullet bullet : bulletList) {
                if (bullet.getOwner() == mainShip || bullet.isDestroyed()) {
                    continue;
                }
                if (mainShip.isBulletCollision(bullet)) {
                    mainShip.damage(bullet.getDamage());
                    bar.setWidth(bar.getInitialWidth()*mainShip.getHp()/100);
                    bar.setLeft(-0.2f);
                    bullet.destroy();
                }
            }

            for (EnemyShip enemy : enemyShipList) {
                if (enemy.isDestroyed()) {
                    continue;
                }
                for (Bullet bullet : bulletList) {
                    if (bullet.getOwner() != mainShip || bullet.isDestroyed()) {
                        continue;
                    }
                    if (enemy.isBulletCollision(bullet)) {
                        enemy.damage(mainShip.getDamage());
                        if (enemy.isDestroyed()) {
                            frage++;
                        }
                        bullet.destroy();
                    }
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
        drawStatus();
        printInfo();
        for (Star aStar : star) {
            aStar.draw(batch);
        }
        if (state == GameState.ON){
            mainShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyPool.drawActiveSprites(batch);
        } else {
            gameOver.draw(batch);
            newGame.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);
        batch.end();
    }

    private void printInfo() {
        sbFrags.setLength(0);
        //font.draw(batch, sbFrags.append(FRAGS).append(frage), worldBounds.getLeft(), worldBounds.getTop());
        font.draw(batch, sbFrags.append(FRAGS).append(frage), worldBounds.getLeft(), worldBounds.getTop());

        sbHp.setLength(0);
        //font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), worldBounds.pos.x, worldBounds.getTop(), Align.center);
        font.draw(batch, sbHp.append(HP).append(mainShip.getHp()), 0.07f, 0.417f);

        sbLevel.setLength(0);
        //font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), worldBounds.getRight(), worldBounds.getTop(), Align.right);
        font.draw(batch, sbLevel.append(LEVEL).append(enemyEmitter.getLevel()), 0.18f, 0.417f);

    }

    private void drawStatus(){
        bar.draw(batch);
        frame.draw(batch);
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (Star aStar : star) {
            aStar.resize(worldBounds);
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
        font.dispose();
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
        music.stop();
    }

    public void gameOn() {
        state = GameState.ON;
        mainShip.restart();
        frage = 0;
        enemyEmitter.setLevel(1);
        bar.start();
        music.play();
        music.setLooping(true);
    }
}
