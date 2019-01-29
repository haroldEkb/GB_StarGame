package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import geekbrains.base.BaseScreen;
import geekbrains.math.Rect;
import geekbrains.sprite.Background;
import geekbrains.sprite.Star;

public class NewScreen extends BaseScreen {
    private Texture bgr;
    private TextureAtlas atlas;
    private TextureAtlas mainAtlas;
    private Background background;
    private Star star[];

    @Override
    public void show() {
        super.show();
        atlas = new TextureAtlas("textures/mainAtlas.tpack");
        bgr = new Texture("textures/background.jpg");
        background = new Background(new TextureRegion(bgr));
        star = new Star[256];
        for (int i = 0; i < star.length; i++) {
            star[i] = new Star(atlas);
        }
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
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {

        batch.dispose();
        atlas.dispose();
        super.dispose();
    }
}
