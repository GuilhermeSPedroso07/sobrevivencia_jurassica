package jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Caixa de suprimentos espalhada pelo mapa.
 * Conforme especificação: das 4 caixas, uma contém Kit Médico, uma contém
 * Bastão Elétrico, e duas contêm Arma de Dardos (sendo que uma destas
 * esconde, como surpresa, um Compsognato).
 */
public class CaixaSuprimento extends Entidade {

    public enum TipoConteudo { KIT_MEDICO, BASTAO, DARDOS }

    private final TipoConteudo conteudo;
    private final boolean compsognatoSurpresa;

    private static List<Object[]> pool;

    public CaixaSuprimento(int x, int y){
        super("Caixa de Suprimentos", x, y, 'X');
        if(pool == null || pool.isEmpty()){
            resetarDistribuicao();
        }
        Object[] sorteado = pool.remove(0);
        this.conteudo = (TipoConteudo) sorteado[0];
        this.compsognatoSurpresa = (boolean) sorteado[1];
    }

    /** Deve ser chamado ao gerar um novo mapa, para reiniciar o sorteio das 4 caixas. */
    public static void resetarDistribuicao(){
        pool = new ArrayList<>();
        pool.add(new Object[]{TipoConteudo.KIT_MEDICO, false});
        pool.add(new Object[]{TipoConteudo.BASTAO, false});
        pool.add(new Object[]{TipoConteudo.DARDOS, false});
        pool.add(new Object[]{TipoConteudo.DARDOS, true});
        Collections.shuffle(pool);
    }

    public TipoConteudo getConteudo(){
        return conteudo;
    }

    public boolean isCompsognatoSurpresa(){
        return compsognatoSurpresa;
    }
}
