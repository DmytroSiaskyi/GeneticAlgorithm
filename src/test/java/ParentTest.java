package test.java;

import geneticalgorithm.model.Parent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

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
        assertNotSame("Copy constructor returned same object", parent, testParent);
        try{
            testParent = new Parent(null);
            fail("Copy constructor failed with null parameter");
        }catch (NullPointerException e){
            testParent = null;
        }
    }

    @Test
    public void testConstructorWithParameters() throws Exception {
        Parent testParent = new Parent(parent.getChromosome(), parent.getWeight(), parent.getUtility());
        assertEquals("Constructor returned not equal object(Parent)", parent, testParent);
        assertNotSame("Constructor returned same object", parent, testParent);
    }

    @Test
    public void testEqualsMethod() throws Exception {
        Parent testParent = new Parent(parent);
        assertTrue("Failed equals method)", parent.equals(testParent));
    }
}
