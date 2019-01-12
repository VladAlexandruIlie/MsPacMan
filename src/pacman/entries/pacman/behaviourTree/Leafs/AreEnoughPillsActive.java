//package pacman.entries.pacman.behaviourTree.Leafs;
//
//import pacman.entries.pacman.behaviourTree.Node;
//import pacman.game.Game;
//
//public class AreEnoughPillsActive extends Node {
//    @Override
//    public void doAction() {
//
//    }
//
//    @Override
//    public nodeState getState(Game game) {
//        Node.game = game;
//        int activePills = game.getNumberOfActivePills();
//        int allPills = game.getNumberOfPills();
//
//        if (activePills < 20)  {
//            System.out.println("too few");
//            return nodeState.Failure;
//        }
//        return nodeState.Success;
//    }
//}
