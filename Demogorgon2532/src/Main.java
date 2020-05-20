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
        DemogorgonBacktracking backtracking;
        List<BacktrackingElement> feiticos;
        String [] input;
        Long resistenciaCastelo;

        int vidaMonstro;
        while(io.hasNext()) {
            input = ask().split(" ");

            feiticos = new ArrayList<BacktrackingElement>();
            feiticos.add(new BacktrackingElement(0, 0, -1));
            vidaMonstro = Integer.parseInt(input[1]);
            int pesoMax= Main.criaEntrada(Integer.parseInt(input[0]), feiticos);
            backtracking = new DemogorgonBacktracking(feiticos, pesoMax,vidaMonstro);
            Long r = backtracking.getBetterCombination();
            if(backtracking.derrotouMonstro) System.out.println(r);
            else System.out.println(-1);
        }
    }

    public static int criaEntrada(int n, List<BacktrackingElement> elements){
        String [] input;
        int nAdicoes=0;
        int valor;
        int peso;
        int counter=0;
        for(int i=0;i<n;i++){
            input= ask().split(" ");
            valor=Integer.parseInt(input[0]);
            peso=Integer.parseInt(input[1]);
            elements.add(new BacktrackingElement(valor,peso,i));
            counter+=peso;
        }
        if(counter>6100){
            return 6100;
        }
        return counter;
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

class DemogorgonBacktracking extends Backtracking{

    public int menorMana;
    public int vidaMonstro;
    public boolean derrotouMonstro;

    public DemogorgonBacktracking(List<BacktrackingElement> elements, int maxWeight,int vidaMonstro) {
        super(elements, maxWeight);
        menorMana=1000000;
        this.vidaMonstro=vidaMonstro;
        this.derrotouMonstro=false;
    }

    @Override
    public boolean avaliaCondicaoParada(int i, int j) {
        if(this.matrizDinamica[i][j]>=vidaMonstro && j<menorMana){
            menorMana=j;
            derrotouMonstro=true;
        }
        return false;
    }

    @Override
    public Long getBetterCombination() {
        super.getBetterCombination();
        return new Long(this.menorMana);
    }
}
