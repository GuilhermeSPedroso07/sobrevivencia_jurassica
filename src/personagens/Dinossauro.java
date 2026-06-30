package personagens;

import interfaces.Movel;
import jogo.Mapa;

public abstract class Dinossauro extends Personagem {

    public Dinossauro(String nome, int x, int y, char simbolo, int vida) {
        super(nome, x, y, simbolo, vida);
    }

    @Override
    public void atacar(Personagem alvo, int dano) {
        alvo.tomarDano(1);
    }

    

    public boolean podeSerAtacadoSemArma() {
        return true;
    }

    public boolean podeSerAtacadoComDardos() {
        return true;
    }

    public int getPassosMovimento() {
        return 1;
    }

    public boolean perseguePersonagem() {
        return false;
    }

    public abstract void mover(int i, int i0, Mapa aThis);
}