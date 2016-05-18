package geneticalgorithm.model.parentschoice;

import geneticalgorithm.model.Parent;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class MyOwnChoice implements ParentsChoice{

    List<ParentsChoice> choices = new ArrayList<>();

    @Override
    public List<Parent> getParents() {
        return null;
    }

    public void add(ParentsChoice parentsChoice){
        choices.add(parentsChoice);
    }

    public void remove(ParentsChoice parentsChoice){
        choices.remove(parentsChoice);
    }

    public void printParentsByMethod(){
        List<Parent> parents;
        for(ParentsChoice choice: choices){
            parents = choice.getParents();
            parents.forEach(parent -> System.out.println(parent));
        }
    }
}
