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

        int nProjeteis;
        ApagaBacktracking backtracking;
        List<BacktrackingElement> numeros;
        String [] input;
        Long resistenciaCastelo;
        while(true) {
            int pesoMax;
            input = ask().split(" ");
            if(input[0].equals("0") && input[1].equals("0")) break;
            numeros = new ArrayList<BacktrackingElement>();
            //numeros.add(new BacktrackingElement(0, 0, -1));
            Main.criaEntrada(numeros);
            pesoMax = numeros.size()-Integer.parseInt(input[1]);
            backtracking = new ApagaBacktracking(numeros, pesoMax);
            String r = backtracking.getBetterCombination();
            System.out.println(r);
        }
    }

    public static void criaEntrada(List<BacktrackingElement> elements){
       String [] input = ask().split("");
       int n=input.length;
       int i=0;
       for(String s: input){
           elements.add(new BacktrackingElement((s),1,i));
           i++;
       }
    }

}

class Backtracking{


    List<BacktrackingElement> inputList;
    String[][] matrizDinamica;

    int maxWeight;


    public Backtracking(List<BacktrackingElement> elements, int maxWeight){
        this.inputList=elements;

        this.matrizDinamica = new String[elements.size()+1][maxWeight+1];

        this.maxWeight=maxWeight;
        this.populaMatrizDinamica();
    }

    public void populaMatrizDinamica(){
        for (int i=0;i<matrizDinamica.length;i++){
            for(int j=0;j<matrizDinamica[i].length;j++){
                matrizDinamica[i][j]="";
            }
        }
    }




    public String executeDinamico(){
        int n= this.matrizDinamica.length;
        String[] currentLine;
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


    private String getMaxValue(BacktrackingElement element, int i, int p){
        String valorSemElemento=this.matrizDinamica[i-1][p];
        if(p<element.getWeight()) return valorSemElemento;
        String valorComElemento=(this.matrizDinamica[i-1][p-element.getWeight()])+(element.getValue());
        if(valorComElemento.compareTo(valorSemElemento)>0) return valorComElemento;
        else return valorSemElemento;
    }


    public boolean avaliaCondicaoParada(int i, int j){
        return false;
        //return this.melhorResult.getCurrentWeight()>=this.melhorResult.getMaxWeight();
    }

    public String getBetterCombination(){
        String r= this.executeDinamico();
        return r;
    }


}



class BacktrackingElement{
    private String value;
    private int weight;
    private int id;

    public boolean conditionToAdd(int maxWeight,int currentWeight){
        return this.weight+currentWeight<=maxWeight;
    }

    public BacktrackingElement(String value, int weight, int id) {
        this.value = value;
        this.weight = weight;
        this.id=id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }


}

class ApagaBacktracking extends Backtracking{

    public ApagaBacktracking(List<BacktrackingElement> elements, int maxWeight) {
        super(elements, maxWeight);

    }

}
