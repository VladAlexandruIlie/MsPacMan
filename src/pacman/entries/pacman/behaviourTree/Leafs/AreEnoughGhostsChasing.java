package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;

public class AreEnoughGhostsChasing extends Node {

    @Override
    public void doAction() {

    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        int closeHostileGhosts = 0;
        int currentNodeIndex = game.getPacmanCurrentNodeIndex();

        for (Constants.GHOST ghost : Constants.GHOST.values()){
            if ( (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 ) &&
                    game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost),currentNodeIndex) <= GHOST_PROXIMITY_THRESHOLD) {
                if (DEBUGGING) GameView.addLines(game, Color.orange, game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex() );
                closeHostileGhosts +=1;
            }
        }

        if (closeHostileGhosts >= HOSTILE_GHOSTS_THRESHOLD) return  nodeState.Success;
        return nodeState.Failure;
    }
}
