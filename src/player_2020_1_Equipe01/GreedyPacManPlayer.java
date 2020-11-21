package player_2020_1_Equipe01;

import pacman.*;

import java.util.*;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class GreedyPacManPlayer implements PacManPlayer, StateEvaluator {

//    int DEBUG_randomCounter = 0;
  /**
   * Chooses a Move for Pac-Man in the Game.
   * 
   * @param game
   * @return a Move for Pac-Man
   */

//  Random rand = new Random();
  int oppositeMove_Counter = 0;
  Move lastMove = Move.NONE;
  int turnaroundPenalty = 0;




  public Move chooseMove(Game game) {
      //TODO Para evitar loop: Gerar um randInt que seleciona um movimento do LegalMoves
      //TODO Para evitar loop: Executar uma booleana que detecta se o PacMan já executou um movimento reverso.
      State s = game.getCurrentState();

      HashMap<State, Move> nextNodes = new HashMap<>();

      for (Move m : game.getLegalPacManMoves()){
          //Coloca os movimentos legais em um HashMap que liga o estado diretamente com o movimento.
          //A instabilidade na ordem dos itens no HashMap adiciona um elemento orgânico de aleatoriedade.
            nextNodes.put(game.getNextState(game.getCurrentState(), m), m);
      }


      System.out.println("Estados seguintes: " + nextNodes.entrySet());                                  //DEBUG
      for (Map.Entry node : nextNodes.entrySet()) {
          System.out.println("Pontuacao do estados seguintes: " + evaluateState((State) node.getKey())); //DEBUG
      }

      State bestNode = game.getNextState(game.getCurrentState(), Move.NONE);
      System.out.println("Estado atual: " + game.getCurrentState());                                     //DEBUG
      System.out.println("Pontuacao do estado atual: " + evaluateState(bestNode));                       //DEBUG
      Move bestMove = Move.NONE;

//      System.out.println(bestMove);


      for (Map.Entry node : nextNodes.entrySet()) {


          State nextNode = (State) node.getKey();
//          System.out.println("Último movimento: " + lastMove);
//          System.out.println(nextNodes.get(nextNode));
//          System.out.println(" ");

          double turnaroundPenalty = (lastMove == nextNodes.get(nextNode).getOpposite() ? -10.0 : 0.0);

          if (evaluateState(nextNode) + turnaroundPenalty >= evaluateState(bestNode)) {  //Isso evita que o PacMan faca o mesmo movimento anterior sem considerar danos graves
              bestNode = nextNode;
              bestMove = nextNodes.get(bestNode);
          }
      }
      System.out.println("Movimento escolhido: " + bestMove);
      System.out.println(" ");

      lastMove = bestMove;
      return bestMove;
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
     heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO



      //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA
      heuristic -= allDotLoc.size();
      //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA


      //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
      heuristic -= pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
      //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO



      return heuristic;

  }



}
