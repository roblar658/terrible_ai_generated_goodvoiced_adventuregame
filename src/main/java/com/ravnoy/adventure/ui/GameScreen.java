package com.ravnoy.adventure.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.ravnoy.adventure.RavnoyGame;
import com.ravnoy.adventure.model.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main game screen handling 10-chapter atmospheric layered rendering,
 * branching English dialogue with dynamic choices, contextual cursor,
 * inventory item usage across all puzzles, radar ping, journal, and dialogue log.
 * Chapter dropdown menu removed per design specifications.
 */
public class GameScreen implements Screen {
    public static final float V_WIDTH = 1280f;
    public static final float V_HEIGHT = 720f;

    private final RavnoyGame game;
    private final Viewport viewport;
    private final SpriteBatch batch;
    private final ShapeRenderer shapeRenderer;
    private final FontManager fonts;
    private final GlyphLayout layout = new GlyphLayout();

    private SceneModel currentScene;
    private Map<String, Texture> textureCache = new HashMap<>();

    // Interaction & Animation Timers
    private Vector2 mousePos = new Vector2();
    private Hotspot hoveredHotspot = null;
    private InventoryItem hoveredInventoryItem = null;
    private float hoverAnimationTimer = 0f;
    private float pingTimer = 0f;
    private static final float PING_DURATION = 2.0f;

    // Scene Transition Fade
    private float sceneFadeAlpha = 0f;
    private boolean isFadingIn = false;
    private String pendingSceneId = null;

    // Active Dialogue System
    private DialogueNode activeDialogue = null;
    private float typewriterTimer = 0f;
    private int visibleChars = 0;
    private boolean typewriterFinished = false;
    private int hoveredChoiceIndex = -1;
    private Rectangle[] choiceRects = new Rectangle[12];

    // Modals (Item Inspect, Backlog, Journal)
    private InventoryItem inspectingItem = null;
    private boolean showBacklog = false;
    private boolean showJournal = false;
    private float backlogScroll = 0f;
    private float journalScroll = 0f;

    // Top Bar HUD Rectangles (Clean layout without chapter dropdown)
    private Rectangle pingBtnRect = new Rectangle(740, 672, 130, 40);
    private Rectangle backlogBtnRect = new Rectangle(880, 672, 130, 40);
    private Rectangle journalBtnRect = new Rectangle(1020, 672, 110, 40);
    private Rectangle soundBtnRect = new Rectangle(1140, 672, 110, 40);

    // Bottom Inventory Dock
    private Rectangle inventoryBarRect = new Rectangle(140, 0, 1000, 72);

    public GameScreen(RavnoyGame game) {
        this.game = game;
        this.viewport = new FitViewport(V_WIDTH, V_HEIGHT);
        this.batch = game.getBatch();
        this.shapeRenderer = game.getShapeRenderer();
        this.fonts = game.getFontManager();

        for (int i = 0; i < choiceRects.length; i++) {
            choiceRects[i] = new Rectangle();
        }
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(new GameInputHandler());
        loadScene(game.getGameState().getCurrentSceneId(), true);
    }

    public void loadScene(String sceneId, boolean fadeIn) {
        SceneModel newScene = game.getSceneRegistry().getScene(sceneId);
        if (newScene == null) {
            Gdx.app.log("GameScreen", "Unknown scene: " + sceneId);
            return;
        }

        currentScene = newScene;
        game.getGameState().setCurrentSceneId(sceneId);
        game.getGameState().addJournalNote(newScene.getTitle() + ": " + newScene.getSubtitle());

        // Preload background and all visible prop textures
        getTexture(newScene.getBackgroundPath());
        for (Hotspot h : newScene.getHotspots()) {
            if (h.getOverlayTexturePath() != null) {
                getTexture(h.getOverlayTexturePath());
            }
        }

        // Start atmospheric ambient music
        if (newScene.getAmbientMusicPath() != null) {
            game.getAudioManager().playAmbient(newScene.getAmbientMusicPath());
        }

        if (fadeIn) {
            sceneFadeAlpha = 1f;
            isFadingIn = true;
        } else {
            sceneFadeAlpha = 0f;
            isFadingIn = false;
        }
    }

    private Texture getTexture(String path) {
        if (path == null) return null;
        Texture t = textureCache.get(path);
        if (t == null) {
            try {
                if (Gdx.files.internal(path).exists()) {
                    t = new Texture(Gdx.files.internal(path), true);
                    t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
                    textureCache.put(path, t);
                }
            } catch (Exception e) {
                Gdx.app.log("GameScreen", "Failed to load texture: " + path, e);
            }
        }
        return t;
    }

    @Override
    public void render(float delta) {
        update(delta);

        // Clear screen
        Gdx.gl.glClearColor(0.04f, 0.05f, 0.08f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        viewport.apply();
        batch.setProjectionMatrix(viewport.getCamera().combined);
        shapeRenderer.setProjectionMatrix(viewport.getCamera().combined);

        // 1. RENDER MAIN 16:9 SCENIC BACKGROUND
        batch.begin();
        Texture bg = getTexture(currentScene.getBackgroundPath());
        if (bg != null) {
            batch.draw(bg, 0, 0, V_WIDTH, V_HEIGHT);
        }
        batch.end();

        // 2. RENDER SOFT RADIANT AURA FOR HOVERED PROP (UNDERNEATH SPRITE)
        if (hoveredHotspot != null && hoveredHotspot.isActive()) {
            renderPropGlowAura(hoveredHotspot);
        }

        // 3. RENDER VISIBLE INTERACTIVE PROPS & CHARACTERS (ON TOP OF BACKGROUND)
        batch.begin();
        for (Hotspot h : currentScene.getHotspots()) {
            if (h.isActive() && h.getOverlayTexturePath() != null) {
                Texture prop = getTexture(h.getOverlayTexturePath());
                if (prop != null) {
                    Rectangle r = h.getBounds();
                    if (h == hoveredHotspot) {
                        // Subtle breathing scale pulse (1.0 to 1.03x)
                        float scale = 1.0f + 0.03f * (float) Math.sin(hoverAnimationTimer * 5f);
                        float dw = r.width * scale;
                        float dh = r.height * scale;
                        float dx = r.x - (dw - r.width) / 2f;
                        float dy = r.y - (dh - r.height) / 2f;
                        batch.setColor(1f, 1f, 1f, 1f);
                        batch.draw(prop, dx, dy, dw, dh);
                    } else {
                        batch.setColor(Color.WHITE);
                        batch.draw(prop, r.x, r.y, r.width, r.height);
                    }
                }
            }
        }
        batch.end();

        // 4. RENDER HOTSPOT REVEAL RADAR PING (SPACEBAR)
        if (pingTimer > 0f) {
            renderRadarPing();
        }

        // 5. RENDER TOP HUD (Chapter Title & Subtitle, Reveal, Log, Journal, Audio)
        renderTopHud();

        // 6. RENDER INVENTORY DOCK AT BOTTOM
        renderInventoryDock();

        // 7. RENDER ACTIVE DIALOGUE OVERLAY WITH AI VOICE & TYPEWRITER TEXT
        if (activeDialogue != null) {
            renderDialogueBox();
        }

        // 8. RENDER SLEEK ACTION BADGE TOOLTIP ON HOVER
        if (hoveredHotspot != null && activeDialogue == null && inspectingItem == null && !showBacklog && !showJournal) {
            renderActionTooltip(hoveredHotspot, mousePos.x, mousePos.y + 24);
        }

        // 9. RENDER MODALS
        if (inspectingItem != null) {
            renderInspectModal();
        }
        if (showBacklog) {
            renderBacklogModal();
        }
        if (showJournal) {
            renderJournalModal();
        }

        // 10. RENDER SCENE CROSS-FADE TRANSITION
        if (sceneFadeAlpha > 0f) {
            Gdx.gl.glEnable(GL20.GL_BLEND);
            Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0.02f, 0.03f, 0.06f, sceneFadeAlpha);
            shapeRenderer.rect(0, 0, V_WIDTH, V_HEIGHT);
            shapeRenderer.end();
            Gdx.gl.glDisable(GL20.GL_BLEND);
        }

        // 11. RENDER CUSTOM CONTEXTUAL CURSOR
        renderCursor();
    }

    private void update(float delta) {
        mousePos.set(Gdx.input.getX(), Gdx.input.getY());
        viewport.unproject(mousePos);

        hoverAnimationTimer += delta;
        if (pingTimer > 0f) {
            pingTimer -= delta;
        }

        if (activeDialogue != null && !typewriterFinished) {
            typewriterTimer += delta * 46f;
            visibleChars = Math.min((int) typewriterTimer, activeDialogue.getText().length());
            if (visibleChars >= activeDialogue.getText().length()) {
                typewriterFinished = true;
            }
        }

        if (isFadingIn) {
            sceneFadeAlpha -= delta * 2.2f;
            if (sceneFadeAlpha <= 0f) {
                sceneFadeAlpha = 0f;
                isFadingIn = false;
            }
        } else if (pendingSceneId != null) {
            sceneFadeAlpha += delta * 2.8f;
            if (sceneFadeAlpha >= 1f) {
                sceneFadeAlpha = 1f;
                String next = pendingSceneId;
                pendingSceneId = null;
                loadScene(next, true);
            }
        }

        hoveredHotspot = null;
        if (activeDialogue == null && inspectingItem == null && !showBacklog && !showJournal) {
            for (Hotspot h : currentScene.getHotspots()) {
                if (h.isActive() && h.getBounds().contains(mousePos)) {
                    hoveredHotspot = h;
                    break;
                }
            }
        }

        hoveredInventoryItem = null;
        if (inventoryBarRect.contains(mousePos) && activeDialogue == null) {
            float slotX = inventoryBarRect.x + 20;
            for (InventoryItem item : game.getGameState().getInventory()) {
                if (mousePos.x >= slotX && mousePos.x <= slotX + 54 && mousePos.y >= 8 && mousePos.y <= 62) {
                    hoveredInventoryItem = item;
                    break;
                }
                slotX += 66;
            }
        }
    }

    private void renderPropGlowAura(Hotspot h) {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE); // Additive blending for soft glow
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        Rectangle r = h.getBounds();
        float pulse = 0.18f + 0.08f * (float) Math.sin(hoverAnimationTimer * 4f);
        shapeRenderer.setColor(0.95f, 0.82f, 0.40f, pulse);

        float pad = 12f;
        shapeRenderer.rect(r.x - pad, r.y - pad, r.width + pad * 2, r.height + pad * 2);

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderRadarPing() {
        float progress = 1.0f - (pingTimer / PING_DURATION);
        float radius = progress * 70f;
        float alpha = (1.0f - progress) * 0.85f;

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);

        for (Hotspot h : currentScene.getHotspots()) {
            if (h.isActive()) {
                Rectangle r = h.getBounds();
                float cx = r.x + r.width / 2f;
                float cy = r.y + r.height / 2f;
                shapeRenderer.setColor(0.95f, 0.85f, 0.45f, alpha);
                shapeRenderer.circle(cx, cy, radius, 32);
                shapeRenderer.circle(cx, cy, radius * 0.5f, 24);
            }
        }

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);
    }

    private void renderTopHud() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        // Translucent dark glass header
        shapeRenderer.setColor(0.06f, 0.08f, 0.13f, 0.90f);
        shapeRenderer.rect(0, 664, V_WIDTH, 56);
        shapeRenderer.setColor(0.25f, 0.35f, 0.50f, 0.9f);
        shapeRenderer.rect(0, 664, V_WIDTH, 2);

        // Buttons
        drawGlassButton(pingBtnRect, pingBtnRect.contains(mousePos));
        drawGlassButton(backlogBtnRect, backlogBtnRect.contains(mousePos));
        drawGlassButton(journalBtnRect, journalBtnRect.contains(mousePos));
        drawGlassButton(soundBtnRect, soundBtnRect.contains(mousePos));

        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        BitmapFont medium = fonts.getMediumFont();
        BitmapFont small = fonts.getSmallFont();

        // Current Chapter Title & Subtitle in English starting smoothly at x = 32
        medium.setColor(0.95f, 0.85f, 0.45f, 1f);
        medium.draw(batch, currentScene.getTitle(), 32, 702);
        small.setColor(0.75f, 0.82f, 0.92f, 0.9f);
        small.draw(batch, currentScene.getSubtitle(), 32, 680);

        // English Button Labels
        small.setColor(Color.WHITE);
        drawCenteredText(small, "Reveal (Space)", pingBtnRect);
        drawCenteredText(small, "Dialogue Log", backlogBtnRect);
        drawCenteredText(small, "Journal", journalBtnRect);
        drawCenteredText(small, game.getAudioManager().isSoundEnabled() ? "Audio: On" : "Audio: Mute", soundBtnRect);

        batch.end();
    }

    private void renderInventoryDock() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.06f, 0.08f, 0.12f, 0.90f);
        shapeRenderer.rect(inventoryBarRect.x, inventoryBarRect.y, inventoryBarRect.width, inventoryBarRect.height);
        shapeRenderer.setColor(0.35f, 0.45f, 0.60f, 0.70f);
        shapeRenderer.rect(inventoryBarRect.x, inventoryBarRect.y + inventoryBarRect.height - 2, inventoryBarRect.width, 2);

        // Inventory slots
        float slotX = inventoryBarRect.x + 20;
        for (int i = 0; i < 14; i++) {
            shapeRenderer.setColor(0.12f, 0.16f, 0.22f, 0.7f);
            shapeRenderer.rect(slotX, 10, 52, 52);
            shapeRenderer.setColor(0.25f, 0.32f, 0.44f, 0.6f);
            shapeRenderer.rect(slotX, 10, 52, 52);
            slotX += 66;
        }
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        slotX = inventoryBarRect.x + 20;
        for (InventoryItem item : game.getGameState().getInventory()) {
            Texture icon = getTexture(item.getIconPath());
            if (icon != null) {
                if (game.getGameState().getActiveItem() == item) {
                    batch.setColor(1f, 0.9f, 0.4f, 1f);
                    batch.draw(icon, slotX - 2, 8, 56, 56);
                    batch.setColor(Color.WHITE);
                } else {
                    batch.draw(icon, slotX, 10, 52, 52);
                }
            }
            slotX += 66;
        }
        batch.end();

        if (hoveredInventoryItem != null && activeDialogue == null) {
            renderActionTooltipCustom(hoveredInventoryItem.getName(), "Right-click: Inspect | Left-click: Use", mousePos.x, mousePos.y + 32);
        }
    }

    private void renderDialogueBox() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        float boxX = 60;
        float boxY = 75;
        float boxW = V_WIDTH - 120;
        float boxH = 300;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.06f, 0.08f, 0.12f, 0.96f);
        shapeRenderer.rect(boxX, boxY, boxW, boxH);
        shapeRenderer.setColor(0.35f, 0.45f, 0.65f, 0.85f);
        shapeRenderer.rect(boxX, boxY + boxH - 2, boxW, 2);

        // Portrait frame
        shapeRenderer.setColor(0.12f, 0.16f, 0.24f, 0.95f);
        shapeRenderer.rect(boxX + 20, boxY + 30, 180, 180);
        shapeRenderer.setColor(0.85f, 0.72f, 0.38f, 0.9f);
        shapeRenderer.rect(boxX + 18, boxY + 28, 184, 184);
        shapeRenderer.end();

        hoveredChoiceIndex = -1;
        List<DialogueChoice> choices = activeDialogue.getAvailableChoices(game.getGameState());
        if (typewriterFinished && !choices.isEmpty()) {
            float choiceY = boxY + 16;
            float choiceW = boxW - 245;
            float choiceH = 34;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            for (int i = choices.size() - 1; i >= 0; i--) {
                DialogueChoice c = choices.get(i);
                choiceRects[i].set(boxX + 225, choiceY, choiceW, choiceH);

                boolean hovered = choiceRects[i].contains(mousePos);
                if (hovered) {
                    hoveredChoiceIndex = i;
                    shapeRenderer.setColor(0.28f, 0.38f, 0.60f, 0.95f);
                } else {
                    shapeRenderer.setColor(0.12f, 0.16f, 0.24f, 0.88f);
                }
                shapeRenderer.rect(choiceRects[i].x, choiceRects[i].y, choiceRects[i].width, choiceRects[i].height);
                shapeRenderer.setColor(0.40f, 0.52f, 0.72f, 0.8f);
                shapeRenderer.rect(choiceRects[i].x, choiceRects[i].y + choiceRects[i].height - 1, choiceRects[i].width, 1);
                choiceY += 40;
            }
            shapeRenderer.end();
        }
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        Texture portrait = getTexture(activeDialogue.getPortraitPath());
        if (portrait != null) {
            batch.draw(portrait, boxX + 20, boxY + 30, 180, 180);
        }

        fonts.getLargeFont().setColor(0.95f, 0.85f, 0.45f, 1f);
        fonts.getLargeFont().draw(batch, activeDialogue.getSpeakerName(), boxX + 225, boxY + boxH - 22);

        BitmapFont medium = fonts.getMediumFont();
        medium.setColor(Color.WHITE);
        String currentText = activeDialogue.getText().substring(0, visibleChars);
        medium.draw(batch, currentText, boxX + 225, boxY + boxH - 58, boxW - 250, -1, true);

        if (typewriterFinished && !choices.isEmpty()) {
            for (int i = 0; i < choices.size(); i++) {
                DialogueChoice c = choices.get(i);
                Rectangle cr = choiceRects[i];
                if (i == hoveredChoiceIndex) {
                    medium.setColor(1f, 0.95f, 0.5f, 1f);
                } else {
                    medium.setColor(0.85f, 0.92f, 1f, 0.9f);
                }
                medium.draw(batch, "• " + c.getText(), cr.x + 14, cr.y + 24);
            }
        } else if (typewriterFinished) {
            BitmapFont small = fonts.getSmallFont();
            small.setColor(0.7f, 0.8f, 0.95f, 0.7f);
            small.draw(batch, "[Click or press Space to continue]", boxX + boxW - 280, boxY + 25);
        }

        batch.end();
    }

    private void renderInspectModal() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.75f);
        shapeRenderer.rect(0, 0, V_WIDTH, V_HEIGHT);

        float mw = 640;
        float mh = 420;
        float mx = (V_WIDTH - mw) / 2f;
        float my = (V_HEIGHT - mh) / 2f;

        shapeRenderer.setColor(0.08f, 0.11f, 0.16f, 0.96f);
        shapeRenderer.rect(mx, my, mw, mh);
        shapeRenderer.setColor(0.75f, 0.62f, 0.35f, 0.9f);
        shapeRenderer.rect(mx, my + mh - 3, mw, 3);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        float mx2 = (V_WIDTH - 640) / 2f;
        float my2 = (V_HEIGHT - 420) / 2f;

        Texture icon = getTexture(inspectingItem.getIconPath());
        if (icon != null) {
            batch.draw(icon, mx2 + 40, my2 + 200, 160, 160);
        }

        fonts.getTitleFont().setColor(0.95f, 0.85f, 0.45f, 1f);
        fonts.getTitleFont().draw(batch, inspectingItem.getName(), mx2 + 220, my2 + 360);

        fonts.getMediumFont().setColor(0.9f, 0.95f, 1f, 1f);
        fonts.getMediumFont().draw(batch, inspectingItem.getDescription(), mx2 + 220, my2 + 300, 380, -1, true);

        fonts.getSmallFont().setColor(0.65f, 0.75f, 0.88f, 0.8f);
        fonts.getSmallFont().draw(batch, "[Click anywhere to close inspection]", mx2 + 200, my2 + 45);

        batch.end();
    }

    private void renderBacklogModal() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(0, 0, V_WIDTH, V_HEIGHT);

        float mw = 880;
        float mh = 560;
        float mx = (V_WIDTH - mw) / 2f;
        float my = (V_HEIGHT - mh) / 2f;

        shapeRenderer.setColor(0.07f, 0.09f, 0.14f, 0.98f);
        shapeRenderer.rect(mx, my, mw, mh);
        shapeRenderer.setColor(0.35f, 0.50f, 0.70f, 0.9f);
        shapeRenderer.rect(mx, my + mh - 3, mw, 3);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        float mx2 = (V_WIDTH - 880) / 2f;
        float my2 = (V_HEIGHT - 560) / 2f;

        fonts.getLargeFont().setColor(0.95f, 0.85f, 0.45f, 1f);
        fonts.getLargeFont().draw(batch, "Dialogue History & Lore Transcript", mx2 + 30, my2 + mh - 25);

        float logY = my2 + mh - 80 + backlogScroll;
        List<DialogueLogEntry> history = game.getGameState().getDialogueHistory();
        if (history.isEmpty()) {
            fonts.getMediumFont().setColor(0.7f, 0.7f, 0.7f, 1f);
            fonts.getMediumFont().draw(batch, "No conversations recorded yet.", mx2 + 30, logY);
        } else {
            for (DialogueLogEntry entry : history) {
                if (logY < my2 + 60) break;
                if (logY < my2 + mh - 50) {
                    fonts.getMediumFont().setColor(0.95f, 0.85f, 0.45f, 1f);
                    fonts.getMediumFont().draw(batch, entry.getSpeaker() + ":", mx2 + 30, logY);
                    fonts.getMediumFont().setColor(0.9f, 0.95f, 1f, 0.95f);
                    fonts.getMediumFont().draw(batch, entry.getText(), mx2 + 30, logY - 24, mw - 60, -1, true);
                }
                logY -= 75;
            }
        }

        fonts.getSmallFont().setColor(0.6f, 0.7f, 0.85f, 0.7f);
        fonts.getSmallFont().draw(batch, "[Click anywhere to close log]", mx2 + 340, my2 + 35);
        batch.end();
    }

    private void renderJournalModal() {
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.8f);
        shapeRenderer.rect(0, 0, V_WIDTH, V_HEIGHT);

        float mw = 840;
        float mh = 540;
        float mx = (V_WIDTH - mw) / 2f;
        float my = (V_HEIGHT - mh) / 2f;

        shapeRenderer.setColor(0.08f, 0.10f, 0.15f, 0.98f);
        shapeRenderer.rect(mx, my, mw, mh);
        shapeRenderer.setColor(0.85f, 0.72f, 0.38f, 0.9f);
        shapeRenderer.rect(mx, my + mh - 3, mw, 3);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        float mx2 = (V_WIDTH - 840) / 2f;
        float my2 = (V_HEIGHT - 540) / 2f;

        fonts.getLargeFont().setColor(0.95f, 0.85f, 0.45f, 1f);
        fonts.getLargeFont().draw(batch, "Expedition Journal & Clues", mx2 + 30, my2 + mh - 25);

        float jY = my2 + mh - 80 + journalScroll;
        List<String> notes = game.getGameState().getJournalNotes();
        for (String note : notes) {
            if (jY < my2 + 60) break;
            if (jY < my2 + mh - 50) {
                fonts.getMediumFont().setColor(0.95f, 0.85f, 0.45f, 1f);
                fonts.getMediumFont().draw(batch, "•", mx2 + 30, jY);
                fonts.getMediumFont().setColor(0.90f, 0.95f, 1f, 0.95f);
                fonts.getMediumFont().draw(batch, note, mx2 + 55, jY, mw - 85, -1, true);
            }
            jY -= 55;
        }

        fonts.getSmallFont().setColor(0.6f, 0.7f, 0.85f, 0.7f);
        fonts.getSmallFont().draw(batch, "[Click anywhere to close journal]", mx2 + 310, my2 + 35);
        batch.end();
    }

    private void renderActionTooltip(Hotspot h, float x, float y) {
        String actionStr = "";
        switch (h.getInteractionType()) {
            case TALK: actionStr = "TALK"; break;
            case PICKUP: actionStr = "TAKE"; break;
            case TRANSITION: actionStr = "ENTER"; break;
            case USE_ITEM: actionStr = "USE ITEM"; break;
            case INSPECT:
            default: actionStr = "EXAMINE"; break;
        }

        String fullText = h.getName() + " [" + actionStr + "]";
        layout.setText(fonts.getSmallFont(), fullText);
        float tw = layout.width + 24;
        float th = layout.height + 14;
        float tx = Math.min(x, V_WIDTH - tw - 12);
        float ty = Math.min(y, V_HEIGHT - th - 12);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.06f, 0.08f, 0.14f, 0.94f);
        shapeRenderer.rect(tx, ty, tw, th);
        shapeRenderer.setColor(0.85f, 0.72f, 0.38f, 0.9f);
        shapeRenderer.rect(tx, ty, tw, 1);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        fonts.getSmallFont().setColor(Color.WHITE);
        fonts.getSmallFont().draw(batch, fullText, tx + 12, ty + th - 4);
        batch.end();
    }

    private void renderActionTooltipCustom(String title, String action, float x, float y) {
        String fullText = title + " (" + action + ")";
        layout.setText(fonts.getSmallFont(), fullText);
        float tw = layout.width + 24;
        float th = layout.height + 14;
        float tx = Math.min(x, V_WIDTH - tw - 12);
        float ty = Math.min(y, V_HEIGHT - th - 12);

        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0.06f, 0.08f, 0.14f, 0.94f);
        shapeRenderer.rect(tx, ty, tw, th);
        shapeRenderer.setColor(0.85f, 0.72f, 0.38f, 0.9f);
        shapeRenderer.rect(tx, ty, tw, 1);
        shapeRenderer.end();
        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        fonts.getSmallFont().setColor(Color.WHITE);
        fonts.getSmallFont().draw(batch, fullText, tx + 12, ty + th - 4);
        batch.end();
    }

    private void renderCursor() {
        InventoryItem active = game.getGameState().getActiveItem();
        if (active != null) {
            Texture icon = getTexture(active.getIconPath());
            if (icon != null) {
                batch.begin();
                batch.draw(icon, mousePos.x - 16, mousePos.y - 16, 32, 32);
                batch.end();
            }
        }
    }

    private void drawGlassButton(Rectangle r, boolean hovered) {
        if (hovered) {
            shapeRenderer.setColor(0.22f, 0.30f, 0.45f, 0.95f);
        } else {
            shapeRenderer.setColor(0.12f, 0.16f, 0.24f, 0.85f);
        }
        shapeRenderer.rect(r.x, r.y, r.width, r.height);
        shapeRenderer.setColor(0.40f, 0.52f, 0.70f, 0.75f);
        shapeRenderer.rect(r.x, r.y, r.width, 1);
    }

    private void drawCenteredText(BitmapFont font, String text, Rectangle r) {
        layout.setText(font, text);
        float tx = r.x + (r.width - layout.width) / 2f;
        float ty = r.y + (r.height + layout.height) / 2f;
        font.draw(batch, text, tx, ty);
    }

    public void startDialogue(String nodeId) {
        DialogueNode node = game.getDialogueRegistry().getNode(nodeId);
        if (node == null) return;

        activeDialogue = node;
        typewriterTimer = 0f;
        visibleChars = 0;
        typewriterFinished = false;

        if (node.getVoiceAudioPath() != null) {
            game.getAudioManager().playVoice(node.getVoiceAudioPath());
        }

        game.getGameState().logDialogue(node.getSpeakerName(), node.getText());

        if ("TORVALD_GIVE_KEY".equals(node.getTriggerAction())) {
            if (!game.getGameState().hasItem("brass_key")) {
                game.getGameState().addItem(new InventoryItem(
                    "brass_key",
                    "Wrought Brass Key (Raven Sigil)",
                    "A heavy, ornate brass key provided by Old Torvald. Fits the lock on the wrought iron gate.",
                    "images/items/brass_key.png"
                ));
                game.getAudioManager().playSfx("pickup");
                game.getGameState().addJournalNote("Received Wrought Brass Key with Raven Sigil from Old Torvald.");
            }
        } else if ("UNIT7_GIVE_FORK".equals(node.getTriggerAction())) {
            if (!game.getGameState().hasItem("tuning_fork")) {
                game.getGameState().addItem(new InventoryItem(
                    "tuning_fork",
                    "Resonance Tuning Fork (432 Hz)",
                    "A precision silver-crystal tuning fork from UNIT-7. Calibrated to sympathetic bedrock resonance.",
                    "images/items/tuning_fork.png"
                ));
                game.getAudioManager().playSfx("crystal");
                game.getGameState().addJournalNote("Received Resonance Tuning Fork (432 Hz) from UNIT-7.");
            }
        } else if ("UNLOCK_CORE_ACCESS".equals(node.getTriggerAction())) {
            game.getGameState().setFlag("CORE_ACCESS_UNLOCKED");
            game.getGameState().addJournalNote("Professor Ravn unlocked the ladder to Chapter 10: The Keeper's Core.");
        } else if ("ENDING_A".equals(node.getTriggerAction())) {
            game.getGameState().setFlag("ENDING_A_TRIGGERED");
            game.getGameState().addJournalNote("Ending A: Harmonic Integration selected. The spire and island rest in permanent equilibrium.");
        } else if ("ENDING_B".equals(node.getTriggerAction())) {
            game.getGameState().setFlag("ENDING_B_TRIGGERED");
            game.getGameState().addJournalNote("Ending B: Controlled Damping selected. The spire is safely deactivated forever.");
        } else if ("ENDING_C".equals(node.getTriggerAction())) {
            game.getGameState().setFlag("ENDING_C_TRIGGERED");
            game.getGameState().addJournalNote("Ending C: Cosmic Transmission selected. The sapphire beam shines upward into the stars.");
        }
    }

    private class GameInputHandler extends InputAdapter {
        @Override
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            mousePos.set(screenX, screenY);
            viewport.unproject(mousePos);

            if (button == Input.Buttons.RIGHT) {
                if (hoveredInventoryItem != null) {
                    inspectingItem = hoveredInventoryItem;
                    game.getAudioManager().playSfx("click");
                    if (hoveredInventoryItem.getVoiceAudioPath() != null) {
                        game.getAudioManager().playVoice(hoveredInventoryItem.getVoiceAudioPath());
                    }
                    return true;
                }
                if (game.getGameState().getActiveItem() != null) {
                    game.getGameState().setActiveItem(null);
                    return true;
                }
            }

            if (button != Input.Buttons.LEFT) return false;

            if (inspectingItem != null) {
                inspectingItem = null;
                game.getAudioManager().stopVoice();
                game.getAudioManager().playSfx("click");
                return true;
            }
            if (showBacklog) {
                showBacklog = false;
                game.getAudioManager().playSfx("click");
                return true;
            }
            if (showJournal) {
                showJournal = false;
                game.getAudioManager().playSfx("click");
                return true;
            }

            if (pingBtnRect.contains(mousePos)) {
                pingTimer = PING_DURATION;
                game.getAudioManager().playSfx("crystal");
                return true;
            }
            if (backlogBtnRect.contains(mousePos)) {
                showBacklog = true;
                game.getAudioManager().playSfx("click");
                return true;
            }
            if (journalBtnRect.contains(mousePos)) {
                showJournal = true;
                game.getAudioManager().playSfx("click");
                return true;
            }
            if (soundBtnRect.contains(mousePos)) {
                game.getAudioManager().toggleSound();
                game.getAudioManager().playSfx("click");
                return true;
            }

            if (activeDialogue != null) {
                if (!typewriterFinished) {
                    visibleChars = activeDialogue.getText().length();
                    typewriterFinished = true;
                    return true;
                }

                List<DialogueChoice> choices = activeDialogue.getAvailableChoices(game.getGameState());
                if (hoveredChoiceIndex >= 0 && hoveredChoiceIndex < choices.size()) {
                    DialogueChoice choice = choices.get(hoveredChoiceIndex);
                    game.getAudioManager().playSfx("click");
                    if (choice.getSetFlag() != null) {
                        game.getGameState().setFlag(choice.getSetFlag());
                    }
                    if ("dialogue_exit".equals(choice.getTargetNodeId())) {
                        activeDialogue = null;
                        game.getAudioManager().stopVoice();
                    } else {
                        startDialogue(choice.getTargetNodeId());
                    }
                    return true;
                } else if (choices.isEmpty()) {
                    activeDialogue = null;
                    game.getAudioManager().stopVoice();
                    game.getAudioManager().playSfx("click");
                    return true;
                }
                return true;
            }

            if (hoveredInventoryItem != null) {
                game.getAudioManager().playSfx("click");
                if (game.getGameState().getActiveItem() == hoveredInventoryItem) {
                    game.getGameState().setActiveItem(null);
                } else {
                    game.getGameState().setActiveItem(hoveredInventoryItem);
                }
                return true;
            }

            if (hoveredHotspot != null) {
                handleHotspotClick(hoveredHotspot);
                return true;
            }

            if (game.getGameState().getActiveItem() != null) {
                game.getGameState().setActiveItem(null);
            }

            return false;
        }

        @Override
        public boolean keyDown(int keycode) {
            if (keycode == Input.Keys.SPACE || keycode == Input.Keys.TAB) {
                pingTimer = PING_DURATION;
                game.getAudioManager().playSfx("crystal");
                return true;
            }
            if (keycode == Input.Keys.ESCAPE) {
                if (inspectingItem != null) inspectingItem = null;
                else if (showBacklog) showBacklog = false;
                else if (showJournal) showJournal = false;
                else if (activeDialogue != null) {
                    activeDialogue = null;
                    game.getAudioManager().stopVoice();
                }
                return true;
            }
            return false;
        }

        private void handleHotspotClick(Hotspot h) {
            InventoryItem active = game.getGameState().getActiveItem();

            // 1. Direct Item Reactions on Characters & Props
            if (active != null) {
                // Torvald reactions
                if ("hotspot_torvald".equals(h.getId())) {
                    if ("telegram".equals(active.getId())) {
                        startDialogue("torvald_telegram_topic");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("lantern".equals(active.getId())) {
                        startDialogue("torvald_unlock_reward");
                        game.getGameState().setActiveItem(null);
                        return;
                    }
                }
                // UNIT-7 reactions
                if ("hotspot_workshop_unit7".equals(h.getId())) {
                    if ("punch_card".equals(active.getId())) {
                        game.getAudioManager().playSfx("crystal");
                        game.getGameState().setFlag("UNIT7_AWAKENED");
                        game.getGameState().addJournalNote("Inserted Protocol A-1 punch card into UNIT-7. Automaton initialized.");
                        startDialogue("unit7_card_awaken");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("telegram".equals(active.getId())) {
                        startDialogue("unit7_telegram_scan");
                        game.getGameState().setActiveItem(null);
                        return;
                    }
                }
                // Astrid reactions
                if ("hotspot_observatory_astrid".equals(h.getId())) {
                    if ("crystal_prism".equals(active.getId())) {
                        startDialogue("astrid_prism_inspect");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("telegram".equals(active.getId())) {
                        startDialogue("astrid_telegram_recall");
                        game.getGameState().setActiveItem(null);
                        return;
                    }
                }
            }

            // 2. Specific Item Requirements to Unlock Mechanisms
            if (h.getRequiredItemId() != null) {
                if (active != null && active.getId().equals(h.getRequiredItemId())) {
                    // Match found!
                    if ("hotspot_pier_gate".equals(h.getId())) {
                        game.getAudioManager().playSfx("door");
                        game.getGameState().setFlag("GATE_UNLOCKED");
                        game.getGameState().addJournalNote("Unlocked the wrought iron gate with Torvald's raven key.");
                        h.setRequiredItemId(null);
                        pendingSceneId = h.getTargetSceneId();
                        startInspectionDialogue("Eivind Holst", "The heavy brass key turns smoothly in the antique padlock. The iron gate swings open, granting access to the keeper's cottage and inland trails.", "audio/voices/inspect_gate_unlocked.mp3");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("hotspot_workshop_valve".equals(h.getId())) {
                        game.getAudioManager().playSfx("door");
                        game.getGameState().setFlag("STEAM_VALVE_OPEN");
                        game.getGameState().addJournalNote("Used forged wrench to vent high-pressure steam manifold. Hydraulic power restored to UNIT-7.");
                        h.setRequiredItemId(null);
                        startInspectionDialogue("Eivind Holst", "With a sharp pull on the steel wrench, the valve turns! Excess steam vents with a deafening hiss, routing hydraulic power to the automaton's circuits.", "audio/voices/inspect_workshop_valve_unlocked.mp3");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("hotspot_staircase_dynamo".equals(h.getId())) {
                        game.getAudioManager().playSfx("door");
                        game.getGameState().setFlag("DYNAMO_RESTORED");
                        game.getGameState().addJournalNote("Used forged wrench to loosen seized dynamo bracket. Lift and electrical circuits energized.");
                        h.setRequiredItemId(null);
                        startInspectionDialogue("Eivind Holst", "Using the forged wrench, I unbolt the seized bracket. The drive shaft spins freely with sapphire light, restoring electrical power to the lift cage below!", "audio/voices/inspect_staircase_dynamo_unlocked.mp3");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("hotspot_caverns_pedestal".equals(h.getId())) {
                        game.getAudioManager().playSfx("crystal");
                        game.getGameState().setFlag("CAVERNS_HARMONIZED");
                        game.getGameState().addJournalNote("Struck 432 Hz tuning fork against piezoelectric altar. Sympathetic resonance established; direct express hoist to observatory powered.");
                        h.setRequiredItemId(null);
                        startInspectionDialogue("Eivind Holst", "I strike the 432 Hz tuning fork against the basalt altar and place it into the crystal socket. A pure, resonant hum reverberates through the caverns, calming the tremors and powering the direct express hoist to the summit!", "audio/voices/inspect_caverns_pedestal_unlocked.mp3");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else if ("hotspot_core_crystal".equals(h.getId())) {
                        game.getAudioManager().playSfx("crystal");
                        game.getGameState().setFlag("CORE_PRISM_INSTALLED");
                        game.getGameState().addJournalNote("Installed The Great Azure Focus Prism into the revolving Fresnel turret. Beacon stabilized; Master Control Levers unlocked.");
                        h.setRequiredItemId(null);
                        startInspectionDialogue("Eivind Holst", "I carefully seat The Great Azure Focus Prism into the central brass turret. The chaotic flares instantly align into a breathtaking, coherent column of azure light! The Master Control Levers on the dais are now unlocked.", "audio/voices/inspect_core_crystal_unlocked.mp3");
                        game.getGameState().setActiveItem(null);
                        return;
                    } else {
                        game.getAudioManager().playSfx("door");
                        h.setRequiredItemId(null);
                        if (h.getInteractionType() == InteractionType.TRANSITION) {
                            pendingSceneId = h.getTargetSceneId();
                        }
                        if (h.getDialogueNodeId() != null) {
                            startDialogue(h.getDialogueNodeId());
                        } else {
                            startInspectionDialogue("Eivind Holst", h.getInspectionText(), h.getVoiceAudioPath());
                        }
                        game.getGameState().setActiveItem(null);
                        return;
                    }
                } else if (active != null) {
                    startInspectionDialogue("Eivind Holst", "That item does not fit here. I must seek another solution.", "audio/voices/eivind_wrong_item.mp3");
                    return;
                } else {
                    // Clicked locked mechanism with empty cursor
                    if ("hotspot_pier_gate".equals(h.getId())) {
                        startInspectionDialogue("Eivind Holst", "The wrought iron gate is locked tight with a raven-crested padlock. Torvald at the pier holds the key.", "audio/voices/inspect_hotspot_pier_gate_locked.mp3");
                        return;
                    }
                    if ("hotspot_workshop_valve".equals(h.getId())) {
                        startInspectionDialogue("Eivind Holst", "The high-pressure steam manifold valve is seized tight. I need a heavy drop-forged wrench to turn it.", "audio/voices/inspect_hotspot_workshop_valve_locked.mp3");
                        return;
                    }
                    if ("hotspot_staircase_dynamo".equals(h.getId())) {
                        startInspectionDialogue("Eivind Holst", "The dynamo drive shaft is blocked by a jammed steel retaining bracket. A wrench can loosen the bolts.", "audio/voices/inspect_hotspot_staircase_dynamo_locked.mp3");
                        return;
                    }
                    if ("hotspot_caverns_pedestal".equals(h.getId())) {
                        startInspectionDialogue("Eivind Holst", "The crystal altar vibrates violently. A precision acoustic tuning fork calibrated to 432 Hz is needed to harmonize the bedrock.", "audio/voices/inspect_hotspot_caverns_pedestal_locked.mp3");
                        return;
                    }
                    if ("hotspot_core_crystal".equals(h.getId())) {
                        startInspectionDialogue("Eivind Holst", "The central Fresnel optical socket is vacant. The Great Azure Focus Prism must be installed here to focus the beam.", "audio/voices/inspect_hotspot_core_crystal_locked.mp3");
                        return;
                    }

                    String lockedVoice = "audio/voices/inspect_" + h.getId() + "_locked.mp3";
                    startInspectionDialogue("Eivind Holst", h.getInspectionText(), lockedVoice);
                    return;
                }
            }

            // 3. Special check for Master Control Levers in Scene 10
            if ("hotspot_core_levers".equals(h.getId())) {
                if (!game.getGameState().hasFlag("CORE_PRISM_INSTALLED")) {
                    startInspectionDialogue("Eivind Holst", "The master control levers are locked behind a safety interlock. The Great Azure Focus Prism must be installed in the central turret first!", "audio/voices/inspect_core_levers_locked.mp3");
                    return;
                } else {
                    startDialogue("core_levers_dialogue");
                    return;
                }
            }

            // 4. Default interaction types
            switch (h.getInteractionType()) {
                case PICKUP:
                    game.getAudioManager().playSfx("pickup");
                    if (h.getGivenItemId() != null) {
                        InventoryItem item = createItemFromId(h.getGivenItemId(), h.getName(), h.getInspectionText());
                        game.getGameState().addItem(item);
                        h.setActive(false);
                    }
                    startInspectionDialogue("Eivind Holst", h.getInspectionText(), h.getVoiceAudioPath());
                    break;

                case TALK:
                    game.getAudioManager().playSfx("click");
                    if (h.getDialogueNodeId() != null) {
                        startDialogue(h.getDialogueNodeId());
                    }
                    break;

                case TRANSITION:
                    game.getAudioManager().playSfx("door");
                    if (h.getTargetSceneId() != null) {
                        pendingSceneId = h.getTargetSceneId();
                    }
                    if (h.getInspectionText() != null) {
                        startInspectionDialogue("Eivind Holst", h.getInspectionText(), h.getVoiceAudioPath());
                    }
                    break;

                case INSPECT:
                case USE_ITEM:
                default:
                    game.getAudioManager().playSfx("click");
                    if (h.getDialogueNodeId() != null) {
                        startDialogue(h.getDialogueNodeId());
                    } else if (h.getInspectionText() != null) {
                        startInspectionDialogue("Eivind Holst", h.getInspectionText(), h.getVoiceAudioPath());
                    }
                    break;
            }
        }

        private void startInspectionDialogue(String speaker, String text, String voicePath) {
            DialogueNode node = new DialogueNode("inspect_node", speaker, "images/portraits/eivind.png", text, voicePath);
            activeDialogue = node;
            typewriterTimer = 0f;
            visibleChars = 0;
            typewriterFinished = false;
            if (voicePath != null) {
                game.getAudioManager().playVoice(voicePath);
            }
        }

        private InventoryItem createItemFromId(String id, String name, String desc) {
            String iconPath = "images/items/" + id + ".png";
            return new InventoryItem(id, name, desc, iconPath);
        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
    }

    @Override
    public void pause() {}
    @Override
    public void resume() {}
    @Override
    public void hide() {}

    @Override
    public void dispose() {
        for (Texture t : textureCache.values()) {
            t.dispose();
        }
        textureCache.clear();
    }
}
