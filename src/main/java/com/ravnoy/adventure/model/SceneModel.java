package com.ravnoy.adventure.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Representerer en spillscene (et kapittel / bilde i reisen).
 */
public class SceneModel {
    private String id;
    private int chapterNumber;
    private String title;
    private String subtitle;
    private String backgroundPath;
    private List<Hotspot> hotspots = new ArrayList<>();
    private String ambientMusicPath;

    public SceneModel(String id, int chapterNumber, String title, String subtitle, String backgroundPath) {
        this.id = id;
        this.chapterNumber = chapterNumber;
        this.title = title;
        this.subtitle = subtitle;
        this.backgroundPath = backgroundPath;
    }

    public String getId() { return id; }
    public int getChapterNumber() { return chapterNumber; }
    public String getTitle() { return title; }
    public String getSubtitle() { return subtitle; }
    public String getBackgroundPath() { return backgroundPath; }
    public List<Hotspot> getHotspots() { return hotspots; }

    public SceneModel addHotspot(Hotspot hotspot) {
        hotspots.add(hotspot);
        return this;
    }

    public String getAmbientMusicPath() { return ambientMusicPath; }
    public SceneModel setAmbientMusicPath(String path) {
        this.ambientMusicPath = path;
        return this;
    }
}
