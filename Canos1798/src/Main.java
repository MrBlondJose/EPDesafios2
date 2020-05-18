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
        CanoBacktracking backtracking;
        TreeSet<BacktrackingElement> canosPedidos;
        BackTrackingResult response;
        String[] canosInput;
        int resistenciaCastelo;
        int tamanhoMax;
        String[] input;
        while(io.hasNext()) {
            input=ask().split(" ");
            int n = Integer.parseInt(input[0]);
            tamanhoMax = Integer.parseInt(input[1]);
            canosPedidos = Main.trataEntrada(n, tamanhoMax);
            backtracking = new CanoBacktracking(canosPedidos, tamanhoMax);
            response = backtracking.getBetterCombination();
            System.out.println(response.getCurrentValue());
        }
    }

    public static TreeSet<BacktrackingElement> trataEntrada(int nInput, int tamanhoMax){
        TreeSet<BacktrackingElement> canosPedidos=new TreeSet<BacktrackingElement>();
        String [] canosInput;
        int counter=0;
        for(int j=0;j<nInput;j++) {
            canosInput = ask().split(" ");
            int nVersoes = tamanhoMax / Integer.parseInt(canosInput[0]);
            for (int k = 0; k < nVersoes; k++) {
                canosPedidos.add(new BacktrackingElement(Integer.parseInt(canosInput[1]), Integer.parseInt(canosInput[0]), counter));
                counter++;
                }
            }
        return canosPedidos;
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

class CanoBacktracking extends Backtracking{
    public int resistenciaCastelo;

    public CanoBacktracking(TreeSet<BacktrackingElement> elements, int maxWeight) {
        super(elements, maxWeight);
    }

    public CanoBacktracking(TreeSet<BacktrackingElement> elements, int maxWeight, int resistenciaCastelo) {
        super(elements, maxWeight);
        this.resistenciaCastelo=resistenciaCastelo;
    }

}
