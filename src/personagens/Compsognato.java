package personagens;

import interfaces.Movel;
import jogo.Mapa;

public class Compsognato extends Dinossauro implements Movel{
    public Compsognato(int x, int y){
        super("Compsognato", x, y, 'C', 1);
    }
    
    @Override
    public void mover(int x, int y, Mapa mapa) {
        mapa.setCelula(this.getX(), this.getY(), null);
        this.setPosition(x, y);
        mapa.setCelula(x, y, this);
    }
    
}
