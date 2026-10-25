package com.ravnoy.adventure.model;

import java.util.*;

/**
 * Global game state holding inventory, quest flags, journal, and dialogue history.
 */
public class GameState {
    private String currentSceneId = "scene_01_pier";
    private Map<String, InventoryItem> inventory = new LinkedHashMap<>();
    private Set<String> flags = new HashSet<>();
    private List<DialogueLogEntry> dialogueHistory = new ArrayList<>();
    private List<String> journalNotes = new ArrayList<>();
    private InventoryItem activeItem = null;

    public GameState() {
        // Initial inventory items
        addItem(new InventoryItem(
            "telegram",
            "Astrid's Urgent Telegram",
            "A crumpled telegram: 'Eivind. Come to Raven Island at once. The resonance has exceeded mathematical thresholds. Do not let the spire fall into silence. — A.R.'",
            "images/items/telegram.png"
        ));
        addJournalNote("Arrived at Raven Island in heavy fog. Must find a way past the locked iron gate and reach the lighthouse where Astrid is working.");
    }

    public String getCurrentSceneId() { return currentSceneId; }
    public void setCurrentSceneId(String currentSceneId) { this.currentSceneId = currentSceneId; }

    public Collection<InventoryItem> getInventory() { return inventory.values(); }

    public void addItem(InventoryItem item) {
        if (item != null) {
            inventory.put(item.getId(), item);
        }
    }

    public void removeItem(String itemId) {
        inventory.remove(itemId);
        if (activeItem != null && activeItem.getId().equals(itemId)) {
            activeItem = null;
        }
    }

    public boolean hasItem(String itemId) {
        return inventory.containsKey(itemId);
    }

    public InventoryItem getItem(String itemId) {
        return inventory.get(itemId);
    }

    public InventoryItem getActiveItem() { return activeItem; }
    public void setActiveItem(InventoryItem activeItem) { this.activeItem = activeItem; }

    public boolean hasFlag(String flag) { return flags.contains(flag); }
    public void setFlag(String flag) { flags.add(flag); }

    public void logDialogue(String speaker, String text) {
        dialogueHistory.add(new DialogueLogEntry(speaker, text));
    }

    public List<DialogueLogEntry> getDialogueHistory() { return dialogueHistory; }

    public void addJournalNote(String note) {
        if (!journalNotes.contains(note)) {
            journalNotes.add(note);
        }
    }

    public List<String> getJournalNotes() { return journalNotes; }
}
