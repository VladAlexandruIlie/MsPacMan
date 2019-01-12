package pacman.entries.pacman.behaviourTree;

import pacman.game.Game;

public class Inverter extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        for (Node child: children){
            if (child.getState(game).equals(nodeState.Failure)) return nodeState.Success;
        }
        return nodeState.Failure;
    }
}
