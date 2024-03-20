package Game1;

import java.util.ArrayList;

import TMGE.Board;
import TMGE.GUI;
import TMGE.Game;
import TMGE.Rule;
import TMGE.Spawner;
import TMGE.TerminalOutputGUI;
import TMGE.Tile;

public class Game1 {
    private static void run() {
        GUI gui = TerminalOutputGUI.getInstance();
        Board template = new Board(4, 4);
        int numPlayers = Integer.parseInt(gui.getInput("Number of Players"));
        
        ArrayList<Tile> tileSet = new ArrayList<Tile>();
        tileSet.add(new Tile(1));
        tileSet.add(new Tile(2));
        tileSet.add(new Tile(3));
        tileSet.add(new Tile(4));
        
        Rule rules = new Game1Rules();
    
        Game game = new Game(gui, template, numPlayers, rules, new Spawner());
        game.run();
    }

    public static void main(String[] args) {
        run();
    }
}