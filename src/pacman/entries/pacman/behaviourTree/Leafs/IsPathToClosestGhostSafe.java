package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class IsPathToClosestGhostSafe extends Node {

    @Override
    public void doAction() {
        int min = 99999;
        Constants.GHOST nearestGhost = null;
        for (Constants.GHOST ghost : Constants.GHOST.values()) {
            if ( game.getGhostLairTime(ghost) == 0 && game.getGhostEdibleTime(ghost) >0 ) {

                int shortestPath = game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(ghost));
                if (shortestPath < min) {
                    nearestGhost = ghost;
                    min = shortestPath;
                }
            }
        }

        if (DEBUGGING) {
            GameView.addLines(game, Color.MAGENTA, game.getPacmanCurrentNodeIndex(), game.getGhostCurrentNodeIndex(nearestGhost));
        }

        int[] pathToNearestGhost = game.getShortestPath(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(nearestGhost));
        List<Integer> pathToNearestGhostList = Arrays.stream(pathToNearestGhost).boxed().collect(Collectors.toList());

        for (Constants.GHOST ghost : Constants.GHOST.values())
            if (!game.isGhostEdible(ghost) && game.getGhostLairTime(ghost)==0 ){

                int nearestNode = game.getClosestNodeIndexFromNodeIndex(game.getGhostCurrentNodeIndex(ghost), pathToNearestGhost, Constants.DM.PATH);

                if (game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), nearestNode) <= GHOST_EAT_RANGE) state = nodeState.Failure ;

                if (pathToNearestGhostList.contains(game.getGhostCurrentNodeIndex(ghost))) state = nodeState.Failure ;
            }
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        state = nodeState.Success;
        doAction();
        return state;
    }
}
