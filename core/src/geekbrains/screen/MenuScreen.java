package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bgr;
    Vector2 direction;
    Vector2 v;
    Vector2 pos;
    float way;
    float speed;

    @Override
    public void show() {
        super.show();
        bgr = new Texture("background.jpg");
        img = new Texture("badlogic.jpg");
        pos = new Vector2(0,0);
        v = new Vector2();
        way = 0;
        direction = new Vector2();
        speed = 0.01f;
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(bgr, -0.5f, -0.5f, 1f, 1f);
        batch.draw(img, pos.x, pos.y, 0.2f, 0.2f);
        batch.end();
        if (way != 0){
            if (way < v.len()){
                way = 0;
                v.set(0,0);
                direction.set(0,0);
            } else {
                pos.add(v);
                way -= v.len();
            }
        }
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);
        direction.set(getTouch().x,getTouch().y);
        way = direction.sub(pos).len();
        v = direction.nor().scl(speed);
        return true;
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 19:
                v = new Vector2(0,speed);
                break;
            case 20:
                v = new Vector2(0,-speed);
                break;
            case 21:
                v = new Vector2(-speed,0);
                break;
            case 22:
                v = new Vector2(speed,0);
                break;
        }
        way = 0.1f;
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }
}
