package player_2020_1_Equipe01.Utilitarios;

import pacman.*;

import java.util.List;

public class GeneralEvaluator implements StateEvaluator {
    @Override
    public double evaluateState(State state) {
        Location pacLOC = state.getPacManLocation();
        List<Location> allGhostLoc = state.getGhostLocations();
        LocationSet allDotLoc = state.getDotLocations();


        double heuristic = 1;

        //HEURÍSTICA 0: TESTE DE FUNCAO OBJETIVO
        if (Game.isLosing(state))
            heuristic -= 99999;
        else if (Game.isWinning(state))
            heuristic +=  99999;
//        System.out.println("H0: " + (heuristic-1));
        //TESTE DE FUNCAO OBJETIVO



//
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
//        heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
//      System.out.println("H1: "  + pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc));
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO



        //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA
        heuristic -=  allDotLoc.size();
//        System.out.println("H2: " + (-allDotLoc.size()));
        //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA


        //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
//        heuristic -= pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
//        System.out.println("H3: "  + (-pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc)));
//        HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO

//        System.out.println("HR: " + heuristic);
//        System.out.println("");

         return heuristic;
    }
}
