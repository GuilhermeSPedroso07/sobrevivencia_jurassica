package personagens;

import jogo.Inventario;
import jogo.Dado;
import jogo.Mapa;
import interfaces.Movel;

public class Player extends Personagem implements Movel{
    private final int percepcao;
    private final Inventario inventario;
    
    public Player(String nome, int x, int y, int vida, int percepcao){
        super(nome, x, y, 'P', vida);
        this.percepcao = percepcao;
        inventario = new Inventario();
    }  
    
    @Override
    public void atacar(Personagem alvo, int dano){
        alvo.tomarDano(dano);
    }
    
    @Override
    public void mover(int x, int y, Mapa mapa){
            mapa.setCelula(this.getX(), this.getY(), null);
            this.setPosition(x, y);
            mapa.setCelula(x, y, this);
    }
    
    public int getPercepcao(){
        return this.percepcao;
    }
    
    public void pegarBastao(){
        inventario.setBastao(true);
    }
    
    public boolean temBastao(){
        return this.inventario.temBastao();
    }
    
    public void pegarDardo(){
        inventario.adicionarDardo();
    }
    
    public boolean temDardos(){
        return inventario.temDardos();
    }
    
    public int getQuantidadeDardos(){
        return inventario.getDardos();
    }
    
    public boolean usarDardo(){
        return inventario.usarDardo();
    }
    
    public void pegarKitMedico(){
        inventario.adicionarKitMedico();
    }
    
    public boolean temKitMedico(){
        return inventario.temKitMedico();
    }
    
    /** Usa um kit médico do inventário, recuperando pontos de vida. Retorna false se não houver kit. */
    public boolean usarKitMedico(){
        if(inventario.usarKitMedico()){
            curar(3);
            return true;
        }
        return false;
    }
    
    public boolean esquivar(Dado dado){
        int testePercepcao = dado.rolar(3);
        return testePercepcao <= this.percepcao;
    }
    
    @Override
    public String toString(){
        return(
                super.toString() + "\n" +
                "Percepção: " + this.percepcao
        );
    }
}
