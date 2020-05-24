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
            //Main.criaEntrada(numeros);
            pesoMax = Integer.parseInt(input[0])-Integer.parseInt(input[1]);
            backtracking = new ApagaBacktracking(numeros, pesoMax,Integer.parseInt(input[0]));
            //Long ini=System.currentTimeMillis();


            //System.out.println(ini);
            String r = backtracking.getBetterCombination();
            //Long fim=System.currentTimeMillis();
            //System.out.println("fi:"+(fim-ini));
            //System.out.println(r);
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

    boolean [] resp;
    int maxWeight;
    int nElements;


    public Backtracking(List<BacktrackingElement> elements, int maxWeight, int nElements){
        this.inputList=elements;
        this.nElements=nElements;
        //this.resp=new boolean[nElements];
        //this.matrizDinamica = new String[2][maxWeight+1];

        this.maxWeight=maxWeight;
        //this.populaMatrizDinamica();
    }

    public void populaMatrizDinamica(){
        for (int i=0;i<matrizDinamica.length;i++){
            for(int j=0;j<matrizDinamica[i].length;j++){
                matrizDinamica[i][j]="";
            }
        }
    }




    public String executeDinamico(){
        int n= this.nElements;
        String[] currentLine;
        BacktrackingElement currentElement;
        for(int i=0;i<n;i++){
            currentLine=matrizDinamica[0];
            currentElement=this.inputList.get(i);
            String [] tempResult= new String[currentLine.length];
            tempResult[0]="";
            int j=0;
            j=currentLine.length-this.maxWeight;

            //j=currentLine.length-this.maxWeight;
            for(j=currentLine.length-2;j<currentLine.length;j++){
                 //System.out.println(i +" "+j);
                 tempResult[j]=this.getMaxValue(currentElement,i,j);
                 if(this.avaliaCondicaoParada(i,j)) return this.matrizDinamica[i][j];
                }
            matrizDinamica[0]=tempResult;
        }
        return matrizDinamica[0][matrizDinamica[0].length-1];
    }


    private String getMaxValue(BacktrackingElement element, int i, int p){
        String valorSemElemento=this.matrizDinamica[0][p];
        if(valorSemElemento==null) valorSemElemento="";
        if(this.matrizDinamica[0][p-element.getWeight()]==null) this.matrizDinamica[0][p-element.getWeight()]="";
        if(p<element.getWeight()) return valorSemElemento;
        String valorComElemento=(this.matrizDinamica[0][p-element.getWeight()])+(element.getValue());
        if(valorComElemento.compareTo(valorSemElemento)>0) return valorComElemento;
        else return valorSemElemento;
    }


    public boolean avaliaCondicaoParada(int i, int j){
        return false;
        //return this.melhorResult.getCurrentWeight()>=this.melhorResult.getMaxWeight();
    }

    public String getBetterCombination(){
        String r= this.apgaNumeros();
        return r;
    }

    private String apgaNumeros(){
        int apagados=0;
        //List<BacktrackingElement> pilha=new ArrayList<BacktrackingElement>();
        char [] input=Main.ask().toCharArray();
        int inseridos=0;
        char [] response=new char[maxWeight];
        //Long ini=System.currentTimeMillis();
        for(int i=0;i<this.nElements;i++){
            //BacktrackingElement currentElement=new BacktrackingElement(input[i],1,i);
            char currentElement=input[i];
            if(i==0) {
                //pilha.add(currentElement);
                response[i]= currentElement;
                inseridos++;
                continue;
            }
            //BacktrackingElement currentElement=inputList.get(i);

            while(inseridos>0 && currentElement>response[inseridos-1]  && apagados<this.nElements-maxWeight){
                response[inseridos-1]=' ';
                //response=response.substring(0,inseridos-1);
                inseridos--;
                apagados++;
            }
            if(inseridos<maxWeight) {
                response[inseridos] = currentElement;
                inseridos++;
            }
        }


        /*for(int i=0;i<this.maxWeight;i++){
            response+=pilha.get(i).getValue();
        }*/

        System.out.println(response);
        return "";
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

    public ApagaBacktracking(List<BacktrackingElement> elements, int maxWeight, int nElements) {
        super(elements, maxWeight, nElements);

    }

}
