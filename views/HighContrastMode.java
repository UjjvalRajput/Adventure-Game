package views;

import javafx.application.Platform;

public class HighContrastMode implements DisplayMode {

    AdventureGameView game;

    public HighContrastMode(AdventureGameView game) { this.game = game; }

    @Override
    public void toggle() {
        // Set the new colours
        game.setColourScheme(
                game.highContrastStatus ? "#000000" : "#FFFFFF", // background
                game.highContrastStatus ? "#FFFFFF" : "#000000", // text
                game.highContrastStatus ? "#17871b" : "#FDBB2D"  // buttons
        );
        game.updateColourScheme(); // A method to update all relevant components' colour
        game.highContrastStatus = !game.highContrastStatus;
    }
}
