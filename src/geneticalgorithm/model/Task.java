package geneticalgorithm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Task implements Cloneable{
    private ObservableList<Thing> things;
    private ObservableList<Parent> parents;
    private List<Integer> crossingPointsList;
    private List<Integer> mutationPoints;
    private Integer backpackMaxWeight;
    private Integer iterations;
    private String parentsChoiceMethod;
    private Boolean staticCrossingPoints;
    private Boolean mutationInversion;
    private int size;

    private Integer crossingPoints;

    /**
     * Default constructor.
     */
    public Task(){
//        Random random = new Random();
//        int size = 10;
//        int mutationPointsNumber;
//        iterations = random.nextInt(5) + 3;
//        backpackMaxWeight = (random.nextInt(5) + 3) * 100;
//        parentsChoiceMethod = "Панміксія";
//
//        crossingPointsList = new ArrayList<>();
//        crossingPoints = mutationPointsNumber= size / 5;
//        staticCrossingPoints = true;
//        generateCrossingPoints(crossingPoints, size);
//
//        mutationInversion = false;
//        generateMutationPoints(mutationPointsNumber, size);
//
//        things = FXCollections.observableArrayList();
//        int weight, utility;
//        for(int i = 0; i < size; i++){
//            weight = (random.nextInt(backpackMaxWeight/30) + 5) * 10;
//            utility = random.nextInt(backpackMaxWeight) + 10;
//            Thing thing = new Thing("C" + i, weight, utility);
//            things.add(thing);
//        }
//
//        parents = FXCollections.observableArrayList();
//        Parent parent;
//        for(int i = 0; i < size; i++){
//            do {
//                parent = generateParent(size, random);
//            }while(parents.indexOf(parent) != -1);
//            parents.add(parent);
//        }
    }
    /**
     * Constructor with parameters
     *
     * @param things
     * @param parents
     * @param backpackMaxWeight
     * @param crossingPoints
     * @param mutationPoints
     * @param iterations
     * @param parentsChoiceMethod
     * @param staticCrossingPoints
     * @param mutationInversion
     */
    public Task(ObservableList<Thing> things, ObservableList<Parent> parents, Integer backpackMaxWeight,
                                  List<Integer> crossingPoints, List<Integer> mutationPoints, Integer iterations, String parentsChoiceMethod,
                                  Boolean staticCrossingPoints, Boolean mutationInversion){
        this.things = things;
        this.parents = parents;
        this.backpackMaxWeight = backpackMaxWeight;
        this.crossingPointsList = crossingPoints;
        this.mutationPoints = mutationPoints;
        this.iterations = iterations;
        this.parentsChoiceMethod = parentsChoiceMethod;
        this.staticCrossingPoints = staticCrossingPoints;
        this.mutationInversion = mutationInversion;
    }

    /**
     * Generate crossing points
     *
     * @param number
     * @param size
     */
    public void generateCrossingPoints(int number, int size){
        Random random = new Random();
        int newCrossPoint;
        for(int i = 0; i < crossingPoints; i++){
            do{
                newCrossPoint = random.nextInt(size - 1);
            }while(crossingPointsList.indexOf(newCrossPoint + 1) != -1 && crossingPointsList.indexOf(newCrossPoint - 1) != -1);
            crossingPointsList.add(newCrossPoint);
        }
    }

    /**
     * Generate mutation points
     *
     * @param number
     * @param size
     */
    public void generateMutationPoints(int number, int size){
        Random random = new Random();
        mutationPoints = new ArrayList<>();
        int mutationElement;
        for(int i = 0; i < number; i++){
            do{
                mutationElement = random.nextInt(size);
            }while (mutationPoints.indexOf(mutationElement) != -1);
            mutationPoints.add(mutationElement);
        }
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
    public Task clone(){
        Task task = new Task();
        task.setSize(size);
        task.setThings(things);
        task.setStaticCrossingPoints(staticCrossingPoints);
        task.setCrossingPointsList(crossingPointsList);
        task.setIterations(iterations);
        task.setBackpackMaxWeight(backpackMaxWeight);
        task.setMutationInversion(mutationInversion);
        task.setCrossingPoints(crossingPoints);
        task.setParents(parents);
        task.setParentsChoiceMethod(parentsChoiceMethod);
        return task;
    }
    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public ObservableList<Thing> getThings() {
        return things;
    }

    public void setThings(ObservableList<Thing> things) {
        this.things = things;
    }

    public ObservableList<Parent> getParents() {
        return parents;
    }

    public void setParents(ObservableList<Parent> parents) {
        this.parents = parents;
    }

    public List<Integer> getCrossingPointsList() {
        return crossingPointsList;
    }

    public void setCrossingPointsList(List<Integer> crossingPointsList) {
        this.crossingPointsList = crossingPointsList;
    }

    public List<Integer> getMutationPoints() {
        return mutationPoints;
    }

    public void setMutationPoints(List<Integer> mutationPoints) {
        this.mutationPoints = mutationPoints;
    }

    public Integer getBackpackMaxWeight() {
        return backpackMaxWeight;
    }

    public void setBackpackMaxWeight(Integer backpackMaxWeight) {
        this.backpackMaxWeight = backpackMaxWeight;
    }

    public Integer getIterations() {
        return iterations;
    }

    public void setIterations(Integer iterations) {
        this.iterations = iterations;
    }

    public String getParentsChoiceMethod() {
        return parentsChoiceMethod;
    }

    public void setParentsChoiceMethod(String parentsChoiceMethod) {
        this.parentsChoiceMethod = parentsChoiceMethod;
    }

    public Boolean getStaticCrossingPoints() {
        return staticCrossingPoints;
    }

    public void setStaticCrossingPoints(Boolean staticCrossingPoints) {
        this.staticCrossingPoints = staticCrossingPoints;
    }

    public Boolean getMutationInversion() {
        return mutationInversion;
    }

    public void setMutationInversion(Boolean mutationInversion) {
        this.mutationInversion = mutationInversion;
    }

    public Integer getCrossingPoints() {
        return crossingPoints;
    }

    public void setCrossingPoints(Integer crossingPoints) {
        this.crossingPoints = crossingPoints;
    }
}
