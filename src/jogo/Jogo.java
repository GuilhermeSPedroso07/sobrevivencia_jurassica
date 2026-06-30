package jogo;

import java.util.List;
import java.util.Scanner;
import personagens.*;

public class Jogo {
    private static final Scanner scan = new Scanner(System.in);
    
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   Bem-vindo a SOBREVIVÊNCIA JURÁSSICA!");
        System.out.println("=================================================");
        
        boolean continuarNoJogo = true;
        while(continuarNoJogo){
            continuarNoJogo = exibirMenuPrincipal();
        }
        
        System.out.println("Obrigado por jogar!");
    }
    
    private static boolean exibirMenuPrincipal(){
        System.out.println("\nMenu:");
        System.out.println("1 - Jogar");
        System.out.println("2 - Sair");
        System.out.print("Escolha uma opção: ");
        
        int escolha = lerInteiro();
        if(escolha == 2){
            return false;
        }
        if(escolha != 1){
            System.out.println("Opção inválida.");
            return true;
        }
        
        int percepcao = exibirMenuDificuldade();
        jogarPartida(percepcao);
        return true;
    }
    
    private static int exibirMenuDificuldade(){
        System.out.println("\nEscolha a dificuldade:");
        System.out.println("1 - Fácil (Percepção 3)");
        System.out.println("2 - Médio (Percepção 2)");
        System.out.println("3 - Difícil (Percepção 1)");
        System.out.print("Escolha uma opção: ");
        
        int escolha = lerInteiro();
        switch(escolha){
            case 2: return 2;
            case 3: return 1;
            default: return 3;
        }
    }
    
    private static void jogarPartida(int percepcao){
        Combate combate = new Combate();
        Mapa mapa = new Mapa(10);
        mapa.gerar(percepcao);
        
        System.out.println("-----------------------------------------");
        List<Personagem> personagens = mapa.getPersonagens();
        Player player = (Player) personagens.get(0);
        
        boolean debug = false;
        boolean jogoEmAndamento = true;
        
        while(jogoEmAndamento && player.estaVivo() && existemDinossauros(mapa)){
            mapa.imprimirParaJogador(player, debug);
            
            System.out.println("\nMenu:");
            System.out.println("1 - Movimentar");
            System.out.println("2 - Cura");
            System.out.println("3 - DEBUG (" + (debug ? "Desativar" : "Ativar") + ")");
            System.out.println("4 - Sair");
            System.out.print("Escolha uma opção: ");
            
            int opcao = lerInteiro();
            
            switch(opcao){
                case 1:
                    jogoEmAndamento = realizarMovimentacao(mapa, combate, player);
                    if(jogoEmAndamento && player.estaVivo()){
                        int resultadoDinos = mapa.moverDinossauros(player, combate);
                        if(resultadoDinos == Combate.DERROTA){
                            jogoEmAndamento = false;
                        }
                    }
                    break;
                case 2:
                    if(player.usarKitMedico()){
                        System.out.println("Você usou um Kit Médico e recuperou pontos de vida!");
                    } else {
                        System.out.println("Você não possui Kit Médico!");
                    }
                    break;
                case 3:
                    debug = !debug;
                    break;
                case 4:
                    jogoEmAndamento = false;
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
        
        exibirResultadoFinal(player, mapa);
    }
    
    private static boolean realizarMovimentacao(Mapa mapa, Combate combate, Player player){
        System.out.println("Escolha a movimentação desejada:");
        System.out.println("a - esquerda");
        System.out.println("d - direita");
        System.out.println("s - baixo");
        System.out.println("w - cima");
        
        char escolha = scan.next().charAt(0);
        
        Entidade entidade = mapa.moverPlayer(player, escolha);
        
        if(entidade != null && !entidade.getNome().equals("Parede")){
            int resultado = combate.iniciarCombate(player, (Dinossauro) entidade, mapa);
            
            if(resultado == Combate.VITORIA){
                mapa.setCelula(entidade.getX(), entidade.getY(), null);
                mapa.moverPlayer(player, escolha);
            }
            
            if(resultado == Combate.DERROTA){
                return false;
            }
        }
        
        return true;
    }
    
    private static boolean existemDinossauros(Mapa mapa){
        for(Personagem p : mapa.getPersonagens()){
            if(p instanceof Dinossauro && p.estaVivo()){
                return true;
            }
        }
        return false;
    }
    
    private static void exibirResultadoFinal(Player player, Mapa mapa){
        mapa.imprimir();
        
        if(!player.estaVivo()){
            System.out.println("\nVocê foi devorado pelos dinossauros... FIM DE JOGO.");
        } else if(!existemDinossauros(mapa)){
            System.out.println("\nParabéns! Você eliminou todos os dinossauros e sobreviveu!");
        } else {
            System.out.println("\nVocê saiu do parque.");
        }
        
        System.out.println("\n1 - Novo Jogo");
        System.out.println("2 - Voltar ao Menu Principal");
        System.out.print("Escolha uma opção: ");
        lerInteiro();
    }
    
    private static int lerInteiro(){
        while(!scan.hasNextInt()){
            scan.next();
        }
        return scan.nextInt();
    }
}
