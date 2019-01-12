package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class GetSafeToReachPowerPill extends Node {
    @Override
    public void doAction() {
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();
        int[] activePowerPills = game.getActivePowerPillsIndices();

        List<Integer> targetNodeIndices = new ArrayList<>();

        for (int activePowerPill : activePowerPills) targetNodeIndices.add(activePowerPill);

        List<Integer> safePowerPillsIndices = new ArrayList<>();

        for (int potentialPP :   targetNodeIndices){
            boolean safe = true;

            for (Constants.GHOST ghost : Constants.GHOST.values()){
                if (game.getShortestPathDistance(currentNodeIndex, potentialPP) + GHOST_EAT_RANGE  >
                        game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), potentialPP)  ) {
                    safe =false;
                    if (DEBUGGING) GameView.addLines(game, Color.RED, currentNodeIndex, potentialPP);
                }
                else { if (DEBUGGING) GameView.addLines(game,Color.GREEN,currentNodeIndex,potentialPP); }
            }
            if (safe) safePowerPillsIndices.add(potentialPP);
        }
        Node.safeToReachPowerPills = safePowerPillsIndices;
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (Node.safeToReachPowerPills.isEmpty()) return nodeState.Failure;
        return nodeState.Success;
    }
}
