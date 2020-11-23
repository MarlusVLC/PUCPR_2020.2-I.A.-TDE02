package Adrian;

import pacman.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Use this class for your basic DFS player implementation.
 * @author grenager
 *
 */
public class AdrianPacManPlayer implements PacManPlayer, StateEvaluator {
  State nextState1;
  double d1;
  double d2 = 1000;
  int x;
  int currentX;
  int depth = 1;

  public Move chooseMove(Game game) {
    List<Move> legalMoves = game.getLegalPacManMoves();

    List<GhostPlayer> ghostPlayers = game.getGhostPlayers();
    List<Move> ghostMoves = new ArrayList<Move>();
    for (int i = 0; i < ghostPlayers.size(); i++) {
      GhostPlayer player = ghostPlayers.get(i);
      Move move = player.chooseMove(game, i);
      ghostMoves.add(move);
    }


    State currentState = game.getCurrentState();
    List<Move> previouGhostMoves = currentState.getPreviousGhostMoves();
    State proximoTotal = game.getNextState(currentState, Move.NONE, ghostMoves);
    System.out.println(proximoTotal);

    for(int i = 0;i < legalMoves.size(); i++){
      nextState1 = game.getNextState(game.getCurrentState(), legalMoves.get(i));
      d1 = evaluateState(nextState1);
      if(d1 <= d2 && depth == 1){
        d2 = d1;
        currentX = i;
//        System.out.println("Nivel 1: " + currentX);
        for(int j = 0;j < game.getLegalPacManMoves(nextState1).size(); j++) {
          nextState1 = game.getNextState(nextState1, game.getLegalPacManMoves(nextState1).get(j));
          d1 = evaluateState(nextState1);
          if (d1 <= d2) {
            d2 = d1;
            x = currentX;
//            System.out.println("Nível 2: " + x);
            System.out.println("");
          }
        }
      }
//      else{
//        d2 = d1;
//        x = i;
//      }
    }
    return legalMoves.get(x);
  }

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
//    heuristic -= pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
//    HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO



    return heuristic;

  }
}
