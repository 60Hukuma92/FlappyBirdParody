package com.mygdx.game.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.game.FlappyBirdParody;
import com.mygdx.game.sprites.Bird;
import com.mygdx.game.sprites.Tube;

import java.util.ArrayList;

public class PlayState extends State {
    public static final int TUBE_SPACE = 125;
    public static final int TUBE_QUANTITY = 4;
    public static final int GROUND_Y_OFFSET = -50;
    private Bird bird;
    private Texture background;
    private Texture ground;
    private Vector2 groundPos1, groundPos2;
    private Tube tube;
    private ArrayList<Tube> tubes = new ArrayList<>();

    public PlayState(GameStateManager gameStatemanager) {
        super(gameStatemanager);
        bird = new Bird(50, 150);
        System.out.println("PlayState Created");
        cam.setToOrtho(false, FlappyBirdParody.WIDTH / 2, FlappyBirdParody.HEIGHT / 2);
        background = new Texture("bg.png");
        ground = new Texture("ground.png");
        groundPos1 = new Vector2(cam.position.x - cam.viewportWidth / 2, GROUND_Y_OFFSET);
        groundPos2 = new Vector2(cam.position.x - cam.viewportWidth / 2 + ground.getWidth(), GROUND_Y_OFFSET);
        for (int i = 1; i <= TUBE_QUANTITY; i++) {
            tubes.add(new Tube(i * (TUBE_SPACE + Tube.TUBE_WIDTH)));
        }
    }

    @Override
    protected void handlerInput() {
        if (Gdx.input.justTouched()) {
            bird.jump();
        }
    }

    @Override
    public void upgrade(float deltaTime) {
        handlerInput();
        updateGround();
        bird.update(deltaTime);
        cam.position.x = bird.getPosition().x + 80;
        for (Tube tube : tubes) {
            if (cam.position.x - (cam.viewportWidth / 2) > tube.getPosTopTube().x + tube.getTopTube().getWidth()) {
                tube.repositionToRight(tube.getPosTopTube().x + (Tube.TUBE_WIDTH + TUBE_SPACE) * TUBE_QUANTITY);
            }

            if (tube.collides(bird.getInvisiblePlayerRectangle())) {
                gameStateManager.set(new MenuState(gameStateManager));
            }
        }
        if (bird.getPosition().y <= ground.getHeight() + GROUND_Y_OFFSET) {
            gameStateManager.set(new MenuState(gameStateManager));
        }
        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(bird.getBird(), bird.getPosition().x, bird.getPosition().y);
        for(Tube tube : tubes) {
            sb.draw(tube.getTopTube(), tube.getPosTopTube().x, tube.getPosTopTube().y);
            sb.draw(tube.getBottomTube(), tube.getPosBotTube().x, tube.getPosBotTube().y);
        }

        sb.draw(ground, groundPos1.x, groundPos1.y);
        sb.draw(ground, groundPos2.x, groundPos2.y);
        sb.end();
    }

    private void updateGround() {
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos1.x + ground.getWidth())
            groundPos1.add(ground.getWidth() * 2, 0);
        if (cam.position.x - (cam.viewportWidth / 2) > groundPos2.x + ground.getWidth())
            groundPos2.add(ground.getWidth() * 2, 0);
    }

    @Override
    public void dispose() {
        background.dispose();
        bird.dispose();
        ground.dispose();
        for (Tube tube : tubes) {
            tube.dispose();
            System.out.println("PlayState Disposed");
        }
    }
}
