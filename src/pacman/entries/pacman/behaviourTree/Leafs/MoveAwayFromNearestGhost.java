package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;

public class MoveAwayFromNearestGhost extends Node {
    @Override
    public void doAction() {
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();

        int[] ghostsPos = new int[Constants.GHOST.values().length];
        int i=0;
        for (Constants.GHOST ghost: Constants.GHOST.values()){
            ghostsPos[i] = game.getGhostCurrentNodeIndex(ghost);
            i+=1;
        }

        int nearest = game.getClosestNodeIndexFromNodeIndex(currentNodeIndex, ghostsPos, Constants.DM.PATH);

//        System.out.println(nearest);
        if (nearest != -1) {
            if (DEBUGGING) GameView.addPoints(game, Color.WHITE, currentNodeIndex, nearest);
            move = game.getNextMoveAwayFromTarget(currentNodeIndex, nearest, Constants.DM.PATH);
        }
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (move != null) state = nodeState.Success;
        return state;
    }
}
