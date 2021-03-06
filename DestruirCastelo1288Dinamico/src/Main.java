import java.util.*;

/**
 * Gustavo Medeiros Santos
 * Amanda Morales Baptista
 */
public class Main {

    public static Scanner io= new Scanner(System.in);

    public static String ask(){
        return io.nextLine();
    }

    public static void main(String [] args){
        int n=Integer.parseInt(ask());
        int nProjeteis;
        CanhaoBacktracking backtracking;
        List<BacktrackingElement> projeteis;

        String [] projeteisInput;
        Long resistenciaCastelo;

        int pesoMax;
        for(int i=0;i<n;i++){
            nProjeteis=Integer.parseInt(ask());
            projeteis=new ArrayList<BacktrackingElement>();
            projeteis.add(new BacktrackingElement(0,0,-1));
            for(int j=0;j<nProjeteis;j++){
                  projeteisInput=ask().split(" ");
                  projeteis.add(new BacktrackingElement(Integer.parseInt(projeteisInput[0]),Integer.parseInt(projeteisInput[1]),j));
            }
            pesoMax=Integer.parseInt(ask());
            resistenciaCastelo=Long.parseLong(ask());
            backtracking=new CanhaoBacktracking(projeteis,pesoMax,resistenciaCastelo);
            Long r=backtracking.getBetterCombination();
            Main.trataResposta(resistenciaCastelo,r);

        }

    }


    public static void trataResposta(Long resistencia, Long melhor){
        if(melhor>=resistencia){
            System.out.println("Missao completada com sucesso");
        }
        else{
            System.out.println("Falha na missao");
        }
    }



}

class Backtracking{


    List<BacktrackingElement> inputList;
    long[][] matrizDinamica;
    long[] currentWeigth;
    int maxWeight;


    public Backtracking(List<BacktrackingElement> elements, int maxWeight){
        this.inputList=elements;

        this.matrizDinamica = new long[elements.size()+1][maxWeight+1];
        this.currentWeigth =new long[matrizDinamica[0].length];
        this.maxWeight=maxWeight;
    }




    public long executeDinamico(){
        int n= this.matrizDinamica.length;
        long[] currentLine;
        BacktrackingElement currentElement;
        for(int i=1;i<n;i++){
            currentLine=matrizDinamica[i];
            currentElement=this.inputList.get(i-1);
            for(int j=1;j<currentLine.length;j++){
                 this.matrizDinamica[i][j]=this.getMaxValue(currentElement,i,j);
                 if(this.avaliaCondicaoParada(i,j)) return this.matrizDinamica[i][j];
                }
        }
        return matrizDinamica[matrizDinamica.length-1][matrizDinamica[0].length-1];
    }


    private Long getMaxValue(BacktrackingElement element,int i, int p){
        long valorSemElemento=this.matrizDinamica[i-1][p];
        if(p<element.getWeight()) return valorSemElemento;
        long valorComElemento=this.matrizDinamica[i-1][p-element.getWeight()]+element.getValue();
        if(valorComElemento>valorSemElemento) return valorComElemento;
        else return valorSemElemento;
    }


    public boolean avaliaCondicaoParada(int i, int j){
        return false;
        //return this.melhorResult.getCurrentWeight()>=this.melhorResult.getMaxWeight();
    }

    public Long getBetterCombination(){
        long r= this.executeDinamico();
        return r;
    }


}



class BacktrackingElement implements Comparable{
    private int value;
    private int weight;
    private int id;

    public boolean conditionToAdd(int maxWeight,int currentWeight){
        return this.weight+currentWeight<=maxWeight;
    }

    public BacktrackingElement(int value, int weight,int id) {
        this.value = value;
        this.weight = weight;
        this.id=id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    @Override
    public int compareTo(Object o) {
        BacktrackingElement b= (BacktrackingElement)o;
        if(b.getValue()==this.getValue() && b.id==this.id) return 0;
        if(b.getValue()>this.getValue()) return 1;
        return -1;
    }
}

class CanhaoBacktracking extends Backtracking{
    public Long resistenciaCastelo;



    public CanhaoBacktracking(List<BacktrackingElement> elements, int maxWeight, Long resistenciaCastelo) {
        super(elements, maxWeight);
        this.resistenciaCastelo=resistenciaCastelo;
    }

    @Override
    public boolean avaliaCondicaoParada(int i,int j) {
        //System.out.println(i+" "+j);
        return this.resistenciaCastelo<=this.matrizDinamica[i][j];

    }
}
