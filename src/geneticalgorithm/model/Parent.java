package geneticalgorithm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Parent {

    private ObservableList<Integer> gene;
    private IntegerProperty weight;
    private IntegerProperty utility;

    /**
     * Default constructor.
     */
    public Parent(){
        weight = new SimpleIntegerProperty(0);
        utility = new SimpleIntegerProperty(0);
    }

    /**
     * Constructor with parameters
     *
     * @param gene
     * @param weight
     * @param utility
     */
    public Parent(ObservableList<Integer> gene, Integer weight, Integer utility){
        this.gene = gene;
        this.weight = new SimpleIntegerProperty(weight);
        this.utility = new SimpleIntegerProperty(utility);
    }

    public ObservableList<Integer> getGene() {
        return gene;
    }

    public void setGene(ObservableList<Integer> gene) {
        this.gene = gene;
    }

    public int getWeight() {
        return weight.get();
    }

    public IntegerProperty weightProperty() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight.set(weight);
    }

    public int getUtility() {
        return utility.get();
    }

    public IntegerProperty utilityProperty() {
        return utility;
    }

    public void setUtility(int utility) {
        this.utility.set(utility);
    }
}
