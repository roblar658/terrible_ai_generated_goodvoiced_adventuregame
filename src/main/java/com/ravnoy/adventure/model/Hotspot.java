package com.ravnoy.adventure.model;

import com.badlogic.gdx.math.Rectangle;

/**
 * Representerer et interaktivt punkt i en scene.
 * Kan ha et synlig overleggsbilde (overlay) som tegnes oppå bakgrunnen,
 * med animert hover-glød og dynamisk muspeker.
 */
public class Hotspot {
    private String id;
    private String name;
    private Rectangle bounds;
    private String overlayTexturePath; // Kan være null hvis kun usynlig klikkområde
    private InteractionType interactionType;
    private String inspectionText;
    private String targetSceneId;      // For TRANSITION
    private String requiredItemId;    // Kreves for USE_ITEM eller låst tilstand
    private String dialogueNodeId;     // For TALK
    private String givenItemId;        // For PICKUP
    private String requiredFlag;       // Valgfritt: aktiveres kun hvis dette flagget er satt
    private String voiceAudioPath;     // Spoken voice line audio for this hotspot
    private boolean active = true;

    public String getVoiceAudioPath() {
        if (voiceAudioPath != null) return voiceAudioPath;
        return "audio/voices/inspect_" + id + ".mp3";
    }

    public Hotspot setVoiceAudioPath(String voiceAudioPath) {
        this.voiceAudioPath = voiceAudioPath;
        return this;
    }

    public Hotspot(String id, String name, float x, float y, float width, float height, InteractionType interactionType) {
        this.id = id;
        this.name = name;
        this.bounds = new Rectangle(x, y, width, height);
        this.interactionType = interactionType;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Rectangle getBounds() { return bounds; }
    public InteractionType getInteractionType() { return interactionType; }

    public String getOverlayTexturePath() { return overlayTexturePath; }
    public Hotspot setOverlayTexturePath(String path) {
        this.overlayTexturePath = path;
        return this;
    }

    public String getInspectionText() { return inspectionText; }
    public Hotspot setInspectionText(String text) {
        this.inspectionText = text;
        return this;
    }

    public String getTargetSceneId() { return targetSceneId; }
    public Hotspot setTargetSceneId(String targetSceneId) {
        this.targetSceneId = targetSceneId;
        return this;
    }

    public String getRequiredItemId() { return requiredItemId; }
    public Hotspot setRequiredItemId(String requiredItemId) {
        this.requiredItemId = requiredItemId;
        return this;
    }

    public String getDialogueNodeId() { return dialogueNodeId; }
    public Hotspot setDialogueNodeId(String dialogueNodeId) {
        this.dialogueNodeId = dialogueNodeId;
        return this;
    }

    public String getGivenItemId() { return givenItemId; }
    public Hotspot setGivenItemId(String givenItemId) {
        this.givenItemId = givenItemId;
        return this;
    }

    public String getRequiredFlag() { return requiredFlag; }
    public Hotspot setRequiredFlag(String requiredFlag) {
        this.requiredFlag = requiredFlag;
        return this;
    }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
