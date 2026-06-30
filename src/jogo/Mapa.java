package jogo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import personagens.*;

public class Mapa {
    private int tamanho;
    private Entidade[][] celulas;
    private Random random;
    
    public Mapa(int tamanho){
        this.tamanho = tamanho;
        celulas = new Entidade[tamanho][tamanho];
        random = new Random();
    }
    
    public void imprimir(){
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(celulas[i][j] != null){
                    System.out.print("| " + celulas[i][j].getSimbolo() + " ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.print("|\n");
        }
    }
    
    public void imprimirParaJogador(Player player, boolean debug){
        boolean[][] visivel = debug ? null : calcularVisibilidade(player);
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(debug || visivel[i][j]){
                    if(celulas[i][j] != null){
                        System.out.print("| " + celulas[i][j].getSimbolo() + " ");
                    } else {
                        System.out.print("|   ");
                    }
                } else {
                    System.out.print("| ? ");
                }
            }
            System.out.print("|\n");
        }
        
        System.out.println(player.getNome() + " - Vida: " + player.getVida() + "/" + player.getVidaMaxima()
                + " | Percepção: " + player.getPercepcao());
    }
    
    private boolean[][] calcularVisibilidade(Player player){
        boolean[][] visivel = new boolean[tamanho][tamanho];
        int px = player.getX();
        int py = player.getY();
        visivel[py][px] = true;
        
        int alcance = player.getPercepcao();
        
        int[][] direcoes = {{1,0},{-1,0},{0,1},{0,-1}};
        for(int[] dir : direcoes){
            int x = px;
            int y = py;
            for(int passo = 0; passo < alcance; passo++){
                x += dir[0];
                y += dir[1];
                if(x < 0 || x >= tamanho || y < 0 || y >= tamanho){
                    break;
                }
                visivel[y][x] = true;
                if(celulas[y][x] != null){
                    break;
                }
            }
        }
        return visivel;
    }
    
    public int getTamanho(){
        return this.tamanho;
    }
    
    public Entidade getCelula(int x, int y){
        return celulas[y][x];
    }
    
    public void setCelula(int x, int y, Entidade entidade){
        celulas[y][x] = entidade;
    }
    
    public void gerar(){
        gerar(3);
    }
    
    public void gerar(int percepcaoJogador){
        int numCelulas = tamanho * tamanho;
        int numParedes = random.nextInt(numCelulas - (int) (numCelulas * 0.85))+tamanho;
        
        CaixaSuprimento.resetarDistribuicao();
               
        setCelula(0,0,new Player("Player", 0, 0, 5, percepcaoJogador));   
        setCelula(tamanho-1, tamanho-1, new TRex(tamanho-1, tamanho-1));
        
        while(numParedes > 0){
            int x = random.nextInt(tamanho);
            int y = random.nextInt(tamanho);

            if(celulas[x][y] == null){
                setCelula(x,y,new Entidade("Parede", x, y, '█'));
                numParedes--;
            }
        }
        
        alocarDinossauro("Compsognato", 2);
        alocarDinossauro("Velociraptor", 2);
        alocarDinossauro("Troodonte", 5);
        alocarCaixas(4);
    }
    
    private void alocarDinossauro(String tipo, int quantidade){
        
        for(int i = 0; i < quantidade; i++){   
            int x = 0;
            int y = 0;
            
            while(getCelula(x,y) != null){
                x = random.nextInt(tamanho);
                y = random.nextInt(tamanho);
            }
            
            switch(tipo){
                case "Compsognato":
                    setCelula(x,y,new Compsognato(x,y));
                    break;
                case "Velociraptor":
                    setCelula(x,y,new Velociraptor(x,y));
                    break;
                case "Troodonte":
                    setCelula(x,y,new Troodonte(x,y));
            }
            
        }
    }
    
    private void alocarCaixas(int quantidade){
        for(int i = 0; i < quantidade; i++){
            int x = 0;
            int y = 0;
            
            while(getCelula(x,y) != null){
                x = random.nextInt(tamanho);
                y = random.nextInt(tamanho);
            }
            
            setCelula(x, y, new CaixaSuprimento(x, y));
        }
    }
    
    public void atualizarMapa(){
        imprimir();
    }
    
    public List<Personagem> getPersonagens(){
        List<Personagem> personagens = new ArrayList<>();
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(celulas[i][j] != null && celulas[i][j] instanceof Personagem){
                    personagens.add((Personagem) celulas[i][j]);
                }
            }
        }
        
        return personagens;
    }

    public Entidade moverPlayer(Player player, char direcao){
        int posAlvoX = player.getX();
        int posAlvoY = player.getY();
        
        switch(direcao){
            case 'a':
                posAlvoX--;
                break;
            case 'd':
                posAlvoX++;
                break;
            case 's':
                posAlvoY++;
                break;
            case 'w':
                posAlvoY--;
                break;
            default:
                System.out.println("Escolha inválida");
                return null;
            }
        
        if(posAlvoX < 0 || posAlvoX >= tamanho || posAlvoY < 0 || posAlvoY >= tamanho){
            System.out.println("Não é possível se mover para fora do mapa!");
            return null;
        }
        
        Entidade destino = getCelula(posAlvoX, posAlvoY);
        
        if(destino == null){
            player.mover(posAlvoX, posAlvoY, this);
            return null;
        }
        
        if(destino instanceof CaixaSuprimento){
            CaixaSuprimento caixa = (CaixaSuprimento) destino;
            Entidade resultado = abrirCaixa(player, caixa, posAlvoX, posAlvoY);
            if(resultado == null){
                player.mover(posAlvoX, posAlvoY, this);
            }
            return resultado;
        }
        
        return destino;
    }

    private Entidade abrirCaixa(Player player, CaixaSuprimento caixa, int x, int y){
        System.out.println("Você encontrou uma Caixa de Suprimentos!");
        
        switch(caixa.getConteudo()){
            case KIT_MEDICO:
                player.pegarKitMedico();
                System.out.println("Você encontrou um Kit Médico!");
                break;
            case BASTAO:
                player.pegarBastao();
                System.out.println("Você encontrou um Bastão Elétrico!");
                break;
            case DARDOS:
                player.pegarDardo();
                System.out.println("Você encontrou munição para a Arma de Dardos!");
                break;
        }
        
        if(caixa.isCompsognatoSurpresa()){
            System.out.println("Surpresa! Um Compsognato estava escondido na caixa!");
            Compsognato comp = new Compsognato(x, y);
            setCelula(x, y, comp);
            return comp;
        }
        
        setCelula(x, y, null);
        return null;
    }
    
    
    public int moverDinossauros(Player player, Combate combate){
        List<Personagem> personagens = getPersonagens();
        
        for(Personagem p : personagens){
            if(!(p instanceof Dinossauro) || p instanceof TRex){
                continue;
            }
            Dinossauro dino = (Dinossauro) p;
            if(!dino.estaVivo()){
                continue;
            }
            
            int passos = dino.getPassosMovimento();
            for(int passo = 0; passo < passos; passo++){
                if(!dino.estaVivo()){
                    break;
                }
                
                int[] destino = calcularProximoPasso(dino, player);
                if(destino == null){
                    continue;
                }
                
                Entidade alvoCelula = getCelula(destino[0], destino[1]);
                
                if(alvoCelula == player){
                    System.out.println(dino.getNome() + " encontrou o jogador!");
                    int resultado = combate.iniciarCombate(player, dino, this);
                    if(resultado == Combate.VITORIA){
                        setCelula(dino.getX(), dino.getY(), null);
                    }
                    return resultado;
                }
                
                if(alvoCelula == null){
                    dino.mover(destino[0], destino[1], this);
                }
            }
        }
        return -1;
    }
    
    private int[] calcularProximoPasso(Dinossauro dino, Player player){
        int dx = 0, dy = 0;
        
        if(dino.perseguePersonagem()){
            int diffX = player.getX() - dino.getX();
            int diffY = player.getY() - dino.getY();
            if(Math.abs(diffX) > Math.abs(diffY)){
                dx = Integer.signum(diffX);
            } else if (diffY != 0){
                dy = Integer.signum(diffY);
            } else if (diffX != 0){
                dx = Integer.signum(diffX);
            }
        } else {
            int[][] direcoes = {{1,0},{-1,0},{0,1},{0,-1}};
            int[] dir = direcoes[random.nextInt(direcoes.length)];
            dx = dir[0];
            dy = dir[1];
        }
        
        int novoX = dino.getX() + dx;
        int novoY = dino.getY() + dy;
        
        if(novoX < 0 || novoX >= tamanho || novoY < 0 || novoY >= tamanho){
            return null;
        }
        return new int[]{novoX, novoY};
    }
}