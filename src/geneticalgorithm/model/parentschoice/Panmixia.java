package geneticalgorithm.model.parentschoice;

import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Panmixia implements ParentsChoice{
    @Override
    public List<Parent> getParents(Task task) {
        List<Parent> parents = task.getParents();
        List<Parent> result = new ArrayList<>();
        Random random = new Random();
        int genNumber = random.nextInt(parents.size()-1);
        result.add(new Parent(parents.get(genNumber)));
        Parent second;
        int secondNumber;
        do {
            secondNumber = random.nextInt(parents.size() - 1);
        }while (genNumber == secondNumber);
        second = parents.get(secondNumber);
        result.add(new Parent(second));
        return result;
    }
}
