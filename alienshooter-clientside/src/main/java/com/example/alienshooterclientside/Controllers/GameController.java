package com.example.alienshooterclientside.Controllers;

import com.example.alienshooterclientside.Entities.ClientMessage;
import com.example.alienshooterclientside.Entities.ServerMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import static com.example.alienshooterclientside.Utilities.Constants.*;



/**
 * This class is used to control the game when the player starts
 * to play the game.
 *
 * There are 6 scenes, 3 for level entry, 3 for level
 *
 * level entry is to show the player which level the player will be playing after
 * the level entry scene.
 *
 * there is a pane for each level and these panes are filled with enemies
 *
 * time attribute is used to show the level entries for approximately 1.5 secs
 *
 * entry attribute is set when we show the level entry, it is set when the game started
 * because its the first level and the player needs to know which level the player will be playing
 * it is unset after time(1.5 secs) is finished
 *
 * level attributs is used to know which level, and this is also used for damage and score
 * based on level player HP is decreased by level * 10 and score is increased by 20 * level
 *
 * player attribute is the player and controlled by the actual player.
 *
 * line attribute is placed in between the play area and the score, HP area
 * when player shoots a bullet the bullet is intersected with this line and doesnt go up.
 *
 * labelScoreText is a static label placed up left side of the scene
 * labelScore is a dynamic label next to labelScoreText and shows the score
 * its refreshed whenever score is increased.
 *
 * labelHPText is a static label placed up right of the scene
 * labelHP is a dynamic label next to labelHPText and show the HP of the player
 * it is refreshed whenever the player is shot and HP is decreased.
 *
 * there are 8 image patterns 2 for player and 6 for enemies
 * there is only one type of player and its bullet
 *
 * there are 3 types of enemies and 3 types of bullet for enemies.
 */
public class GameController {

    private Stage stage;

    private Scene sceneLevel1;
    private Scene sceneLevel2;
    private Scene sceneLevel3;
    private Scene sceneLevel4;
    private Scene sceneWon;
    private Scene sceneLost;

    private Pane root1 = new Pane();
    private Pane root2 = new Pane();
    private Pane root3 = new Pane();
    private Pane root4 = new Pane();

    private  double time = 1;

    private boolean entry = true;
    private boolean gameOver = false;
    private boolean multiplayer = false;

    AnimationTimer timer;

    private int level = 1;

    private GameObject player = new GameObject(600, 620, 80, 80, "player", Color.BLUE);
    private GameObject player2 = new GameObject(600, 620, 80, 80, "player2", Color.BLUE);
    private GameObject line = new GameObject(0, 75, 1200, 2, "line", Color.WHITE);

    private Label labelScoreText = new Label("Score : ");
    private Label labelScore = new Label("0");
    private int score = 0;

    private Label labelHPText = new Label("Health Point : ");
    private Label labelHP = new Label("1000");
    private int healthPoint = 1000;

    private Label enemyHPText = new Label("Enemy Health : ");
    private Label enemyHP = new Label("1000");

    ImagePattern playerImagePattern = new ImagePattern(new Image("/Assets/player.png"));
    ImagePattern playerBulletImagePattern = new ImagePattern(new Image("/Assets/playerBullet.png"));
    ImagePattern playerImagePattern2 = new ImagePattern(new Image("/Assets/player2.png"));
    ImagePattern playerBulletImagePattern2 = new ImagePattern(new Image("/Assets/playerBullet2.png"));
    ImagePattern easyImagePattern = new ImagePattern(new Image("/Assets/easy.png"));
    ImagePattern easyBulletImagePattern = new ImagePattern(new Image("/Assets/easyBullet.png"));
    ImagePattern mediumImagePattern = new ImagePattern(new Image("/Assets/medium.png"));
    ImagePattern mediumBulletImagePattern = new ImagePattern(new Image("/Assets/mediumBullet.png"));
    ImagePattern hardImagePattern = new ImagePattern(new Image("/Assets/hard.png"));
    ImagePattern hardBulletImagePattern = new ImagePattern(new Image("/Assets/hardBullet.png"));

    ////////////////////////////////////////////////////////////////////
    ObjectMapper objectMapper = new ObjectMapper();
    ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
    Socket socket;
    DataOutputStream toServer;
    DataInputStream fromServer;
    ServerMessage serverMessage = new ServerMessage(0, 0, 0, false, false);
    ClientMessage clientMessage = new ClientMessage(0, false, false, 0);
    boolean playerShot = false;
    boolean player2Shot = false;
    boolean enemyDamaged = false;
    ////////////////////////////////////////////////////////////////////

    public GameController(Stage stage) throws IOException {
        //stage is set to the current stage coming from main menu
        this.stage = stage;

        Parent parent;
        //level 1,2,3 entry pages are loaded
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/level1Entry.fxml"));
        sceneLevel1 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/level2Entry.fxml"));
        sceneLevel2 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/level3Entry.fxml"));
        sceneLevel3 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/level4Entry.fxml"));
        sceneLevel4 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/youWon.fxml"));
        sceneWon = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/FXMLFiles/youLost.fxml"));
        sceneLost = new Scene(parent, 1200, 720);

        //background image of the level is retrieved
        ImageView imageView = new ImageView(new Image("/Assets/background_final.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/Assets/background_final.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);

        //level 1,2,3 background image of the scene is set
        root1.setBackground(new Background(backgroundImage));
        root2.setBackground(new Background(backgroundImage));
        root3.setBackground(new Background(backgroundImage));
        root4.setBackground(new Background(backgroundImage));

        //scene size is set
        root1.setPrefSize(1200, 720);
        root2.setPrefSize(1200, 720);
        root3.setPrefSize(1200, 720);
        root4.setPrefSize(1200, 720);

        //players image is filled with a pattern
        player.setFill(playerImagePattern);
        player2.setFill(playerImagePattern2);

        //Score labels adjusments are done below
        labelScore.setLayoutX(250);
        labelScore.setLayoutY(15);
        labelScore.setTextFill(Color.WHITE);
        labelScore.setFont(Font.font("Cousine Bold", 32));
        labelScoreText.setLayoutX(100);
        labelScoreText.setLayoutY(15);
        labelScoreText.setTextFill(Color.WHITE);
        labelScoreText.setFont(Font.font("Cousine Bold", 32));

        //HP labels adjusments are done below
        labelHP.setLayoutX(1055);
        labelHP.setLayoutY(15);
        labelHP.setTextFill(Color.WHITE);
        labelHP.setFont(Font.font("Cousine Bold", 32));
        labelHPText.setLayoutX(770);
        labelHPText.setLayoutY(15);
        labelHPText.setTextFill(Color.WHITE);
        labelHPText.setFont(Font.font("Cousine Bold", 32));

        enemyHP.setLayoutX(585);
        enemyHP.setLayoutY(15);
        enemyHP.setTextFill(Color.WHITE);
        enemyHP.setFont(Font.font("Cousine Bold", 32));
        enemyHPText.setLayoutX(300);
        enemyHPText.setLayoutY(15);
        enemyHPText.setTextFill(Color.WHITE);
        enemyHPText.setFont(Font.font("Cousine Bold", 32));

    }

    /**
     * this function is where the animation is started and
     * player starts to player the game
     *
     * every frame the handle function is called and handle calls update() and checks
     * if the game is over or not
     * @throws IOException
     */
    public void start() throws IOException {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    update();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (gameOver) {
                    timer.stop();
                    System.out.println("Timer Stopped.");
                    GameOverController gameOverController = new GameOverController();
                    gameOverController.start(stage, healthPoint + score);
                }
            }
        };
        timer.start();
        stage.setScene(sceneLevel1);
        stage.show();
    }

    /**
     * this update is like the backbone of the game
     * we can consider the code below like a finite state machine
     *
     * Lwl 1 Entry -> Lwl 1 -> Lwl 2 Entry -> Lwl 2 -> Lwl 3 Entry -> Lwl 3 -> game over
     *
     * to make a transition we check the level and entry attributes
     *
     * after an entry of a level ends we prepare the scene for the coming level
     *
     * if there is still a enemy to kill in the scene we call updateLevel
     */
    private void update() throws IOException {
        if (level == 1) {
            if (entry) {
                time -= TIME_STEP;
                if (time < 0) {
                    entry = DISABLED;
                    time = TOTAL_TIME;
                    getReadyForLevel1();
                }
            }
            else {
                updateLevel(root1);
            }
        }
        else if (level == 2) {
            if (entry) {
                time -= TIME_STEP;
                if (time < 0) {
                    entry = DISABLED;
                    time = TOTAL_TIME;
                    getReadyForLevel2();
                }
            }
            else {
                updateLevel(root2);
            }
        }
        else if (level == 3) {
            if (entry) {
                time -= TIME_STEP;
                if (time < 0) {
                    entry = DISABLED;
                    time = TOTAL_TIME;
                    getReadyForLevel3();
                }
            }
            else {
                updateLevel(root3);
            }
        }
        else {
            if (multiplayer == ENABLED) {
                updateMultiplayerLevel();
            }
            if (entry) {
                time -= TIME_STEP;
                if (time < 0) {
                    entry = DISABLED;
                    gameOver = true;
                }
            }
        }

    }

    private void connectToServer() {
        try {
            // Create a socket to connect to the server
            Socket socket = new Socket("127.0.0.1", 8000);
            fromServer = new DataInputStream(socket.getInputStream());
            toServer = new DataOutputStream(socket.getOutputStream());
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        new Thread(() -> {
            try {
                System.out.println("in the thread");
                receiveMessage();

                System.out.println("server message received");
                System.out.println("Server Message Player : " + serverMessage.getStatus() );

                multiplayer = ENABLED;

                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        getReadyForLevel4();
                    }
                });

                while (true) {
                    receiveMessage();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    private void receiveMessage() throws IOException {
        String response = fromServer.readUTF();
        String message = new String(response.getBytes(), "UTF-8");
        serverMessage = objectMapper.readValue(message, ServerMessage.class);
        if (serverMessage.getShot()) {
            player2Shot = true;
            System.out.println("player2shot set to true");
        }
    }

    private void updateMultiplayerLevel() throws IOException {
        if (serverMessage.getStatus() == FINISH) {
            enemyHP.setText("0");
            entry = ENABLED;
            multiplayer = DISABLED;
            if (serverMessage.getWon() == WON) {
                score += 1000;
                System.out.println("You won.");
                stage.setScene(sceneWon);
                stage.show();
            }
            else {
                System.out.println("You lost.");
                stage.setScene(sceneLost);
                stage.show();
            }
            return;
        }
        allGameObjects(root4).forEach(gameObject -> {
            if (gameObject.getType().equals("enemybullet")) {
                gameObject.moveDown();
            }
            else if (gameObject.getType().equals("playerbullet")) {
                gameObject.moveUp();
                allGameObjects(root4).stream().filter(o -> o.getType().equals("enemy")).forEach(enemy -> {
                    //check if the player bullet intersects with the player if so score is updated
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        gameObject.kill();
                        enemyDamaged = true;
                    }
                });

                //if player bullet is intersected with the line it is set to be deleted.
                allGameObjects(root4).stream().filter(o -> o.getType().equals("line")).forEach(enemy -> {
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        gameObject.kill();
                    }
                });
            }
            else if (gameObject.getType().equals("player2bullet")) {
                gameObject.moveUp();
                allGameObjects(root4).stream().filter(o -> o.getType().equals("enemy")).forEach(enemy -> {
                    //check if the player bullet intersects with the player if so score is updated
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        gameObject.kill();
                    }
                });

                //if player bullet is intersected with the line it is set to be deleted.
                allGameObjects(root4).stream().filter(o -> o.getType().equals("line")).forEach(enemy -> {
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        gameObject.kill();
                    }
                });
            }
        });
        for (GameObject gameObject : allGameObjects(root4)) {
            if (gameObject.isDead()) root4.getChildren().remove(gameObject);
        }

        player2.setTranslateX((double) serverMessage.getPosition());
        if (player2Shot) {
            shoot(player2, root4);
            player2Shot = false;
        }

        if (player2Shot) {
            System.out.println("player shot");
        }
        enemyHP.setText(serverMessage.getHealth() + "");
        clientMessage = new ClientMessage(0, playerShot, enemyDamaged, (int) player.getTranslateX());
        transmitMessage();
        playerShot = false;
        enemyDamaged = false;
    }

    private void transmitMessage() throws IOException {
        String message = objectWriter.writeValueAsString(clientMessage);
        new DataOutputStream(toServer).writeUTF(message);
    }

    /**
     * this takes a pane as a parameter and controls the children of the pane
     *
     * it checks the collisions
     *
     * it checks if there is a dead enemy if there is deletes that enemy from the pane
     *
     * since it is called each frame we update the creates a new bullets here and
     * updates the position of the bullets below
     *
     * if the player kills all the enemies on the scene it levels up
     *
     * if the player gets killed by the enemies, the game is over
     *
     * @param root
     */
    private void updateLevel(Pane root) {
        allGameObjects(root).forEach(gameObject -> {
            //if the bullet is coming from enemy bullet goes down.
            if (gameObject.getType().equals("enemybullet")) {
                gameObject.moveDown();

                //if the enemy bullet intersects with the player the HP decreases
                //and the HP on the scene is updated also the enemybullet is set to be deleted.
                if (gameObject.getBoundsInParent().intersects(player.getBoundsInParent())) {
                    healthPoint -= 10 * level;
                    labelHP.setText(healthPoint + "");

                    if (healthPoint < 0) {
                        player.kill();
                        gameOver = true;
                    }
                    gameObject.kill();
                }
            }
            //the player bullet goes up
            else if (gameObject.getType().equals("playerbullet")) {
                gameObject.moveUp();
                allGameObjects(root).stream().filter(o -> o.getType().equals("enemy")).forEach(enemy -> {

                    //check if the player bullet intersects with the player if so score is updated
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        score += SCORE_FACTOR * level;
                        labelScore.setText(score + "");

                        //depending on enemy type I set some randomization to kill the enemy
                        if (enemy.getDifficulty().equals("hard")) {
                            if (Math.random() > HARD) enemy.kill();
                        }
                        else if (enemy.getDifficulty().equals("medium")) {
                            if (Math.random() > MEDIUM) enemy.kill();
                        }
                        else enemy.kill();
                        gameObject.kill();
                    }
                });

                //if player bullet is intersected with the line it is set to be deleted.
                allGameObjects(root).stream().filter(o -> o.getType().equals("line")).forEach(enemy -> {
                    if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                        gameObject.kill();
                    }
                });
            }
            //if gameobject is enemy it shoots based on a random number.
            else if (gameObject.getType().equals("enemy")) {
                if (Math.random() < SHOOT_CHANCE) {
                    shoot(gameObject, root);
                }
            }
        });

        //the game objects that are set to be deleted are deleted here.
        for (GameObject gameObject : allGameObjects(root)) {
            if (gameObject.isDead()) root.getChildren().remove(gameObject);
        }

        //if there is no more enemies to kill, we change the level by adding 1, if level is 3, the game over is set
        if (allGameObjects(root).stream().filter(o ->  (o.getType().equals("enemy") )).count() == 0) {
            if (level == 1) {
                entry = ENABLED;
                level = 2;
                stage.setScene(sceneLevel2);
                stage.show();
            }
            else if (level == 2) {
                entry = ENABLED;
                level = 3;
                stage.setScene(sceneLevel3);
                stage.show();
            }
            else if (level == 3) {
                level = 4;
                stage.setScene(sceneLevel4);
                stage.show();
                connectToServer();
            }
        }
    }

    private void getReadyForLevel4() {
        if (serverMessage.getStatus() == PLAYER1) {
            player.translate(500, 620);
            player2.translate(700, 620);
        }
        else if (serverMessage.getStatus() == PLAYER2) {
            player.translate(700, 620);
            player2.translate(500, 620);
        }
        callEnemiesForLevel4(root4);
        stage.setScene(createSceneForLevel(root4));
        stage.show();
    }

    //scene is prepared for the level 3
    private void getReadyForLevel3() {
        player.translate(600, 620);
        callEnemiesForLevel3(root3);
        stage.setScene(createSceneForLevel(root3));
        stage.show();
    }

    //scene is prepared for the level 2
    private void getReadyForLevel2() {
        player.translate(600, 620);
        callEnemiesForLevel2(root2);
        stage.setScene(createSceneForLevel(root2));
        stage.show();
    }

    //scene is prepared for the level 1
    private void getReadyForLevel1() {
        player.translate(600, 620);
        callEnemiesForLevel1(root1);
        stage.setScene(createSceneForLevel(root1));
        stage.show();
    }

    /**
     * for each level, the scene is prepared wit this function
     *
     * we add the player line labels to the pane here
     *
     * scene is set for the key actions
     *
     * @param root
     * @return
     */
    private Scene createSceneForLevel(Pane root) {
        root.getChildren().addAll(player, line, labelScore, labelScoreText, labelHP, labelHPText);
        if (multiplayer == ENABLED) {
            root.getChildren().add(player2);
            labelScoreText.setLayoutX(50);
            labelScore.setLayoutX(200);
            root.getChildren().addAll(enemyHP, enemyHPText);
        }
        Scene scene = new Scene(root);

        scene.setOnKeyPressed(e -> {
            switch (e.getCode()) {
                case LEFT:
                    player.moveLeft();
                    break;
                case RIGHT:
                    player.moveRight();
                    break;
                case SPACE:
                    shoot(player, root);
                    break;
            }
        });
        return scene;
    }


    /**
     * this is helper function for level functions callEnemiesForLevelX.
     * @param root
     */
    private void callEasyEnemies(Pane root) {
        for (int i = 0; i < EASY_ENEMY_COUNT; i++) {
            GameObject enemy = new GameObject(REF_POS_OF_EASY_ENEMY + i*200, 100, 60, 60, "enemy", "easy");
            enemy.setFill(easyImagePattern);
            root.getChildren().add(enemy);
        }
    }

    /**
     * this is helper function for level functions callEnemiesForLevelX.
     * @param root
     */
    private void callMediumEnemies(Pane root) {
        for (int i = 0; i < MEDIUM_ENEMY_COUNT; i++) {
            GameObject enemy = new GameObject(REF_POS_OF_MEDIUM_ENEMY + i*200, 175, 60, 60, "enemy", "medium");
            enemy.setFill(mediumImagePattern);
            root.getChildren().add(enemy);
        }
    }

    /**
     * this is helper function for level functions callEnemiesForLevelX.
     * @param root
     */
    private void callDifficultEnemies(Pane root) {
        for (int i = 0; i < DIFFICULT_ENEMY_COUNT; i++) {
            GameObject enemy = new GameObject(REF_POS_OF_DIFFICULT_ENEMY + i*200, 250, 60, 60, "enemy", "hard");
            enemy.setFill(hardImagePattern);
            root.getChildren().add(enemy);
        }
    }

    /**
     * it takes a pane as a parameter and this function fill the pane
     * with the enemies for level1
     *
     * 3 easy enemies in default mode
     *
     * @param root
     */
    private void callEnemiesForLevel1(Pane root) {
        callEasyEnemies(root);
    }

    /**
     * it takes a pane as a parameter and this function fills the pane
     * with the enemies for level 2
     *
     * 3 easy 2 medium enemies in default mode
     *
     * @param root
     */
    private void callEnemiesForLevel2(Pane root) {
        callEasyEnemies(root);
        callMediumEnemies(root);
    }

    /**
     * this function takes a pane as a parameter and it fills the pane
     * with the enemies for level 3
     *
     * 3 easy 2 medium 1 hard enemies in default mode
     *
     * @param root
     */
    private void callEnemiesForLevel3(Pane root) {
        callEasyEnemies(root);
        callMediumEnemies(root);
        callDifficultEnemies(root);
    }

    /**
     * this function takes a pane and
     * it fills the pane with the boss enemy.
     * 1 boss enemy in default mode
     * @param root
     */
    private void callEnemiesForLevel4(Pane root) {
        GameObject enemy = new GameObject(550, 150, 160, 160, "enemy", "boss");
        enemy.setFill(hardImagePattern);
        root.getChildren().add(enemy);
    }

    /**
     * if an enemy or the player shoots, this function called,
     *
     * this is where bullets are created and added to the pane.
     *
     * according to who shoots, the bullet pattern is decided
     *
     * @param who
     * @param root
     */
    private void shoot(GameObject who, Pane root) {
        GameObject s = new GameObject((int) who.getTranslateX() + (int) (who.getWidth() / 2), (int) who.getTranslateY() +
                (int) (who.getHeight() / 2), 15, 35, who.getType() + "bullet", Color.BLACK);
        if (who == player) {
            s.setFill(playerBulletImagePattern);
            if (multiplayer) {
                playerShot = true;
            }
        }
        else if (who == player2) s.setFill(playerBulletImagePattern2);
        else {
            if (who.getDifficulty().equals("hard"))  s.setFill(hardBulletImagePattern);
            else if (who.getDifficulty().equals("medium"))  s.setFill(mediumBulletImagePattern);
            else s.setFill(easyBulletImagePattern);
        }
        root.getChildren().add(s);
    }

    /**
     * this function is used to get all the game objects in the pane
     *
     * @param root
     * @return ArrayList<GameObject> allGameObjects in the pane
     */
    public ArrayList<GameObject> allGameObjects(Pane root) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for (Object object : root.getChildren()) {
            if (object instanceof GameObject) {
                gameObjects.add((GameObject) object);
            }
        }
        return gameObjects;
    }

    /**
     * This is the atomic class of the game objects like player enemies bullets and line
     *
     * It extends Rectange class
     *
     * dead indicates if the object is dead or not
     *
     * type is used for which type the object is, like player, enemy bullet, line
     *
     * difficulty is used for enemy
     */
    private static class GameObject extends Rectangle {
        private boolean dead = false;
        private final String type;
        private String difficulty;

        //Constructors
        GameObject(int x, int y, int w, int h, String type, Color color) {
            super(w, h, color);
            this.type = type;
            setTranslateX(x);
            setTranslateY(y);
        }

        GameObject(int x, int y, int w, int h, String type, String difficulty) {
            super(w, h);
            this.type = type;
            this.difficulty = difficulty;
            setTranslateX(x);
            setTranslateY(y);
        }

        //getters and setters
        public boolean isDead() {
            return dead;
        }

        void kill() {
            dead = true;
        }

        public String getType() {
            return type;
        }

        void moveLeft() {
            setTranslateX(getTranslateX() - HORIZONTAL_STEP);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + HORIZONTAL_STEP);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - VERTICAL_UP_STEP);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + VERTICAL_DOWN_STEP);
        }

        void translate(int x, int y) {
            setTranslateX(x);
            setTranslateY(y);
        }

        public String getDifficulty() {
            return difficulty;
        }
    }
}