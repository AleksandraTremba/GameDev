package server;

public class Raccoon {
    private float xPosition;
    private float yPosition;
    private final int id;

    public Raccoon(int id, float x, float y) {
        this.xPosition = x;
        this.yPosition = y;
        this.id = id;
    }

    public void move(float xPos, float yPos) {
        xPosition += xPos;
        yPosition += yPos;

        if (xPosition < 0) xPosition= 0;
        if (yPosition < 0) yPosition = 0;
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
}
