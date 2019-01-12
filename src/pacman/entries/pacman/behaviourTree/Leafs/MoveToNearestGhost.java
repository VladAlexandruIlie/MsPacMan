package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants.*;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;

public class MoveToNearestGhost extends Node{
    @Override
    public void doAction() {
        int min = 99999;
        GHOST nearestGhost = null;
        for (GHOST ghost : GHOST.values()) {
            if ( game.getGhostLairTime(ghost) == 0 && game.getGhostEdibleTime(ghost) >0) {

                int shortestPath = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost));

                if (shortestPath <= min) {
                    nearestGhost = ghost;
                    min = shortestPath;
                    Node.move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(nearestGhost), DM.PATH);
                }
            }
        }
        if (DEBUGGING) GameView.addLines(game, Color.magenta, game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(nearestGhost));
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (move != null) state = nodeState.Success;
        return state;
    }
}
