package com.ravnoy.adventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a node in the branching dialogue tree with speaker, text, portrait, and choices.
 */
public class DialogueNode {
    private String id;
    private String speakerName;
    private String portraitPath;
    private String text;
    private String voiceAudioPath;
    private List<DialogueChoice> choices = new ArrayList<>();
    private String triggerAction; // e.g. TORVALD_GIVE_KEY, UNIT7_GIVE_FORK, UNLOCK_CORE_ACCESS

    public DialogueNode(String id, String speakerName, String portraitPath, String text, String voiceAudioPath) {
        this.id = id;
        this.speakerName = speakerName;
        this.portraitPath = portraitPath;
        this.text = text;
        this.voiceAudioPath = voiceAudioPath;
    }

    public String getId() { return id; }
    public String getSpeakerName() { return speakerName; }
    public String getPortraitPath() { return portraitPath; }
    public String getText() { return text; }
    public String getVoiceAudioPath() { return voiceAudioPath; }
    public List<DialogueChoice> getChoices() { return choices; }

    public List<DialogueChoice> getAvailableChoices(GameState state) {
        if (state == null) return choices;
        List<DialogueChoice> available = new ArrayList<>();
        for (DialogueChoice c : choices) {
            if (c.getRequiredItemId() != null && !state.hasItem(c.getRequiredItemId())) {
                continue;
            }
            if (c.getRequiredFlag() != null && !state.hasFlag(c.getRequiredFlag())) {
                continue;
            }
            available.add(c);
        }
        return available;
    }

    public DialogueNode addChoice(DialogueChoice choice) {
        choices.add(choice);
        return this;
    }

    public String getTriggerAction() { return triggerAction; }
    public DialogueNode setTriggerAction(String action) {
        this.triggerAction = action;
        return this;
    }
}
