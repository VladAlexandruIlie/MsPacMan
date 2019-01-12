package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveToSafestJunction extends Node {
    @Override
    public void doAction() {
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
        int[] allActivePills = game.getActivePillsIndices();
        int[] junctions = game.getJunctionIndices();

        List<Integer> allJunctions = new ArrayList<>();
        List<Integer> allPills = new ArrayList<>();
        for(int i: junctions){ allJunctions.add(i); }
        for(int i: allActivePills) {allPills.add(i); }

        List<Integer> safeSpots = Node.safeToReachJunctions;

        int maxBranchingJunction = -1;
        int maxGhostBranching = -1;

        for (Integer safeSpot : safeSpots) {
            int branchingToGhosts = 0;
            for (Constants.GHOST ghost : Constants.GHOST.values()) {
                if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 ) {

                    int[] pathFromGhostToJunction = game.getShortestPath(game.getGhostCurrentNodeIndex(ghost), safeSpot);


                    for (int safePathStep : pathFromGhostToJunction) {
                        if (allJunctions.contains(safePathStep)) branchingToGhosts += 1;
                    }
                }
            }

            if (branchingToGhosts > maxGhostBranching) {
                maxBranchingJunction = safeSpot;
                maxGhostBranching = branchingToGhosts;
            }
        }

        if (DEBUGGING) GameView.addPoints(game, Color.BLUE, game.getShortestPath(currentNodeIndex, maxBranchingJunction));

        for (int safespot: safeToReachJunctions) GameView.addLines(game, Color.YELLOW, currentNodeIndex, safespot);
//        for (int safespot: safeToReachJunctions) GameView.addPoints(game, Color.CYAN, game.getShortestPath(currentNodeIndex,safespot, game.getPacmanLastMoveMade()));
        if (DEBUGGING) GameView.addPoints(game, Color.yellow, game.getShortestPath(currentNodeIndex,maxBranchingJunction, game.getPacmanLastMoveMade()));
        move = game.getNextMoveTowardsTarget(currentNodeIndex, maxBranchingJunction, Constants.DM.PATH);
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        if (Node.safeToReachJunctions.isEmpty()) return nodeState.Failure;
        doAction();
        if (move != null) state = nodeState.Success;
        return state;
    }
}
