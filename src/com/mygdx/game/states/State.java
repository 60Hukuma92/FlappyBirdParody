package com.mygdx.game.states;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;

public abstract class State {
    protected OrthographicCamera cam;
    protected Vector3 vector;
    protected GameStateManager gameStateManager;

    public State(GameStateManager gameStatemanager) {
        this.cam = new OrthographicCamera();
        this.vector = new Vector3();
        this.gameStateManager = gameStatemanager;
    }

    protected abstract void handlerInput();
    public abstract void upgrade(float deltaTime);
    public abstract void render(SpriteBatch spriteBatch);
    public abstract void dispose();
}
