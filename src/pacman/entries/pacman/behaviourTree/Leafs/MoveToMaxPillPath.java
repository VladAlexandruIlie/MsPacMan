//package pacman.entries.pacman.behaviourTree.Leafs;
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import pacman.game.GameView;
//
//import java.awt.*;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class MoveToMaxPillPath extends Node {
//
//    @Override
//    public boolean doAction() {
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
//        int maxUtilityNode = -1;
//        int maxPills = -1;
//        int minDistance = 999;
//
//        for (Integer safeSpot : Node.safeToReachTargets) {
//            int pills = 0;
//
//            int[] pathToSafeSpot = game.getShortestPath(currentNodeIndex , safeSpot);
//            for (int safePathStep : pathToSafeSpot){  if(activePills.contains(safePathStep)) pills +=1; }
//
//            if ( pills > maxPills && pathToSafeSpot.length < minDistance) {
//                minDistance = pathToSafeSpot.length;
//                maxUtilityNode = safeSpot;
//                maxPills = pills;
//            }
//        }
//
//        if (maxUtilityNode != -1)
//            if (DEBUGGING) GameView.addPoints(game, Color.BLUE, game.getShortestPath(currentNodeIndex, maxUtilityNode));
//
//
//        if (maxUtilityNode!= -1){
//
//            if (DEBUGGING) for (int safespot: safeToReachTargets) GameView.addLines(game, Color.GREEN, currentNodeIndex, safespot);
//            move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), maxUtilityNode, Constants.DM.PATH);
//        }
//        else if (maxPills == 0 ){
//
//            move = null;
////            int minDistance2 = 9999;
////            for (Integer safeSpot : safeToReachTargets) {
////
////                int nearestActivePill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePillsIndices(), Constants.DM.PATH);
////
////                if ( game.getShortestPathDistance(safeSpot, nearestActivePill) < minDistance2 ) {
////                    maxUtilityNode = safeSpot;
////                    minDistance2 = game.getShortestPathDistance(safeSpot, nearestActivePill) ;
////                }
////            }
////
////            move = game.getNextMoveTowardsTarget(currentNodeIndex, maxUtilityNode, Constants.DM.PATH);
//        }
//
//
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        Node.move = null;
//        doAction();
//        if (move != null) state = nodeState.Success;
//        return state;
//    }
//}
//
//
////  for (int potentialNode : targetNodeIndices){
////            boolean safe = true;
////            for (Constants.GHOST ghost: Constants.GHOST.values()) {
////                if (game.getGhostEdibleTime(ghost) == 0 &&
////                        game.getGhostLairTime(ghost) == 0 &&
////                        game.getShortestPathDistance( currentNodeIndex, potentialNode) >
////                                game.getShortestPathDistance( game.getGhostCurrentNodeIndex(ghost), potentialNode) ){
////                    safe = false;
////                }
////            }
////            if (safe) safeSpots.add(potentialNode);
////        }
////
////        for (Integer safeSpot: safeSpots){
////            GameView.addLines(game, Color.GREEN, currentNodeIndex , safeSpot);
////        }
//
//
////        List<Integer> allActivePils = new ArrayList<Integer>();
////        for (Integer i: targetNodeIndices){ allActivePils.add(i);}
////
////        LinkedList<Integer> frontier = new LinkedList<Integer>();
////        LinkedList<Integer> safeSpots = new LinkedList<Integer>();
////
////        frontier.add(currentNodeIndex);
////        HashMap<Integer , Boolean> visited = new HashMap<>();
////
////        for (int i=0; i<=game.getNumberOfNodes(); i++){
////            visited.put(i,false);
////        }
////        visited.put(currentNodeIndex , true);
////
////        int searchDepth = 100;
////        while (!frontier.isEmpty() && game.getShortestPathDistance(frontier.getFirst(), currentNodeIndex) < searchDepth) {
////
////            int currentNode = frontier.getFirst();
////            frontier.removeFirst();
////            for (Integer nextNodeIndex : game.getNeighbouringNodes(currentNode)) {
////                if (visited.get(nextNodeIndex).equals(false)) {
////                    frontier.add(nextNodeIndex);
////                    visited.put(nextNodeIndex, true);
////
////                    boolean safe = true;
////                    for (Constants.GHOST ghost: Constants.GHOST.values()) {
////                        if (game.getGhostEdibleTime(ghost) == 0 &&
////                                game.getGhostLairTime(ghost) == 0 &&
////                                game.getShortestPathDistance(currentNodeIndex, nextNodeIndex) >
////                                        game.getShortestPathDistance( game.getGhostCurrentNodeIndex(ghost), nextNodeIndex ) ){
////                            safe = false;
////                        }
////                    }
////
//////                    if (safe) { safeSpots.add(nextNodeIndex); }
////                }
////            }
////        }