package pacman.entries.pacman.behaviourTree.Leafs;
import pacman.game.Constants.GHOST;
import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Game;
import pacman.game.GameView;
import java.awt.*;

public class IsPacManInDanger extends Node {
//    private int MIN_DISTANCE = 21;

    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        int current = game.getPacmanCurrentNodeIndex();
        int safe = 0 ;
        for (GHOST ghost : GHOST.values())
            if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0)
                if (game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost)) < MIN_DISTANCE) {
                    if (DEBUGGING) GameView.addLines(game, Color.RED, game.getGhostCurrentNodeIndex(ghost), game.getPacmanCurrentNodeIndex() );
                    safe +=1 ;
                }

        if (safe < MIN_CLOSE_GHOSTS) return nodeState.Failure;



        int maxDistance = -1;
        for (GHOST ghost1 : GHOST.values()){
            if (!game.isGhostEdible(ghost1) && game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost1)) < MIN_DISTANCE)
                for (GHOST ghost2 : GHOST.values())
                    if (!game.isGhostEdible(ghost2) && game.getShortestPathDistance(current, game.getGhostCurrentNodeIndex(ghost2)) < MIN_DISTANCE)
                        if (!ghost1.equals(ghost2))
                        {
                            int distance = game.getManhattanDistance(game.getGhostCurrentNodeIndex(ghost1), game.getGhostCurrentNodeIndex(ghost2));
                            if (distance > maxDistance) maxDistance = distance;
                        }
        }

        if (maxDistance < GHOSTS_MAX_DISTANCE) return nodeState.Failure;

        return nodeState.Success;
    }
}
