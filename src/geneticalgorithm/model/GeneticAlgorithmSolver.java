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

    private ObservableList<Thing> things;
    private ObservableList<Parent> parents;
    private Integer crossingPoints;
    private Integer backpackMaxWeight;
    private Integer iterations;
    private String parentsChoiceMethod;
    private Boolean staticCrossingPoints;
    private List<Integer> mutationPoints;

    /**
     * Default constructor.
     */
    public GeneticAlgorithmSolver(){
        Random random = new Random();
        int size = 10;
        int mutationPointsNumber;
        staticCrossingPoints = true;
        parentsChoiceMethod = "Панміксія";
        crossingPoints = mutationPointsNumber= size / 5;
        mutationPoints = new ArrayList<>();
        int mutationElement;
        for(int i = 0; i < mutationPointsNumber; i++){
            do{
                mutationElement = random.nextInt(size);
            }while (mutationPoints.indexOf(mutationElement) != -1);
            mutationPoints.add(mutationElement);
        }
        iterations = random.nextInt(5) + 3;
        backpackMaxWeight = (random.nextInt(5) + 3) * 100;
        things = FXCollections.observableArrayList();
        parents = FXCollections.observableArrayList();
        int weight, utility;
        for(int i = 0; i < size; i++){
            weight = (random.nextInt(backpackMaxWeight/30) + 5) * 10;
            utility = random.nextInt(backpackMaxWeight) + 10;
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

    public List<Integer> getMutationPoints() {
        return mutationPoints;
    }

    public void setMutationPoints(List<Integer> mutationPoints) {
        this.mutationPoints = mutationPoints;
    }

    public Boolean getStaticCrossingPoints() {
        return staticCrossingPoints;
    }

    public void setStaticCrossingPoints(Boolean staticCrossingPoints) {
        this.staticCrossingPoints = staticCrossingPoints;
    }

    public String getParentsChoiceMethod() {
        return parentsChoiceMethod;
    }

    public void setParentsChoiceMethod(String parentsChoiceMethod) {
        this.parentsChoiceMethod = parentsChoiceMethod;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public Integer getCrossingPoints() {
        return crossingPoints;
    }

    public void setCrossingPoints(Integer crossingPoints) {
        this.crossingPoints = crossingPoints;
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
