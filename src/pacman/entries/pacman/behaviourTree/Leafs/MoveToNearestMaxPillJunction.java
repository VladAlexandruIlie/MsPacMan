package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MoveToNearestMaxPillJunction extends Node {
    @Override
    public void doAction() {
        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> activePowerPills = Arrays.stream(game.getActivePowerPillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> juncations = Arrays.stream(game.getJunctionIndices()).boxed().collect(Collectors.toList());

        int amountOfSafeJunctions = 0;
        for (Integer safeSpot : Node.nearestTargets) { if (juncations.contains(safeSpot)) amountOfSafeJunctions+=1; }

        int currentPos = game.getPacmanCurrentNodeIndex();
        int maxUtilityNode = -1;
        int maxPills = 0;
        int minDistanceToPill = 999;

        for (Integer safeSpot : Node.junctionPaths.keySet()) {

            int pills = 0;
            int powerPills = 0;

            ArrayList<Integer> pathToSafeSpot = Node.junctionPaths.get(safeSpot);

            int nearestPill;
            if (activePills.size() > 0)
                nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePillsIndices(), Constants.DM.PATH);
            else nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePowerPillsIndices(), Constants.DM.PATH);


            int distanceToPill = game.getShortestPathDistance(safeSpot,nearestPill);

            for (int safePathStep : pathToSafeSpot) {
                if (activePills.contains(safePathStep) ) pills +=1;
                if (activePowerPills.contains(safePathStep)) powerPills +=1;
            }

            if ( (pills > maxPills &&  powerPills == 0 ) || ( powerPills > 0 && amountOfSafeJunctions < 2 ) || (powerPills > 0 && activePills.size() < game.getNumberOfPills() * 0.1) ) {
                    maxUtilityNode = safeSpot;
                    maxPills = pills;
                }
//            else if (pills > maxPills && activePills.size() < game.getNumberOfPills() * 0.1) {
//                maxUtilityNode = safeSpot;
//                maxPills = pills;
//            }
            else if (maxPills == 0 && powerPills == 0 && distanceToPill < minDistanceToPill) {
                maxUtilityNode = safeSpot;
                minDistanceToPill = distanceToPill;
            }

        }

        int[] pathToJunction = new int[Node.junctionPaths.get(maxUtilityNode).size()];
        for (int i = 0; i < Node.junctionPaths.get(maxUtilityNode).size(); i += 1)
            pathToJunction[i] = Node.junctionPaths.get(maxUtilityNode).get(i);
        if (DEBUGGING) {
            GameView.addPoints(game, Color.magenta, pathToJunction);
        }

//        if (pathToJunction.length != game.getShortestPathDistance(currentPos, maxUtilityNode)) {
//            move = game.getNextMoveTowardsTarget(currentPos, pathToJunction[pathToJunction.length-1], Constants.DM.PATH);
//        }
//        else
//            move = game.getNextMoveTowardsTarget(currentPos, maxUtilityNode, Constants.DM.PATH);



        move = game.getNextMoveTowardsTarget(currentPos, pathToJunction[pathToJunction.length-1], Constants.DM.PATH);
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (move != null) return nodeState.Success;
        return nodeState.Failure;
    }
}
