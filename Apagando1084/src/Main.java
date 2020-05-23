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
            Long r = backtracking.getBetterCombination();
            System.out.println(r);
        }
    }

    public static void criaEntrada(List<BacktrackingElement> elements){
       String [] input = ask().split("");
       int n=input.length;
       int i=0;
       for(String s: input){
           elements.add(new BacktrackingElement(Integer.parseInt(s),1,i));
           i++;
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
        long valorComElemento=Long.parseLong(Long.toString(this.matrizDinamica[i-1][p-element.getWeight()])+Integer.toString(element.getValue()));
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

class ApagaBacktracking extends Backtracking{

    public ApagaBacktracking(List<BacktrackingElement> elements, int maxWeight) {
        super(elements, maxWeight);

    }

}
