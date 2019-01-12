//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Game;
//
//public class IsSafeToReachNodesEmpty extends Node {
//    @Override
//    public boolean doAction() {
//
//        return false;
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        if (Node.safeToReachJunctions.isEmpty()) return nodeState.Success;
//        return nodeState.Failure;
//    }
//}
