package com.mygame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;

public class Player extends Sprite {

    // instance variables
    private int score;
    private int hits;
    private float speed;

    // constructor
    public Player(String imagePath) {
        super(new Texture(imagePath));
        this.score = 0;
        this.hits = 0;
        this.speed = 2f;
        setSize(1, 1);
    }

    // getters
    public int getScore() {
        return score;
    }

    public int getHits() {
        return hits;
    }

    public float getSpeed() {
        return speed;
    }

    // setters
    public void setScore(int score) {
        this.score = score;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    // toString method
    @Override
    public String toString() {
        return "Player [score=" + score + ", hits=" + hits + ", speed=" + speed + "]";
    }
}