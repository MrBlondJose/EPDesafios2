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
        TreeSet<BacktrackingElement> projeteis;
        BackTrackingResult response;
        String [] projeteisInput;
        int resistenciaCastelo;
        int pesoMax;
        for(int i=0;i<n;i++){
            nProjeteis=Integer.parseInt(ask());
            projeteis=new TreeSet<BacktrackingElement>();
            for(int j=0;j<nProjeteis;j++){
                  projeteisInput=ask().split(" ");
                  projeteis.add(new BacktrackingElement(Integer.parseInt(projeteisInput[0]),Integer.parseInt(projeteisInput[1]),j));
            }
            pesoMax=Integer.parseInt(ask());
            resistenciaCastelo=Integer.parseInt(ask());
            backtracking=new CanhaoBacktracking(projeteis,pesoMax,resistenciaCastelo);
            response=backtracking.getBetterCombination();
            Main.trataResposta(backtracking);
        }

    }


    public static void trataResposta(CanhaoBacktracking canhao){
        if(canhao.avaliaCondicaoParada()){
            System.out.println("Missao completada com sucesso");
        }
        else{
            System.out.println("Falha na missao");
        }
    }



}

class Backtracking{

    BackTrackingResult backTrackingResult;
    BackTrackingResult melhorResult;
    List<BacktrackingElement> inputList;

    public Backtracking(TreeSet<BacktrackingElement> elements, int maxWeight){
        this.inputList=new ArrayList<BacktrackingElement>();
        for(BacktrackingElement e: elements){
            this.inputList.add(e);
        }
        this.backTrackingResult=new BackTrackingResult(maxWeight);
        this.melhorResult=new BackTrackingResult(maxWeight);
    }

    public boolean executeBacktracking(int elementPos){
        if(this.avaliaCondicaoParada()) return true;
        int t=inputList.size();
        //BacktrackingElement currentElement = this.inputList.get(elementPos);
        for(int i = elementPos;i<t;i++){
            BacktrackingElement element=this.inputList.get(i);
            if(!this.backTrackingResult.addElement(element)) continue;
            this.avaliaMelhorResult();
            if(i==t-1){
                backTrackingResult.removeElement(element);
                continue;
            }
            if(this.executeBacktracking(i+1)){
                this.backTrackingResult.removeElement(element);
                return true;
            }
            else{
                this.backTrackingResult.removeElement(element);
            }

        }
        //this.backTrackingResult.removeElement(this.inputList.get(elementPos));
        return false;
    }

    public void avaliaMelhorResult(){
        if(this.backTrackingResult.getCurrentValue()> this.melhorResult.getCurrentValue()){
            this.melhorResult=backTrackingResult.copy();
        }
    }

    public boolean avaliaCondicaoParada(){
        return false;
        //return this.melhorResult.getCurrentWeight()>=this.melhorResult.getMaxWeight();
    }

    public BackTrackingResult getBetterCombination(){
        this.executeBacktracking(0);
        return this.melhorResult;
    }


}

class BackTrackingResult{
    private List<BacktrackingElement> responseList;
    private final int maxWeight;
    private int currentWeight;
    private int currentValue;


    public int getCurrentValue() {
        return currentValue;
    }

    public BackTrackingResult(int maxWeight) {
        this.maxWeight = maxWeight;
        this.currentWeight=0;
        this.currentValue=0;
        this.responseList=new ArrayList<BacktrackingElement>();
    }

    public List<BacktrackingElement> getResponseList() {
        return responseList;
    }

    public void setResponseList(List<BacktrackingElement> responseList) {
        this.responseList = responseList;
    }

    public int getMaxWeight() {
        return maxWeight;
    }


    public int getCurrentWeight() {
        return currentWeight;
    }



    public boolean addElement(BacktrackingElement element){
        if(element.conditionToAdd(this.maxWeight,this.currentWeight)){
            this.responseList.add(element);
            this.currentWeight+=element.getWeight();
            this.currentValue+=element.getValue();
            return true;
        }
        return false;
    }

    public void removeElement(BacktrackingElement element){
            this.responseList.remove(element);
            this.currentWeight-=element.getWeight();
            this.currentValue-=element.getValue();
    }

    public BackTrackingResult copy(){
        BackTrackingResult backTrackingResult=new BackTrackingResult(this.maxWeight);
        backTrackingResult.currentWeight=this.currentWeight;
        backTrackingResult.getResponseList().addAll(this.responseList);
        backTrackingResult.currentValue=this.currentValue;
        return backTrackingResult;
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
    public int resistenciaCastelo;

    public CanhaoBacktracking(TreeSet<BacktrackingElement> elements, int maxWeight) {
        super(elements, maxWeight);
    }

    public CanhaoBacktracking(TreeSet<BacktrackingElement> elements, int maxWeight, int resistenciaCastelo) {
        super(elements, maxWeight);
        this.resistenciaCastelo=resistenciaCastelo;
    }

    @Override
    public boolean avaliaCondicaoParada() {
        return this.resistenciaCastelo<=this.melhorResult.getCurrentValue();
    }
}
