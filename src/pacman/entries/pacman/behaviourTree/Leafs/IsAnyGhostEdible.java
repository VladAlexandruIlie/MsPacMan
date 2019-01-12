package pacman.entries.pacman.behaviourTree.Leafs;
import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants.*;
import pacman.game.Game;

public class IsAnyGhostEdible extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        for (GHOST ghost : GHOST.values()){
            if (game.isGhostEdible(ghost) && game.getGhostEdibleTime(ghost) >  game.getShortestPathDistance(game.getPacmanCurrentNodeIndex(),game.getGhostCurrentNodeIndex(ghost)) - GHOST_EAT_RANGE )
                   return nodeState.Success;
        }
        return nodeState.Failure;
    }
}
