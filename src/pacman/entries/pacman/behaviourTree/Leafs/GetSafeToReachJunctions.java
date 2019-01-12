package pacman.entries.pacman.behaviourTree.Leafs;

import pacman.entries.pacman.behaviourTree.Node;
import pacman.game.Constants;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetSafeToReachJunctions extends Node {
    @Override
    public void doAction() {
        int currentPos = game.getPacmanCurrentNodeIndex();
        int[] junctions = game.getJunctionIndices();

//        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
//        List<Integer> activePowerPills = Arrays.stream(game.getActivePowerPillsIndices()).boxed().collect(Collectors.toList());

        List<Integer> junctionsList = new ArrayList<>();
        List<Integer> directJunctions = new ArrayList<>();
        List<Integer> safeDirectJunctions = new ArrayList<>();

        HashMap<Integer, ArrayList<Integer>> junctionPaths = new HashMap<>();

        for (int junction : junctions) junctionsList.add(junction);

        List<Integer> frontier = new ArrayList<>();
        HashMap<Integer, Integer> cameFrom = new HashMap<>();
        HashMap<Integer, Integer> costSoFar = new HashMap<>();

        frontier.add(currentPos);
        cameFrom.put(currentPos,-1);
        costSoFar.put(currentPos,0);

        while (!frontier.isEmpty()){
            int currentNode = frontier.get(0);
            frontier.remove(0);

            for (int neighbour: game.getNeighbouringNodes(currentNode)){

                int newCost = costSoFar.get(currentNode) + 1;

                boolean safe = true;
                for (Constants.GHOST ghost : Constants.GHOST.values()) {
                    if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 &&
                            game.getShortestPathDistance(currentPos, neighbour) + GHOST_EAT_RANGE  >
                                    game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), neighbour) ) {
                        safe = false;
                    }
                }

                if (safe) {
                    if (!costSoFar.keySet().contains(neighbour)) {

                        if (junctionsList.contains(neighbour)) {
                            directJunctions.add(neighbour);
                            frontier.add(neighbour);
                        } else {
                            frontier.add(neighbour);
                        }

                        costSoFar.put(neighbour, newCost);
                        cameFrom.put(neighbour, currentNode);

                    }
                }
            }
        }

        for (int junction: directJunctions){
            int goal = junction;
            ArrayList<Integer> path = new ArrayList<>();
            while ( goal != currentPos){
                path.add(goal);
                goal = cameFrom.get(goal);
            }

            int[] pathToJunction = new int[path.size()];
            for (int i=0;i<path.size();i+=1) pathToJunction[i]=path.get(i);

            if (DEBUGGING) {
//                    GameView.addPoints(game, Color.YELLOW, pathToJunction);
                GameView.addLines(game, Color.YELLOW, currentPos,junction );
            }

            junctionPaths.put(junction,path);
            safeDirectJunctions.add(junction);
        }

        Node.junctionPaths = junctionPaths;
        Node.nearestTargets = safeDirectJunctions;
    }

    @Override
    public nodeState getState(Game game) {
        Node.game = game;
        doAction();
        if (Node.nearestTargets.isEmpty()) return nodeState.Failure;
        return nodeState.Success;
    }

}
