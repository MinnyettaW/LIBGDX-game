package com.mygame;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main implements ApplicationListener {
    private ShapeRenderer shape;
    private Sprite dog2;
    private int score2;
    private int lives2;
    private int lives;
    private ArrayList<Ball> ballsP1;
    private ArrayList<Ball> ballsP2;
    private BitmapFont font;
    private int score;
    private Texture dogImage,dogImage2, backgroundImage;
    private Sprite dog, background;
    private SpriteBatch batch;
    private FitViewport viewport;
    @Override
    public void create() {
        // Prepare your application here.
        shape = new ShapeRenderer();
        lives = 3;
        dogImage = new Texture("dog.png");
        dogImage2 = new Texture("dog2.png");
        backgroundImage = new Texture("background.jpg");
        dog = new Sprite(dogImage);
        dog2 = new Sprite(dogImage2);
        dog2.setSize(2, 2);

        // place Player 2 on right side
        dog2.setPosition(5, 2);

        score2 = 0;
        lives2 = 3;
        background = new Sprite(backgroundImage);
        batch = new SpriteBatch();
        viewport = new FitViewport(8, 5); //8x5 units
        dog.setSize(2, 2); //set in 1 unit block in 8x5 viewport
        dog.setPosition(3, 2); //default (0,0) bottom left!
        //set to full viewport size
        background.setSize(viewport.getWorldWidth(), viewport.getWorldHeight());
        background.setPosition(0, 0); //bottom left
        ballsP1 = new ArrayList<>();
        ballsP2 = new ArrayList<>();
        // Player 1 balls (LEFT SIDE)
        ballsP1.add(new Ball("ball.jpg", false));
        ballsP1.add(new Ball("redball.png", true));

        // Player 2 balls (RIGHT SIDE)
        ballsP2.add(new Ball("ball.jpg", false));
        ballsP2.add(new Ball("redball.png", true));
       for (Ball b : ballsP1) {
        b.setPosition(
        MathUtils.random(0, 4 - b.getWidth()),
        MathUtils.random(0, viewport.getWorldHeight() - b.getHeight())
        );
}
       for (Ball b : ballsP2) {
        b.setPosition(
        MathUtils.random(4, 8 - b.getWidth()),
        MathUtils.random(0, viewport.getWorldHeight() - b.getHeight())
        );
}






        // score
        font = new BitmapFont();
        score = 0;
        }

    @Override
    public void resize(int width, int height) {
        // If the window is minimized on a desktop (LWJGL3) platform, width and height are 0, which causes problems.
        // In that case, we don't resize anything, and wait for the window to be a normal size before updating.
        if(width <= 0 || height <= 0) return;

        // Resize your application here. The parameters represent the new window size.
        viewport.update(width, height, true);// true centers the camera
    }

    @Override
    public void render() {
        if (lives > 0 && lives2 > 0) {
        input();
        inputPlayer2(); 
    }
        // Draw your application here.

ScreenUtils.clear(Color.BLACK);
viewport.apply();

// SET CAMERA
batch.setProjectionMatrix(viewport.getCamera().combined);
shape.setProjectionMatrix(viewport.getCamera().combined);


batch.begin();
background.draw(batch);
batch.end();


shape.begin(ShapeRenderer.ShapeType.Filled);
shape.setColor(1, 1, 1, 1);
shape.rect(3.7f, 0, 0.6f, 5);
shape.end();


batch.begin();
font.getData().setScale(0.08f);

// players
dog.draw(batch);
dog2.draw(batch);

// balls
for (Ball b : ballsP1) b.draw(batch);
for (Ball b : ballsP2) b.draw(batch);




// Player 1 (LEFT)
font.draw(batch, "P1: " + score, 0.3f, 4.8f);
font.draw(batch, "L: " + lives, 0.3f, 4.4f);

// Player 2 (RIGHT)
font.draw(batch, "P2: " + score2, 5.2f, 4.8f);
font.draw(batch, "L: " + lives2, 5.2f, 4.4f);
font.getData().setScale(0.05f); // reset font BEFORE game over text

// GAME OVER
if (lives <= 0 || lives2 <= 0) {

    // Winner text (top)
    font.getData().setScale(0.06f);

    if (score > score2) {
        font.draw(batch, "PLAYER 1 WINS!", 1.8f, 3.3f);
    } else if (score2 > score) {
        font.draw(batch, "PLAYER 2 WINS!", 1.8f, 3.3f);
    } else {
        font.draw(batch, "TIE GAME!", 2.2f, 3.3f);
    }

    // Game Over (middle)
    font.getData().setScale(0.05f);
    font.draw(batch, "GAME OVER", 2.4f, 2.7f);

    // Restart instruction (bottom)
    font.getData().setScale(0.45f);
    font.draw(batch, "Press R to Restart", 1.9f, 2.2f);
}

batch.end();
        if (lives > 0 && lives2 > 0) {
        move();
        checkCollision();
    }
        if ((lives <= 0 || lives2 <= 0) && Gdx.input.isKeyJustPressed(Input.Keys.R)) {
        resetGame();
}

    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void dispose() {
        // Destroy application's resources here.
        dogImage.dispose();
        backgroundImage.dispose();
        // Player 1 balls
        for (Ball b : ballsP1) {
        b.getTexture().dispose();
}

        // Player 2 balls
        for (Ball b : ballsP2) {
        b.getTexture().dispose();
}
        batch.dispose();
        font.dispose();
    }
    public void move() {
        dog.translateX(1 * Gdx.graphics.getDeltaTime());
        dog.setX(MathUtils.clamp(
            dog.getX(), 
            0, 
            4 - dog.getWidth()
            ));
 
    }
    public void input() {
        float speed = 2f; // can adjust 
        float delta = Gdx.graphics.getDeltaTime(); // for all hw frame rate
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            dog.translateX(-speed*delta); // Move left
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
        dog.translateX(speed * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
        dog.translateY(speed * delta);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
        dog.translateY(-speed * delta);
       }

}
public void checkCollision() {
    for (Ball b : ballsP1) {
    if (dog.getBoundingRectangle().overlaps(b.getBoundingRectangle())) {

        if (b.isBad()) {
            lives--;
        } else {
            score++;
        }

        b.setPosition(
            MathUtils.random(0, 4 - b.getWidth()),
            MathUtils.random(0, viewport.getWorldHeight() - b.getHeight())
        );
    }
}
    for (Ball b : ballsP2) {
    if (dog2.getBoundingRectangle().overlaps(b.getBoundingRectangle())) {

        if (b.isBad()) {
            lives2--;
        } else {
            score2++;
        }

        b.setPosition(
            MathUtils.random(4, 8 - b.getWidth()),
            MathUtils.random(0, viewport.getWorldHeight() - b.getHeight())
        );
    }
}
}
public void inputPlayer2() {
    float speed = 2f;
    float delta = Gdx.graphics.getDeltaTime();

    if (Gdx.input.isKeyPressed(Input.Keys.A)) {
        dog2.translateX(-speed * delta);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.D)) {
        dog2.translateX(speed * delta);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.W)) {
        dog2.translateY(speed * delta);
    }
    if (Gdx.input.isKeyPressed(Input.Keys.S)) {
        dog2.translateY(-speed * delta);
    }

    // clamp to right side
    dog2.setX(MathUtils.clamp(dog2.getX(), 4, 8 - dog2.getWidth()));
}
public void resetGame() {
    score = 0;
    score2 = 0;
    lives = 3;
    lives2 = 3;

    dog.setPosition(1, 2);
    dog2.setPosition(6, 2);
}
        }
     