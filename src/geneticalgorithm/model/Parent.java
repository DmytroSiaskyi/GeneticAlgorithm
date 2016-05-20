package geneticalgorithm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Parent {

    private ObservableList<Integer> chromosome;
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
     * @param chromosome
     * @param weight
     * @param utility
     */
    public Parent(ObservableList<Integer> chromosome, Integer weight, Integer utility){
        this.chromosome = chromosome;
        this.weight = new SimpleIntegerProperty(weight);
        this.utility = new SimpleIntegerProperty(utility);
    }
    @Override
    public String toString(){
        String parent = "";
        for(int i = 0; i < chromosome.size(); i++){
            parent += chromosome.get(i) + " ";
        }
        parent += utility + " ";
        parent += weight;
        return parent;
    }
    @Override
    public boolean equals(Object o){
        if(o == null)
            return false;
        Parent parent = (Parent)o;
        if((parent.getUtility() == this.utility.intValue())&&(parent.getWeight() == this.weight.intValue())){
            return true;
        }
        return false;
    }
    public ObservableList<Integer> getChromosome() {
        return chromosome;
    }

    public void setChromosome(ObservableList<Integer> chromosome) {
        this.chromosome = chromosome;
    }

    public void setChromosomeList(List<Integer> chromosomeList){
        chromosome = FXCollections.observableArrayList();
        chromosomeList.forEach(gene -> chromosome.add(gene));
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
