package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;

public class WaitForGhostsToGetCloser extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game=game;
        move = Constants.MOVE.NEUTRAL;
        return state = nodeState.Success;
    }
}
