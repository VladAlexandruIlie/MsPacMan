//package pacman.entries.pacman.behaviourTree.Leafs;
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import java.util.LinkedList;
//
//public class GetSafeToReachTargets extends Node {
//
//    @Override
//    public boolean doAction() {
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        int[] activePills = game.getActivePillsIndices();
//        int[] activePowerPills = game.getActivePowerPillsIndices();
//        int[] junctions = game.getJunctionIndices();
//
//        int[] targetNodeIndices = new int[activePowerPills.length + activePills.length + junctions.length];
//        LinkedList<Integer> safeTargets = new LinkedList<Integer>();
//        LinkedList<Integer> safeJunctions = new LinkedList<Integer>();
//
//        for (int i = 0; i < activePills.length; i++) targetNodeIndices[i] = activePills[i];
//        for (int i = 0; i < activePowerPills.length; i++) targetNodeIndices[i + activePills.length] = activePowerPills[i];
//        for (int i = 0; i < junctions.length; i++) targetNodeIndices[i + activePills.length + activePowerPills.length]= junctions[i];
//
//
//        for (int potentialJunction : targetNodeIndices) {
//            if (game.getShortestPathDistance(currentNodeIndex, potentialJunction) < AWARENESS_THRESHOLD ) {
//                boolean safe = true;
//                for (Constants.GHOST ghost : Constants.GHOST.values()) {
//                    if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 &&
//                            game.getShortestPathDistance(currentNodeIndex, potentialJunction) + 3 >
//                                    game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), potentialJunction)) {
//                        safe = false;
//                    }
//                }
//                if (safe) safeTargets.add(potentialJunction);
//            }
//        }
//
//        Node.safeToReachTargets = safeTargets;
//
//        LinkedList<Integer> junctionsList = new LinkedList<Integer>();
//        for (int i = 0; i < junctions.length; i++) junctionsList.add(junctions[i]);
//
//        for (int i : Node.safeToReachTargets){
//            if(junctionsList.contains(i)) safeJunctions.add(i);
//        }
//        Node.safeToReachJunctions = safeJunctions;
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        doAction();
//        Node.state = nodeState.Success;
//        return state;
//    }
//}
