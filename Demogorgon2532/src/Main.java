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
            //feiticos.add(new BacktrackingElement(0, 0, -1));
            vidaMonstro = Integer.parseInt(input[1]);
            int pesoMax= Main.criaEntrada(Integer.parseInt(input[0]), feiticos,vidaMonstro);
            if(pesoMax==-1){
                System.out.println(-1);
                continue;
            }

            backtracking = new DemogorgonBacktracking(feiticos, pesoMax,vidaMonstro,Integer.parseInt(input[0]));
            Long r = backtracking.getBetterCombination();
            if(backtracking.derrotouMonstro) System.out.println(r);
            else System.out.println(-1);
        }
    }

    public static int criaEntrada(int n, List<BacktrackingElement> elements,int vidaMonstro){
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
            valor=Integer.parseInt(input[0]);
            peso=Integer.parseInt(input[1]);
            BacktrackingElement element=new BacktrackingElement(valor,peso,i);
            elements.add(element);
            if(element.getValue()>melhorElement.getValue()) melhorElement=element;
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
        return counter;
    }




}

class Backtracking{


    List<BacktrackingElement> inputList;
    long[][] matrizDinamica;
    long[] currentWeigth;
    int maxWeight;
    int nFeiticos;

    public Backtracking(List<BacktrackingElement> elements, int maxWeight,int nFeiticos){
        this.inputList=elements;

        this.matrizDinamica = new long[1][maxWeight+1];
        this.currentWeigth =new long[matrizDinamica[0].length];
        this.maxWeight=maxWeight;
        this.nFeiticos=nFeiticos;
    }




    public long executeDinamico(){
        int n= this.nFeiticos;
        long[] currentLine;
        int nColunas=matrizDinamica[0].length;
        BacktrackingElement currentElement;
        for(int i=1;i<=n;i++){
            currentLine=matrizDinamica[0];
            currentElement=this.inputList.get(i-1);
            long[] tempMatriz=new long[currentLine.length];
            for(int j=1;j<nColunas;j++){
                tempMatriz[j] =this.getMaxValue(currentElement,i,j);
                if(this.avaliaCondicaoParada(i,j,tempMatriz[j])) {
                    nColunas=j;
                }
            }
            this.matrizDinamica[0]=tempMatriz;
        }
        return matrizDinamica[matrizDinamica.length-1][matrizDinamica[0].length-1];
    }


    private Long getMaxValue(BacktrackingElement element,int i, int p){
        long valorSemElemento=this.matrizDinamica[0][p];
        if(p<element.getWeight()) return valorSemElemento;
        long valorComElemento=this.matrizDinamica[0][p-element.getWeight()]+element.getValue();
        if(valorComElemento>valorSemElemento) return valorComElemento;
        else return valorSemElemento;
    }


    public boolean avaliaCondicaoParada(int i, int j,long valor){
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


    public DemogorgonBacktracking(List<BacktrackingElement> elements, int maxWeight,int vidaMonstro,int nFeiticos) {
        super(elements, maxWeight,nFeiticos);
        menorMana=1000000;
        this.vidaMonstro=vidaMonstro;
        this.derrotouMonstro=false;

    }

    @Override
    public boolean avaliaCondicaoParada(int i, int j,long valor) {
        if(valor>=vidaMonstro && j<menorMana){
            menorMana=j;
            derrotouMonstro=true;
            return true;
        }
        return false;
    }

    @Override
    public Long getBetterCombination() {
        super.getBetterCombination();
        return new Long(this.menorMana);
    }
}
