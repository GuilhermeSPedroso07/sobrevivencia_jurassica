package personagens;

import jogo.Mapa;

public class TRex extends Dinossauro{
    public TRex(int x, int y){
        super("T-Rex", x, y, 'R', 3);
    }
    @Override
    public void mover(int x, int y, Mapa mapa) {
    }
    
    @Override
    public void atacar(Personagem alvo, int dano){
        super.atacar(alvo, 2);
    }
    
    @Override
    public boolean podeSerAtacadoSemArma(){
        return false;
    }
}
