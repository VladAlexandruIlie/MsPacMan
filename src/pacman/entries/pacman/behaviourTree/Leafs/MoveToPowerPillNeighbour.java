package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MoveToPowerPillNeighbour extends Node {
    @Override
    public void doAction() {
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
        List<Integer> safeToReachPowerPillNeighbours = new ArrayList<>();

        for (int i: Node.safeToReachPowerPills){
            for (int j : game.getNeighbouringNodes(i)) {
                safeToReachPowerPillNeighbours.add(j);
//                GameView.addLines(game, Color.RED, currentNodeIndex, j);
            }

        }

        if (DEBUGGING) for (int safespot: safeToReachPowerPillNeighbours) GameView.addLines(game, Color.MAGENTA, currentNodeIndex, safespot);



        int[] targets = new int[safeToReachPowerPillNeighbours.size()];
        for (int i=0; i< safeToReachPowerPillNeighbours.size();i++) targets[i] = safeToReachPowerPillNeighbours.get(i);

        int nearest = game.getClosestNodeIndexFromNodeIndex(currentNodeIndex, targets, Constants.DM.PATH);

        if (DEBUGGING) GameView.addPoints(game, Color.RED, game.getShortestPath(game.getPacmanCurrentNodeIndex(), nearest));
        move = game.getNextMoveTowardsTarget(game.getPacmanCurrentNodeIndex(), nearest, Constants.DM.PATH);
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (move != null) state = nodeState.Success;
        return state;
    }
}
