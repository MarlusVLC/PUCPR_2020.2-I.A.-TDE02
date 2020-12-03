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


    int depth = 9;



    @Override
    public Move chooseMove(Game game) {
        double lowestCost = Double.POSITIVE_INFINITY;
        Node bestNode = null;

        Node origin = new Node(game, Move.NONE, game.getCurrentState(), 0);
        List<Node> allNodes = new ArrayList<>();
        allNodes.addAll(origin.getChildren());

        System.out.println("");
        System.out.println(origin.getPacManLocation());

        //                EVITA QUE O PACMAN EXECUTE LOOPING
        if (lastMove != Move.NONE) {
            int backwardNode_index = -1;
            for (Node node : allNodes){
                if (node.getMove().getOpposite() == lastMove)
                    backwardNode_index = allNodes.indexOf(node);
            }
            if (backwardNode_index > -1){
//                                allNodes.remove(backwardNode_index);  //LIGUE SE QUISER QUE ELE SEMPRE VÁ EM FRENTE
                allNodes.get(backwardNode_index).addPenaltyAddend(1000); //LIGUE SE QUISER ADICIONAR UMA PENALIDADE AO MOVIMENTO INVERSO AO INVÉS DE DELETAR.
            }
        }
//                EVITA QUE O PACMAN EXECUTE LOOPING



        Node visitedNode = null;
        List<Node> randomQuery = new ArrayList<>();

        while (bestNode == null){

            for (Node node : allNodes){
                System.out.print(node.getMove());
                System.out.print(node.getLevel());
                System.out.println(node.evaluateState(eval));
                if (node.AStarScore(eval) < lowestCost){
                    lowestCost = node.AStarScore(eval);
                    visitedNode = node;
                    randomQuery.clear();
                }
                //Se for igual adicione em um lista que gerárá um resultado aleaório
                else if (node.AStarScore(eval) == lowestCost){
                    randomQuery.add(node);
                    if (visitedNode != null && !randomQuery.contains(visitedNode)){
                        randomQuery.add(visitedNode);
                    }
                }
            }

            //ESCOLHER UM MOVIENTO ALEATÓRIO, CASO SEJAM TODOS IGUAIS
            if (!randomQuery.isEmpty()) {
                visitedNode = getRandomNode(randomQuery);
                randomQuery.clear();
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

    private Node getRandomNode(List<Node> nodeList){
//        Random r = new Random();
        System.out.println(nodeList.size());
        int randomNode = new Random().nextInt(nodeList.size());
        return nodeList.get(randomNode);
    }
}



