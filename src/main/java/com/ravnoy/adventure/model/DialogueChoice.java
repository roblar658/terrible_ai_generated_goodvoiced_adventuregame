package com.ravnoy.adventure.model;

/**
 * Representerer et svaralternativ i en dialog med forgreninger.
 */
public class DialogueChoice {
    private String text;
    private String targetNodeId;
    private String requiredFlag;
    private String requiredItemId;
    private String setFlag;
    private String giveItemId;

    public DialogueChoice(String text, String targetNodeId) {
        this.text = text;
        this.targetNodeId = targetNodeId;
    }

    public String getText() { return text; }
    public String getTargetNodeId() { return targetNodeId; }

    public String getRequiredFlag() { return requiredFlag; }
    public DialogueChoice setRequiredFlag(String requiredFlag) {
        this.requiredFlag = requiredFlag;
        return this;
    }

    public String getRequiredItemId() { return requiredItemId; }
    public DialogueChoice setRequiredItemId(String requiredItemId) {
        this.requiredItemId = requiredItemId;
        return this;
    }

    public String getSetFlag() { return setFlag; }
    public DialogueChoice setSetFlag(String setFlag) {
        this.setFlag = setFlag;
        return this;
    }

    public String getGiveItemId() { return giveItemId; }
    public DialogueChoice setGiveItemId(String giveItemId) {
        this.giveItemId = giveItemId;
        return this;
    }
}
