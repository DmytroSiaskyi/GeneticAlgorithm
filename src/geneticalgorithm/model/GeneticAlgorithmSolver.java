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
    private Integer crossingPoints;

    /**
     * Default constructor.
     */
    public GeneticAlgorithmSolver(){
        int size = 10;
        crossingPoints = size / 5;
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

    /**
     * Constructor with parameters
     *
     * @param backpackMaxWeight
     * @param things
     * @param parents
     */
    public GeneticAlgorithmSolver(Integer backpackMaxWeight, List<Thing> things, List<Parent> parents){
        this.backpackMaxWeight = backpackMaxWeight;
        this.things = FXCollections.observableArrayList();
        this.parents = FXCollections.observableArrayList();
        things.forEach(thing -> this.things.add(thing));
        parents.forEach(parent -> this.parents.add(parent));
    }

    /**
     * Create one random parent
     *
     * @param size
     * @param random
     */
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
