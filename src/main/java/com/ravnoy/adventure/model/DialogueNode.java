package com.ravnoy.adventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representerer en node i dialogtreet med tekst, AI-stemmefil og svarvalg.
 */
public class DialogueNode {
    private String id;
    private String speakerName;
    private String portraitPath;
    private String text;
    private String voiceAudioPath;
    private List<DialogueChoice> choices = new ArrayList<>();
    private String triggerAction; // F.eks. "UNLOCK_GATE", "ACTIVATE_UNIT7", "END_GAME_A", osv.

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
