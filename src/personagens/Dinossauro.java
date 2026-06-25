package personagens;

public abstract class Dinossauro extends Personagem{
    
    public Dinossauro(String nome, int x, int y, char simbolo, int vida) {
        super(nome, x, y, simbolo, vida);
    }
    
    @Override
    public void atacar(Personagem alvo, int dano){
        alvo.tomarDano(1);
    }
}
