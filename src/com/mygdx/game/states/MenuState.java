package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.FlappyBirdParody;

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gameStatemanager) {
        super(gameStatemanager);
        cam.setToOrtho(false, FlappyBirdParody.WIDTH / 2, FlappyBirdParody.HEIGHT / 2);
        System.out.println("MenuState Created");
        background = new Texture("bg.png");
        playBtn = new Texture("playBtn.png");
    }

    @Override
    protected void handlerInput() {
        if (Gdx.input.justTouched()) {
            gameStateManager.set(new PlayState(gameStateManager));
        }
    }

    @Override
    public void upgrade(float deltaTime) {
        handlerInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0,0);
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("MenuState Disposed");
    }
}
