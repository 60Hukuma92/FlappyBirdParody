package com.mygdx.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.FlappyBirdParody;

public class Bird {
    public static final int MOVEMENT = 100;
    public static final int GRAVITY = -15;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle invisiblePlayerRectangle;
    private Texture bird;
    private Texture animationTexture;
    private Animation birdAnimation;
    private Sound flap;

    public Bird(int x, int y) {
        position = new Vector3(x, y, 0);
        velocity = new Vector3(0, 0, 0);
        animationTexture = new Texture("birdanimation.png");
        birdAnimation = new Animation(new TextureRegion(animationTexture), 3, 0.5f);
        invisiblePlayerRectangle = new Rectangle(x, y, animationTexture.getWidth() / 3, animationTexture.getHeight());
        flap = Gdx.audio.newSound(Gdx.files.internal("sfx_wing.ogg"));
    }

    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getBird() {
        return birdAnimation.getFrame();
    }

    public Rectangle getInvisiblePlayerRectangle() {
        return invisiblePlayerRectangle;
    }

    public void update(float deltaTime) {
        birdAnimation.update(deltaTime);
        if (position.y > 0) velocity.add(0, GRAVITY, 0);
        velocity.scl(deltaTime);
        position.add(MOVEMENT * deltaTime, velocity.y, 0);
        if (position.y < 0) position.y = 0;
        velocity.scl(1 / deltaTime);
        invisiblePlayerRectangle.setPosition(position.x, position.y);
    }

    public void jump() {
        velocity.y = 250;
        flap.play(0.1f);
    }

    public void dispose() { animationTexture.dispose(); flap.dispose();}
}
