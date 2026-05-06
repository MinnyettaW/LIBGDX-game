package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Ball extends Sprite {

    private boolean isBad;
    private float speed;

    public Ball(String imagePath, boolean isBad) {
        super(new Texture(imagePath));
        this.isBad = isBad;
        this.speed = 1.5f;
        setSize(1, 1);
    }

    public boolean isBad() {
        return isBad;
    }

    public float getSpeed() {
        return speed;
    }

    public void setBad(boolean isBad) {
        this.isBad = isBad;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "Ball [isBad=" + isBad + ", speed=" + speed + "]";
    }
}