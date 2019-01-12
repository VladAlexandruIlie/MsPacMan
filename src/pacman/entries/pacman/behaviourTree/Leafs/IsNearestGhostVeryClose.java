package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;

public class IsNearestGhostVeryClose extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        int current = game.getPacmanCurrentNodeIndex();
        boolean veryClose = false ;
        for (Constants.GHOST ghost : Constants.GHOST.values())
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0)
                if (game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost)) <= GHOST_EAT_RANGE + BAIT_THRESHOLD) {

                    if (DEBUGGING) GameView.addLines(game, Color.red, game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex() );
                    veryClose = true;
                }
                else if (DEBUGGING) GameView.addPoints(game, Color.yellow, game.getShortestPath(game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex() ));


        if (veryClose) return nodeState.Success;
        return nodeState.Failure;
    }
}
