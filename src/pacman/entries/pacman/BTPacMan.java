package pacman.entries.pacman;
import pacman.controllers.Controller;
import pacman.entries.pacman.behaviourTree.Inverter;
import pacman.entries.pacman.behaviourTree.Leafs.*;
import pacman.entries.pacman.behaviourTree.Node;
import pacman.entries.pacman.behaviourTree.Selector;
import pacman.entries.pacman.behaviourTree.Sequence;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;


public class BTPacMan extends Controller<MOVE> {
    private static Node root;

    public MOVE getMove(Game game, long timeDue) {

        if (root == null) {
            root = BehaviourTree(false, 30, 2, 25, 60, 3, 40, 80 );
//
//            root = BehaviourTree(false,  33,   4,  48,  29,   2,  67, 161);

        }

        MOVE currentMove = MOVE.NEUTRAL;
        if (root.getState(game).equals(Node.nodeState.Success)) currentMove = Node.move;
//        System.out.println(Node.move);
        return currentMove;

    }

    public void setRoot(Node newRoot){
        root = newRoot;
    }

    public static Node BehaviourTree(boolean DEBUGING,
                                     int GHOST_PROXIMITY_THRESHOLD ,
                                     int HOSTILE_GHOSTS_THRESHOLD,
                                     int CHASE_THRESHOLD,
                                     int MIN_DISTANCE,
                                     int MIN_CLOSE_GHOSTS,
                                     int GHOSTS_MAX_DISTANCE,
                                     int AWARENESS_THRESHOLD
                                     ){
        root  = BehaviourTree();
        root.setParameters(DEBUGING, GHOST_PROXIMITY_THRESHOLD, HOSTILE_GHOSTS_THRESHOLD, CHASE_THRESHOLD, MIN_DISTANCE, MIN_CLOSE_GHOSTS ,GHOSTS_MAX_DISTANCE ,AWARENESS_THRESHOLD );
        return root;
    }

    public static Node BehaviourTree() {
        root = new Selector();


        Sequence eatPowerPill = new Sequence();
            eatPowerPill.addChildren( new IsAnyPowerPillActive());                                          // Uses no parameters
            eatPowerPill.addChildren( new AreEnoughGhostsChasing());                                        // Uses GHOST_PROXIMITY_THRESHOLD , HOSTILE_GHOSTS_THRESHOLD , DEBUGGING
            eatPowerPill.addChildren( new GetSafeToReachPowerPill());                                       // Uses GHOST_EAT_RANGE , DEBUGGING

            Selector decision = new Selector();
                Sequence eat = new Sequence();
                    eat.addChildren(new IsNearestGhostVeryClose());                                         // Uses GHOST_EAT_RANGE , BAIT_THRESHOLD , DEBUGGING
                    eat.addChildren(new MoveToNearestPowerPill());                                          // Uses DEBUGGING
                Sequence notNextToPP = new Sequence();
                    Inverter notInPos = new Inverter(); notInPos.addChildren(new IsPacManNextToPP());       // Uses no parameters
                    notNextToPP.addChildren( notInPos);
                    notNextToPP.addChildren(new MoveToPowerPillNeighbour());                                // Uses DEBUGGING
                decision.addChildren(eat);
                decision.addChildren(notNextToPP);
                decision.addChildren(new WaitForGhostsToGetCloser());                                       // Uses no parameters
            eatPowerPill.addChildren(decision);

        Sequence chaseGhosts = new Sequence();
            chaseGhosts.addChildren( new IsAnyGhostEdible());                                               // Uses GHOST_EAT_RANGE
            chaseGhosts.addChildren( new IsAnyGhostClose());                                                // Uses CHASE_THRESHOLD
            chaseGhosts.addChildren( new IsPathToClosestGhostSafe());                                       // Uses DEBUGGING
            chaseGhosts.addChildren( new MoveToNearestGhost());                                             // Uses DEBUGGING

        Sequence survive = new Sequence();
            survive.addChildren( new IsPacManInDanger());                                                   // Uses MIN_DISTANCE , MIN_CLOSE_GHOSTS , DEBUGGING
            survive.addChildren( new GetSafeToReachJunctions());                                            // Uses GHOST_EAT_RANGE
            survive.addChildren( new MoveToSafestCloseTarget());                                            // Uses no parameters

        Sequence wonder = new Sequence();
            wonder.addChildren(new GetNearestJunctions());                                                   // Uses AWARENESS_THRESHOLD , GHOST_EAT_RANGE , DEBUGGING
            wonder.addChildren(new MoveToNearestMaxPillJunction());                                          // Uses DEBUGGING

        root.addChildren( eatPowerPill);
        root.addChildren( chaseGhosts);
        root.addChildren( survive);
        root.addChildren( wonder);
        root.addChildren( new MoveAwayFromNearestGhost());


        return root;
    }
}























