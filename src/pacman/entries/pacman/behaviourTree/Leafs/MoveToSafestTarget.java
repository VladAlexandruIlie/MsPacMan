//package pacman.entries.pacman.behaviourTree.Leafs;
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import pacman.game.Constants.*;
//import pacman.game.GameView;
//import java.awt.*;
//import java.util.ArrayList;
//import java.util.List;
//
//public class MoveToSafestTarget extends Node {
//    @Override
//    public boolean doAction() {
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        int[] allActivePills = game.getActivePillsIndices();
//        int[] junctions = game.getJunctionIndices();
//
//        List<Integer> allJunctions = new ArrayList<>();
//        List<Integer> allPills = new ArrayList<>();
//        for(int i: junctions){ allJunctions.add(i); }
//        for(int i: allActivePills) {allPills.add(i); }
//
//        List<Integer> safeSpots = Node.safeToReachTargets;
//
//        int maxBranchingJunction = -1;
//        int maxGhostBranching = -1;
//
//        for (Integer safeSpot : safeSpots) {
//            int branchingToGhosts = 0;
//            for (GHOST ghost : GHOST.values()) {
//                if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 ) {
//                    int[] pathFromGhostToJunction = game.getShortestPath(game.getGhostCurrentNodeIndex(ghost), safeSpot);
//                    for (int safePathStep : pathFromGhostToJunction) {
//                        if (allJunctions.contains(safePathStep)) branchingToGhosts += 1;
//                    }
//                }
//            }
//
//            if (branchingToGhosts > maxGhostBranching) {
//                maxBranchingJunction = safeSpot;
//                maxGhostBranching = branchingToGhosts;
//            }
//        }
//
//        if (DEBUGGING && maxBranchingJunction!=-1) GameView.addPoints(game, Color.BLUE, game.getShortestPath(currentNodeIndex, maxBranchingJunction));
//
//        if (DEBUGGING) for (int safespot: safeToReachTargets) GameView.addLines(game, Color.yellow, currentNodeIndex, safespot);
//        move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), maxBranchingJunction, Constants.DM.PATH);
//
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        if (Node.safeToReachTargets.isEmpty()) return nodeState.Failure;
//        doAction();
//        if (move != null) state = nodeState.Success;
//        return state;
//    }
//}
