package geneticalgorithm.model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class TableResultObject {
    private IntegerProperty z1;
    private IntegerProperty z2;
    private IntegerProperty z3;

    public TableResultObject(int z1, int z2, int z3){
        this.z1 = new SimpleIntegerProperty(z1);
        this.z2 = new SimpleIntegerProperty(z2);
        this.z3 = new SimpleIntegerProperty(z3);
    }

    public int getZ1() {
        return z1.get();
    }

    public IntegerProperty z1Property() {
        return z1;
    }

    public void setZ1(int z1) {
        this.z1.set(z1);
    }

    public int getZ2() {
        return z2.get();
    }

    public IntegerProperty z2Property() {
        return z2;
    }

    public void setZ2(int z2) {
        this.z2.set(z2);
    }

    public int getZ3() {
        return z3.get();
    }

    public IntegerProperty z3Property() {
        return z3;
    }

    public void setZ3(int z3) {
        this.z3.set(z3);
    }
}
