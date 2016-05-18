package geneticalgorithm.model;

import javafx.beans.property.IntegerProperty;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ThingIterator implements Iterator<IntegerProperty> {
    private int index = -1;
    private Thing thing;

    public ThingIterator(Thing t){
        thing = t;
    }
    @Override
    public boolean hasNext() {
        if(index == -1){
            if(thing.weightProperty() != null)
                return true;
        }
        if(index == 0){
            if(thing.utilityProperty() != null){
                return true;
            }
        }
        return false;
    }

    @Override
    public IntegerProperty next() {
        if (index == -1) {
            if (thing.weightProperty() != null) { index = 0; return thing.weightProperty(); }
            if (thing.utilityProperty() != null) { index = 1; return thing.utilityProperty(); }
        }
        if (index == 0) {
            if (thing.utilityProperty() != null) { index = 1; return thing.utilityProperty(); }
        }
        throw new NoSuchElementException();
    }

    @Override
    public void remove() {
        if (index == -1) throw new IllegalStateException();
        if (index == 0)
            if (thing.weightProperty() != null) thing.setWeight(0);
            else throw new IllegalStateException();
        if (index == 1)
            if (thing.utilityProperty() != null) thing.setUtility(0);
            else throw new IllegalStateException();
    }
}
