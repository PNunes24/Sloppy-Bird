package org.academiadecodigo.notorbios;

public class WallFactory {

    public Wall createWall(int randomX) {
        return new Wall(randomX);
    }

    public Wall createWall(int heightPlus, int randX) {
        return new Wall(heightPlus, randX);
    }
}
