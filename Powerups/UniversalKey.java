package Powerups;

import AdventureModel.AdventureObject;
import AdventureModel.Room;
import views.AdventureGameView;

import java.lang.reflect.Array;
import java.util.*;

public class UniversalKey extends Powerup {
    //allows player to enter any room using a "universal key"

    public int remainingUses;

    public static String[] question_answer = {"What is the capital of Canada?", "Ottawa"};

    public static boolean gameStarted = false;
    public static boolean keyAcquired = false;

    public UniversalKey(String name, PowerupType type, String description, int strength, int remainingUses) {
        super(name, type, description, strength);
        this.remainingUses = remainingUses;
    }

    @Override
    public void activatePowerUp(PowerupUser user) {
        if (remainingUses <= 0) {
            throw new RuntimeException();
        }


        remainingUses--;

        if (remainingUses == 0) {
            //delete the universal key from the inventory
            //TODO
        }
    }

    public static void acquireKey() {
        keyAcquired = true;

        String name = "UNIVERSAL_KEY"; //NOTE: MUST BE SAME NAME AS IMAGE NAME
        String desc = "";
        Room location = AdventureGameView.instance.getModel().player.getCurrentRoom();
        AdventureObject key = new AdventureObject(name, desc, location);
        AdventureGameView.instance.getModel().player.addToInventory(key);
        AdventureGameView.instance.updateItems();
    }
}
