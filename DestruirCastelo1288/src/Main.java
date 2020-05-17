import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.TreeSet;

/**
 * Gustavo Medeiros Santos
 * Amanda Morales Baptista
 */
public class Main {

    public static void main(String [] args){
        Main.chamaBackTracking();
    }

    public static void chamaBackTracking(){
        TreeSet<BacktrackingElement> elements = new TreeSet<BacktrackingElement>();
        elements.add(new BacktrackingElement(600,8));
        elements.add(new BacktrackingElement(500,7));
        elements.add(new BacktrackingElement(300,4));
        elements.add(new BacktrackingElement(250,3));
        elements.add(new BacktrackingElement(480,5));
        elements.add(new BacktrackingElement(290,3));
        elements.add(new BacktrackingElement(285,3));
        Backtracking b= new Backtracking(elements,10);
        BackTrackingResult response = b.getBetterCombination();
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

    public boolean conditionToAdd(int maxWeight,int currentWeight ){
        return this.weight+currentWeight<=maxWeight;
    }

    public BacktrackingElement(int value, int weight) {
        this.value = value;
        this.weight = weight;
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
        if(b.getValue()==this.getValue()) return 0;
        if(b.getValue()>this.getValue()) return 1;
        return -1;
    }
}
