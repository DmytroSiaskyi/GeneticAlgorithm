package geneticalgorithm.model;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class TaskArchitector {
    private TaskBuilder taskBuilder;

    public void setTaskBuilder(TaskBuilder taskBuilder) {
        this.taskBuilder = taskBuilder;
    }

    public Task getTask() {
        return taskBuilder.getTask();
    }

    public void constructTask(int size) {
        taskBuilder.createNewTask(size);

        taskBuilder.buildWeightLimit();
        taskBuilder.buildThing();
        taskBuilder.buildParents();

        taskBuilder.buildCrossingPoints();
        taskBuilder.buildMutationPoints();

        taskBuilder.buildIterationNumber();
        taskBuilder.buildParentsChoiceMethod();
        taskBuilder.buildMutationInversion();
        taskBuilder.buildStaticCrossingPoints();
    }
}
