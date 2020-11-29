package player_2020_1_Equipe01.Players;

import pacman.*;
import player_2020_1_Equipe01.Utilitarios.GeneralEvaluator;
import player_2020_1_Equipe01.Utilitarios.Node;

import java.util.*;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class BFSPacManPlayer implements PacManPlayer {

    /**
     * Chooses a Move for Pac-Man in the Game.
     *
     * @param game
     * @return a Move for Pac-Man
     */

    GeneralEvaluator eval = new GeneralEvaluator();
    Move lastMove = Move.NONE;


    int depth = 9;




    public Move chooseMove(Game game) {
       double bestScore = Double.NEGATIVE_INFINITY; //f(n) inicial
       Node bestNode = null;

        Node origin = new Node(game, Move.NONE, game.getCurrentState(), 0); //Nó original: Estado atual;
        List<List<Node>> allNodes = new ArrayList<>(depth+1); //Árvore de todos os nós
        while (allNodes.size() < depth+1){ //Determina o tamanho inicial da lista, de acordo com o nível de profundidade
            allNodes.add(new ArrayList<>());
        }

        allNodes.get(0).add(origin); //Adiciona o nó original no nível 0 (uma lista unitária) da árvore
        System.out.println("OriginPos" + origin.getPacManLocation());
        for (int i = 0; i < depth; i++){ //Até chegar no último nível da árvore
            for (Node node : allNodes.get(i)){  //Percorre por todos os nós de cada nível
                for (Node child : node.getChildren()) { //Expande o nó e percorre as "crianças"
                    allNodes.get(child.getLevel()).add(child); //Adiciona cada criança no seu adequado,

                    System.out.print(child.getLevel());
                    System.out.print(child.getMove());
                    System.out.print(child.getParent().getMove());
                    System.out.println(child.getPacManLocation());
                    }

//                EVITA QUE O PACMAN EXECUTE LOOPING
                if (lastMove != Move.NONE && i == 0) { //Checa se estamos no primeiro nível
                    int backwardNode_index = 0; //Para guardar a posição do nó de movimento inverso na lista
                    for (Node node1 : allNodes.get(1)) { //Percorre todos os nós do primeiro nível
                        if (node1.getMove().equals(lastMove.getOpposite()) ) //Verifica se seu movimento é igual ao ultimo movimento executado
                            backwardNode_index = allNodes.get(1).indexOf(node1); //Pega o índice
                    }
                    allNodes.get(1).remove(backwardNode_index); //Remove o nó que tem o último movimento e o remove do primeiro nível
                }
//                EVITA QUE O PACMAN EXECUTE LOOPING
            }
        }


        //FINALIZA A EXPANSÃO CASO ENCONTRE UM ESTADO FINAL
        int deepestLevel = allNodes.size()-1; //Guarda o valor do último nível
        while (allNodes.get(deepestLevel).isEmpty()){ //Enquanto o último nível estiver vazio
            allNodes.remove(deepestLevel); //Apaga o último nível
            deepestLevel = allNodes.size()-1; //Redefine para o novo último nível
        }


        //EXECUTA A COMPARAÇÃO ENTRE AS AVALIAÇÕES ENTRE OS DIFERENTES ESTADOS/NÓS
        for (Node node : allNodes.get(deepestLevel)){ //Percorre todos os nós do último nível
            double currScore = node.evaluateState(eval); //Guarda a h(n) do nó/estado atual;
            System.out.println(currScore);
            if (currScore >= bestScore){ //Se h(n) for maior - bestScore inicialmente é -INFINITO, então nada é menor ou igual.
                bestScore = currScore; //Associa o h(n) atual como melhor avaliação
                bestNode = node; //Associa o nó que gerou o melhor h(n) como melhor nó.
            }
        }


        //FAZ A REGRESSÃO PARA O PRIMEIRO NÍVEL E ASSOCIA O NÓ-ESCOLHEDOR AO ORIGINADOR DO MELHOR NÓ.
        Node chosenNode = bestNode.getOriginator(bestNode);


        lastMove = chosenNode.getMove(); //Guarda o movimento à ser executado como último movimento.

        System.out.println(" ");

        return chosenNode.getMove(); //retorna o movimento à partir do nó selecionado.
    }
}
