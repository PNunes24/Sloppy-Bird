package org.academiadecodigo.notorbios;

import org.academiadecodigo.simplegraphics.keyboard.Keyboard;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEvent;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardEventType;
import org.academiadecodigo.simplegraphics.keyboard.KeyboardHandler;
import org.academiadecodigo.simplegraphics.pictures.Picture;

import static java.lang.Thread.sleep;

public class Bird implements KeyboardHandler {

    private boolean collided = false;
    private Picture bird;

    public Bird(){

        int randomMC = (int) (Math.random() * 7);
        bird = new Picture(175, 325, randomMC == 0 ? "gus.png" : randomMC == 1 ? "bern.png" : randomMC == 2 ? "sara.png" : randomMC == 3 ? "bia.png" : randomMC == 4 ? "mar.png" : randomMC == 5 ? "carol.png" : "git.png");
        initializeBirdKeyboard();

    }

    // ########## CREATE A NEW PICTURE OF THE BIRD ########## (EXCEPTION FIXED)
    public void newBird() {

        int randomMC = (int) (Math.random() * 7);
        bird = new Picture(175, 325, randomMC == 0 ? "gus.png" : randomMC == 1 ? "bern.png" : randomMC == 2 ? "sara.png" : randomMC == 3 ? "bia.png" : randomMC == 4 ? "mar.png" : randomMC == 5 ? "carol.png" : "git.png");
        this.collided = false;

    }

    public void setCollided() {
        this.collided = true;
    }

    public boolean getCollided() {
        return collided;
    }

    public int getY() {
        return bird.getY();
    }

    public int getHeight() {
        return bird.getHeight();
    }

    public void fall() throws InterruptedException {
        if (!collided) {
            bird.translate(0, 1.50);
        } else {
            bird.translate(0, 2.75);
        }
        sleep(0,500);
    }

    private void initializeBirdKeyboard() {

        Keyboard keyboard = new Keyboard(this);

        KeyboardEvent spacePressed = new KeyboardEvent();
        spacePressed.setKey(KeyboardEvent.KEY_SPACE);
        spacePressed.setKeyboardEventType(KeyboardEventType.KEY_PRESSED);

        KeyboardEvent spaceReleased = new KeyboardEvent();
        spaceReleased.setKey(KeyboardEvent.KEY_SPACE);
        spaceReleased.setKeyboardEventType(KeyboardEventType.KEY_RELEASED);

        keyboard.addEventListener(spacePressed);
        keyboard.addEventListener(spaceReleased);

    }


    @Override
    public void keyPressed(KeyboardEvent keyboardEvent) {
        if (!collided && bird.getY() > 0) {
            bird.translate(0, getY() - 55 < 0 ? getY() : - 55);
        }
        try {
            sleep(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void keyReleased(KeyboardEvent keyboardEvent) {
    }

    public void delete() {
        bird.delete();
    }

    public int getX() {
        return bird.getX();
    }

    public void setNotCollided() {
        collided = false;
    }

    public void draw() {
        bird.draw();
    }
}