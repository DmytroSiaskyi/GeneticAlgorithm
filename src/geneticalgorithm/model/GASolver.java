package geneticalgorithm.model;

import geneticalgorithm.model.parentschoice.Inbreeding;
import geneticalgorithm.model.parentschoice.Panmixia;
import geneticalgorithm.model.parentschoice.ParentsChoice;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class GASolver {
    private static GASolver instance;

    private Task task;
    private ParentsChoice parentsChoice;

    private GASolver(){}

    /**
     * Return instance of GASolver
     */
    public static GASolver getInstance(){
        if(instance == null){
            synchronized (GASolver.class){
                if(instance == null)
                    instance = new GASolver();
            }
        }
        return instance;
    }

    /**
     * Set parents choice method
     */
    public void setParentsChoice(String method){
        switch (method){
            case "Панміксія":
                parentsChoice = new Panmixia();
                break;
            case "Імбридинг":
                parentsChoice = new Inbreeding();
                break;
            default:
                parentsChoice = new Panmixia();
                break;
        }
    }

    public void setParentsChoice(ParentsChoice parentsChoice) {
        this.parentsChoice = parentsChoice;
    }

    public Task getTask() {
        return task;
    }

    public ParentsChoice getParentsChoice() {
        return parentsChoice;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
