package test.java;

import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Task;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class TaskTest {

    public static Task task;

    @BeforeClass
    public static void initTask(){
        task = new Task(10);
    }
    @Test
    public void testConstrucotrWithSize() throws Exception {

        assertNotNull("Constructor with size parameter doesn't work", task);

        assertTrue("Generated false number of parents", task.getParents().size() == 10);
        assertTrue("Generated false number of tasks", task.getThings().size() == 10);
        assertTrue("Crossing point are not static", task.getStaticCrossingPoints() == true);
        assertTrue("Failed generate crossing points", task.getCrossingPoints() != null);
        assertTrue("Failed generate mutation points", task.getMutationPoints() != null);
    }

    @Test
    public void testCrossingPointsGenerating() throws Exception {

        task.generateCrossingPoints(2, 10);
        List<Integer> points = task.getCrossingPointsList();

        assertNotNull("Failed generating crossing points", points);
        assertTrue("Generated false number of points", points.size()==2);
        assertTrue("Failed points generation, first point is out of bounds.", points.get(0) < 10);
        assertTrue("Failed points generation, second point is out of bounds.", points.get(1) < 10);
    }

    @Test
    public void testMutationPointsGenerating() throws Exception {

        task.generateMutationPoints(2, 10);
        List<Integer> points = task.getMutationPoints();

        assertNotNull("Failed generating mutation points", points);
        assertTrue("Generated false number of points", points.size()==2);
        assertTrue("Failed points generation, first point is out of bounds.", points.get(0) < 10);
        assertTrue("Failed points generation, second point is out of bounds.", points.get(1) < 10);
    }

    @Test
    public void testParentGenerating() throws Exception {

        Parent parent = task.generateParent(10);
        assertNotNull("Failed generating parent", parent);
        assertNotNull("Chromosome is empty", parent.getChromosome());
        assertTrue("Bad parent weight", parent.getWeight() != 0);
        assertFalse("Bad parent utility", parent.getUtility() == 0);
    }

    @Test
    public void testTaskCopy() throws Exception {

        Task copiedTask = task.copyTask();
        assertNotSame("Failed copy method", task, copiedTask);
    }
}
