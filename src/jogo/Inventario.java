package jogo;

public class Inventario {
    private boolean bastao;
    private int dardos;
    private int kitsMedicos;
    
    public Inventario(){
        bastao = false;
        dardos = 0;
        kitsMedicos = 0;
    }
    
    public void setBastao(boolean valor){
        bastao = valor;
    }
    
    public boolean temBastao(){
        return bastao;
    }
    
    public void adicionarDardo(){
        dardos++;
    }
    
    public boolean temDardos(){
        return dardos > 0;
    }
    
    public int getDardos(){
        return dardos;
    }
    
    public boolean usarDardo(){
        if(dardos > 0){
            dardos--;
            return true;
        }
        return false;
    }
    
    public void adicionarKitMedico(){
        kitsMedicos++;
    }
    
    public boolean temKitMedico(){
        return kitsMedicos > 0;
    }
    
    public int getKitsMedicos(){
        return kitsMedicos;
    }
    
    public boolean usarKitMedico(){
        if(kitsMedicos > 0){
            kitsMedicos--;
            return true;
        }
        return false;
    }
}
