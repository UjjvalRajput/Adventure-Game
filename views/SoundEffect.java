package views;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
 * Interface representing the sound effects that can be played in the game.
 * Each method corresponds to a different operation that can be performed on a sound effect.
 *
 * walkSound - A MediaPlayer object representing the sound effect for walking.
 * pickupItemSound - A MediaPlayer object representing the sound effect for picking up an item.
 *
 * play(MediaPlayer sound) - Plays the specified sound effect.
 * stop(MediaPlayer sound) - Stops the specified sound effect.
 * adjustVolume(MediaPlayer sound, double volume) - Adjusts the volume of the specified sound effect.
 * mute(MediaPlayer sound) - Mutes the specified sound effect.
 */
interface SoundEffect {
    public MediaPlayer walkSound = null;
    public MediaPlayer pickupItemSound = null;

    void play(MediaPlayer sound);

    void stop(MediaPlayer sound);

    void adjustVolume(MediaPlayer sound, double volume);

    void mute(MediaPlayer sound);
}