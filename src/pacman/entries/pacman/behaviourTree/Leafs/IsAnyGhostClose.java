package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;

public class IsAnyGhostClose extends Node {
//    private static int CHASE_THRESHOLD = 25;

    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            if (game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),
                                             game.getGhostCurrentNodeIndex(ghost)) < CHASE_THRESHOLD ) return nodeState.Success;
        }
        return nodeState.Failure;
    }
}
