package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveToSafestCloseTarget extends Node {
    public void doAction() {
        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> activePowerPills = Arrays.stream(game.getActivePowerPillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> allJunctions = Arrays.stream(game.getJunctionIndices()).boxed().collect(Collectors.toList());

        int currentPos = game.getPacmanCurrentNodeIndex();
        int maxUtilityNode = -1;
        int maxPills = 0;
        int minDistanceToPill = 999;
        int minPathDistance = 999;

        int maxBranchingJunction = -1;
        int maxGhostBranching = -1;


        List<Integer> safeSpots = Node.nearestTargets;

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


        int[] pathToJunction = new int[Node.junctionPaths.get(maxBranchingJunction).size()];
        if (DEBUGGING) {
            for (int i = 0; i < Node.junctionPaths.get(maxBranchingJunction).size(); i += 1)
                pathToJunction[i] = Node.junctionPaths.get(maxBranchingJunction).get(i);
            GameView.addPoints(game, Color.YELLOW, pathToJunction);
        }

        if (pathToJunction.length != game.getShortestPathDistance(currentPos, maxBranchingJunction))
        {
//            int nearest = game.getClosestNodeIndexFromNodeIndex(currentPos, game.getActivePillsIndices(), Constants.DM.PATH);
//            move = game.getNextMoveTowardsTarget(currentPos,nearest, Constants.DM.PATH);
            move = game.getNextMoveTowardsTarget(currentPos, pathToJunction[pathToJunction.length-1], Constants.DM.PATH);
        }
        else
            move = game.getNextMoveTowardsTarget(currentPos, maxBranchingJunction, Constants.DM.PATH);

        //        for (Integer safeSpot : Node.junctionPaths.keySet()) {
//            int pills = 0;
//            int powerPills = 0;
//
//            ArrayList<Integer> pathToSafeSpot = Node.junctionPaths.get(safeSpot);
//
//            int nearestPill;
//            if (activePills.size() >0) nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePillsIndices(), Constants.DM.PATH);
//            else nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePowerPillsIndices(), Constants.DM.PATH);
//
//
//            int distanceToPill = game.getShortestPathDistance(safeSpot,nearestPill);
//
//            for (int safePathStep : pathToSafeSpot) {
//                if(activePills.contains(safePathStep)) pills +=1;
//                if (activePowerPills.contains(safePathStep)) powerPills +=1;
//            }
//
//
//            if ( (pills > maxPills && powerPills==0 ) || (pills > maxPills && junctionPaths.keySet().size() < 1)) {
//                maxUtilityNode = safeSpot;
//                maxPills = pills;
//            }
//
//            else if ( maxPills == 0 && distanceToPill < minDistanceToPill ) {
//                maxUtilityNode = safeSpot;
//                minDistanceToPill = distanceToPill;
//                minPathDistance = pathToSafeSpot.size();
////                minPathDistance  = game.getShortestPathDistance(currentPos, safeSpot);
//            }
////            else if ( pills == maxPills && pathToSafeSpot.size() < minPathDistance){
////                maxUtilityNode = safeSpot;
////                minPathDistance = pathToSafeSpot.size();
////            }
//
//        }


    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (move != null) return nodeState.Success;
        return nodeState.Failure;
    }
}
