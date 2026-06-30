package personagens;

import interfaces.Movel;
import jogo.Mapa;

public class Velociraptor extends Dinossauro implements Movel{
    public Velociraptor(int x, int y){
        super("Velociraptor", x, y, 'V', 2);
    }
    
    @Override
    public void mover(int x, int y, Mapa mapa) {
        mapa.setCelula(this.getX(), this.getY(), null);
        this.setPosition(x, y);
        mapa.setCelula(x, y, this);
    }
    
    @Override
    public boolean podeSerAtacadoComDardos(){
        return false;
    }
    
    @Override
    public int getPassosMovimento(){
        return 2;
    }
}
