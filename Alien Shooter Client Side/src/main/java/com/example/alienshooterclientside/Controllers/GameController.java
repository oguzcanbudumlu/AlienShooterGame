package com.example.alienshooterclientside.Controllers;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Collectors;

public class GameController {
    private Stage stage;

    private Scene sceneLevel1;
    private Scene sceneLevel2;
    private Scene sceneLevel3;

    private Pane root1 = new Pane();
    private Pane root2 = new Pane();
    private Pane root3 = new Pane();

    private  double time = 1;

    private boolean entry = true;
    private boolean gameOver = false;

    AnimationTimer timer;

    private int level = 1;

    private GameObject player = new GameObject(600, 620, 80, 80, "player", Color.BLUE);
    private GameObject line = new GameObject(0, 75, 1200, 2, "line", Color.WHITE);

    private Label labelScoreText = new Label("Score : ");
    private Label labelScore = new Label("0");
    private int score = 0;

    private Label labelHPText = new Label("Health Point : ");
    private Label labelHP = new Label("1000");
    private int HP = 1000;

    ImagePattern playerImagePattern = new ImagePattern(new Image("/Assets/player.png"));
    ImagePattern playerBulletImagePattern = new ImagePattern(new Image("/Assets/playerBullet.png"));
    ImagePattern easyImagePattern = new ImagePattern(new Image("/Assets/easy.png"));
    ImagePattern easyBulletImagePattern = new ImagePattern(new Image("/Assets/easyBullet.png"));
    ImagePattern mediumImagePattern = new ImagePattern(new Image("/Assets/medium.png"));
    ImagePattern mediumBulletImagePattern = new ImagePattern(new Image("/Assets/mediumBullet.png"));
    ImagePattern hardImagePattern = new ImagePattern(new Image("/Assets/hard.png"));
    ImagePattern hardBulletImagePattern = new ImagePattern(new Image("/Assets/hardBullet.png"));

    public GameController(Stage stage) throws IOException {
        this.stage = stage;

        Parent parent;
        parent = FXMLLoader.load(getClass().getResource("/level1Entry.fxml"));
        sceneLevel1 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/level2Entry.fxml"));
        sceneLevel2 = new Scene(parent, 1200, 720);
        parent = FXMLLoader.load(getClass().getResource("/level3Entry.fxml"));
        sceneLevel3 = new Scene(parent, 1200, 720);

        ImageView imageView = new ImageView(new Image("/Assets/background_final.jpg"));
        BackgroundImage backgroundImage = new BackgroundImage(new Image("/Assets/background_final.jpg"),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        root1.setBackground(new Background(backgroundImage));
        root2.setBackground(new Background(backgroundImage));
        root3.setBackground(new Background(backgroundImage));
        root1.setPrefSize(1200, 720);
        root2.setPrefSize(1200, 720);
        root3.setPrefSize(1200, 720);

        player.setFill(playerImagePattern);

        labelScore.setLayoutX(250);
        labelScore.setLayoutY(15);
        labelScore.setTextFill(Color.WHITE);
        labelScore.setFont(Font.font("Cousine Bold", 32));
        labelScoreText.setLayoutX(100);
        labelScoreText.setLayoutY(15);
        labelScoreText.setTextFill(Color.WHITE);
        labelScoreText.setFont(Font.font("Cousine Bold", 32));

        labelHP.setLayoutX(1055);
        labelHP.setLayoutY(15);
        labelHP.setTextFill(Color.WHITE);
        labelHP.setFont(Font.font("Cousine Bold", 32));
        labelHPText.setLayoutX(770);
        labelHPText.setLayoutY(15);
        labelHPText.setTextFill(Color.WHITE);
        labelHPText.setFont(Font.font("Cousine Bold", 32));

    }

    public void start() throws IOException {
        timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                if (gameOver) {
                    timer.stop();
                    GameOverController gameOverController = new GameOverController();
                    gameOverController.start(stage, HP + score);
                }
            }
        };
        timer.start();
        stage.setScene(sceneLevel1);
        stage.show();
    }

    private void update() {
        if (level == 1) {
            if (entry) {
                time -= 0.01;
                if (time < 0) {
                    entry = false;
                    time = 1;
                    getReadyForLevel1();
                }
            }
            else {
                updateLevel(root1);
            }
        }
        else if (level == 2) {
            if (entry) {
                time -= 0.01;
                if (time < 0) {
                    entry = false;
                    time = 1;
                    getReadyForLevel2();
                }
            }
            else {
                updateLevel(root2);
            }
        }
        else {
            if (entry) {
                time -= 0.01;
                if (time < 0) {
                    entry = false;
                    time = 1;
                    getReadyForLevel3();
                }
            }
            else {
                updateLevel(root3);
            }
        }
    }

    private void updateLevel(Pane root) {
        allGameObjects(root).forEach(gameObject -> {
            if (gameObject instanceof GameObject) {
                if (gameObject.getType().equals("enemybullet")) {
                    gameObject.moveDown();

                    if (gameObject.getBoundsInParent().intersects(player.getBoundsInParent())) {
                        HP -= 10 * level;
                        labelHP.setText(HP + "");
                        if (HP < 0) {
                            player.kill();
                            gameOver = true;
                        }
                        gameObject.kill();
                    }
                } else if (gameObject.getType().equals("playerbullet")) {
                    gameObject.moveUp();
                    allGameObjects(root).stream().filter(o -> o.getType().equals("enemy")).forEach(enemy -> {
                        if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            score += 20 * level;
                            labelScore.setText(score + "");

                            if (enemy.getDifficulty().equals("hard")) {
                                if (Math.random() > 0.85) enemy.kill();
                            }
                            else if (enemy.getDifficulty().equals("medium")) {
                                if (Math.random() > 0.65) enemy.kill();
                            }
                            else enemy.kill();
                            gameObject.kill();
                        }
                    });
                    allGameObjects(root).stream().filter(o -> o.getType().equals("line")).forEach(enemy -> {
                        if (gameObject.getBoundsInParent().intersects(enemy.getBoundsInParent())) {
                            gameObject.kill();
                        }
                    });
                } else if (gameObject.getType().equals("enemy")) {
                    if (Math.random() < 0.001) {
                        shoot(gameObject, root);
                    }
                }
            }
        });

        for (GameObject gameObject : allGameObjects(root)) {
            if (gameObject.isDead()) root.getChildren().remove(gameObject);
        }

        if (allGameObjects(root).stream().filter(o ->  (o instanceof  GameObject && o.getType().equals("enemy") )).count() == 0) {
            if (level == 1) {
                entry = true;
                level = 2;
                stage.setScene(sceneLevel2);
                stage.show();
            }
            else if (level == 2) {
                entry = true;
                level = 3;
                stage.setScene(sceneLevel3);
                stage.show();
            }
            else {
                gameOver = true;
            }
        }
    }

    private void getReadyForLevel3() {
        callEnemiesForLevel3(root3);
        stage.setScene(createSceneForLevel(root3));
        stage.show();
    }

    private void getReadyForLevel2() {
        callEnemiesForLevel2(root2);
        stage.setScene(createSceneForLevel(root2));
        stage.show();
    }

    private void getReadyForLevel1() {
        callEnemiesForLevel1(root1);
        stage.setScene(createSceneForLevel(root1));
        stage.show();
    }

    private Scene createSceneForLevel(Pane root) {
        root.getChildren().addAll(player, line, labelScore, labelScoreText, labelHP, labelHPText);
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

    private void callEnemiesForLevel1(Pane root) {
        for (int i = 0; i < 5; i++) {
            GameObject enemy = new GameObject(200 + i*200, 100, 60, 60, "enemy", "easy");
            enemy.setFill(easyImagePattern);
            root.getChildren().add(enemy);
        }
    }

    private void callEnemiesForLevel2(Pane root) {
        for (int i = 0; i < 5; i++) {
            GameObject enemy = new GameObject(200 + i*200, 100, 60, 60, "enemy", "easy");
            enemy.setFill(easyImagePattern);
            root.getChildren().add(enemy);
        }
        for (int i = 0; i < 4; i++) {
            GameObject enemy = new GameObject(300 + i*200, 175, 60, 60, "enemy", "medium");
            enemy.setFill(mediumImagePattern);
            root.getChildren().add(enemy);
        }
    }

    private void callEnemiesForLevel3(Pane root) {
        for (int i = 0; i < 5; i++) {
            GameObject enemy = new GameObject(200 + i*200, 100, 60, 60, "enemy", "easy");
            enemy.setFill(easyImagePattern);
            root.getChildren().add(enemy);
        }
        for (int i = 0; i < 4; i++) {
            GameObject enemy = new GameObject(300 + i*200, 175, 60, 60, "enemy", "medium");
            enemy.setFill(mediumImagePattern);
            root.getChildren().add(enemy);
        }
        for (int i = 0; i < 3; i++) {
            GameObject enemy = new GameObject(400 + i*200, 250, 60, 60, "enemy", "hard");
            enemy.setFill(hardImagePattern);
            root.getChildren().add(enemy);
        }
    }

    private void shoot(GameObject who, Pane root) {
        GameObject s = new GameObject((int) who.getTranslateX() + (int) (who.getWidth() / 2), (int) who.getTranslateY() +
                (int) (who.getHeight() / 2), 15, 35, who.type + "bullet", Color.BLACK);
        if (who == player) s.setFill(playerBulletImagePattern);
        else {
            if (who.getDifficulty().equals("hard"))  s.setFill(hardBulletImagePattern);
            else if (who.getDifficulty().equals("medium"))  s.setFill(mediumBulletImagePattern);
            else s.setFill(easyBulletImagePattern);
        }
        root.getChildren().add(s);
    }

    public ArrayList<GameObject> allGameObjects(Pane root) {
        ArrayList<GameObject> gameObjects = new ArrayList<>();
        for (Object object : root.getChildren()) {
            if (object instanceof GameObject) {
                gameObjects.add((GameObject) object);
            }
        }
        return gameObjects;
    }

    private static class GameObject extends Rectangle {
        private boolean dead = false;

        private final String type;
        private String difficulty;

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
            setTranslateX(getTranslateX() - 8);
        }

        void moveRight() {
            setTranslateX(getTranslateX() + 8);
        }

        void moveUp() {
            setTranslateY(getTranslateY() - 1);
        }

        void moveDown() {
            setTranslateY(getTranslateY() + 1);
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
