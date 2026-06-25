package jogo;

import personagens.Player;
import personagens.Dinossauro;
import java.util.Scanner;

public class Combate {
    //private final Player player;
    //private final Dinossauro dino;
    private final Scanner scan;
    private final Dado dado;
    
    public Combate(/*Player player, Dinossauro dino*/){
        //this.player = player;
        //this.dino = dino;
        scan = new Scanner(System.in);
        this.dado = new Dado();
    }
    
    public boolean iniciarCombate(Player player, Dinossauro dino){
        int armaEscolhida = 1;
        int turno = 1;
        
        while(player.estaVivo() && dino.estaVivo()){
            System.out.println("-".repeat(20) + " Turno " + turno + "-".repeat(20));
            executarAtaque(dino, player);
            armaEscolhida = menuEscolhaArma(player);
            executarAtaque(player, dino, armaEscolhida);
            System.out.println(dino.getNome() + " " + dino.getVida() + "/" + dino.getVidaMaxima());
            System.out.println(player.getNome() + " " + player.getVida() + "/" + player.getVidaMaxima());
            turno++;
        }
        
        return player.estaVivo() && !dino.estaVivo();
    }
    
    private void executarAtaque(Player atacante, Dinossauro alvo, int arma){
        int resultadoDado = dado.rolar();
        System.out.println(atacante.getNome() + " ataca!\n" + "Resultado do dado: " + resultadoDado);
        
        if(arma == 1){
            if(resultadoDado <= 2){
                System.out.println("Errou!");
            } else if (resultadoDado <= 5){
                System.out.println("Acertou!");
                atacante.atacar(alvo, 1);
            } else{
                System.out.println("Crítico");
                atacante.atacar(alvo, 2);
            }
        } else if (arma == 2){
            if(resultadoDado <= 1){
                System.out.println("Errou!");
            }else if (resultadoDado <= 4){
                System.out.println("Acertou!");
                atacante.atacar(alvo, 1);
            }else {
                System.out.println("Crítico!");
                atacante.atacar(alvo, 2);
            }
        }
    }
    
    private void executarAtaque(Dinossauro atacante, Player alvo){
        System.out.println(atacante.getNome() + " ataca!");
        
        if(!alvo.esquivar(dado)){
            atacante.atacar(alvo, 1);
            System.out.println(atacante.getNome() + " acertou o player!");
        }else{
            System.out.println(alvo.getNome() + " esquivou!");
        }
    }
    
    private int menuEscolhaArma(Player player){
        System.out.println("-".repeat(20) + " ESCOLHA A SUA ARMA " + "-".repeat(20));
        System.out.println("1 - Punhos");
        
        if(player.temBastao()){
            System.out.println("2 - Bastão Elétrico");
        }
        
        return scan.nextInt();
    }
}
