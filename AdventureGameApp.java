import AdventureModel.AdventureGame;
import javafx.application.Application;
import javafx.stage.Stage;
import views.AdventureGameView;
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
import javafx.scene.media.MediaPlayer;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

/**
 * Class AdventureGameApp.
 */
public class AdventureGameApp extends  Application {

    AdventureGame model;
    AdventureGameView view;

    public static void main(String[] args) {
        launch(args);
    }

    /*
    * JavaFX is a Framework, and to use it we will have to
    * respect its control flow!  To start the game, we need
    * to call "launch" which will in turn call "start" ...
     */
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Create a new dialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Game Difficulty");
        dialog.setHeaderText("Please enter the difficulty of the game: E for easy, M for medium, H for hard");

        // Keep prompting the user until they enter a valid input
        while (true) {
            // Get the user's input
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent()) {
                String difficulty = result.get().toUpperCase();

                // Check the difficulty and start the game accordingly
                switch (difficulty) {
                    case "E":
                        this.model = new AdventureGame("TinyGame");
                        break;
                    case "M":
                        this.model = new AdventureGame("MediumGame");
                        break;
                    case "H":
                        this.model = new AdventureGame("HardGame");
                        break;
                    default:
                        // Show an error message if the input is not E, M, or H
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Invalid Input");
                        alert.setContentText("Please enter E for easy, M for medium, or H for hard.");
                        alert.showAndWait();
                        continue;
                }

                this.view = new AdventureGameView(model, primaryStage);
                break;
            }
        }
    }

}
