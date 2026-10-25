package com.ravnoy.adventure.model;

/**
 * Representerer en gjenstand i spillerens inventar.
 */
public class InventoryItem {
    private String id;
    private String name;
    private String description;
    private String iconPath;
    private String voiceAudioPath;

    public InventoryItem(String id, String name, String description, String iconPath) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.iconPath = iconPath;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getIconPath() { return iconPath; }

    public String getVoiceAudioPath() {
        if (voiceAudioPath != null) return voiceAudioPath;
        return "audio/voices/item_" + id + ".mp3";
    }

    public InventoryItem setVoiceAudioPath(String voiceAudioPath) {
        this.voiceAudioPath = voiceAudioPath;
        return this;
    }
}
