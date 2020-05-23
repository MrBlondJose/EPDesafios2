import java.util.*;

/**
 * Gustavo Medeiros Santos
 * Amanda Morales Baptista
 */
public class Main {

    public static Scanner io= new Scanner(System.in);

    public static boolean usaTodos=false;

    public static String ask(){
        return io.nextLine();
    }

    public static void main(String [] args) throws Exception {

        int nProjeteis;
        DemogorgonBacktracking backtracking;
        List<BacktrackingElement> feiticos;
        String [] input;
        Long resistenciaCastelo;

        int vidaMonstro;
        while(io.hasNext()) {
            input = ask().split(" ");

            feiticos = new ArrayList<BacktrackingElement>();
            //feiticos.add(new BacktrackingElement(0, 0, -1));
            vidaMonstro = Integer.parseInt(input[1]);
            int pesoMax= Main.criaEntrada(Integer.parseInt(input[0]), feiticos,vidaMonstro);
            /*if(pesoMax==-1){
                System.out.println(-1);
                continue;
            }*/

            backtracking = new DemogorgonBacktracking(feiticos, vidaMonstro,vidaMonstro);
            Long r = backtracking.getBetterCombination();
            if(backtracking.derrotouMonstro) System.out.println(r);
            else System.out.println(-1);
        }
    }

    public static int criaEntrada(int n, List<BacktrackingElement> elements,int vidaMonstro) throws Exception {
        String [] input;
        int nAdicoes=0;
        int valor;
        int peso;
        int counter=0;
        int danos=0;
        int somaPesos=0;
        boolean insere=true;
        BacktrackingElement melhorElement=new BacktrackingElement(-1,100,-1);
        for(int i=0;i<n;i++){
            input= ask().split(" ");
            valor=Integer.parseInt(input[1]);
            peso=Integer.parseInt(input[0]);
            BacktrackingElement element=new BacktrackingElement(valor,peso,i);
            elements.add(element);
            if(element.getValue()>melhorElement.getValue()) melhorElement=element;
            if(i>0) Main.executaTrocaAnterior(elements,i-1,element);
            danos+=valor;
            somaPesos+=peso;
            if(danos<vidaMonstro || insere){
                counter+=peso;
            }
            if(danos>=vidaMonstro){
                if(i==n-1) Main.usaTodos=true;
                insere=false;
            }
        }
        int auxValor=melhorElement.getValue();
        int auxPeso=melhorElement.getWeight();
        BacktrackingElement primeiro=elements.get(0);
        melhorElement.setValue(primeiro.getValue());
        melhorElement.setWeight(primeiro.getWeight());
        primeiro.setValue(auxValor);
        primeiro.setWeight(auxPeso);
        if(danos<vidaMonstro) return -1;
        if(counter>7500){
            return recalculaMana(elements,vidaMonstro);
        }
        return counter;
    }

    public static int recalculaMana(List<BacktrackingElement> elements, int vidaMonstro){
        int manas=0;
        int danos=0;
        int t=elements.size();
        for(int i=0;i<t && danos<vidaMonstro;i++){
            manas+=elements.get(i).getWeight();
            danos+=elements.get(i).getValue();
        }
        if(manas>15000) return 10000;
        return manas;
    }

    public static void executaTrocaAnterior(List<BacktrackingElement> list,int i,BacktrackingElement element) {
        BacktrackingElement anterior = list.get(i);
        int auxValor = anterior.getValue();
        int auxPeso = anterior.getWeight();
        if (anterior.getValue() < element.getValue()) {
            BacktrackingElement primeiro = list.get(0);
            anterior.setValue(primeiro.getValue());
            anterior.setWeight(primeiro.getWeight());
            primeiro.setValue(auxValor);
            primeiro.setWeight(auxPeso);
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
        this.populaMatriz();
    }

    public void populaMatriz(){
        for(int i=0;i<this.matrizDinamica.length;i++){
            matrizDinamica[i][0]=10000000000l;
        }
        for(int i=0;i<matrizDinamica[0].length;i++){
            matrizDinamica[0][i]=10000000000l;
        }
    }





    public long executeDinamico(){
        int n= this.matrizDinamica.length;
        long[] currentLine;
        int nColunas=matrizDinamica[0].length;
        BacktrackingElement currentElement;
        for(int i=1;i<n;i++){
            currentLine=matrizDinamica[i];
            currentElement=this.inputList.get(i-1);
            for(int j=1;j<nColunas;j++){
                this.matrizDinamica[i][j]=this.getMaxValue(currentElement,i,j);
                if(this.avaliaCondicaoParada(i,j)) {
                    nColunas=j;
                }
            }
        }
        return matrizDinamica[matrizDinamica.length-1][matrizDinamica[0].length-1];
    }


    private Long getMaxValue(BacktrackingElement element,int i, int p){
        long valorSemElemento=this.matrizDinamica[i-1][p];
        if(!(element.getWeight()>=p)) return valorSemElemento;
        long valorComElemento;
        //if(i==1) valorComElemento=element.getValue();
        int ind=element.getWeight();
        if(element.getWeight()>p) ind=p;
        valorComElemento=this.matrizDinamica[i-1][p-ind]+element.getValue();
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

    /*
    @Override
    public boolean avaliaCondicaoParada(int i, int j) {
        if(this.matrizDinamica[i][j]>=vidaMonstro && j<menorMana){
            menorMana=j;
            derrotouMonstro=true;
            return true;
        }
        return false;
    }*/

    @Override
    public Long getBetterCombination() {
        super.getBetterCombination();
        return new Long(this.menorMana);
    }
}
