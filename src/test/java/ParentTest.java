package test.java;

import geneticalgorithm.model.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ParentTest {

    Parent parent;

    @Before
    public void initParent(){
        parent = new Parent();
        parent.setUtility(1000);
        parent.setWeight(500);
        ObservableList<Integer> chromosome = FXCollections.observableArrayList();
        Random random = new Random();
        for(int i = 0; i < 10; i++){
            chromosome.add(random.nextInt(1));
        }
        parent.setChromosomeList(chromosome);
    }

    @Test
    public void testCopyConstructor() throws Exception {
        Parent testParent = new Parent(parent);
        assertEquals("Copy constructor returned not equal object(Parent)", parent, testParent);
    }

}
