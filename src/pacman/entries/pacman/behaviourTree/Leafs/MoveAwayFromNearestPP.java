//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Constants;
//import pacman.game.Game;
//import pacman.game.GameView;
//
//import java.awt.*;
//
//public class MoveAwayFromNearestPP extends Node {
//    @Override
//    public boolean doAction() {
//        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
//        int[] activePowerPills = game.getActivePowerPillsIndices();
//
//        int nearest = game.getClosestNodeIndexFromNodeIndex(currentNodeIndex, activePowerPills, Constants.DM.PATH);
//
//        if (DEBUGGING) GameView.addPoints(game, Color.PINK, game.getShortestPath(currentNodeIndex, nearest));
//        move = game.getNextMoveAwayFromTarget(currentNodeIndex, nearest, Constants.DM.PATH);
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        doAction();
//        if (move != null) return nodeState.Success;
//        return nodeState.Failure;
//    }
//}
