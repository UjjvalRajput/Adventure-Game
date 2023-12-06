package views;

import javafx.application.Platform;

public class MagnifiedMode implements DisplayMode {

    AdventureGameView game;
    public MagnifiedMode(AdventureGameView game) {
        this.game = game;
    }

    @Override
    public void toggle() {
        // Update the font size
        game.setFontSize(game.magnifiedStatus ? 16 : 20);
        game.updateFontSize(); // A method to update all relevant components' font size
        game.magnifiedStatus = !game.magnifiedStatus;
    }
}
