package geneticalgorithm.model;

import geneticalgorithm.model.parentschoice.Inbreeding;
import geneticalgorithm.model.parentschoice.Panmixia;
import geneticalgorithm.model.parentschoice.ParentsChoice;

import java.util.List;

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

    public String solve(boolean showIterations){
        String result = "-----------------------------------\n";
        result += "        Початкова умова\n";
        result += "-----------------------------------\n";
        result += printTask();



        return result;
    }

    /**
     * Return string with task data
     */
    public String printTask(){
        String printedTask = new String();

        printedTask += "Максимальна вага рюкзака: " + task.getBackpackMaxWeight() + "\n";
        printedTask += "Оператор вибору батьків: " + task.getParentsChoiceMethod() + "\n";
        printedTask += "Точки кросинговеру: ";
        List<Integer> crossingPoints = task.getCrossingPointsList();
        for(int i = 0; i < crossingPoints.size(); i++){
            printedTask += crossingPoints.get(i) + " ";
        }
        printedTask += "\n";
        printedTask += "Постійність точок кросинговеру: " + task.getStaticCrossingPoints() + "\n";
        printedTask += "Точки мутації: ";
        List<Integer> mutationPoints = task.getMutationPoints();
        for(int i = 0; i < mutationPoints.size(); i++){
            printedTask += mutationPoints.get(i) + " ";
        }
        printedTask += "\n";
        printedTask += "Інверсія при мутації: " + task.getMutationInversion() + "\n";
        printedTask += "Кількість ітерацій: " + task.getIterations() + "\n";
        printedTask += "\n";
        printedTask += "Набір предметів(назва, корисність, вага): " + "\n";
        List<Thing> things = task.getThings();
        for(int i = 0; i < things.size(); i++){
            printedTask += things.get(i).getName() + " " + things.get(i).getUtility() + " " + things.get(i).getWeight() + "\n";
        }
        printedTask += "\n";
        List<Parent> parents = task.getParents();
        printedTask += "Набір предків(хромосома, вага, корисність): " + "\n";
        for(int i = 0; i < parents.size(); i++){
            Parent parent = parents.get(i);
            for(int j = 0; j < things.size(); j ++){
                printedTask += parent.getChromosome().get(j) + " ";
            }
            printedTask += parent.getWeight() + " " + parent.getUtility() + "\n";
        }

        return printedTask;
    }

    public void setParentsChoice(ParentsChoice parentsChoice) {
        this.parentsChoice = parentsChoice;
    }

    public Task getTask() { return task; }

    public ParentsChoice getParentsChoice() {
        return parentsChoice;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
