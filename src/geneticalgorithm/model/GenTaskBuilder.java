package geneticalgorithm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class GenTaskBuilder extends TaskBuilder{

    Random random = new Random();
    int size = 0;

    @Override
    public void buildParents() {
        ObservableList<Parent> parents = FXCollections.observableArrayList();
        Parent parent;
        for(int i = 0; i < size; i++){
            do {
                parent = generateParent(size, random, task.getThings(), task.getBackpackMaxWeight());
            }while(parents.indexOf(parent) != -1);
            parents.add(parent);
        }
        task.setParents(parents);
    }

    /**
     * Create one random parent
     *
     * @param size
     * @param random
     */
    private Parent generateParent(int size, Random random, ObservableList<Thing> things, int backpackMaxWeight){
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

    @Override
    public void buildThing() {
        size = task.getSize();
        ObservableList<Thing> things = FXCollections.observableArrayList();
        int weight, utility;
        for(int i = 0; i < size; i++){
            weight = (random.nextInt(task.getBackpackMaxWeight()/30) + 5) * 10;
            utility = random.nextInt(task.getBackpackMaxWeight()) + 10;
            Thing thing = new Thing("C" + i, weight, utility);
            things.add(thing);
        }
        task.setThings(things);
    }

    @Override
    public void buildCrossingPoints() {
        List<Integer> crossingPointsList = new ArrayList<>();
        int newCrossPoint;
        for(int i = 0; i < size/5; i++){
            do{
                newCrossPoint = random.nextInt(size - 1);
            }while(crossingPointsList.indexOf(newCrossPoint + 1) != -1 && crossingPointsList.indexOf(newCrossPoint - 1) != -1);
            crossingPointsList.add(newCrossPoint);
        }
        task.setCrossingPointsList(crossingPointsList);
    }

    @Override
    public void buildMutationPoints() {
        List<Integer> mutationPoints = new ArrayList<>();
        int mutationElement;
        for(int i = 0; i < size/5; i++){
            do{
                mutationElement = random.nextInt(size);
            }while (mutationPoints.indexOf(mutationElement) != -1);
            mutationPoints.add(mutationElement);
        }
        task.setMutationPoints(mutationPoints);
    }

    @Override
    public void buildWeightLimit() {
        task.setBackpackMaxWeight((random.nextInt(5) + 3) * 100);
    }

    @Override
    public void buildIterationNumber() {
        task.setIterations(random.nextInt(5) + 3);
    }

    @Override
    public void buildParentsChoiceMethod() {
        task.setParentsChoiceMethod("Панміксія");
    }

    @Override
    public void buildMutationInversion() {
        task.setMutationInversion(false);
    }

    @Override
    public void buildStaticCrossingPoints() {
        task.setStaticCrossingPoints(true);
    }
}
