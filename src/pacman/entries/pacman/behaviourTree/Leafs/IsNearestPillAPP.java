//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class IsNearestPillAPP extends Node {
//    @Override
//    public boolean doAction() {
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        int[] activePills = game.getActivePillsIndices();
//        int[] activePowerPills = game.getActivePowerPillsIndices();
//        int[] junctions = game.getJunctionIndices();
//
//        List<Integer> junction = new ArrayList<>();
//        for (int i = 0; i < junctions.length; i++) junction.add(junctions[i]);
//
//
//        int[] targetNodeIndices = new int[activePills.length + activePowerPills.length];
//
//        List<Integer> powerPills = new ArrayList<>();
//
//
//        for (int i = 0; i < activePowerPills.length;i++) {
//            targetNodeIndices[i] = activePowerPills[i];
//            powerPills.add(activePowerPills[i]);
//        }
//        for (int i = 0; i < activePills.length; i++) targetNodeIndices[i + activePowerPills.length] = activePills[i];
//        int nearest = game.getClosestNodeIndexFromNodeIndex(currentNodeIndex, targetNodeIndices, Constants.DM.PATH);
//
//        if (powerPills.contains(nearest)) return nodeState.Success;
//        return nodeState.Failure;
//
//    }
//}
