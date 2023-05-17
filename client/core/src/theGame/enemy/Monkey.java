package theGame.enemy;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class Monkey extends Sprite {
    private int id;
    private float xPosition;
    private float yPosition;
    private Texture[] textures; // Array to store textures for monkey
    private Music music;
    private boolean isPlaying;

    public Monkey(float xPosition, float yPosition, int id) {
        this.id = id;
        this.xPosition = xPosition;
        this.yPosition = yPosition;

        textures = new Texture[2];
        for (int i = 1; i <= 2; i++) {
            textures[i-1] = new Texture(Gdx.files.internal("kong/rsz_kong" + i + ".png"));
        }
        music = Gdx.audio.newMusic(Gdx.files.internal("kong/donkey_kong.mp3"));
        isPlaying = false;
    }

    public float getXPosition() {
        return xPosition;
    }

    public float getYPosition() {
        return yPosition;
    }

    public int getId() {
        return id;
    }

    public Texture getTexture() {
        int frameIndex = (int) ((System.currentTimeMillis() / 800) % 2);
        return textures[frameIndex];
    }

    public void update(float playerX, float playerY) {
        // calculate the distance between the player and the monkey
        float distance = (float) Math.sqrt(Math.pow(playerX - xPosition, 2) + Math.pow(playerY - yPosition, 2));

        if (distance <= 1000 && !isPlaying) {
            // start playing the music and set it to loop
            music.setLooping(true);
            music.play();
            isPlaying = true;
        } else if (distance > 1000 && isPlaying) {
            // stop playing the music
            music.stop();
            isPlaying = false;
        }
    }

    public void dispose() {
        music.dispose();
    }

}
