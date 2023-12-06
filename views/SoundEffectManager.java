package views;
/**
 * This class manages the sound effects in the game.
 * It contains a GameSoundEffects object and methods to play, stop, and replay sound effects based on game actions.
 *
 * gameSoundEffects - A GameSoundEffects object that provides the sound effects.
 *
 * SoundEffectManager() - Constructor that initializes the GameSoundEffects object.
 *
 * executeActionSound(GameAction action) - Plays the sound effect corresponding to the given action.
 * undoActionSound(GameAction action) - Stops the sound effect corresponding to the given action.
 * redoActionSound(GameAction action) - Replays the sound effect corresponding to the given action.
 */
public class SoundEffectManager {
    private GameSoundEffects gameSoundEffects;

    public SoundEffectManager() {
        gameSoundEffects = new GameSoundEffects();
    }

    public void executeActionSound(GameAction action) {
        switch (action) {
            case WALK:
                gameSoundEffects.playWalkSound();
                break;
            case PICKUP_ITEM:
                gameSoundEffects.playPickupItemSound();
                break;
            case DEATH:
                gameSoundEffects.playDeathSound();
                break;
            case VICTORY:
                gameSoundEffects.playVictorySound();
                break;
            case DROP_ITEM:
                gameSoundEffects.playDropItemSound();
                break;
            // ... other cases for other actions ...
        }
    }

    public void undoActionSound(GameAction action) {
        switch (action) {
            case WALK:
                gameSoundEffects.stop(gameSoundEffects.walkSound);
                break;
            case PICKUP_ITEM:
                gameSoundEffects.stop(gameSoundEffects.pickupItemSound);
                break;
            case DEATH:
                gameSoundEffects.stop(gameSoundEffects.deathSound);
                break;
            case VICTORY:
                gameSoundEffects.stop(gameSoundEffects.victorySound);
                break;
            case DROP_ITEM:
                gameSoundEffects.stop(gameSoundEffects.dropItemSound);
                break;
            // ... other cases for other actions ...
        }
    }


    public void redoActionSound(GameAction action) {
        executeActionSound(action);
    }
}

