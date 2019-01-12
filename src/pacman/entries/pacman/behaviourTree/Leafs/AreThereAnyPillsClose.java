//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Game;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class AreThereAnyPillsClose extends Node {
//    @Override
//    public void doAction() {
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//
//        if (Node.junctionPaths.isEmpty()) return  nodeState.Failure;
//
//        int currentNode = game.getPacmanCurrentNodeIndex();
//
//        int[] activePills = game.getPillIndices();
//        int[] activePowerPills = game.getActivePowerPillsIndices();
//
//        List<Integer> pills = new ArrayList<>();
//        List<Integer> powerPills = new ArrayList<>();
//        for (int i: activePills) if (game.isPillStillAvailable(i)) pills.add(activePills[i]);
//        for (int i: activePowerPills) if (game.isPowerPillStillAvailable(i)) powerPills.add(activePills[i]);
//
//        int pillsOnPaths = 0;
//        int powerPillsOnPaths = 0;
//        for (int junction: Node.nearestTargets){
//            int[] pathToJunction = game.getShortestPath(currentNode, junction);
//            for (int step: pathToJunction){
//                if (pills.contains(step)) pillsOnPaths +=1;
////                if (powerPills.contains(step)) powerPillsOnPaths +=1;
//            }
//        }
//
////        if (powerPillsOnPaths > 0 ) return nodeState.Failure;
//        if (pillsOnPaths > 0) return nodeState.Success;
//        return nodeState.Failure;
//    }
//}
