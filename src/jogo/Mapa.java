package jogo;

import java.util.Random;
import personagens.*;

public class Mapa {
    private int tamanho;
    private Entidade[][] entidades;
    private Random random;
    
    public Mapa(int tamanho){
        this.tamanho = tamanho;
        entidades = new Entidade[tamanho][tamanho];
        random = new Random();
    }
    
    public void imprimir(){
        
        for(int i = 0; i < tamanho; i++){
            for(int j = 0; j < tamanho; j++){
                if(entidades[i][j] != null){
                    System.out.print("| " + entidades[i][j].getSimbolo() + " ");
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.print("|\n");
        }
    }
    
    public Entidade getCelula(int x, int y){
        return entidades[x][y];
    }
    
    public void setCelula(int x, int y, Entidade entidade){
        entidades[x][y] = entidade;
    }
    
    public void gerar(){
        int numCelulas = tamanho * tamanho;
        int numParedes = random.nextInt(numCelulas - (int) (numCelulas * 0.85))+tamanho;
        
        
        entidades[0][0] = new Player("Player", 0, 0, 5, 3);
        entidades[tamanho-1][tamanho-1] = new TRex(tamanho-1,tamanho-1);
        
        while(numParedes > 0){
            int x = random.nextInt(tamanho);
            int y = random.nextInt(tamanho);
            //System.out.println("(" + x + "," + y + ")");
            //System.out.println(numParedes);
            if(entidades[x][y] == null){
                entidades[x][y] = new Entidade("Parede", x, y, '█');
                numParedes--;
            }
        }
        
        alocarDinossauro("Compsognato", 2);
        alocarDinossauro("Velociraptor", 2);
        alocarDinossauro("Troodonte", 5);

    }
    
    private void alocarDinossauro(String tipo, int quantidade){
        
        for(int i = 0; i < quantidade; i++){   
            int x = 0;
            int y = 0;
            
            while(entidades[x][y] != null){
                x = random.nextInt(tamanho);
                y = random.nextInt(tamanho);
            }
            
            switch(tipo){
                case "Compsognato":
                    entidades[x][y] = new Compsognato(x,y);
                    break;
                case "Velociraptor":
                    entidades[x][y] = new Velociraptor(x,y);
                    break;
                case "Troodonte":
                    entidades[x][y] = new Troodonte(x,y);   
            }
        
        }
    }
    
    public void atualizarMapa(){
        Entidade entidade;
        
         for(int i = 0; i < tamanho; i++){
             for(int j = 0; j < tamanho; j++){
                 if(entidades[i][j].getSimbolo() == 'P'){
                     entidade = entidades[i][j];
                 }
             }
         }
    }
}