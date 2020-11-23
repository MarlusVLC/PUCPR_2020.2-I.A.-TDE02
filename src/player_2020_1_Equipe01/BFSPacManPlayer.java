package player_2020_1_Equipe01;

import pacman.*;

import java.util.*;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class BFSPacManPlayer implements PacManPlayer, StateEvaluator {

    /**
     * Chooses a Move for Pac-Man in the Game.
     *
     * @param game
     * @return a Move for Pac-Man
     */

    Move lastMove = Move.NONE;

//    int turnaroundPenalty = 0;

    int depth = 2;



    public Move chooseMove(Game game) {
       double bestScore = Double.NEGATIVE_INFINITY;
       Node bestNode = null;

        Node origin = new Node(game, Move.NONE, game.getCurrentState(), 0);
        List<List<Node>> allNodes = new ArrayList<>(depth+1);
        while (allNodes.size() < depth+1){
            allNodes.add(new ArrayList<>());
        }

        System.out.println(allNodes.size());
        List<Node> level0 = new ArrayList<>();
        level0.add(origin);
        allNodes.add(level0);
        int i = 0;
        while (i < depth){
            for (Node node : allNodes.get(i)){
                for (Node child : node.getChildren()) {
                    allNodes.get(child.getLevel()).add(child);
                }
            }
            i++;
        }

        System.out.println(allNodes);


        for (Node node : allNodes.get(depth)){
            double currScore = node.evaluateState(this);
            if (currScore >= bestScore){
                bestScore = currScore;
                bestNode = node;
            }
        }

        Node chosenNode = bestNode;

        while (chosenNode.getParent().hasParent()){
            chosenNode = chosenNode.getParent();
        }

        lastMove = chosenNode.getMove();

        System.out.println("");

        return chosenNode.getMove();
    }







    /**
     * Computes an estimate of the value of the State.
     * @param s the State to evaluate.
     * @return an estimate of the value of the State.
     */
    public double evaluateState(State state) {
        Location pacLOC = state.getPacManLocation();
        List<Location> allGhostLoc = state.getGhostLocations();
        LocationSet allDotLoc = state.getDotLocations();


        double heuristic = 1;

        //TESTE DE FUNCAO OBJETIVO
        if (Game.isLosing(state))
            heuristic += Double.NEGATIVE_INFINITY;
//        heuristic += 10000;
        else if (Game.isWinning(state))
            heuristic +=  Double.POSITIVE_INFINITY;
//        heuristic -= 10000;
        //TESTE DE FUNCAO OBJETIVO



//
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
//        heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO



        //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA
        heuristic -= allDotLoc.size();
        //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA


        //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
//        heuristic -= pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
        //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO



        return heuristic;

    }

//    private int breadth_first_search(Game game, State state, int depth) {
//        State s = game.getCurrentState();
//
//        HashMap<State, Move> nextNodes = new HashMap<>();
//
//        for (Move m : game.getLegalPacManMoves()) {
//            nextNodes.put(game.getNextState(game.getCurrentState(), m), m);
//        }
//    }




}
