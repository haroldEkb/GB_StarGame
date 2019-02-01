package geekbrains.sprite.game;

import geekbrains.base.Sprite;
import geekbrains.math.Rect;
import geekbrains.math.Rnd;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;


public class Bullet extends Sprite {

    private Rect worldBounds;
    private Vector2 v = new Vector2();
    private int damage;
    private Object owner;

    public Bullet() {
        regions = new TextureRegion[1];
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(v, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    public void set(
            Object owner,
            TextureRegion region,
            Vector2 pos0,
            Vector2 v0,
            float height,
            Rect worldBounds,
            int damage
    ) {
        this.owner = owner;
        this.regions[0] = region;
        this.pos.set(pos0);
        this.v.set(v0);
        setHeightProportion(height);
        this.worldBounds = worldBounds;
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    public Object getOwner() {
        return owner;
    }

    public static class Star extends Sprite {

        private Vector2 v = new Vector2();
        private Rect worldBounds;

        public Star(TextureAtlas atlas) {
            super(atlas.findRegion("star"));
            setHeightProportion(0.01f);
            v.set(Rnd.nextFloat(-0.005f, 0.005f), Rnd.nextFloat(-0.5f,-0.1f));
        }

        @Override
        public void update(float delta) {
            pos.mulAdd(v, delta);
            checkAnHandleBounds();
        }

        @Override
        public void resize(Rect bounds) {
            this.worldBounds = bounds;
            float posX = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
            float posY = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
            pos.set(posX,posY);
        }

        private void checkAnHandleBounds(){
            if (getRight() < worldBounds.getLeft()) setLeft(worldBounds.getRight());
            if (getLeft() > worldBounds.getRight()) setRight(worldBounds.getLeft());
            if (getTop() < worldBounds.getBottom()) setBottom(worldBounds.getTop());
            if (getBottom() > worldBounds.getTop()) setTop(worldBounds.getBottom());
        }
    }
}
