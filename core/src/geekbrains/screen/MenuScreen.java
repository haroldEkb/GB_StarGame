package geekbrains.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import geekbrains.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    Texture img;
    Texture bgr;
    Vector2 touch;
    Vector2 v;
    Vector2 pos;
    Vector2 motion;
    float speed;
    float rate;

    @Override
    public void show() {
        super.show();
        bgr = new Texture("background.jpg");
        img = new Texture("badlogic.jpg");
        pos = new Vector2(0,0);
        v = new Vector2();
        touch = new Vector2();
        motion = new Vector2();
        speed = 0.01f;
        rate = 5;
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
        motion.set(touch);
        if (motion.sub(pos).len() < speed){
            pos.set(touch);
        } else {
            pos.add(v);
        }
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        this.touch = touch;
        v.set(touch.cpy().sub(pos).setLength(speed));
        return super.touchDown(touch,pointer);
    }


    @Override
    public boolean keyDown(int keycode) {
        switch (keycode){
            case 19:
                v = new Vector2(0,speed*rate);
                break;
            case 20:
                v = new Vector2(0,-speed*rate);
                break;
            case 21:
                v = new Vector2(-speed*rate,0);
                break;
            case 22:
                v = new Vector2(speed*rate,0);
                break;
        }
        this.touch = pos.add(v);
        return super.keyDown(keycode);
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        super.dispose();
    }
}
