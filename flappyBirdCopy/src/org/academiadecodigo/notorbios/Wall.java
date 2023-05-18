package org.academiadecodigo.notorbios;

import org.academiadecodigo.simplegraphics.graphics.Rectangle;
import org.academiadecodigo.simplegraphics.pictures.Picture;

public class Wall extends Rectangle {

    Picture[] pipes = new Picture[40];

    public Wall(int randomX) {
        super(randomX, 0, 98, (int) ((Math.random() * 300) + 100));

        int wallHeight = super.getHeight() - 50;

        for (int i = 0; i < pipes.length; i++) {
            if (wallHeight <= 0) {
                pipes[i] = new Picture(super.getX(), super.getY(), "modulo.jpg");
                break;
            }

            pipes[i] = new Picture(super.getX(), wallHeight, (i == 0 ? "upperpipe.png" : "modulo.jpg"));
            wallHeight -= 50;
        }
    }

    public Wall(int heightPlus, int randX) {
        super(randX, heightPlus + 200, 98, 700 - (heightPlus + 200));

        int wallHeight = super.getY();

        for (int i = 0; i < pipes.length; i++) {
            if (wallHeight >= 650) {
                pipes[i] = new Picture(super.getX(), 650, "modulo.jpg");
                break;
            }

            pipes[i] = new Picture(super.getX(), wallHeight, (i == 0 ? "lowerpipe.png" : "modulo.jpg"));
            wallHeight += 50;
        }
    }

    public void movePipes(double velocity) {

        for (Picture pipe : pipes) {
            if (pipe == null) {
                continue;
            }

            if (pipe.getX() > 1100 || pipe.getX() < -150) {
                pipe.delete();
            } else {
                pipe.draw();
            }

            pipe.translate(velocity, 0);
        }

    }

    public void delete() {
        super.delete();

        for (Picture pipe : pipes) {
            if (pipe != null) {
                pipe.delete();
            }
        }
    }
}
