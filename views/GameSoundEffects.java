package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.File;

/**
 * This class implements the SoundEffect interface and provides sound effects for the game.
 * It contains MediaPlayer objects for each sound effect and methods to control these sounds.
 *
 * walkSound - A MediaPlayer object representing the sound effect for walking.
 * pickupItemSound - A MediaPlayer object representing the sound effect for picking up an item.
 * deathSound - A MediaPlayer object representing the sound effect for player death.
 * victorySound - A MediaPlayer object representing the sound effect for player victory.
 * dropItemSound - A MediaPlayer object representing the sound effect for dropping an item.
 *
 * GameSoundEffects() - Constructor that initializes the MediaPlayers with the sound files.
 *
 * play(MediaPlayer sound) - Plays the specified sound effect.
 * stop(MediaPlayer sound) - Stops the specified sound effect.
 * adjustVolume(MediaPlayer sound, double volume) - Adjusts the volume of the specified sound effect.
 * mute(MediaPlayer sound) - Mutes the specified sound effect.
 *
 * playWalkSound() - Plays the sound effect for walking.
 * playPickupItemSound() - Plays the sound effect for picking up an item.
 * playDeathSound() - Plays the sound effect for player death.
 * playVictorySound() - Plays the sound effect for player victory.
 * playDropItemSound() - Plays the sound effect for dropping an item.
 */
class GameSoundEffects implements SoundEffect {
    MediaPlayer walkSound;
    MediaPlayer pickupItemSound;
    MediaPlayer deathSound;
    MediaPlayer victorySound;

    MediaPlayer dropItemSound;

    public GameSoundEffects() {
        // Initialize the MediaPlayers with your sound files
        File file1 = new File("./audios/walkingsound.mp3");
        File file2 = new File("./audios/Pickitem-SoundEffect(from game Minecraft).mp3/");
        File file3 = new File("./audios/SadTrumpetMeme.mp3");
        File file4 = new File("./audios/Victory-SoundEffect.mp3");
        File file5 = new File("./audios/MinecraftItemDropSound.mp3/");
        walkSound = new MediaPlayer(new Media(file1.toURI().toString()));
        pickupItemSound = new MediaPlayer(new Media(file2.toURI().toString()));
        deathSound = new MediaPlayer(new Media(file3.toURI().toString()));
        victorySound = new MediaPlayer(new Media(file4.toURI().toString()));
        dropItemSound = new MediaPlayer(new Media(file5.toURI().toString()));
    }

    @Override
    public void play(MediaPlayer sound) {
        if(sound != null) {
            sound.stop();
            sound.play();
        }
    }
    @Override
    public void stop(MediaPlayer sound) {
        if(sound != null) {
            sound.stop();
        }
    }
    @Override
    public void adjustVolume(MediaPlayer sound, double volume) {
        if(sound != null) {
            sound.setVolume(volume);
        }
    }
    @Override
    public void mute(MediaPlayer sound) {
        if(sound != null) {
            sound.setMute(true);
        }
    }

    public void playWalkSound() {
        play(walkSound);
    }

    public void playPickupItemSound() {
        play(pickupItemSound);
    }
    public void playDeathSound() {
        play(deathSound);
    }

    public void playVictorySound() {
        play(victorySound);
    }

    public void playDropItemSound() {
        play(dropItemSound);
    }

    // ... other methods for other sound effects ...
}

