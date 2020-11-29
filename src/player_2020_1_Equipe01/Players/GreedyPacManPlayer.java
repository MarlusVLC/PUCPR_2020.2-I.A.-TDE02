package player_2020_1_Equipe01.Players;

import pacman.*;
import player_2020_1_Equipe01.Utilitarios.GeneralEvaluator;

import java.util.*;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class GreedyPacManPlayer implements PacManPlayer {

  /**
   * Chooses a Move for Pac-Man in the Game.
   * 
   * @param game
   * @return a Move for Pac-Man
   */


  GeneralEvaluator eval = new GeneralEvaluator();
  Move lastMove = Move.NONE;
  int turnaroundPenalty = 0;




  public Move chooseMove(Game game) {
      State s = game.getCurrentState();

      HashMap<State, Move> nextNodes = new HashMap<>();

      for (Move m : game.getLegalPacManMoves()){
          //Coloca os movimentos legais em um HashMap que liga o estado diretamente com o movimento.
          //A instabilidade na ordem dos itens no HashMap adiciona um elemento orgânico de aleatoriedade.
            nextNodes.put(game.getNextState(game.getCurrentState(), m), m);
      }




      State bestNode = game.getNextState(game.getCurrentState(), Move.NONE);
      Move bestMove = Move.NONE;

      for (Map.Entry node : nextNodes.entrySet()) {

          State nextNode = (State) node.getKey();

          if (eval.evaluateState(nextNode) + turnaroundPenalty >= eval.evaluateState(bestNode)) {  //Isso evita que o PacMan faca o mesmo movimento anterior sem considerar danos graves
              bestNode = nextNode;
              bestMove = nextNodes.get(bestNode);
          }
      }


      lastMove = bestMove;
      return bestMove;
  }



  /**
   * Computes an estimate of the value of the State.
   * @param s the State to evaluate.
   * @return an estimate of the value of the State.
   */




}
