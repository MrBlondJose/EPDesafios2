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
        String [] input;
        TreeSet<ApagarNumeroBackTrackingElement> elements;
        ApagarNumeroBacktracking apagarNumeroBacktracking;
        BackTrackingResult result;
        String [] numbersInput;
        int inputNumber;
        while(true){
            input = ask().split(" ");
            if(input[0].equals("0") && input[1].equals("0")) break;
            numbersInput=ask().split("");
            int pesoMax=numbersInput.length-Integer.parseInt(input[1]);
            apagarNumeroBacktracking=Main.montaEntrada(numbersInput,pesoMax);
            result=apagarNumeroBacktracking.getBetterCombination();
            int x=0;
            System.out.println(result.getCurrentValue());
        }


    }

    public static ApagarNumeroBacktracking montaEntrada(String [] input, int pesoMax){
        TreeSet<ApagarNumeroBackTrackingElement> elements=new TreeSet<ApagarNumeroBackTrackingElement>();
        int i=0;
        for(String s:input){
            elements.add(new ApagarNumeroBackTrackingElement(Integer.parseInt(s),i));
            i++;
        }
        return new ApagarNumeroBacktracking(elements,pesoMax);
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

    public Backtracking(int maxWeight){
        this.backTrackingResult=new BackTrackingResult(maxWeight);
        this.melhorResult=new BackTrackingResult(maxWeight);
    }

    public boolean executeBacktracking(int elementPos){
        if(this.avaliaCondicaoParada()) return true;
        int t=inputList.size();
        //BacktrackingElement currentElement = this.inputList.get(elementPos);
        for(int i = elementPos;i<t;i++){
            BacktrackingElement element=this.inputList.get(i);
            //System.out.println(element.getValue());
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

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

class ApagarNumeroBackTrackingElement extends BacktrackingElement{

    public ApagarNumeroBackTrackingElement(int value, int id) {
        super(value, 1, id);
    }


    @Override
    public int compareTo(Object o) {
        BacktrackingElement b= (BacktrackingElement)o;
        if(b.getId()==this.getId()) return 0;
        if(b.getId()>this.getId()) return -1;
        return 1;
    }
}

class ApagarNumeroBackTrackingResult extends BackTrackingResult{

    private boolean [] escolhidos;



    private  Integer  [] numeros;

    public ApagarNumeroBackTrackingResult(int maxWeight,Collection<ApagarNumeroBackTrackingElement> inputList) {
        super(maxWeight);
        this.numeros= new Integer [inputList.size()];
        int i=0;
        for(ApagarNumeroBackTrackingElement e: inputList){
            this.numeros[i]=e.getValue();
            i++;
        }
        this.escolhidos=new boolean [this.numeros.length];
    }

    public ApagarNumeroBackTrackingResult(int maxWeight,boolean[] escolhidos, Integer [] numeros) {
        super(maxWeight);

    }

    @Override
    public boolean addElement(BacktrackingElement element) {
        if(element.conditionToAdd(this.getMaxWeight(),this.getCurrentWeight())){
            this.getResponseList().add(element);
            this.setCurrentWeight(element.getWeight()+this.getCurrentWeight());
            this.escolhidos[element.getId()]=true;
            this.setCurrentValue(this.ajustaValor(element));

            return true;
        }
        return false;
    }

    private Integer ajustaValor(BacktrackingElement element){
        //this.escolhidos[element.getId()]=true;
        int valor=0;
        int potencia=1;
        for(int i=escolhidos.length-1;i>=0;i--){
            if(this.escolhidos[i]){
                valor+=this.numeros[i]*potencia;
                potencia=potencia*10;
            }
        }
        return valor;
    }

    @Override
    public void removeElement(BacktrackingElement element) {
            this.getResponseList().remove(element);
            this.setCurrentWeight(this.getCurrentWeight()-element.getWeight());
            this.escolhidos[element.getId()]=false;
            this.setCurrentValue(this.ajustaValor(element));
    }

    @Override
    public ApagarNumeroBackTrackingResult copy() {
        ApagarNumeroBackTrackingResult backTrackingResult=new ApagarNumeroBackTrackingResult(this.getMaxWeight(),this.escolhidos.clone(),this.numeros.clone());
        backTrackingResult.setCurrentWeight(this.getCurrentWeight());
        backTrackingResult.getResponseList().addAll(this.getResponseList());
        backTrackingResult.setCurrentValue(this.getCurrentValue());
        return backTrackingResult;
    }
}


class ApagarNumeroBacktracking extends Backtracking{


    public ApagarNumeroBacktracking(TreeSet<ApagarNumeroBackTrackingElement> elements, int maxWeight) {
        super( maxWeight);
        this.inputList= new ArrayList<BacktrackingElement>();

        this.backTrackingResult=new ApagarNumeroBackTrackingResult(maxWeight,elements);
        this.melhorResult=new ApagarNumeroBackTrackingResult(maxWeight,elements);
        int i=0;
        for(ApagarNumeroBackTrackingElement e:elements){

            inputList.add(e);
        }
    }

}
