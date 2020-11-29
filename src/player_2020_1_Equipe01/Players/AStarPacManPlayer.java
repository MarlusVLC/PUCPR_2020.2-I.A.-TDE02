package player_2020_1_Equipe01.Players;

import pacman.Game;
import pacman.Move;
import pacman.PacManPlayer;
import player_2020_1_Equipe01.Utilitarios.GeneralEvaluator;
import player_2020_1_Equipe01.Utilitarios.Node;

import java.util.*;

public class AStarPacManPlayer implements PacManPlayer {

    GeneralEvaluator eval = new GeneralEvaluator();
    Move lastMove = Move.NONE;


    int depth = 4;



    @Override
    public Move chooseMove(Game game) {
        double lowestCost = Double.POSITIVE_INFINITY;
        Node bestNode = null;

        Node origin = new Node(game, Move.NONE, game.getCurrentState(), 0);
        List<Node> allNodes = new ArrayList<>();
        allNodes.addAll(origin.getChildren());

        System.out.println("");
        System.out.println(origin.getPacManLocation());



        Node visitedNode = null;

        while (bestNode == null){

            for (Node node : allNodes){
                System.out.print(node.getMove());
                System.out.print(node.getLevel());
                System.out.println(node.evaluateState(eval));
                if (node.AStarScore(eval) <= lowestCost){
                    lowestCost = node.AStarScore(eval);
                    visitedNode = node;
                }
            }



            //Se estiver encurralado, só execute o último movimento
            if (isCornered(allNodes, game)){
                return lastMove;
            }


            if (visitedNode.getLevel() < depth && visitedNode.getLevel() > 0) {
                allNodes.addAll(visitedNode.getChildren());
                System.out.println(" k");
                System.out.println(allNodes.size());
            }
            else if (visitedNode.getLevel() == depth)
                bestNode = visitedNode;
            else{
                throw new ArithmeticException("Os nós só podem ir até o nível de profundidade indicado");
            }


            allNodes.remove(visitedNode);
            visitedNode = null;
            lowestCost = Double.POSITIVE_INFINITY;

        }


        Node chosenNode = bestNode.getOriginator(bestNode);



        lastMove = chosenNode.getMove();

//        System.out.println(" ");
        return chosenNode.getMove();
    }

    private boolean isCornered(List<Node> nodeList, Game game){
        for (Node node : nodeList){
            if (!game.isFinal(node.getState()))
                return false;
        }
        return true;
    }
}



