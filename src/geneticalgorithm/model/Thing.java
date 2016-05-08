package geneticalgorithm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Thing {

    private StringProperty name;
    private IntegerProperty weight;
    private IntegerProperty utility;

    /**
     * Default constructor.
     */
    public Thing(){
        name = new SimpleStringProperty("Undefined");
        weight = new SimpleIntegerProperty(0);
        utility = new SimpleIntegerProperty(0);
    }

    /**
     * Constructor with parameters
     *
     * @param name
     * @param weight
     * @param utility
     */
    public Thing(String name, int weight, int utility){
        this.name = new SimpleStringProperty(name);
        this.weight = new SimpleIntegerProperty(weight);
        this.utility = new SimpleIntegerProperty(utility);
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
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
