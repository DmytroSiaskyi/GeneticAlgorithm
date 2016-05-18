package geneticalgorithm.model;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
abstract class TaskBuilder{
    protected Task task;
    public Task getTask(){
        return task;
    }
    public void createNewTask(int size){
        task = new Task();
        task.setSize(size);
    }
    public abstract void buildParents();
    public abstract void buildThing();
    public abstract void buildCrossingPoints();
    public abstract void buildMutationPoints();
    public abstract void buildWeightLimit();
    public abstract void buildIterationNumber();
    public abstract void buildParentsChoiceMethod();
    public abstract void buildMutationInversion();
    public abstract void buildStaticCrossingPoints();
}
