//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import pacman.game.GameView;
//import java.awt.*;
//
//public class MoveToNearestPill extends Node {
//    @Override
//    public boolean doAction() {
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        int[] activePills = game.getActivePillsIndices();
//        int[] activePowerPills = game.getActivePowerPillsIndices();
//
//        int[] targetNodeIndices = new int[activePills.length ];
//        System.arraycopy(activePills, 0, targetNodeIndices, 0, activePills.length);
//        if (activePills.length == 0) {
//            targetNodeIndices = new int[activePowerPills.length];
//            System.arraycopy(activePowerPills, 0, targetNodeIndices, 0, activePowerPills.length);
//        }
//        int nearest = game.getClosestNodeIndexFromNodeIndex(currentNodeIndex, targetNodeIndices, Constants.DM.PATH);
//
//        move = game.getNextMoveTowardsTarget(currentNodeIndex,nearest, Constants.DM.PATH);
//        if (DEBUGGING)
//            GameView.addPoints(game, Color.CYAN, game.getShortestPath(currentNodeIndex, nearest));
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        move = null;
//        doAction();
//        if (move != null) return nodeState.Success;
//        return nodeState.Failure;
//    }
//}
