package player_2020_1_Equipe01;

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
        return evaluator.evaluateState(state);
    }

    public List<Node> getChildren(){ //Expande o nó e gera uma lista de "filhos"
        List<Node> children = new ArrayList<>(); //Declara a lista que será retornada
        for (Move m : game.getLegalPacManMoves(state)){ //Percorre por todos os movimentos possíveis à partir do estado do nó.
            //TODO:  CORRIGIR ESSA LINHA
//            State nextState = game.getNextState(state, m, getGhostMoves());
            if (game.isFinal(state)) //O jogo acaba aqui?
                break; //Se sim, pare a expansão.
            State nextState = game.getNextState(state, m); //Instancia um próximo estado com base no estado e o movimento do nó.
            Node child = new Node(game, m,  nextState, this.level+1 ); //Instancia um novo nó-filh que armazena um dos movimentos possíveis, seu estado associado e um nível mais profundo que seu "pai"
            child.setParent(this); //Declara o antecessor desse nó como sendo o nó atual.
            if (this.hasParent() && child.getPacManLocation().equals(this.getParent().getPacManLocation()))
                continue; //Se a localização do PacMan no próximo estado for semelhante ao do estado anterior, não inclua na lista
            children.add(child); //Inclui o nó na lista
        }
        return children; //Retorna a lista de filhos
    }

    public List<Move> getGhostMoves(){ //Retorno todos os movimentos possíveis dos fantasmas
        List<GhostPlayer> ghostPlayers = game.getGhostPlayers();
        List<Move> ghostMoves = new ArrayList<Move>();
        for (int i = 0; i < ghostPlayers.size(); i++) {
            GhostPlayer player = ghostPlayers.get(i);
            Move move = player.chooseMove(game, i);
            ghostMoves.add(move);
        }
        return ghostMoves;
    }


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
