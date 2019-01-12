package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;

public class IsPacManNextToPP extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        int current = game.getPacmanCurrentNodeIndex();

        for (int i: game.getNeighbouringNodes(current)){
            if (game.isPowerPillStillAvailable(i)) return nodeState.Success;
        }
        return nodeState.Failure;
    }
}
