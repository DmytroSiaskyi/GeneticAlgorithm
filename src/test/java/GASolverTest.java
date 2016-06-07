package test.java;

import geneticalgorithm.model.GASolver;
import geneticalgorithm.model.Task;
import geneticalgorithm.model.parentschoice.Panmixia;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class GASolverTest {

    @Test
    public void testGASolverInit() throws Exception {
        GASolver solver = GASolver.getInstance();
        assertNotNull("Failed getInstance method, can't get instance of singleton object", solver);
    }

    @Test
    public void testSettingParrentChoiceMethod() throws Exception {
        GASolver solver = GASolver.getInstance();
        solver.setParentsChoice("Панміксія");
        assertTrue("Failed setting of parent choice method", solver.getParentsChoice().getClass().equals(Panmixia.class));
    }

    @Test
    public void testSolveMethod() throws Exception {
        GASolver solver = GASolver.getInstance();
        solver.setParentsChoice("Панміксія");
        Task task = new Task(10);
        solver.setTask(task);
        String result = solver.solve(false);
        assertNotNull("Failed solving task", result);
    }
}
