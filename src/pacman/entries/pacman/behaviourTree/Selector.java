package pacman.entries.pacman.behaviourTree;

import pacman.game.Game;

public class Selector extends Node {

    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        for (Node child: children){
            if (child.getState(game).equals(nodeState.Success)) return nodeState.Success;
        }
         return nodeState.Failure;
    }

}
