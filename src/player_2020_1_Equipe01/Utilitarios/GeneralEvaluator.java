package player_2020_1_Equipe01.Utilitarios;

import pacman.*;

import java.util.Arrays;
import java.util.List;

public class GeneralEvaluator implements StateEvaluator {
    /**
     * Os valores na lista determinam quais heurísticas serão usadas.
     * Inclua os valores das heurísticas que deseja utilizar :)
     */
    List<Integer>
            activeHeuristics =
            Arrays.asList(new Integer[]{0,1,2,3,4,5,6,7});

    /**
    * Heurísticas disponíveis:
     * 0: TESTE DE FUNCAO OBJETIVO
     * 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
     * 2: QUANTIDADE DE PONTOS EXISTENTES NO MAPA
     * 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
     * 4: DISTÂNCIA MÉDIA ENTRE pacman e os itens restantes;
     * 5: DISTÂNCIA ENTRE PACMAN E O PONTO MAIS DISTANTE
     * 6: DISTÂNCIA MÉDIA ENTRE PACMAN E TODOS OS FANTASMAS
     * 7: DISTÂNCIA MÉDIA ENTRE OS PONTOS
     */



    @Override
    public double evaluateState(State state) {
        Location pacLOC = state.getPacManLocation();
        List<Location> allGhostLoc = state.getGhostLocations();
        LocationSet allDotLoc = state.getDotLocations();


        double heuristic = 1;

        //HEURÍSTICA 0: TESTE DE FUNCAO OBJETIVO
        if (activeHeuristics.contains(0)) {
            System.out.println("Heurística 0 ATIVADA");
            if (Game.isLosing(state))
                heuristic -= 99999;
            if (Game.isWinning(state))
                heuristic += 999999;
        }


//
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
        if (activeHeuristics.contains(1)){
            heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
        }


        //HEURÍSTICA 2: QUANTIDADE DE PONTOS EXISTENTES NO MAPA
        if (activeHeuristics.contains(2)) {
            heuristic -=  allDotLoc.size();
        }


        //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
        if (activeHeuristics.contains(3)){
            heuristic -= 2*pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
        }


        //HEURÍSTCA 4: DISTÂNCIA MÉDIA ENTRE pacman e os itens restantes;
        if (activeHeuristics.contains(4)){
            heuristic -= mediumItenstoItemDist(allDotLoc, pacLOC);
        }

        //HEURÍSTICA 5: DISTÂNCIA ENTRE PACMAN E O PONTO MAIS DISTANTE
        if (activeHeuristics.contains(5)){
            heuristic -= getFurthestDist(allDotLoc,pacLOC);
        }

        //HEURÍSTICA 6: DISTÂNCIA MÉDIA ENTRE PACMAN E FANTASMAS:
        if (activeHeuristics.contains(6)){
            heuristic += mediumItenstoItemDist(allGhostLoc, pacLOC);
        }

        //HEURÍSTICA 7: DISTÂNCIA MÉDIA ENTRE OS PONTOS
        if (activeHeuristics.contains(7)){
            if (allDotLoc.size()<50){
                float dotMediumDist = mediumItemDist(allDotLoc, pacLOC);
                heuristic -= dotMediumDist;
//            System.out.println("DIstancia meida entre os itens: " + dotMediumDist);
            }
        }


//        System.out.println("HR: " + heuristic);
//        System.out.println("");

         return heuristic;
    }









    float mediumItemDist(LocationSet allItemLoc, Location sampleLoc){

        float distSum = 0;
        int allDist = 0;
        for (Location loc_i : allItemLoc){
            for (Location loc_j : allItemLoc){
                distSum += sampleLoc.euclideanDistance(loc_i,loc_j);
                allDist++;
            }
        }
        return distSum/allItemLoc.size();
    }

    float mediumItenstoItemDist(List<Location> allItemLoc,Location sampleLoc){
        float distSum = 0;
        float allDist = 0;
        for (Location loc : allItemLoc){
            distSum += sampleLoc.euclideanDistance(sampleLoc, loc);
            allDist++;
        }
        return distSum/allItemLoc.size();
    }

    float mediumItenstoItemDist(LocationSet allItemLoc,Location sampleLoc){
        float distSum = 0;
        float allDist = 0;
        for (Location loc : allItemLoc){
            distSum += sampleLoc.euclideanDistance(sampleLoc, loc);
            allDist++;
        }
        return distSum/allItemLoc.size();
    }

    double getFurthestDist(LocationSet alItemLoc, Location sampleLoc){
        double furthestDist = 0;
        for (Location loc : alItemLoc){
            double currDist = sampleLoc.euclideanDistance(sampleLoc, loc);
            if (currDist > furthestDist){
                furthestDist = currDist;
            }
        }
        return furthestDist;
    }
}
