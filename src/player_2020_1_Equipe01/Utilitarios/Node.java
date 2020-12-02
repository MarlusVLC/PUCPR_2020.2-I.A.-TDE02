package player_2020_1_Equipe01.Utilitarios;

import pacman.*;

import java.util.ArrayList;
import java.util.List;

//

public class Node {
    private Game game;
    private Move move;
    private State state;
    private Branch branch;
    private Node parent = null;
    private int level;
    private int penaltyAddend = 0;
    private boolean visited = false;


    //Classe feita para ligar movimento e estado (à partir do movimento), mantendo um nível de abstração próximo ao conceito
    //da árvore de estados.
    public Node(Game game, Move move, State state, int level){
        this.game = game;
        this.move = move;
        this.state = state;
        this.level = level;
    }


    public void enterBranch(Branch branch){
        branch.addNewNode(this);
    }

    //Avalia o Estado do nó, retornando uma avaliação heurística numérica.
    //Usado como meio para a avaliação heurística
    public double evaluateState(StateEvaluator evaluator){
        return evaluator.evaluateState(state) - penaltyAddend;
    }

    public double AStarScore(StateEvaluator evaluator){
        return -1*(evaluator.evaluateState(state) + level);
    }

    public List<Node> getChildren(){
        List<Node> children = new ArrayList<>();
        for (Move m : game.getLegalPacManMoves(state)){
            //TODO:  CORRIGIR ESSA LINHA
            if (game.isFinal(state))
                break;
            State nextState = game.getNextState(state, m);
            Node child = new Node(game, m,  nextState, this.level+1 );
            child.setParent(this);
            if (this.hasParent() && child.getPacManLocation().equals(this.getParent().getPacManLocation()))
//                child.penaltyAddend += 10;
                continue;
            children.add(child);
        }
        return children;
    }

//    public List<Move> getGhostMoves(State state){ //Retorno todos os movimentos possíveis dos fantasmas
//        List<List<Move>>
//
//        game.getLegalGhostMoves()
//        List<GhostPlayer> ghostPlayers = game.getGhostPlayers();
//        List<Move> ghostMoves = new ArrayList<Move>();
//        for (int i = 0; i < ghostPlayers.size(); i++) {
//            GhostPlayer player = ghostPlayers.get(i);
//            Move move = player.chooseMove(game, i);
//            ghostMoves.add(move);
//        }
//        return ghostMoves;
//    }


    public boolean hasBeenVisited(){
        return visited;
    } //O nó já voi expandido?



    public State getState(){
        return this.state;
    } //retorna o estado associado. Use para fazer a avaliação heurística

    public Move getMove(){
        return this.move;
    } //retorna o movimento associado. Use para escolher o próximo movimento

    public Location getPacManLocation(){ //retorna a localização do PacMan
        return this.state.getPacManLocation();
    }

    public void setLevel(int Level){
        this.level = level;
    }  //determina o nível de profundidade na árvore

    public void addPenaltyAddend(int addend){
        this.penaltyAddend += addend;
    }

    public int getLevel (){
        return level;
    } //retorna o nível de profundidade

    public Branch getBranch(){
        return branch;
    }

    public Node getParent() { //retorna o antecessor desse nó
        return parent;
    }

    public void setParent(Node parent) { //determia o antecessor desse nós
        this.parent = parent;
    }

    public boolean hasParent(){
        return parent != null;
    } //Esse nó tem antecessor?

    //Retorna o nó ao qual está ligado. Usado para determinar o próximo movimento.
    public Node  getOriginator(Node node){
        if (node.getParent().hasParent()){
            return getOriginator(node.getParent());
        }
        return node;
    }
}
