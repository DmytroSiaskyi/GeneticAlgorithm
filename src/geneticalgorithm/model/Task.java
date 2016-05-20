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

    private int size;
    private ObservableList<Thing> things;
    private ObservableList<Parent> parents;
    private List<Integer> crossingPointsList;
    private List<Integer> mutationPoints;
    private Integer backpackMaxWeight;
    private Integer iterations;
    private String parentsChoiceMethod;
    private Boolean staticCrossingPoints;
    private Boolean mutationInversion;

    private Integer crossingPoints;

    /**
     * Default constructor.
     */
    public Task(){}

    /**
     * Generate task with size.
     */
    public Task(int size){
        Random random = new Random();
        int mutationPointsNumber;
        iterations = random.nextInt(5) + 3;
        backpackMaxWeight = (random.nextInt(5) + 3) * 100;
        parentsChoiceMethod = "Панміксія";

        crossingPointsList = new ArrayList<>();
        crossingPoints = mutationPointsNumber= size / 5;
        if(crossingPoints < 2){
            crossingPoints = 2;
        }
        if(mutationPointsNumber < 1){
            mutationPointsNumber = 1;
        }
        staticCrossingPoints = true;
        generateCrossingPoints(crossingPoints, size);

        mutationInversion = false;
        generateMutationPoints(mutationPointsNumber, size);

        things = FXCollections.observableArrayList();
        int weight, utility;
        for(int i = 0; i < size; i++){
            weight = (random.nextInt(backpackMaxWeight/30) + 5) * 10;
            utility = random.nextInt(backpackMaxWeight) + 10;
            Thing thing = new Thing("C" + i, weight, utility);
            things.add(thing);
        }

        parents = FXCollections.observableArrayList();
        Parent parent;
        for(int i = 0; i < size; i++){
            do {
                parent = generateParent(size);
            }while(parents.indexOf(parent) != -1);
            parents.add(parent);
        }
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
     * @param pointsNumber
     * @param numberOfThings
     */
    public void generateCrossingPoints(int pointsNumber, int numberOfThings){
        Random random = new Random();
        int newCrossPoint;
        crossingPointsList = new ArrayList<>();
        boolean flag;
        for(int i = 0; i < pointsNumber; i++){
            do{
                flag = true;
                newCrossPoint = random.nextInt(numberOfThings - 1) + 1;
                if(crossingPointsList.indexOf(newCrossPoint + 1) != -1){
                    flag = false;
                }
                if(crossingPointsList.indexOf(newCrossPoint - 1) != -1){
                    flag = false;
                }
                if(crossingPointsList.indexOf(newCrossPoint) != -1){
                    flag = false;
                }
            }while(!flag);
            crossingPointsList.add(newCrossPoint);
        }
    }

    /**
     * Generate mutation points
     *
     * @param pointsNumber
     * @param parentsNumber
     */
    public void generateMutationPoints(int pointsNumber, int parentsNumber){
        Random random = new Random();
        mutationPoints = new ArrayList<>();
        int mutationElement;
        for(int i = 0; i < pointsNumber; i++){
            do{
                mutationElement = random.nextInt(parentsNumber - 1) + 1;
            }while (mutationPoints.indexOf(mutationElement) != -1);
            mutationPoints.add(mutationElement);
        }
    }

    /**
     * Create one random parent
     *
     * @param numberOfThings
     */
    private Parent generateParent(int numberOfThings){
        Random random = new Random();
        Parent result;
        int weight = 0;
        int utility = 0;
        List<Integer> chromosome = new ArrayList<>();
        for(int j = 0; j < numberOfThings; j++){
            double genPosibility = random.nextDouble();
            double myPosibility = 1;
            myPosibility /= numberOfThings - j;
            myPosibility += 1/numberOfThings;
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
