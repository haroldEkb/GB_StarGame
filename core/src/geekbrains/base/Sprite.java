package geekbrains.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import geekbrains.utils.Regions;

import geekbrains.math.Rect;

public class Sprite extends Rect {

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;
    protected boolean isDestroyed;

    public Sprite(){
    }

    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("Create Sprite witth null region");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public Sprite(TextureRegion region, int rows, int cols, int frames) {
        if (region == null) {
            throw new NullPointerException("Create Sprite with null region");
        }
        this.regions = Regions.split(region, rows, cols, frames);
    }

    public void setHeightProportion(float height){
        setHeight(height);
        float aspect = regions[frame].getRegionWidth()/(float)regions[frame].getRegionWidth();
        setWidth(height*aspect);
    }

    public void resize(Rect bounds){

    }

    public void update(float delta){

    }

    public boolean touchDown(Vector2 touch, int pointer) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer) {
        return false;
    }


    public void draw(SpriteBatch batch){
        batch.draw(
                regions[frame],
                getLeft(), getBottom(),
                halfWidth, halfHeight,
                getWidth(), getHeight(),
                scale, scale,
                angle
        );
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void destroy() {
        this.isDestroyed = true;
    }

    public void recreate(){
        this.isDestroyed = false;
    }

    public void flushDestroy() {
        this.isDestroyed = false;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }
}
