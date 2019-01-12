package pacman.entries.pacman.behaviourTree.Leafs;
import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Game;

public class IsAnyPowerPillActive extends Node {
    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        if (game.getActivePowerPillsIndices().length == 0) return  nodeState.Failure;
        return nodeState.Success;
    }
}
