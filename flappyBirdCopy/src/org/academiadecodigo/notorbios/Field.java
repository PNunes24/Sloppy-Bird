package org.academiadecodigo.notorbios;

import org.academiadecodigo.simplegraphics.graphics.Color;
import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.graphics.Text;
import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Field implements KeyboardHandler {

    private final Picture picture;

    private final WallFactory wallFactory = new WallFactory();

    private final Rectangle field = new Rectangle(0, 0, 1200, 700);

    private Bird bird;

    Wall[] walls = new Wall[10];

    public Field(String pathToFile) {
        this.picture = new Picture(0, 0, pathToFile);
        displayScore.grow(75, 25);
        displayHighScore.grow(90, 25);
        displayCurrentScore.grow(80, 25);
        bird = new Bird();
    }

    private void fieldKeyboard() {

        Keyboard kb = new Keyboard(this);

        KeyboardEvent SPressed = new KeyboardEvent();
        SPressed.setKey(KeyboardEvent.KEY_S);
        SPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent RPressed = new KeyboardEvent();
        RPressed.setKey(KeyboardEvent.KEY_R);
        RPressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        kb.addEventListener(SPressed);

        kb.addEventListener(RPressed);


    }

    // ########## INITIALIZE GAME ENGINE ##########

    static int highScore = 0;
    boolean startMenu = true;
    boolean overMenu = false;
    Picture startGame = new Picture(0, 0, "start.png");
    Picture gameOver = new Picture(0, 0, "gameOver.jpg");
    Text displayScore = new Text(80, 20, "");
    Text displayHighScore = new Text(565, 355, "High score: " + highScore);
    Text displayCurrentScore = new Text(585, 200, "");


    public void init() throws InterruptedException {

        fieldKeyboard();
        startMenu = true;

        double velocity = -1.50;
        int tempScore = 0;
        int score = 0;

        for (int i = 0; i < walls.length; i += 2) {
            if (i == 0) {
                walls[i] = wallFactory.createWall(1200);
            } else {
                walls[i] = wallFactory.createWall(walls[i - 2].getX() + 350);
            }
            walls[i + 1] = wallFactory.createWall(walls[i].getHeight(), walls[i].getX());
        }

        field.draw();
        this.picture.draw();

        // ########## START MENU ##########

        while (startMenu) {

            startGame.draw();

        }

        startGame.delete();

        bird.draw();

        displayScore.setColor(Color.WHITE);

        // ########## GAME STARTS ##########

        while (!bird.getCollided()) {

            displayScore.draw();

            Thread.sleep(5);

            for (Rectangle wall : walls) {
                if (wall.getX() > 1100 || wall.getX() < -150) {
                    wall.delete();
                } else {
                    wall.draw();
                }
            }

            for (int i = 0; i < walls.length; i += 2) {
                if (walls[i].getX() < -100) {
                    score++;
                    int furthestX = 0;
                    for (Wall wall : walls) {
                        if (wall.getX() > furthestX) {
                            furthestX = wall.getX();
                        }
                    }

                    walls[i] = wallFactory.createWall(furthestX + 350);
                    walls[i + 1] = wallFactory.createWall(walls[i].getHeight(), walls[i].getX());
                }
            }

            if (score > (tempScore + 1)) {
                tempScore = score;
                velocity -= 0.20;
            }

            for (Wall wall : walls) {
                wall.translate(velocity, 0);
                wall.movePipes(velocity);
            }

            for (Wall wall : walls) {
                if (bird.getX() < wall.getX() + wall.getWidth() &&
                        bird.getX() + bird.getHeight() > wall.getX() &&
                        bird.getY() < wall.getY() + wall.getHeight() &&
                        bird.getHeight() + bird.getY() > wall.getY()) {

                    bird.setCollided();
                }
            }

            if (bird.getY() >= field.getHeight() - bird.getHeight()) {
                bird.setCollided();
            }

            if (bird.getY() < field.getHeight() - bird.getHeight()) {
                bird.fall();
            }

            displayScore.delete();
            displayScore.setText("Score: " + score);

        }

        // ########## CLEANING GAME SCREEN ##########

        for (Wall wall : walls) {
            wall.delete();
        }

        bird.delete();

        // ########## GAME OVER SCREEN ##########

        overMenu = true;

        while (overMenu) {

            gameOver.draw();

            if (score > highScore) {
                highScore = score;
                displayHighScore.setText("High score: " + highScore);
            }

            displayCurrentScore.setText("Score: " + score);
            displayCurrentScore.setColor(Color.WHITE);
            displayCurrentScore.draw();

            displayHighScore.setColor(Color.WHITE);
            displayHighScore.draw();

        }

        displayCurrentScore.delete();
        displayHighScore.delete();
        gameOver.delete();

        // ########## START NEW GAME ##########
        init();

    }

    @Override
    public void keyPressed(KeyboardEvent kbEvent) {

        if (kbEvent.getKey() == KeyboardEvent.KEY_S) {
            startMenu = false;
        }

        if(kbEvent.getKey() == KeyboardEvent.KEY_R) {
            if(bird.getCollided()) {
                overMenu = false;
                bird.newBird();
            }
        }

    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {

    }
}
