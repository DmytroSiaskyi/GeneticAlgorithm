package test.java;

import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Task;
import geneticalgorithm.model.parentschoice.Inbreeding;
import geneticalgorithm.model.parentschoice.Outbreeding;
import geneticalgorithm.model.parentschoice.Panmixia;
import geneticalgorithm.model.parentschoice.ParentsChoice;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class ParentsChoiceTest {

    private Task task;

    @Before
    public void initTask(){
        task = new Task(10);
    }
    @Test(expected=NullPointerException.class)
    public void testInbreeding() throws Exception {
        ParentsChoice parentsChoice = new Inbreeding();
        List<Parent> choosen = parentsChoice.getParents(task);

        assertTrue("Inbreeding returned not two parents.", choosen.size() == 2);

        assertNotNull("First parent is null", choosen.get(0));
        assertNotNull("Second parent is null", choosen.get(1));

        choosen = parentsChoice.getParents(null);
    }

    @Test(expected=NullPointerException.class)
    public void testOutbreeding() throws Exception {
        ParentsChoice parentsChoice = new Outbreeding();
        List<Parent> choosen = parentsChoice.getParents(task);

        assertTrue("Outbreeding returned not two parents.", choosen.size() == 2);
        assertNotNull("First parent is null", choosen.get(0));
        assertNotNull("Second parent is null", choosen.get(1));

        choosen = parentsChoice.getParents(null);
    }

    @Test(expected=NullPointerException.class)
    public void testPanmixia() throws Exception {
        ParentsChoice parentsChoice = new Panmixia();
        List<Parent> choosen = parentsChoice.getParents(task);

        assertTrue("Panmixia returned not two parents.", choosen.size() == 2);
        assertNotNull("First parent is null", choosen.get(0));
        assertNotNull("Second parent is null", choosen.get(1));

        choosen = parentsChoice.getParents(null);
    }
}
