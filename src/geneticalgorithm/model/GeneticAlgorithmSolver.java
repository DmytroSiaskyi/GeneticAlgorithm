package geneticalgorithm.model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class GeneticAlgorithmSolver {

    private ObservableList<Thing> things;

    /**
     * Default constructor.
     */
    public GeneticAlgorithmSolver(){
        things = FXCollections.observableArrayList();
        for(int i = 0; i < 10; i++){
            Thing thing = new Thing("C" + i, i*10 + 100, 100 + 100*i);
            things.add(thing);
        }
    }

    public ObservableList<Thing> getThings() {
        return things;
    }

    public void setThings(ObservableList<Thing> things) {
        this.things = things;
    }
}
