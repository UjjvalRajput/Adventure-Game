package views;

import AdventureModel.AdventureGame;
import AdventureModel.AdventureObject;
import AdventureModel.Player;
import AdventureModel.Room;
import Powerups.UniversalKey;
import Shortcuts.KeyboardShortcuts;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.AccessibleRole;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.MediaPlayer;
import java.io.File;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Class AdventureGameView.
 *
q * You are asked to demo your visualization via a Zoom
 * recording. Place a link to your recording below.
 *
 * ZOOM LINK: https://utoronto-my.sharepoint.com/:v:/g/personal/amro_issa_mail_utoronto_ca/EdfitiCkt4FOkgd26wKU6kIBBtkNw1HzWvTJE3TMHL7kLA?nav=eyJyZWZlcnJhbEluZm8iOnsicmVmZXJyYWxBcHAiOiJPbmVEcml2ZUZvckJ1c2luZXNzIiwicmVmZXJyYWxBcHBQbGF0Zm9ybSI6IldlYiIsInJlZmVycmFsTW9kZSI6InZpZXciLCJyZWZlcnJhbFZpZXciOiJNeUZpbGVzTGlua0RpcmVjdCJ9fQ&e=crKeLc
 * PASSWORD: [NO PASSWORD]
 */
public class AdventureGameView {
    public static AdventureGameView instance;
    AdventureGame model; //model of the game
    public Stage stage; //stage on which all is rendered
    Button saveButton, loadButton, helpButton; //buttons

    Button magnifyButton, highContrastButton;

    //instructions
    boolean instructionsPaneInitialized = false;
    private ScrollPane instructionsScrollPane = new ScrollPane();
    private Button hideInstructionsButton = new Button();

    Boolean helpToggle = false; //is help on display?

    public GridPane gridPane = new GridPane(); //to hold images and buttons
    Label roomDescLabel = new Label(); //to hold room description and/or instructions
    VBox objectsInRoom = new VBox(); //to hold room items
    VBox objectsInInventory = new VBox(); //to hold inventory items
    ImageView roomImageView; //to hold room image
    TextField inputTextField; //for user input

    private MediaPlayer mediaPlayer; //to play audio
    private boolean mediaPlaying; //to know if the audio is playing
    private SoundEffectManager soundEffectManager;

    protected boolean magnifiedStatus; //to know if magnified mode is on or off
    protected boolean highContrastStatus; // to know if high contrast mode is on or off
    protected double fontSize;
    protected String backgroundColour;
    protected String textColour;
    protected String buttonColour;
    private Map<String, String> synonyms = new HashMap<>();

    public MagnifiedMode magnify;
    public HighContrastMode highContrast;

    /**
     * Adventure Game View Constructor
     * __________________________
     * Initializes attributes
     */
    public AdventureGameView(AdventureGame model, Stage stage) {
        instance = this;
        this.model = model;
        this.stage = stage;
        this.fontSize = 16;
        this.backgroundColour = "#000000";
        this.textColour = "#FFFFFF";
        this.buttonColour = "#17871b";
        intiUI();

        this.soundEffectManager = new SoundEffectManager();

        //allowing for key presses for shortcuts
        KeyboardShortcuts.setUpKeyboardShortcuts(stage);
    }

    public void setFontSize(int size) {
        this.fontSize = size;
    }

    public void setColourScheme(String background, String text, String button) {
        this.backgroundColour = background;
        this.textColour = text;
        this.buttonColour = button;
    }

    /**
     * Initialize the UI
     */
    public void intiUI() {

        // setting up the stage
        this.stage.setTitle("Group_24's Adventure Game"); //Replace <YOUR UTORID> with your UtorID

        //Inventory + Room items
        objectsInInventory.setSpacing(10);
        objectsInInventory.setAlignment(Pos.TOP_CENTER);
        objectsInRoom.setSpacing(10);
        objectsInRoom.setAlignment(Pos.TOP_CENTER);

        // GridPane, anyone?
        gridPane.setPadding(new Insets(20));
        gridPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));

        //Three columns, three rows for the GridPane
        ColumnConstraints column1 = new ColumnConstraints(150);
        ColumnConstraints column2 = new ColumnConstraints(650);
        ColumnConstraints column3 = new ColumnConstraints(150);
        column3.setHgrow( Priority.SOMETIMES ); //let some columns grow to take any extra space
        column1.setHgrow( Priority.SOMETIMES );

        // Row constraints
        RowConstraints row1 = new RowConstraints();
        RowConstraints row2 = new RowConstraints( 550 );
        RowConstraints row3 = new RowConstraints();
        row1.setVgrow( Priority.SOMETIMES );
        row3.setVgrow( Priority.SOMETIMES );

        gridPane.getColumnConstraints().addAll( column1 , column2 , column1 );
        gridPane.getRowConstraints().addAll( row1 , row2 , row1 );

        // Buttons
        magnifyButton = new Button("Magnify");
        magnifyButton.setId("Magnify");
        customizeButton(magnifyButton, 100, 50);
        makeButtonAccessible(magnifyButton, "Magnify Button", "This button increases font size.", "This button increases font size, click on it to toggle on or off.");
        magnify = new MagnifiedMode(this);
        addMagnifyEvent(magnify);

        highContrastButton = new Button("High Contrast");
        highContrastButton.setId("High Contrast");
        customizeButton(highContrastButton, 100, 50);
        makeButtonAccessible(highContrastButton, "High Contrast Button", "This button changes the colour scheme.", "Click to change colour scheme to accommodate low vision players.");
        highContrast = new HighContrastMode(this);
        addHighContrastEvent(highContrast);

        saveButton = new Button("Save");
        saveButton.setId("Save");
        customizeButton(saveButton, 100, 50);
        makeButtonAccessible(saveButton, "Save Button", "This button saves the game.", "This button saves the game. Click it in order to save your current progress, so you can play more later.");
        addSaveEvent();

        loadButton = new Button("Load");
        loadButton.setId("Load");
        customizeButton(loadButton, 100, 50);
        makeButtonAccessible(loadButton, "Load Button", "This button loads a game from a file.", "This button loads the game from a file. Click it in order to load a game that you saved at a prior date.");
        addLoadEvent();

        helpButton = new Button("Instructions");
        helpButton.setId("Instructions");
        customizeButton(helpButton, 200, 50);
        makeButtonAccessible(helpButton, "Help Button", "This button gives game instructions.", "This button gives instructions on the game controls. Click it to learn how to play.");
        addInstructionEvent();

        Button remapButton = new Button("Remap Keys");
        remapButton.setId("RemapKeys");
        customizeButton(remapButton, 150, 50);
        makeButtonAccessible(remapButton, "Remap Keys Button", "Remap the keys used in the game.", "Click to remap the keys used in the game.");
        addRemapEvent(remapButton);

        HBox topButtons = new HBox();
        topButtons.getChildren().addAll(magnifyButton, highContrastButton, saveButton, helpButton, loadButton, remapButton);
        topButtons.setSpacing(10);
        topButtons.setAlignment(Pos.CENTER);

        Tooltip highContrastButtonTooltip = new Tooltip("HighContrast (B/W) Button");
        Tooltip.install(highContrastButton, highContrastButtonTooltip);

        Tooltip magnifyButtonTooltip = new Tooltip("Magnify Button");
        Tooltip.install(magnifyButton, magnifyButtonTooltip);

        Tooltip saveButtonTooltip = new Tooltip("Save Button");
        Tooltip.install(saveButton, saveButtonTooltip);

        Tooltip loadButtonTooltip = new Tooltip("Load Button");
        Tooltip.install(loadButton, loadButtonTooltip);

        Tooltip helpButtonTooltip = new Tooltip("Help Button");
        Tooltip.install(helpButton, helpButtonTooltip);

        Tooltip remapButtonTooltip = new Tooltip("Remap Button");
        Tooltip.install(remapButton, remapButtonTooltip);

        inputTextField = new TextField();
        inputTextField.setFont(new Font("Arial", this.fontSize));
        inputTextField.setFocusTraversable(true);

        inputTextField.setAccessibleRole(AccessibleRole.TEXT_AREA);
        inputTextField.setAccessibleRoleDescription("Text Entry Box");
        inputTextField.setAccessibleText("Enter commands in this box.");
        inputTextField.setAccessibleHelp("This is the area in which you can enter commands you would like to play.  Enter a command and hit return to continue.");
        addTextHandlingEvent(); //attach an event to this input field

        //labels for inventory and room items
        Label objLabel =  new Label("Objects in Room");
        objLabel.setAlignment(Pos.CENTER);
        objLabel.setStyle("-fx-text-fill: " + textColour + ";");
        objLabel.setFont(new Font("Arial", this.fontSize));

        Label invLabel =  new Label("Your Inventory");
        invLabel.setAlignment(Pos.CENTER);
        invLabel.setStyle("-fx-text-fill: " + textColour + ";");
        invLabel.setFont(new Font("Arial", this.fontSize));

        //add all the widgets to the GridPane
        gridPane.add( objLabel, 0, 0, 1, 1 );  // Add label
        gridPane.add( topButtons, 1, 0, 1, 1 );  // Add buttons
        gridPane.add( invLabel, 2, 0, 1, 1 );  // Add label

        Label commandLabel = new Label("What would you like to do?");
        commandLabel.setStyle("-fx-text-fill: " + textColour + ";");
        commandLabel.setFont(new Font("Arial", this.fontSize));

        updateScene(""); //method displays an image and whatever text is supplied
        updateItems(); //update items shows inventory and objects in rooms

        // adding the text area and submit button to a VBox
        VBox textEntry = new VBox();
        textEntry.setStyle("-fx-background-color: " + backgroundColour + ";");
        textEntry.setPadding(new Insets(20, 20, 20, 20));
        textEntry.getChildren().addAll(commandLabel, inputTextField);
        textEntry.setSpacing(10);
        textEntry.setAlignment(Pos.CENTER);
        gridPane.add( textEntry, 0, 2, 3, 1 );

        // Render everything
        var scene = new Scene( gridPane ,  1000, 800);
        scene.setFill(Color.BLACK);
        this.stage.setScene(scene);
        this.stage.setResizable(false);
        this.stage.show();

    }


    /**
     * makeButtonAccessible
     * __________________________
     * For information about ARIA standards, see
     * https://www.w3.org/WAI/standards-guidelines/aria/
     *
     * @param inputButton the button to add screenreader hooks to
     * @param name ARIA name
     * @param shortString ARIA accessible text
     * @param longString ARIA accessible help text
     */
    public static void makeButtonAccessible(Button inputButton, String name, String shortString, String longString) {
        inputButton.setAccessibleRole(AccessibleRole.BUTTON);
        inputButton.setAccessibleRoleDescription(name);
        inputButton.setAccessibleText(shortString);
        inputButton.setAccessibleHelp(longString);
        inputButton.setFocusTraversable(true);
    }

    /**
     * customizeButton
     * __________________________
     *
     * @param inputButton the button to make stylish :)
     * @param w width
     * @param h height
     */
    private void customizeButton(Button inputButton, int w, int h) {
        inputButton.setPrefSize(w, h);
        inputButton.setFont(new Font("Arial", this.fontSize));
        inputButton.setStyle("-fx-background-color: " + buttonColour + "; -fx-text-fill: " + textColour + ";");
    }

    /**
     * Adds a remap event to the specified button.
     *
     * When the specified button is clicked, this method stops any ongoing audio
     * (by calling {@link #stopArticulation()}) and then initiates the key remapping
     * process (by calling {@link #remapKeys()}).
     *
     * @param remapButton The button to which the remap event is added.
     */
    public void addRemapEvent(Button remapButton) {
        remapButton.setOnAction(e -> {
            stopArticulation(); // Stop any ongoing audio
            remapKeys(); // Call the method for key remapping
        });
    }

    /**
     * Initiates the key remapping process by prompting the user to enter the
     * current key and its new synonym. The input should be in the format
     * "currentKey=newSynonym" (e.g., "W=UP"). After a successful remapping, the
     * user is informed, and the process can be repeated to add more remappings.
     *
     * If the input format is incorrect or if there are issues with the remapping,
     * appropriate error messages are displayed, and the user can retry the
     * remapping process.
     *
     * This method uses recursion to allow adding multiple remappings in a single
     * invocation.
     */
    public void remapKeys() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Remap Keys");
        dialog.setHeaderText("Enter the current key and its new synonym, separated by '=' (e.g., W=UP):");
        dialog.setContentText("Remap:");

        // Traditional way to get the response value.
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(remapInput -> {
            String[] remapTokens = remapInput.split("=");
            if (remapTokens.length == 2) {
                String currentKey = remapTokens[0].toUpperCase();
                String newSynonym = remapTokens[1].toUpperCase();

                // Remove existing synonym
                if (model.getSynonyms().containsValue(newSynonym)) {
                    synonyms = synonyms.entrySet()
                            .stream()
                            .filter(entry -> !entry.getValue().equals(newSynonym))
                            .collect(HashMap::new, (m, entry) -> m.put(entry.getKey(), entry.getValue()), HashMap::putAll);
                }

                // Add new synonym
                model.getSynonyms().put(currentKey, newSynonym);

                // Inform the user about successful remapping
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Remap Successful");
                alert.setHeaderText(null);
                alert.setContentText("Key remapped successfully!");
                alert.showAndWait();

                // Recursive call to allow adding another remap
            } else {
                // Inform the user about invalid input format
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText(null);
                alert.setContentText("Invalid input format. Key remapping failed.");
                alert.showAndWait();

                // Recursive call to retry remapping
            }
            remapKeys();
        });
    }

    /**
     * addTextHandlingEvent
     * __________________________
     * Add an event handler to the myTextField attribute 
     *
     * Your event handler should respond when users 
     * hits the ENTER or TAB KEY. If the user hits 
     * the ENTER Key, strip white space from the
     * input to myTextField and pass the stripped 
     * string to submitEvent for processing.
     *
     * If the user hits the TAB key, move the focus 
     * of the scene onto any other node in the scene 
     * graph by invoking requestFocus method.
     */
    private void addTextHandlingEvent() {
        inputTextField.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {
                if (keyEvent.getCode() == KeyCode.ENTER) {
                    submitEvent(inputTextField.getText());
                    inputTextField.clear();
                }
                else if (keyEvent.getCode() == KeyCode.TAB) {
                    //move focus of the scene from the inputTextField to saveButton
                    saveButton.requestFocus();
                }
            }
        });

        //adding ability to shift focus from each button by pressing TAB
        Button[] buttons = new Button[]{saveButton, helpButton, loadButton}; //order important (must be same as grid order)

        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];

            int finalI = i;
            button.setOnKeyPressed(new EventHandler<KeyEvent>() {
                @Override
                public void handle(KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.TAB) {
                        //move focus of the scene to the inputTextField if this is the loadButton
                        if (finalI == buttons.length - 1) {
                            inputTextField.requestFocus();
                        }
                        //else move focus of the scene to next node
                        else {
                            buttons[finalI + 1].requestFocus();
                        }


                    }
                }
            });
        }

    }


    /**
     * submitEvent
     * __________________________
     *
     * @param text the command that needs to be processed
     */
    public void submitEvent(String text) {
        text = text.strip(); //get rid of white space
        stopArticulation(); //if speaking, stop

        if (text.equalsIgnoreCase("LOOK") || text.equalsIgnoreCase("L")) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription();
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (!objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            stopArticulation();
            articulateRoomDescription(); //all we want, if we are looking, is to repeat description.
            return;
        } else if (text.equalsIgnoreCase("HELP") || text.equalsIgnoreCase("H")) {
            showInstructions();
            return;
        } else if (text.equalsIgnoreCase("COMMANDS") || text.equalsIgnoreCase("C")) {
            showCommands(); //this is new!  We did not have this command in A1
            return;
        } else if (!UniversalKey.keyAcquired) {
            if (text.equalsIgnoreCase("UNIVERSAL KEY")) {
                roomDescLabel.setText(UniversalKey.question_answer[0]);
                UniversalKey.gameStarted = true;
                return;
            }
            else if (text.equalsIgnoreCase(UniversalKey.question_answer[1]) && UniversalKey.gameStarted) {
                roomDescLabel.setText("Correct! You have acquired the universal key!");
                UniversalKey.acquireKey();
                UniversalKey.gameStarted = false;
                return;
            }
        }

        UniversalKey.gameStarted = false;

        //try to move!
        String output = this.model.interpretAction(text); //process the command!

        if (output == null || (!output.equals("GAME OVER") && !output.equals("FORCED") && !output.equals("HELP"))) {
            updateScene(output);
            updateItems();
        } else if (output.equals("GAME OVER")) {
            updateScene("");
            updateItems();
            if (containsIgnoreCase(this.model.player.getCurrentRoom().getRoomDescription(),"congratulations")) {
                PauseTransition pause1 = new PauseTransition(Duration.seconds(8));
                pause1.setOnFinished(event -> {
                    soundEffectManager.executeActionSound(GameAction.VICTORY);
                });
                pause1.play();
            }
            else{
                PauseTransition pause1 = new PauseTransition(Duration.seconds(8));
                pause1.setOnFinished(event -> {
                    soundEffectManager.executeActionSound(GameAction.DEATH);
                });
                pause1.play();
            }
            PauseTransition pause = new PauseTransition(Duration.seconds(20));
            pause.setOnFinished(event -> {
                Platform.exit();
            });
            pause.play();
        } else if (output.equals("FORCED")) {
            //write code here to handle "FORCED" events!
            //Your code will need to display the image in the
            //current room and pause, then transition to
            //the forced room.
            updateScene("");
            updateItems();
            PauseTransition pause2 = new PauseTransition(Duration.seconds(5));
            pause2.setOnFinished(event -> {
                //checking if there is a chained force command
                if (model.player.getCurrentRoom().getCommands().contains("FORCED")) {
                    submitEvent("FORCED");
                }
            });
            pause2.play();

        }
    }


    /**
     * Checks if the search string exists in the provided string, ignoring case considerations.
     *
     * @param str The string in which to search.
     * @param searchStr The string to search for.
     * @return true if the search string is found in the provided string, false otherwise.
     * If either the search string or the provided string is null, the method returns false.
     * If the search string is empty, the method returns true.
     */
    public static boolean containsIgnoreCase(String str, String searchStr)     {
        if(str == null || searchStr == null) return false;

        final int length = searchStr.length();
        if (length == 0)
            return true;

        for (int i = str.length() - length; i >= 0; i--) {
            if (str.regionMatches(true, i, searchStr, 0, length))
                return true;
        }
        return false;
    }


    /**
     * showCommands
     * __________________________
     *
     * update the text in the GUI (within roomDescLabel)
     * to show all the moves that are possible from the 
     * current room.
     */
    private void showCommands() {
        roomDescLabel.setText(model.player.getCurrentRoom().getCommands());
    }


    /**
     * updateScene
     * __________________________
     *
     * Show the current room, and print some text below it.
     * If the input parameter is not null, it will be displayed
     * below the image.
     * Otherwise, the current room description will be dispplayed
     * below the image.
     * 
     * @param textToDisplay the text to display below the image.
     */
    public void updateScene(String textToDisplay) {

        getRoomImage(); //get the image of the current room
        formatText(textToDisplay); //format the text to display
        roomDescLabel.setPrefWidth(500);
        roomDescLabel.setPrefHeight(500);
        roomDescLabel.setTextOverrun(OverrunStyle.CLIP);
        roomDescLabel.setWrapText(true);
        VBox roomPane = new VBox(roomImageView,roomDescLabel);
        roomPane.setPadding(new Insets(10));
        roomPane.setAlignment(Pos.TOP_CENTER);
        roomPane.setStyle("-fx-background-color: " + backgroundColour + ";");

        gridPane.add(roomPane, 1, 1);
        stage.sizeToScene();

        //finally, articulate the description
        if (textToDisplay == null || textToDisplay.isBlank()) {
            PauseTransition pause2 = new PauseTransition(Duration.seconds(2));
            pause2.setOnFinished(event -> {stopArticulation(); articulateRoomDescription();});
            pause2.play();


        }


    }

    /**
     * formatText
     * __________________________
     *
     * Format text for display.
     * 
     * @param textToDisplay the text to be formatted for display.
     */
    private void formatText(String textToDisplay) {
        if (textToDisplay == null || textToDisplay.isBlank()) {
            String roomDesc = this.model.getPlayer().getCurrentRoom().getRoomDescription() + "\n";
            String objectString = this.model.getPlayer().getCurrentRoom().getObjectString();
            if (objectString != null && !objectString.isEmpty()) roomDescLabel.setText(roomDesc + "\n\nObjects in this room:\n" + objectString);
            else roomDescLabel.setText(roomDesc);
        } else roomDescLabel.setText(textToDisplay);
        roomDescLabel.setStyle("-fx-text-fill: " + textColour + ";");
        roomDescLabel.setFont(new Font("Arial", this.fontSize));
        roomDescLabel.setAlignment(Pos.CENTER);
    }

    /**
     * getRoomImage
     * __________________________
     *
     * Get the image for the current room and place 
     * it in the roomImageView 
     */
    private void getRoomImage() {

        int roomNumber = this.model.getPlayer().getCurrentRoom().getRoomNumber();
        String roomImage = this.model.getDirectoryName() + "/room-images/" + roomNumber + ".png";

        Image roomImageFile = new Image(roomImage);
        roomImageView = new ImageView(roomImageFile);
        roomImageView.setPreserveRatio(true);
        roomImageView.setFitWidth(400);
        roomImageView.setFitHeight(400);

        //set accessible text
        roomImageView.setAccessibleRole(AccessibleRole.IMAGE_VIEW);
        roomImageView.setAccessibleText(this.model.getPlayer().getCurrentRoom().getRoomDescription());
        roomImageView.setFocusTraversable(true);
    }

    /**
     * updateItems
     * __________________________
     *
     * This method is partially completed, but you are asked to finish it off.
     *
     * The method should populate the objectsInRoom and objectsInInventory Vboxes.
     * Each Vbox should contain a collection of nodes (Buttons, ImageViews, you can decide)
     * Each node represents a different object.
     * 
     * Images of each object are in the assets 
     * folders of the given adventure game.
     */
    public void updateItems() {
        // Clear the existing items
        objectsInRoom.getChildren().clear();
        objectsInInventory.getChildren().clear();

        // Get the current room and player from the model
        Room currentRoom = this.model.getPlayer().getCurrentRoom();
        Player player = this.model.getPlayer();

        ScrollPane scrollPaneRoom = new ScrollPane(objectsInRoom);
        ScrollPane scrollPaneInventory = new ScrollPane(objectsInInventory);
        scrollPaneRoom.setStyle("-fx-background-color: " + backgroundColour + "; -fx-background: " + backgroundColour + ";");
        scrollPaneInventory.setStyle("-fx-background-color: " + backgroundColour + "; -fx-background: " + backgroundColour + ";");


        // Add buttons for objects in the current room to the objectsInRoom VBox
        for (AdventureObject object : currentRoom.objectsInRoom) {
            String imagePath = this.model.getDirectoryName() + "/objectImages/" + object.getName() + ".jpg";
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(120);
            imageView.setFitHeight(70);

            Label nameLabel = new Label(object.getName());
            nameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-alignment: center;");

            VBox vbox = new VBox(imageView, nameLabel);
            vbox.setAlignment(Pos.CENTER);

            Button objectButton = new Button("", vbox);
            objectButton.setStyle("-fx-background-color: white;");
            objectButton.setOnAction(e -> {
                this.model.player.takeObject(object.getName());
                roomDescLabel.setText("YOU HAVE TAKEN:" + object.getName());
                soundEffectManager.executeActionSound(GameAction.PICKUP_ITEM);
                updateItems();
            });

            objectButton.setPrefSize(140, 80);

            objectsInRoom.getChildren().add(objectButton);
        }

        // Add buttons for objects in the player's inventory to the objectsInInventory VBox
        for (String objectName : player.getInventory()) {
            String imagePath = this.model.getDirectoryName() + "/objectImages/" + objectName + ".jpg";
            System.out.println(imagePath);
            ImageView imageView = new ImageView(new Image(imagePath));
            imageView.setFitWidth(120);
            imageView.setFitHeight(70);

            Label nameLabel = new Label(objectName);
            nameLabel.setStyle("-fx-text-fill: black; -fx-font-size: 16px; -fx-alignment: center;");

            VBox vbox = new VBox(imageView, nameLabel);
            vbox.setAlignment(Pos.CENTER);

            Button objectButton = new Button("", vbox);
            objectButton.setStyle("-fx-background-color: white;");

            if (objectName != "UNIVERSAL_KEY") {
                objectButton.setOnAction(e -> {
                    this.model.player.dropObject(objectName);
                    roomDescLabel.setText("YOU HAVE DROPPED:" + objectName);
                    soundEffectManager.executeActionSound(GameAction.DROP_ITEM);
                    updateItems();
                });
            }

            objectButton.setPrefSize(140, 80);

            objectsInInventory.getChildren().add(objectButton);
        }

        // Check if objectsInRoom and objectsInInventory have a parent and remove them if they do
        if (scrollPaneRoom.getParent() != null) {
            ((Pane) scrollPaneRoom.getParent()).getChildren().remove(scrollPaneRoom);
        }
        if (scrollPaneInventory.getParent() != null) {
            ((Pane) scrollPaneInventory.getParent()).getChildren().remove(scrollPaneInventory);
        }

        gridPane.add(scrollPaneRoom,0,1);
        gridPane.add(scrollPaneInventory,2,1);
    }

    /*
     * Show the game instructions.
     *
     * If helpToggle is FALSE:
     * -- display the help text in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- use whatever GUI elements to get the job done!
     * -- set the helpToggle to TRUE
     * -- REMOVE whatever nodes are within the cell beforehand!
     *
     * If helpToggle is TRUE:
     * -- redraw the room image in the CENTRE of the gridPane (i.e. within cell 1,1)
     * -- set the helpToggle to FALSE
     * -- Again, REMOVE whatever nodes are within the cell beforehand!
     */
    public void showInstructions() {
        if (!instructionsPaneInitialized) {
            //initializing instructions pane
            instructionsPaneInitialized = true;
            initInstructions();
            gridPane.add(instructionsScrollPane, 1, 1, 1, 1);
        }

        instructionsScrollPane.setVisible(!helpToggle);

        helpToggle = !helpToggle;
    }

    /**
     * This method handles the event related to the
     * help button.
     */
    public void addInstructionEvent() {
        helpButton.setOnAction(e -> {
            stopArticulation(); //if speaking, stop
            showInstructions();
        });
    }

    /**
     * This method handles the event related to the
     * save button.
     */
    public void addSaveEvent() {
        saveButton.setOnAction(e -> {
            gridPane.requestFocus();
            SaveView saveView = new SaveView(this);
        });
    }

    /**
     * This method handles the event related to the
     * load button.
     */
    public void addLoadEvent() {
        loadButton.setOnAction(e -> {
            gridPane.requestFocus();
            LoadView loadView = new LoadView(this);
        });
    }

    /**
     * This method handles the event related to the magnify button.
     * @param magnify
     */
    private void addMagnifyEvent(MagnifiedMode magnify) {
        magnifyButton.setOnAction(e -> magnify.toggle());
    }

    private void addHighContrastEvent(HighContrastMode highContrast) {
        highContrastButton.setOnAction(e -> highContrast.toggle());
    }
    /**
     * This method articulates Room Descriptions
     */
    public void articulateRoomDescription() {
        String musicFile;
        String adventureName = this.model.getDirectoryName();
        String roomName = this.model.getPlayer().getCurrentRoom().getRoomName();

        if (!this.model.getPlayer().getCurrentRoom().getVisited()) musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-long.mp3" ;
        else musicFile = "./" + adventureName + "/sounds/" + roomName.toLowerCase() + "-short.mp3" ;
        musicFile = musicFile.replace(" ","-");

        Media sound = new Media(new File(musicFile).toURI().toString());

        mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
        mediaPlaying = true;

    }

    /**
     * This method stops articulations 
     * (useful when transitioning to a new room or loading a new game)
     */
    public void stopArticulation() {
        if (mediaPlaying) {
            mediaPlayer.stop(); //shush!
            mediaPlaying = false;
        }
    }

    private void initInstructions() {
        instructionsScrollPane.setBackground(new Background(new BackgroundFill(
                Color.valueOf(backgroundColour),
                new CornerRadii(0),
                new Insets(0)
        )));
        instructionsScrollPane.setContent(new Label(model.getInstructions()));
        instructionsScrollPane.setStyle("-fx-background: " + backgroundColour + ";-fx-font-size: " + (fontSize - 1) + ";");
    }

    public AdventureGame getModel() {
        return model;
    }

    public Label getRoomDescLabel() {
        return roomDescLabel;
    }

    /**
     * Updates the font size of all UI components in the game view.
     */
    void updateFontSize() {
        for (Node node : gridPane.getChildren()) {

            // Check if the node is an instance of Pane (e.g., GridPane, HBox, VBox)
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setFont(new Font("Arial", this.fontSize));

            } else if (node instanceof Label) {
                Label label = (Label) node;
                label.setFont(new Font("Arial", this.fontSize));

            } else if (node instanceof TextField) {
                TextField textField = (TextField) node;
                textField.setFont(new Font("Arial", this.fontSize));
            } else if (node instanceof HBox) {
                for (Node node1 : ((HBox) node).getChildren()) {
                    if (node1 instanceof Button) {
                        Button button1 = (Button) node1;
                        button1.setFont(new Font("Arial", this.fontSize));
                    }
                }
            } else if (node instanceof VBox) {
                for (Node node2 : ((VBox) node).getChildren()) {
                    if (node2 instanceof Label) {
                        Label label1 = (Label) node2;
                        label1.setFont(new Font("Arial", this.fontSize));
                    }
                }
            }
        }
        initInstructions();
        formatText("");
        this.stage.show();
    }

    void updateColourScheme() {
        gridPane.setStyle("-fx-background-color: " + backgroundColour + ";");
        for (Node node : gridPane.getChildren()) {

            // Check if the node is an instance of Pane (e.g., GridPane, HBox, VBox)
            if (node instanceof Button) {
                Button button = (Button) node;
                button.setStyle("-fx-background-color: " + buttonColour + "; -fx-text-fill: " + textColour + ";");
            }

            else if (node instanceof Pane && !(node instanceof ScrollPane)) {
                Pane pane = (Pane) node;
                pane.setStyle("-fx-background-color: " + backgroundColour + ";");
            }
            else if (node instanceof Label) {
                Label label = (Label) node;
                label.setStyle("-fx-text-fill: " + textColour + ";");
            }

            else if (node instanceof ScrollPane) {
                ScrollPane scrollPane = (ScrollPane) node;
                scrollPane.setStyle("-fx-border-color: transparent; -fx-background-color:transparent;");
                scrollPane.lookup(".viewport").setStyle("-fx-background-color: " + backgroundColour + ";");
            } else if (node instanceof HBox) {
                for (Node node1 : ((HBox) node).getChildren()) {
                    if (node1 instanceof Button) {
                        Button button1 = (Button) node1;
                        button1.setStyle("-fx-background-color: " + buttonColour + "; -fx-text-fill: " + textColour + ";");
                    }
                }
            } else if (node instanceof VBox) {
                for (Node node2 : ((VBox) node).getChildren()) {
                    if (node2 instanceof Label) {
                        Label label1 = (Label) node2;
                        label1.setStyle("-fx-text-fill: " + textColour + ";");
                    }
                }
            }
        }
        initInstructions();
        formatText("");
        this.stage.show();
    }

    public void removeNodeByPosition(GridPane gridPane, int row, int col) {
        for (Node node : gridPane.getChildren()) {
            // Check if the node is in the given row and column
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == col) {
                gridPane.getChildren().remove(node);
                break; // Break the loop once the node is found and removed
            }
        }
    }
}
