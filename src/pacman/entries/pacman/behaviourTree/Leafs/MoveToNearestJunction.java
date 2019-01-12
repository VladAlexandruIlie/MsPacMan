//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import pacman.game.GameView;
//import java.awt.*;
//import java.util.*;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class MoveToNearestJunction extends Node {
//    @Override
//    public void doAction() {
//        int currentPos = game.getPacmanCurrentNodeIndex();
//        int[] junctions = game.getJunctionIndices();
//
//        List<Integer> junctionsList = new ArrayList<>();
//        List<Integer> directJunctions = new ArrayList<>();
//
//        for (int junction : junctions) junctionsList.add(junction);
//
//        for (int junction : junctions){
//            int[] path = game.getShortestPath(currentPos, junction);
//            boolean directCorrdor = true;
//            for (int spotInPath: path){
//                if (junctionsList.contains(spotInPath) && spotInPath!=junction) directCorrdor = false;
//            }
//            if (directCorrdor) directJunctions.add(junction);
//        }
//
////
////        HashMap<Integer,Integer> frontier = new HashMap<>();
////        frontier.put(currentPos,0);
////        List<Integer> came_from = new ArrayList<>();
////        came_from.get(currentPos) = null;
////        List<Integer> cost_so_far = new ArrayList<>();
//
//
//
/////frontier = PriorityQueue()
////frontier.put(start, 0)
////came_from = {}
////cost_so_far = {}
////came_from[start] = None
////cost_so_far[start] = 0
////
////while not frontier.empty():
////   current = frontier.get()
////
////   if current == goal:
////      break
////
////   for next in graph.neighbors(current):
////      new_cost = cost_so_far[current] + graph.cost(current, next)
////      if next not in cost_so_far or new_cost < cost_so_far[next]:
////         cost_so_far[next] = new_cost
////         priority = new_cost + heuristic(goal, next)
////         frontier.put(next, priority)
////         came_from[next] = current
//
//
//
//
//
//
//
//        int[] nearestTargets = new int[directJunctions.size()];
//        for (int i=0; i<nearestTargets.length;i++) nearestTargets[i]=directJunctions.get(i);
//
//        for (int junction: directJunctions){
//            GameView.addLines(game, Color.GREEN, currentPos, junction );
//        }
//
//        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
//        int maxUtilityNode = -1;
//        int maxPills = -1;
//        int minDistanceToPill = 999;
//
//        for (Integer safeSpot : directJunctions) {
//            int pills = 0;
//
//            int[] pathToSafeSpot = game.getShortestPath(currentPos , safeSpot);
//            int nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePillsIndices(), Constants.DM.PATH);
//            int distanceToPill = game.getShortestPathDistance(safeSpot,nearestPill);
//            for (int safePathStep : pathToSafeSpot) { if(activePills.contains(safePathStep)) pills +=1; }
//
//
//            if ( pills > maxPills ) { maxUtilityNode = safeSpot; maxPills = pills; }
////                else if (pills == maxPills && distanceToPill < minDistanceToPill) { maxUtilityNode = safeSpot; minDistanceToPill = distanceToPill; }
//
//        }
//
//        if (maxUtilityNode > 0) {
//            GameView.addPoints(game, Color.GREEN, game.getShortestPath(currentPos, maxUtilityNode));
//            move = game.getNextMoveTowardsTarget(currentPos, maxUtilityNode, Constants.DM.PATH);
//        }
//        else move = Constants.MOVE.NEUTRAL;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        doAction();
//        if (move != null) Node.state = nodeState.Success;
//        return nodeState.Failure;
//    }
//}
