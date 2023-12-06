package views;

/**
 * Enum representing the different actions that can occur in the game.
 * Each action will have a corresponding sound effect that will be played when the action occurs.
 *
 * WALK - Represents the action of the player walking.
 * PICKUP_ITEM - Represents the action of the player picking up an item.
 * DEATH - Represents the action of the player dying.
 * VICTORY - Represents the action of the player achieving victory.
 * DROP_ITEM - Represents the action of the player dropping an item.
 */
public enum GameAction {
    WALK,
    PICKUP_ITEM,
    DEATH,
    VICTORY,
    DROP_ITEM
}
