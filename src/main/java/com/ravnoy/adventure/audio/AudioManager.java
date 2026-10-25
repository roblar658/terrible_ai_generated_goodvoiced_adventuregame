package com.ravnoy.adventure.audio;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.HashMap;
import java.util.Map;

/**
 * Handles playback of atmospheric music, procedural sound effects, and neural voice lines.
 */
public class AudioManager {
    private Music ambientMusic;
    private Music currentVoice;
    private Map<String, Sound> soundCache = new HashMap<>();
    private boolean soundEnabled = true;
    private float masterVolume = 0.85f;
    private float voiceVolume = 1.0f;

    public void playAmbient(String path) {
        if (!soundEnabled) return;
        try {
            if (ambientMusic != null) {
                ambientMusic.stop();
                ambientMusic.dispose();
                ambientMusic = null;
            }
            if (path != null && Gdx.files.internal(path).exists()) {
                ambientMusic = Gdx.audio.newMusic(Gdx.files.internal(path));
                ambientMusic.setLooping(true);
                ambientMusic.setVolume(0.40f * masterVolume);
                ambientMusic.play();
            }
        } catch (Exception e) {
            Gdx.app.log("AudioManager", "Could not play ambient music: " + path, e);
        }
    }

    public void playSfx(String name) {
        if (!soundEnabled) return;
        try {
            Sound sound = soundCache.get(name);
            if (sound == null) {
                String path = "audio/sfx/" + name + ".wav";
                if (Gdx.files.internal(path).exists()) {
                    sound = Gdx.audio.newSound(Gdx.files.internal(path));
                    soundCache.put(name, sound);
                }
            }
            if (sound != null) {
                sound.play(0.75f * masterVolume);
            }
        } catch (Exception e) {
            Gdx.app.log("AudioManager", "Could not play SFX: " + name, e);
        }
    }

    public void playVoice(String path) {
        stopVoice();
        if (!soundEnabled || path == null) return;
        try {
            // Check direct path first (e.g. .mp3 or .wav)
            if (Gdx.files.internal(path).exists()) {
                currentVoice = Gdx.audio.newMusic(Gdx.files.internal(path));
                currentVoice.setVolume(voiceVolume * masterVolume);
                currentVoice.play();
                return;
            }
            // Fallback: Check if .mp3 exists when .wav was passed
            if (path.endsWith(".wav")) {
                String mp3Path = path.substring(0, path.length() - 4) + ".mp3";
                if (Gdx.files.internal(mp3Path).exists()) {
                    currentVoice = Gdx.audio.newMusic(Gdx.files.internal(mp3Path));
                    currentVoice.setVolume(voiceVolume * masterVolume);
                    currentVoice.play();
                    return;
                }
            }
            // Fallback: Check if .wav exists when .mp3 was passed
            if (path.endsWith(".mp3")) {
                String wavPath = path.substring(0, path.length() - 4) + ".wav";
                if (Gdx.files.internal(wavPath).exists()) {
                    currentVoice = Gdx.audio.newMusic(Gdx.files.internal(wavPath));
                    currentVoice.setVolume(voiceVolume * masterVolume);
                    currentVoice.play();
                    return;
                }
            }
        } catch (Exception e) {
            Gdx.app.log("AudioManager", "Could not play voice line: " + path, e);
        }
    }

    public void stopVoice() {
        if (currentVoice != null) {
            try {
                currentVoice.stop();
                currentVoice.dispose();
            } catch (Exception ignored) {}
            currentVoice = null;
        }
    }

    public boolean isSoundEnabled() { return soundEnabled; }
    public void toggleSound() {
        soundEnabled = !soundEnabled;
        if (!soundEnabled) {
            if (ambientMusic != null) ambientMusic.pause();
            stopVoice();
        } else {
            if (ambientMusic != null) ambientMusic.play();
        }
    }

    public void dispose() {
        if (ambientMusic != null) {
            ambientMusic.stop();
            ambientMusic.dispose();
        }
        stopVoice();
        for (Sound s : soundCache.values()) {
            s.dispose();
        }
        soundCache.clear();
    }
}
