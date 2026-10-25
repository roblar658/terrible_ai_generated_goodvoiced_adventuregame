package com.ravnoy.adventure.model;

/**
 * En loggføring av tidligere talt dialog for samtalehistorikk / backlog.
 */
public class DialogueLogEntry {
    private String speaker;
    private String text;

    public DialogueLogEntry(String speaker, String text) {
        this.speaker = speaker;
        this.text = text;
    }

    public String getSpeaker() { return speaker; }
    public String getText() { return text; }
}
