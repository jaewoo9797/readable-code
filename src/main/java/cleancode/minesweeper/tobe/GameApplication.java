package cleancode.minesweeper.tobe;

import cleancode.minesweeper.tobe.gamelevel.Beginner;
import cleancode.minesweeper.tobe.gamelevel.GameLevel;
import cleancode.minesweeper.tobe.gamelevel.Middle;

public class GameApplication {

    public static void main(String[] args) {
        GameLevel gameLevel = new Beginner();

        MineSweeper mineSweeper = new MineSweeper(gameLevel);
        mineSweeper.run();
    }

}
