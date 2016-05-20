package geneticalgorithm.model.parentschoice;

import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Task;

import java.util.List;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public interface ParentsChoice{
    List<Parent> getParents(Task task);
}
