package jogo;

import java.util.ArrayList;
import java.util.List;
import personagens.*;
import java.util.Scanner;

public class Jogo {
    public static void main(String[] args) {
        Combate combate = new Combate();
        Mapa mapa = new Mapa(10);
        Scanner scan = new Scanner(System.in);
        List<Personagem> personagens;
        
        mapa.gerar();
        System.out.println("-----------------------------------------");
        personagens = mapa.getPersonagens();

        exibirMenuMovimentacao(mapa, scan, combate, personagens);
        System.out.println(personagens.size());
        
        
    }
    
    private static void exibirMenuMovimentacao(Mapa mapa, Scanner scan, Combate combate, List<Personagem> personagens){
        char escolha = '-';
        while(escolha != 'q'){
            System.out.println("Esolha a movimentação desejada:");
            System.out.println("a - esquerda");
            System.out.println("d - direita");
            System.out.println("s - baixo");
            System.out.println("w - cima");
            
            escolha = scan.next().charAt(0);
            
            Entidade entidade = mapa.moverPlayer((Player) personagens.get(0), escolha);
 
            if(entidade != null && !entidade.getNome().equals("Parede")){
                if(combate.iniciarCombate((Player) personagens.get(0), (Dinossauro) entidade)){
                    mapa.setCelula(entidade.getX(), entidade.getY(), null);
                    mapa.moverPlayer((Player) personagens.get(0), escolha);
                    
                    personagens.remove(entidade);
                    System.out.println(personagens.size());

                }
            }

            
            
            mapa.atualizarMapa();
        }
    }
}
