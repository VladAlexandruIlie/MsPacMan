package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;

public class MoveToNearestPowerPill extends Node {
    @Override
    public void doAction() {
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();

        int[] targets = new int[safeToReachPowerPills.size()];
        for (int i=0; i< safeToReachPowerPills.size();i++) targets[i] = safeToReachPowerPills.get(i);

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
