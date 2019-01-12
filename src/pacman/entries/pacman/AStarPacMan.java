package pacman.entries.pacman;
import pacman.controllers.Controller;
import pacman.game.Constants;
import pacman.game.Constants.MOVE;
import pacman.game.Game;
import pacman.game.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;


public class AStarPacMan extends Controller<MOVE> {

    public MOVE getMove(Game game, long timeDue) {

        int currentPos = game.getPacmanCurrentNodeIndex();

        List<Integer> activePills = Arrays.stream(game.getActivePillsIndices()).boxed().collect(Collectors.toList());
        List<Integer> activePowerPills = Arrays.stream(game.getActivePowerPillsIndices()).boxed().collect(Collectors.toList());

        int[] junctions = game.getJunctionIndices();

        List<Integer> junctionsList = new ArrayList<>();
        List<Integer> directJunctions = new ArrayList<>();
        List<Integer> safeDirectJunctions = new ArrayList<>();

        HashMap<Integer, ArrayList<Integer>> junctionPaths = new HashMap<>();

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
                int reward =0;
                int cost =0;

                if (activePills.contains(neighbour)) reward =1;
                else cost =1;

                int new_reward = rewardSoFar.get(currentNode) + reward;
                int new_cost = costSoFar.get(currentNode) + cost;


                if (!costSoFar.keySet().contains(neighbour) && game.getShortestPathDistance(currentPos,neighbour) < 60 ) {
                    if (junctionsList.contains(neighbour) || activePowerPills.contains(neighbour) || activePills.contains(neighbour))  {
                        directJunctions.add(neighbour);
                        frontier.add(neighbour);
                        if (activePills.contains(cameFrom.get(neighbour))) directJunctions.remove(cameFrom.get(neighbour));
                        rewardSoFar.put(neighbour,reward);
                        costSoFar.put(neighbour,cost);
                        cameFrom.put(neighbour, currentNode);
                    }
                    else  {
                        frontier.add(neighbour);
                        rewardSoFar.put(neighbour, reward);
                        costSoFar.put(neighbour,cost);
                        cameFrom.put(neighbour, currentNode);
                    }
                }
            }
        }

        for (int junction: directJunctions){
            boolean safe = true;
            for (Constants.GHOST ghost : Constants.GHOST.values()) {
                if (game.getGhostEdibleTime(ghost) == 0 && game.getGhostLairTime(ghost) == 0 &&
                        game.getShortestPathDistance(currentPos, junction) + 3 >
                                game.getShortestPathDistance(game.getGhostCurrentNodeIndex(ghost), junction)) {
                    safe = false;
                }
            }
            if (safe) {

                int goal = junction;
                ArrayList<Integer> path = new ArrayList<>();
                while ( goal != currentPos){
                    path.add(goal);
                    goal = cameFrom.get(goal);
                }

                int[] pathToJunction = new int[path.size()];
                for (int i=0;i<path.size();i+=1) pathToJunction[i]=path.get(i);

//                GameView.addPoints(game, Color.GREEN, pathToJunction);
//                GameView.addLines(game,Color.GREEN, currentPos,junction );

                junctionPaths.put(junction,path);
                safeDirectJunctions.add(junction);
            }
        }


        int maxUtilityNode = -1;
        int maxPills = 0;
        int minDistanceToPill = 999;

        for (Integer safeSpot : junctionPaths.keySet()) {
            int pills = 0;

            ArrayList<Integer> pathToSafeSpot = junctionPaths.get(safeSpot);

            int nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePillsIndices(), Constants.DM.PATH);
            if (nearestPill == -1) nearestPill = game.getClosestNodeIndexFromNodeIndex(safeSpot,game.getActivePowerPillsIndices(), Constants.DM.PATH);

            int distanceToPill = game.getShortestPathDistance(safeSpot,nearestPill);

            for (int safePathStep : pathToSafeSpot) { if(activePills.contains(safePathStep)) pills +=1; }

            if ( pills > maxPills ) { maxUtilityNode = safeSpot; maxPills = pills; }
            else if (pills == maxPills && maxPills == 0 && distanceToPill < minDistanceToPill) { maxUtilityNode = safeSpot; minDistanceToPill = distanceToPill; }

        }

        MOVE currentMove;
        if (maxUtilityNode!=-1) {
            int[] pathToJunction = new int[junctionPaths.get(maxUtilityNode).size()];
            for (int i = 0; i < junctionPaths.get(maxUtilityNode).size(); i += 1)
                pathToJunction[i] = junctionPaths.get(maxUtilityNode).get(i);
//            GameView.addPoints(game, Color.magenta, pathToJunction);
            currentMove = game.getNextMoveTowardsTarget(currentPos, maxUtilityNode, Constants.DM.PATH);
        }
        else currentMove = MOVE.NEUTRAL;
        return currentMove;

    }
}























