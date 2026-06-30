package personagens;

import jogo.Entidade;

public abstract class Personagem extends Entidade{
    private int vida;
    private final int vidaMaxima;
    
    public Personagem(String nome, int x, int y, char simbolo, int vida){
        super(nome,x,y,simbolo);
        this.vida = vida;
        this.vidaMaxima = vida;
    }
    
    public void tomarDano(int dano){
        this.vida -= dano;
    }
    
    public void curar(int quantidade){
        this.vida = Math.min(this.vida + quantidade, this.vidaMaxima);
    }
    
    public boolean estaVivo(){
        return this.vida > 0;
    }
    
    public int getVida(){
        return this.vida;
    }
    
    public int getVidaMaxima(){
        return this.vidaMaxima;
    }
    
    @Override
    public String toString(){
        return super.toString() + "\n" +
                "Vida: " + this.vida + "/" + this.vidaMaxima;
    }
      
    public abstract void atacar(Personagem alvo, int dano);
}
