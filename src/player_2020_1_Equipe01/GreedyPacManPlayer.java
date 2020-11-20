package player_2020_1_Equipe01;

import pacman.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class GreedyPacManPlayer implements PacManPlayer, StateEvaluator {

    int DEBUG_randomCounter = 0;
  /**
   * Chooses a Move for Pac-Man in the Game.
   * 
   * @param game
   * @return a Move for Pac-Man
   */

  Random rand = new Random();

  public Move chooseMove(Game game) {
      //TODO Para evitar loop: Gerar um randInt que seleciona um movimento do LegalMoves
      //TODO Para evitar loop: Executar uma booleana que detecta se o PacMan já executou um movimento reverso.
      State s = game.getCurrentState();

//      HashMap<Move, State> nextNodes = new HashMap<>();

      List<Move> legalMoves = game.getLegalPacManMoves();

      State bestNode = game.getNextState(game.getCurrentState(), Move.NONE);
      Move bestMove = Move.NONE;

      List <Move> bestMoveProspectors = new ArrayList<>();
      bestMoveProspectors.add(bestMove);

      for (Move m : legalMoves){
          State nextNode = game.getNextState(game.getCurrentState(), m);
          if (evaluateState(nextNode) >= evaluateState(bestNode)){
              bestNode = nextNode;
              bestMove = m;
          }
//          else if (evaluateState(nextNode) == evaluateState(bestNode)){
//              bestMoveProspectors.add(m);
//          }
      }
//      if (bestMoveProspectors.size() > 1){
//          System.out.println("RANDOM!" + DEBUG_randomCounter);
//          DEBUG_randomCounter++;
//          int randomMove = rand.nextInt(bestMoveProspectors.size());
//          bestMove = bestMoveProspectors.get(randomMove);
//      }

      return bestMove;
  }



  /**
   * Computes an estimate of the value of the State.
   * @param s the State to evaluate.
   * @return an estimate of the value of the State.
   */
  public double evaluateState(State state) {
     double heuristic = 1;

    //TESTE DE FUNCAO OBJETIVO
    if (Game.isLosing(state))
      heuristic += Double.NEGATIVE_INFINITY;
    else if (Game.isWinning(state))
      heuristic +=  Double.POSITIVE_INFINITY;
    //TESTE DE FUNCAO OBJETIVO



//
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
     Location pacLOC = state.getPacManLocation();
     List<Location> allGhostLoc = state.getGhostLocations();

     heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO



      //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA
      LocationSet allDotLoc = state.getDotLocations();
      heuristic -= allDotLoc.size();
      //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA


      //HEURÍSTICA 3: DISTÂNCIA ENTRE O PAMAN E O PONTO MAIS PRÓXIMO
      heuristic -= pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
      //HEURÍSTICA 3: DISTÂNCIA ENTRE O PAMAN E O PONTO MAIS PRÓXIMO
      


      return heuristic;

  }

}
