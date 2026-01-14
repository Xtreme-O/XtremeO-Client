package com.mycompany.xtremeo.client.service.audio;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.mycompany.xtremeo.client.util.AudioFiles;


public class AudioService {
    
    private static AudioService instance;
    
    private MediaPlayer backgroundMusicPlayer;
    private String currentBackgroundMusicPath;
    
    private final Map<String, MediaPlayer> soundEffectsCache = new HashMap<>();
    
    private boolean backgroundMusicEnabled = true;
    private boolean soundEffectsEnabled = true;
    
    private double backgroundMusicVolume = 0.4;
    private double soundEffectsVolume = 0.7;
    
    private AudioService() {}
    


    public static AudioService getInstance() {
        if (instance == null) {
            instance = new AudioService();
        }
        return instance;
    }
    

    public void startDefaultBackgroundMusic() {
        if (!backgroundMusicEnabled || backgroundMusicPlayer != null) {
            return;
        }
        playBackgroundMusic(AudioFiles.BACKGROUND_MUSIC);
    }
    
    public void playBackgroundMusic(String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty()) {
            return;
        }
        
        stopBackgroundMusic();
        
        if (!backgroundMusicEnabled) {
            currentBackgroundMusicPath = resourcePath;
            return;
        }
        
        try {
            URL resourceUrl = getClass().getResource(resourcePath);
            if (resourceUrl == null) {
                System.err.println("Background music resource not found: " + resourcePath);
                return;
            }
            
            Media media = new Media(resourceUrl.toExternalForm());
            backgroundMusicPlayer = new MediaPlayer(media);
            backgroundMusicPlayer.setVolume(backgroundMusicVolume);
            backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            backgroundMusicPlayer.setOnError(() -> {
                System.err.println("Error playing background music: " + backgroundMusicPlayer.getError());
            });
            backgroundMusicPlayer.play();
            currentBackgroundMusicPath = resourcePath;
        } catch (Exception e) {
            System.err.println("Failed to play background music: " + resourcePath);
            e.printStackTrace();
        }
    }

    public void stopBackgroundMusic() {
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.stop();
            backgroundMusicPlayer.dispose();
            backgroundMusicPlayer = null;
        }
    }
    

    public void pauseBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PLAYING) {
            backgroundMusicPlayer.pause();
        }
    }
    

    public void resumeBackgroundMusic() {
        if (backgroundMusicPlayer != null && backgroundMusicPlayer.getStatus() == MediaPlayer.Status.PAUSED) {
            backgroundMusicPlayer.play();
        } else if (backgroundMusicPlayer == null && currentBackgroundMusicPath != null && backgroundMusicEnabled) {
            playBackgroundMusic(currentBackgroundMusicPath);
        }
    }
    

    public void playSoundEffect(String resourcePath) {
        if (resourcePath == null || resourcePath.isEmpty() || !soundEffectsEnabled) {
            return;
        }
        
        try {
            MediaPlayer soundPlayer;
            
            if (soundEffectsCache.containsKey(resourcePath)) {
                soundPlayer = soundEffectsCache.get(resourcePath);
                soundPlayer.seek(Duration.ZERO);
            } else {
                URL resourceUrl = getClass().getResource(resourcePath);
                if (resourceUrl == null) {
                    System.err.println("Sound effect resource not found: " + resourcePath);
                    return;
                }
                
                Media media = new Media(resourceUrl.toExternalForm());
                soundPlayer = new MediaPlayer(media);
                soundPlayer.setVolume(soundEffectsVolume);
                soundPlayer.setOnError(() -> {
                    System.err.println("Error playing sound effect: " + soundPlayer.getError());
                });
                soundEffectsCache.put(resourcePath, soundPlayer);
            }
            
            soundPlayer.play();
        } catch (Exception e) {
            System.err.println("Failed to play sound effect: " + resourcePath);
            e.printStackTrace();
        }
    }
    
    public void setBackgroundMusicEnabled(boolean enabled) {
        this.backgroundMusicEnabled = enabled;
        
        if (enabled) {
            if (currentBackgroundMusicPath != null) {
                if (backgroundMusicPlayer == null || 
                    backgroundMusicPlayer.getStatus() == MediaPlayer.Status.STOPPED) {
                    playBackgroundMusic(currentBackgroundMusicPath);
                } else {
                    resumeBackgroundMusic();
                }
            }
        } else {
            pauseBackgroundMusic();
        }
    }
    

    public boolean isBackgroundMusicEnabled() {
        return backgroundMusicEnabled;
    }
    

    public void setSoundEffectsEnabled(boolean enabled) {
        this.soundEffectsEnabled = enabled;
        
        if (!enabled) {
            for (MediaPlayer player : soundEffectsCache.values()) {
                if (player.getStatus() == MediaPlayer.Status.PLAYING) {
                    player.stop();
                }
            }
        }
    }
    
    public boolean isSoundEffectsEnabled() {
        return soundEffectsEnabled;
    }
    
    public void setBackgroundMusicVolume(double volume) {
        this.backgroundMusicVolume = Math.max(0.0, Math.min(1.0, volume)); 
        
        if (backgroundMusicPlayer != null) {
            backgroundMusicPlayer.setVolume(this.backgroundMusicVolume);
        }
    }
    

    public double getBackgroundMusicVolume() {
        return backgroundMusicVolume;
    }
    

    public void setSoundEffectsVolume(double volume) {
        this.soundEffectsVolume = Math.max(0.0, Math.min(1.0, volume)); // Clamp between 0 and 1
        
        for (MediaPlayer player : soundEffectsCache.values()) {
            player.setVolume(this.soundEffectsVolume);
        }
    }
    

    public double getSoundEffectsVolume() {
        return soundEffectsVolume;
    }
    

    public void clearSoundEffectsCache() {
        for (MediaPlayer player : soundEffectsCache.values()) {
            player.dispose();
        }
        soundEffectsCache.clear();
    }
    

    public void dispose() {
        stopBackgroundMusic();
        clearSoundEffectsCache();
        currentBackgroundMusicPath = null;
    }
}

