package theGame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Objects;

public class Coin {

    private Integer xPos = null;
    private Integer yPos = null;
    private String color;
    private Texture texture;
    private Rectangle boundingBox = new Rectangle();
    private Integer width = 200;
    private Integer height = 50;

    /**
     * Constructor.
     */
    public Coin(Integer xPos, Integer yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public Integer getXPos() {
        return xPos;
    }

    public void setXPos(Integer xPos) {
        this.xPos = xPos;
    }

    public Integer getYPos() {
        return yPos;
    }

    public void setYPos(Integer yPos) {
        this.yPos = yPos;
    }


    /**
     * Return the Texture of coin.
     * @return Texture
     */
    public Texture getTexture() {
        texture = new Texture(Gdx.files.internal("stick.png"));
        return texture;
    }

    public Rectangle getBoundingBox() {
        return boundingBox;
    }

    public void setBoundingBox(Rectangle boundingBox) {
        this.boundingBox = boundingBox;
    }

    public void draw(Batch batch) {
        float centerX = xPos - width / 2f;
        float centerY = yPos - height / 2f;
        boundingBox.setX(centerX + 200);
        boundingBox.setY(centerY);
        batch.draw(getTexture(), centerX, centerY, width, height);
    }
}
