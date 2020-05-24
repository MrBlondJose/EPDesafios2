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

        PapaiNoelBacktracking backtracking;
        List<BacktrackingElement> pacotes;
        String [] input;
        int pesoMax=50;
        int n =Integer.parseInt(ask());
        int nPacotes=0;
        for(int i=0;i<n;i++) {
            nPacotes= Integer.parseInt(ask());
            pacotes = new ArrayList<BacktrackingElement>();
            Main.criaEntrada(nPacotes, pacotes);
            backtracking = new PapaiNoelBacktracking(pacotes, pesoMax);
            Long r = backtracking.getBetterCombination();
            System.out.println(r+ " brinquedos");
            System.out.println("Peso: "+backtracking.menorPeso+" kg");
            int usados=(int) backtracking.nPacotes;
            int sobra=(nPacotes-usados);
            System.out.println("sobra(m) "+sobra+" pacote(s)");
            System.out.println();
        }
    }

    public static void criaEntrada(int n, List<BacktrackingElement> elements){
        String [] input;
        for(int i=0;i<n;i++){
            input=ask().split(" ");
            elements.add(new BacktrackingElement(Integer.parseInt(input[0]),Integer.parseInt(input[1]),i));
        }
    }


}

class Backtracking{


    List<BacktrackingElement> inputList;
    long[][] matrizDinamica;
    long[][] nUsados;
    int maxWeight;


    public Backtracking(List<BacktrackingElement> elements, int maxWeight){
        this.inputList=elements;

        this.matrizDinamica = new long[elements.size()+1][maxWeight+1];
        this.nUsados=new long[elements.size()+1][maxWeight+1];
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
        if(p<element.getWeight()) {
            nUsados[i][p]=this.nUsados[i-1][p];
            return valorSemElemento;
        }
        long valorComElemento=this.matrizDinamica[i-1][p-element.getWeight()]+element.getValue();
        if(valorComElemento>valorSemElemento){
            nUsados[i][p]=nUsados[i-1][p-element.getWeight()]+1;
            return valorComElemento;
        }
        else {
            nUsados[i][p]=this.nUsados[i-1][p];
            return valorSemElemento;
        }
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



class BacktrackingElement{
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


}

class PapaiNoelBacktracking extends Backtracking{
    long maiorValor=0;
    int menorPeso=100000000;
    long nPacotes=0;

    public PapaiNoelBacktracking(List<BacktrackingElement> elements, int maxWeight) {
        super(elements, maxWeight);

    }

    @Override
    public boolean avaliaCondicaoParada(int i, int j) {
        if(this.matrizDinamica[i][j]>maiorValor){
            this.maiorValor=matrizDinamica[i][j];
            menorPeso=j;
            this.nPacotes=nUsados[i][j];
        }
        //if(this.matrizDinamica[i][j]>=maiorValor) this.nPacotes=nUsados[i][j];
        return false;
    }
}
