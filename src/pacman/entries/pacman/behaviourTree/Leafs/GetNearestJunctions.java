package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class GetNearestJunctions extends Node {
    @Override
    public void doAction() {
        int currentPos = game.getPacmanCurrentNodeIndex();
        int[] junctions = game.getJunctionIndices();
        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> activePowerPills = Arrays.stream(game.getActivePowerPillsIndices()).boxed().collect(Collectors.toList());

        List<Integer> junctionsList = new ArrayList<>();
        List<Integer> directTargets = new ArrayList<>();
        List<Integer> safeDirectTargets = new ArrayList<>();

        HashMap<Integer, ArrayList<Integer>> targetPaths = new HashMap<>();

        for (int junction : junctions) junctionsList.add(junction);

        List<Integer> frontier = new ArrayList<>();

        frontier.add(currentPos);

        HashMap<Integer, Integer> cameFrom = new HashMap<>();
        HashMap<Integer, Integer> rewardSoFar = new HashMap<>();
        HashMap<Integer, Integer> costSoFar = new HashMap<>();

        cameFrom.put(currentPos,-1);
        rewardSoFar.put(currentPos,0);
        costSoFar.put(currentPos,0);

        while (!frontier.isEmpty()){
            int currentNode = frontier.get(0);
            frontier.remove(0);

            for (int neighbour: game.getNeighbouringNodes(currentNode)){

                int newReward = 0;
                int newCost = 0;

                if (junctionsList.contains(neighbour)) {
                    newReward = rewardSoFar.get(currentNode) + 1;
                }
                if (activePowerPills.contains(neighbour)){
                    newCost = costSoFar.get(currentNode) + 1;
                }

                boolean safe = true;
                for (Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 &&
                            game.getShortestPathDistance(currentPos, neighbour) + GHOST_EAT_RANGE  >
                            game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), neighbour)
//                              &&  game.getGhostLastMoveMade(ghost).equals(game.getNextMoveTowardsTarget(game.getGhostCurrentNodeIndex(ghost), neighbour, Constants.DM.PATH))
                            ) {

                            safe = false;
                    }
                }
                if (safe) {
                    if ((!costSoFar.keySet().contains(neighbour)            )
                            && game.getShortestPathDistance(currentPos, neighbour) < AWARENESS_THRESHOLD) {

                        if (junctionsList.contains(neighbour) ) {
                            directTargets.add(neighbour);

//                            frontier.add(neighbour);
//                            int goal = neighbour;
//                            while ( goal != currentPos){
//                                if (!junctionsList.contains(goal))
//                                    if (directTargets.contains(directTargets.indexOf(goal))) directTargets.remove(directTargets.indexOf(goal));
//                                goal = cameFrom.get(goal);

                        }

                        else if (activePills.contains(neighbour) || activePowerPills.contains(neighbour)){
                            directTargets.add(neighbour);
                            frontier.add(neighbour);
                        }

                        else {
                            frontier.add(neighbour);
                        }

                        rewardSoFar.put(neighbour, newReward);
                        costSoFar.put(neighbour, newCost);
                        cameFrom.put(neighbour, currentNode);

                    }
                }
            }
        }


        for (int safeTarget: directTargets){
            int goal = safeTarget;
            ArrayList<Integer> path = new ArrayList<>();
            while ( goal != currentPos){
                path.add(goal);
                goal = cameFrom.get(goal);
            }

            if (DEBUGGING) {
                int[] pathToJunction = new int[path.size()];
                for (int i=0;i<path.size();i+=1) pathToJunction[i]=path.get(i);
                GameView.addPoints(game, Color.GREEN, pathToJunction);
//                GameView.addLines(game,Color.GREEN, currentPos,safeTarget );
            }

            targetPaths.put(safeTarget,path);
            safeDirectTargets.add(safeTarget);
        }

        Node.junctionPaths = targetPaths;
        Node.nearestTargets = safeDirectTargets;
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (Node.nearestTargets.isEmpty()) return nodeState.Failure;
        return nodeState.Success;
    }
}
