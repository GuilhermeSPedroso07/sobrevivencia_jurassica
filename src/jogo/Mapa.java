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
        int numCelulas = tamanho * tamanho;
        int numParedes = random.nextInt(numCelulas - (int) (numCelulas * 0.85))+tamanho;
               
        setCelula(0,0,new Player("Player", 0, 0, 5, 3));   
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
        
        imprimir();
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
    
    public void atualizarMapa(){
        imprimir();
    }
    
    public List<Personagem> getPersonagens(){
        List<Personagem> personagens = new ArrayList<>();
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(celulas[i][j] != null && !celulas[i][j].getNome().equals("Parede")){
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
                //break;
            }
        
        boolean isFree = validarPosicaoDestino(posAlvoX, posAlvoY);
        
        if(isFree){
            player.mover(posAlvoX, posAlvoY, this);
            return null; 
        }
        
        return getCelula(posAlvoX,posAlvoY);
    }
    
   private boolean validarPosicaoDestino(int x, int y){
       /*
       if(getCelula(x,y) == null){
           return null;
       }
       
       if(getCelula(x,y) != null){
           for(Personagem personagem: personagens){
               if(personagem.equals(getCelula(x,y))){
                   return personagem;
               }
           }
       }*/
       
       if(getCelula(x,y) == null){
           return true;
       }
       return false;
   }
}