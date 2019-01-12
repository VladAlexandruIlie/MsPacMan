package pacman.entries.pacman.behaviourTree;

import pacman.game.Constants;
import pacman.game.Game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Base abstract class for all the tasks in the behavior tree.
 */
public abstract class Node {
    public static Game game;
    public static Constants.MOVE move;

//  Final  Thresholds
    protected static int GHOST_EAT_RANGE = 3;
    protected static int BAIT_THRESHOLD = 2;

//  Variable  Thresholds
    // for testing
    protected static boolean DEBUGGING ;
    // "eat Power Pill" Sequence
    protected static int GHOST_PROXIMITY_THRESHOLD ;
    protected static int HOSTILE_GHOSTS_THRESHOLD ;
    // "chase Ghosts" Sequence
    protected static int CHASE_THRESHOLD ;
    // "survive" Sequence
    protected static int MIN_DISTANCE ;
    protected static int MIN_CLOSE_GHOSTS ;
    protected static int GHOSTS_MAX_DISTANCE;
    // "wonder" Sequence
    protected static int AWARENESS_THRESHOLD ;


    public enum nodeState {
        Success,
        Failure,
        Running
    }
    protected static nodeState state;

    protected static  List<Integer> safeToReachTargets = new ArrayList<>();
    protected static  List<Integer> safeToReachPowerPills = new ArrayList<>();
    protected static  List<Integer> safeToReachJunctions = new ArrayList<>();
    protected static  List<Integer> nearestTargets = new ArrayList<>();
    protected static  HashMap<Integer, ArrayList<Integer>> junctionPaths = new HashMap<>();

    List<Node> children = new ArrayList<Node>();
    public void addChildren(Node child) {
        this.children.add(child);
    }

    public void setParameters( boolean DEBUGING,
                               int GHOST_PROXIMITY_THRESHOLD ,
                               int HOSTILE_GHOSTS_THRESHOLD,
                               int CHASE_THRESHOLD,
                               int MIN_DISTANCE,
                               int MIN_CLOSE_GHOSTS,
                               int GHOSTS_MAX_DISTANCE,
                               int AWARENESS_THRESHOLD
                               ){
        Node.DEBUGGING = DEBUGING;
        Node.GHOST_PROXIMITY_THRESHOLD = GHOST_PROXIMITY_THRESHOLD;
        Node.HOSTILE_GHOSTS_THRESHOLD = HOSTILE_GHOSTS_THRESHOLD;
        Node.MIN_DISTANCE = MIN_DISTANCE;
        Node.CHASE_THRESHOLD = CHASE_THRESHOLD;
        Node.MIN_CLOSE_GHOSTS = MIN_CLOSE_GHOSTS;
        Node.GHOSTS_MAX_DISTANCE = GHOSTS_MAX_DISTANCE;
        Node.AWARENESS_THRESHOLD = AWARENESS_THRESHOLD;
    }

    /**
     * Override to specify the logic the node
     */
    public abstract void doAction();

    /**
     * Goes through the tree according to how it was built and finds the state that best suits the game state
     * @return The specific task state.
     */
    public abstract nodeState getState(Game game);
}