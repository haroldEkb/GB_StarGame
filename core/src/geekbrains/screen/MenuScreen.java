package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    SpriteBatch batch;
    Texture img;
    Texture bgr;
    Vector2 speed;
    Vector2 pos;
    Vector2 direction;
    float way;

    @Override
    public void show() {
        super.show();
        batch = new SpriteBatch();
        bgr = new Texture("background.jpg");
        img = new Texture("badlogic.jpg");
        pos = new Vector2(0,0);
        speed = new Vector2(0,0);
        direction = new Vector2(0,0);
        way = 0;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgr, 0, 0);
        batch.draw(img, pos.x, pos.y);
        batch.end();
        if (way != 0){
            if (way < speed.len()){
                way = 0;
                speed.set(0,0);
                direction.set(0,0);
            } else {
                pos.add(speed);
                way -= speed.len();
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        direction = new Vector2(screenX - pos.x, (Gdx.graphics.getHeight() - screenY) - pos.y);
        way = direction.len();
        speed = direction.nor().scl(5f);
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 19:
                speed = new Vector2(0,1);
                break;
            case 20:
                speed = new Vector2(0,-1);
                break;
            case 21:
                speed = new Vector2(-1,0);
                break;
            case 22:
                speed = new Vector2(1,0);
                break;
        }
        way = 20;
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }
}
