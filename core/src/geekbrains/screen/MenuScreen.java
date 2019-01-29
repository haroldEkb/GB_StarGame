package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import geekbrains.MyStarGame;
import geekbrains.base.BaseScreen;
import geekbrains.math.Rect;
import geekbrains.sprite.Background;
import geekbrains.sprite.ExitButton;
import geekbrains.sprite.PlayButton;
import geekbrains.sprite.Star;

public class MenuScreen extends BaseScreen {
    private MyStarGame game;
    private Texture bgr;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;
    private Background background;
    private Star star[];
    private PlayButton play;
    private ExitButton exit;

    public MenuScreen(MyStarGame game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgr = new Texture("textures/background.jpg");
        mainAtlas = new TextureAtlas("textures/menuAtlas.tpack");
        background = new Background(new TextureRegion(bgr));
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
        play = new PlayButton(mainAtlas, this);
        exit = new ExitButton(mainAtlas);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw(delta);
    }

    public void update(float delta){
        for (int i = 0; i < star.length; i++) {
            star[i].update(delta);
        }
    }

    public void draw(float delta){
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        background.draw(batch);
        for (int i = 0; i < star.length; i++) {
            star[i].draw(batch);
        }
        play.draw(batch);
        exit.draw(batch);
        batch.end();
    }

    @Override
    public void resize(Rect worldBounds) {
        background.resize(worldBounds);
        for (int i = 0; i < star.length; i++) {
            star[i].resize(worldBounds);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        play.touchDown(touch,pointer);
        exit.touchDown(touch,pointer);
        return super.touchDown(touch,pointer);

    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        play.touchUp(touch,pointer);
        exit.touchUp(touch,pointer);
        return super.touchUp(touch, pointer);
    }

    @Override
    public void dispose() {

        batch.dispose();
        atlas.dispose();
        mainAtlas.dispose();
        super.dispose();
    }

    public MyStarGame getGame() {
        return game;
    }
}
