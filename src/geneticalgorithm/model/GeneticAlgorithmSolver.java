package geneticalgorithm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class GeneticAlgorithmSolver {

    private Integer backpackMaxWeight;
    private ObservableList<Thing> things;
    private ObservableList<Parent> parents;

    /**
     * Default constructor.
     */
    public GeneticAlgorithmSolver(){
        int size = 10;
        Random random = new Random();
        backpackMaxWeight = (random.nextInt(5) + 3) * 100;
        things = FXCollections.observableArrayList();
        parents = FXCollections.observableArrayList();
        int weight, utility;
        for(int i = 0; i < size; i++){
            weight = (random.nextInt(backpackMaxWeight/30) + 5) * 10;
            utility = random.nextInt(backpackMaxWeight);
            Thing thing = new Thing("C" + i, weight, utility);
            things.add(thing);
        }

        Parent parent;
        for(int i = 0; i < size; i++){
            do {
                parent = generateParent(size, random);
            }while(parents.indexOf(parent) != -1);
            parents.add(parent);
        }
    }
    private Parent generateParent(int size, Random random){
        Parent result;
        int weight = 0;
        int utility = 0;
        List<Integer> chromosome = new ArrayList<>();
        for(int j = 0; j < size; j++){
            double genPosibility = random.nextDouble();
            double myPosibility = 1;
            myPosibility /= size - j;
            myPosibility += 1/size;
            if((genPosibility <= myPosibility)&&(weight + things.get(j).getWeight() <= backpackMaxWeight)){
                chromosome.add(1);
                utility += things.get(j).getUtility();
                weight += things.get(j).getWeight();
            }else{
                chromosome.add(0);
            }
        }
        result = new Parent();
        result.setUtility(utility);
        result.setWeight(weight);
        result.setChromosomeList(chromosome);
        return result;
    }

    public void countInduvidualData(Parent parent){

    }

    public Integer getBackpackMaxWeight() {
        return backpackMaxWeight;
    }

    public void setBackpackMaxWeight(Integer backpackMaxWeight) {
        this.backpackMaxWeight = backpackMaxWeight;
    }

    public ObservableList<Parent> getParents() {
        return parents;
    }

    public void setParents(ObservableList<Parent> parents) {
        this.parents = parents;
    }

    public ObservableList<Thing> getThings() {
        return things;
    }

    public void setThings(ObservableList<Thing> things) {
        this.things = things;
    }
}
