package com.ravnoy.adventure;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.ravnoy.adventure.audio.AudioManager;
import com.ravnoy.adventure.data.DialogueRegistry;
import com.ravnoy.adventure.data.SceneRegistry;
import com.ravnoy.adventure.model.GameState;
import com.ravnoy.adventure.ui.FontManager;
import com.ravnoy.adventure.ui.GameScreen;

/**
 * Sentral spillapplikasjon for 'Mysteriet på Ravnøy'.
 */
public class RavnoyGame extends Game {
    private SpriteBatch batch;
    private ShapeRenderer shapeRenderer;
    private FontManager fontManager;
    private AudioManager audioManager;
    private GameState gameState;
    private SceneRegistry sceneRegistry;
    private DialogueRegistry dialogueRegistry;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

        fontManager = new FontManager();
        fontManager.init();

        audioManager = new AudioManager();
        gameState = new GameState();

        sceneRegistry = new SceneRegistry();
        sceneRegistry.init();

        dialogueRegistry = new DialogueRegistry();
        dialogueRegistry.init();

        setScreen(new GameScreen(this));
    }

    public SpriteBatch getBatch() { return batch; }
    public ShapeRenderer getShapeRenderer() { return shapeRenderer; }
    public FontManager getFontManager() { return fontManager; }
    public AudioManager getAudioManager() { return audioManager; }
    public GameState getGameState() { return gameState; }
    public SceneRegistry getSceneRegistry() { return sceneRegistry; }
    public DialogueRegistry getDialogueRegistry() { return dialogueRegistry; }

    @Override
    public void dispose() {
        super.dispose();
        if (batch != null) batch.dispose();
        if (shapeRenderer != null) shapeRenderer.dispose();
        if (fontManager != null) fontManager.dispose();
        if (audioManager != null) audioManager.dispose();
    }
}
