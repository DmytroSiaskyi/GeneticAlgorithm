package geneticalgorithm.model.parentschoice;

import geneticalgorithm.model.Parent;
import geneticalgorithm.model.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Outbreeding implements ParentsChoice{

    @Override
    public List<Parent> getParents(Task task) {
        List<Parent> parents = task.getParents();
        List<Parent> result = new ArrayList<>();
        Random random = new Random();
        result.add(new Parent(parents.get(random.nextInt(parents.size()-1))));
        result.add(new Parent(getOutmostParent(parents, result.get(0))));
        return result;
    }
    private Parent getOutmostParent(List<Parent> parents, Parent parent){
        Parent result = null;
        int difference = 0;
        int indexOfCurrent = parents.indexOf(parent);
        for(int i = 0; i < parents.size(); i++){
            if((Math.abs(parents.get(i).getUtility() - parent.getUtility()) > difference) && (indexOfCurrent != i)){
                result = parents.get(i);
                difference = Math.abs(parents.get(i).getUtility() - parent.getUtility());
            }
        }
        return result;
    }
}
