package pacman.entries.pacman.behaviourTree;
import pacman.game.Game;

public class Sequence extends Node{

    @Override
    public void doAction() {
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;

        for (Node child: children){
            nodeState stateChild = child.getState(game);
            if (stateChild.equals(nodeState.Failure)) return nodeState.Failure;
        }
        return nodeState.Success;
    }

}
