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
        if (Game.isWinning(state))
            heuristic +=  999999;
//        System.out.println("H0: " + (heuristic-1));
        //TESTE DE FUNCAO OBJETIVO



//
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO
//        heuristic += pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc);
//      System.out.println("H1: "  + pacLOC.manhattanDistanceToClosest(pacLOC, allGhostLoc));
//    //HEURÍSTICA 1: DISTÂNCIA ENTRE PACMAN E O FANTASMA MAIS PRÓXIMO



        //HEURÍSTICA 2: QUANTIDADE DE PONTOS EXISTENTES NO MAPA
        heuristic -=  allDotLoc.size();
//        System.out.println("H2: " + (-allDotLoc.size()));
        //HEURÍSTICA 2: PONTOS EXISTENTES NO MAPA


        //HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO
        heuristic -= 2*pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc);
//        System.out.println("H3: "  + (-pacLOC.manhattanDistanceToClosest(pacLOC, allDotLoc)));
//        HEURÍSTICA 3: DISTÂNCIA ENTRE O PACMAN E O PONTO MAIS PRÓXIMO


        //HEURÍSTCA 4: DISTÂNCIA MÉDIA ENTRE pacman e os itens restantes;
        heuristic -= mediumItenstoItemDist(allDotLoc, pacLOC);

        //HEURÍSTICA 5: DISTÂNCIA ENTRE PACMAN E O PONTO MAIS DISTANTE
        heuristic -= getFurthestDist(allDotLoc,pacLOC);

        //HEURÍSTICA 6: DISTÂNCIA MÉDIA ENTRE PACMAN E FANTASMAS:
//        heuristic += mediumItenstoItemDist(allGhostLoc, pacLOC);

        //HEURÍSTICA 7: DISTÂNCIA MÉDIA ENTRE OS PONTOS
        if (allDotLoc.size()<50){
            float dotMediumDist = mediumItemDist(allDotLoc, pacLOC);
            heuristic -= dotMediumDist;
//            System.out.println("DIstancia meida entre os itens: " + dotMediumDist);
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
