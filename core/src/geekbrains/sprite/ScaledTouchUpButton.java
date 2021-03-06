package geekbrains.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import geekbrains.base.Sprite;
import geekbrains.screen.MenuScreen;

public abstract class ScaledTouchUpButton extends Sprite {

    private static final float PRESS_SCALE = 0.5f;

    private int pointer;
    private boolean isPressed = false;
    MenuScreen screen;

    public ScaledTouchUpButton(TextureRegion region) {
        super(region);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer) {
        if (this.isPressed || !isMe(touch)) {
            return false;
        }
        this.pointer = pointer;
        this.scale = PRESS_SCALE;
        this.isPressed = true;
        return super.touchDown(touch, pointer);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer) {
        if (this.pointer != pointer || !isPressed) {
            return false;
        }
        if (isMe(touch)){
            action();
        }
        this.isPressed = false;
        scale = 1f;
        return super.touchUp(touch, pointer);
    }

    public abstract void action();

}
