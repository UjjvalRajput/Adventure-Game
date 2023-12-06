package Shortcuts;

import AdventureModel.Player;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import views.AdventureGameView;
import views.LoadView;
import views.SaveView;

public class KeyboardShortcuts {
    public static void setUpKeyboardShortcuts(Stage stage) {
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.D) {
                    dropFirstInventoryObject();
                }
                else if (keyEvent.getCode() == KeyCode.P) {
                    takeFirstRoomObject();
                }
                else if (keyEvent.getCode() == KeyCode.I) {
                    AdventureGameView.instance.showInstructions();
                }
                else if (keyEvent.getCode() == KeyCode.S) {
                    openSaveGame();
                }
                else if (keyEvent.getCode() == KeyCode.L) {
                    openLoadGame();
                }
                else if (keyEvent.getCode() == KeyCode.R) {
                    AdventureGameView.instance.remapKeys();
                }
                else if (keyEvent.getCode() == KeyCode.M) {
                    AdventureGameView.instance.magnify.toggle();
                }
                else if (keyEvent.getCode() == KeyCode.H) {
                    AdventureGameView.instance.highContrast.toggle();
                }
            }
        });
    }

    public static void takeFirstRoomObject() {
        Player player = AdventureGameView.instance.getModel().player;

        if(!player.getCurrentRoom().objectsInRoom.isEmpty()) {
            //if there is at least 1 object in the room
            String objectName = player.getCurrentRoom().objectsInRoom.get(0).getName();
            AdventureGameView.instance.submitEvent("take " + objectName);
        }
        else {
            AdventureGameView.instance.getRoomDescLabel().setText("NO OBJECT IN ROOM");
        }

    }

    public static void dropFirstInventoryObject() {
        Player player = AdventureGameView.instance.getModel().player;

        if (!player.inventory.isEmpty()) {
            //if inventory not empty
            String objectName = player.inventory.get(0).getName();
            AdventureGameView.instance.submitEvent("drop " + objectName);
        } else {
            AdventureGameView.instance.getRoomDescLabel().setText("NO OBJECT IN INVENTORY");
        }

    }

    public static void openSaveGame() {
        AdventureGameView.instance.gridPane.requestFocus();
        SaveView saveView = new SaveView(AdventureGameView.instance);
    }

    public static void openLoadGame() {
        AdventureGameView.instance.gridPane.requestFocus();
        LoadView loadView = new LoadView(AdventureGameView.instance);
    }
}

