package geneticalgorithm.model;

import geneticalgorithm.model.parentschoice.Inbreeding;
import geneticalgorithm.model.parentschoice.Outbreeding;
import geneticalgorithm.model.parentschoice.Panmixia;
import geneticalgorithm.model.parentschoice.ParentsChoice;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.Collections;
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
            case "Аутбридинг":
                parentsChoice = new Outbreeding();
                break;
            default:
                parentsChoice = new Panmixia();
                break;
        }
    }

    public String solve(boolean showIterations){
        int bestResult = 0;
        int bestResultNotChanged = 0;
        setParentsChoice(task.getParentsChoiceMethod());
        String result = "-----------------------------------\n";
        result += "        Початкова умова            \n";
        result += "-----------------------------------\n";
        result += printTask();

        String iterationResult;
        Parent currentBestParent = null;
        for(int i = 0; i < task.getIterations(); i++){
            iterationResult = "";
            iterationResult += "-----------------------------------\n";
            iterationResult += "             Ітерація " + (i + 1) + "\n";
            iterationResult += "-----------------------------------\n";

            List<Parent> chosenParents = parentsChoice.getParents(task);
            iterationResult += "Обрані для схрещування батьки (хромосома, вага, корисність): \n";
            if(chosenParents != null) {
                iterationResult += chosenParents.get(0).toString() + " (" + (task.getParents().indexOf( chosenParents.get(0)) + 1) + ")\n";
                iterationResult += chosenParents.get(1).toString() + " (" + (task.getParents().indexOf( chosenParents.get(1)) + 1) + ")\n";
            }else{
                result = "Помилка виконання.";
                return result;
            }
            if(!task.getStaticCrossingPoints()){
                task.generateCrossingPoints(task.getCrossingPointsList().size(), task.getThings().size());
            }
            iterationResult += "Точки кросинговеру: ";
            List<Integer> crossingPoints = task.getCrossingPointsList();
            for(int j = 0; j < crossingPoints.size(); j++){
                iterationResult += crossingPoints.get(j) + " ";
            }
            iterationResult += "\n";
            crossingover(crossingPoints, chosenParents.get(0), chosenParents.get(1));
            iterationResult += "Результат кросинговеру: " + "\n";
            iterationResult += chosenParents.get(0).toString() + "\n";
            iterationResult += chosenParents.get(1).toString() + "\n";
            iterationResult += "Точки мутації: ";
            List<Integer> mutationPoints = task.getMutationPoints();
            for(int j = 0; j < mutationPoints.size(); j++){
                iterationResult += mutationPoints.get(j) + " ";
            }
            iterationResult += "\n";
            if(task.getMutationInversion()){
                inversMutation(mutationPoints, chosenParents.get(0), chosenParents.get(1));
            }else {
                mutation(mutationPoints, chosenParents.get(0), chosenParents.get(1));
            }
            iterationResult += "Результат мутації: " + "\n";
            iterationResult += chosenParents.get(0).toString() + "\n";
            iterationResult += chosenParents.get(1).toString() + "\n";

            if(chosenParents.get(0).getWeight() < task.getBackpackMaxWeight()){
                selection(chosenParents.get(0));
            }else{
                iterationResult += "Нащадок 1 не підходить під перевірку максимальної  ваги." + "\n";
                chosenParents.set(0, null);
            }

            if(chosenParents.get(1).getWeight() < task.getBackpackMaxWeight()){
                selection(chosenParents.get(1));
            }else{
                iterationResult += "Нащадок 2 не підходить під перевірку максимальної  ваги." + "\n";
                chosenParents.set(1, null);
            }
            if(chosenParents.get(0) != null && chosenParents.get(1) != null) {
                iterationResult += "Етап селекції(визначамо вагу, яку ще можна покласти в рюкзак): " + "\n";
            }
            if(chosenParents.get(0) != null){
                selection(chosenParents.get(0));
                iterationResult += chosenParents.get(0).toString() + "\n";
            }
            if(chosenParents.get(1) != null){
                selection(chosenParents.get(1));
                iterationResult += chosenParents.get(1).toString() + "\n";
            }
            addChildrens(chosenParents.get(0), chosenParents.get(1));
            iterationResult += "Результат ітерації: " + "\n";
            iterationResult += printParentsTable();

            currentBestParent = getBestResult();
            if(currentBestParent.getUtility() != bestResult){
                bestResult = currentBestParent.getUtility();
                bestResultNotChanged = 0;
            }else{
                bestResultNotChanged++;
            }
            if(showIterations){
                result += iterationResult;
            }
            if(bestResultNotChanged == 20){
                result += "Виконано " + i + " ітреацій." + "\n";
                result += "За двадцять ітерацій розвязок не змінився!" + "\n";
                break;
            }

        }
        if(currentBestParent == null){
            currentBestParent = getBestResult();
        }
        result += "-----------------------------------\n";
        result += "             Результат             \n";
        result += "-----------------------------------\n";
        result += printResult(currentBestParent);

        return result;
    }

    public Integer solveForExperiment(){
        int bestResult = 0;
        Parent currentBestParent = null;
        for(int i = 0; i < task.getIterations(); i++){
            List<Parent> chosenParents = parentsChoice.getParents(task);
            if(!task.getStaticCrossingPoints()){
                task.generateCrossingPoints(task.getCrossingPointsList().size(), task.getThings().size());
            }
            List<Integer> crossingPoints = task.getCrossingPointsList();
            crossingover(crossingPoints, chosenParents.get(0), chosenParents.get(1));
            List<Integer> mutationPoints = task.getMutationPoints();
            if(task.getMutationInversion()){
                inversMutation(mutationPoints, chosenParents.get(0), chosenParents.get(1));
            }else {
                mutation(mutationPoints, chosenParents.get(0), chosenParents.get(1));
            }
            if(chosenParents.get(0).getWeight() < task.getBackpackMaxWeight()){
                selection(chosenParents.get(0));
            }else{
                chosenParents.set(0, null);
            }

            if(chosenParents.get(1).getWeight() < task.getBackpackMaxWeight()){
                selection(chosenParents.get(1));
            }else{
                chosenParents.set(1, null);
            }
            if(chosenParents.get(0) != null){
                selection(chosenParents.get(0));
            }
            if(chosenParents.get(1) != null){
                selection(chosenParents.get(1));
            }
            addChildrens(chosenParents.get(0), chosenParents.get(1));
            currentBestParent = getBestResult();
            bestResult = currentBestParent.getUtility();
        }
        return bestResult;
    }
    private String printParentsTable(){
        String result = "";
        List<Parent> parents = task.getParents();

        for(int i = 0; i < parents.size(); i++){
            Parent parent = parents.get(i);
            for(int j = 0; j < task.getThings().size(); j ++){
                result += parent.getChromosome().get(j) + " ";
            }
            result += parent.getWeight() + " " + parent.getUtility() + "\n";
        }

        return result;
    }

    private Parent getBestResult(){
        Parent parent = null;
        int maxUtility = 0;
        for(int i = 0; i < task.getParents().size(); i++){
            if(task.getParents().get(i).getUtility() >= maxUtility){
                parent = task.getParents().get(i);
                maxUtility = parent.getUtility();
            }
        }
        return parent;
    }

    private void addChildrens(Parent first, Parent second){
        ObservableList<Parent> parents = task.getParents();
        Parent p1, p2;
        if(first != null && second != null){
            p1 = new Parent(first);
            p2 = new Parent(second);
            Parent b1 = getWorstParent();
            parents = removeElement(b1, parents);
            task.setParents(parents);
            Parent b2 = getWorstParent();
            parents = removeElement(b2, parents);
            parents.add(p1);
            parents.add(p2);
        }else{
            if(first != null){
                Parent b1 = getWorstParent();
                p1 = new Parent(first);
                parents = removeElement(b1, parents);
                parents.add(p1);
            }
            if(second != null){
                Parent b2 = getWorstParent();
                p2 = new Parent(second);
                parents = removeElement(b2, parents);
                parents.add(p2);
            }
        }
        task.setParents(parents);
    }
    private ObservableList<Parent> removeElement(Parent parent, ObservableList<Parent> parents){
        ObservableList<Parent> newList = FXCollections.observableArrayList();
        for(int i = 0; i < parents.size(); i++){
            if(parents.get(i) != parent){
                newList.add(parents.get(i));
            }
        }
        return newList;
    }
    private Parent getWorstParent(){
        Parent parent = null;
        int minUtility = Integer.MAX_VALUE;
        for(int i = 0; i < task.getParents().size(); i++){
            if(task.getParents().get(i).getUtility() <= minUtility){
               parent = task.getParents().get(i);
               minUtility = parent.getUtility();
            }
        }
        return parent;
    }

    private void crossingover(List<Integer> crossingPoints, Parent first, Parent second){
        Collections.sort(crossingPoints);
        ObservableList<Integer> chromosome1 = FXCollections.observableArrayList();
        ObservableList<Integer> chromosome2 = FXCollections.observableArrayList();
        for(int i = 0; i < first.getChromosome().size(); i++){
            chromosome1.add(first.getChromosome().get(i));
            chromosome2.add(second.getChromosome().get(i));
        }
        boolean addedLastPoint = false;
        if(crossingPoints.size() % 2 != 0){
            crossingPoints.add(chromosome1.size());
            addedLastPoint = true;
        }
        int intervals = crossingPoints.size() / 2;
        int pointInList = 0;
        crossingPoints.set(0, crossingPoints.get(0) + 1);
        for(int i = 0; i < intervals; i++){
            for(int j = crossingPoints.get(pointInList); j < crossingPoints.get(pointInList + 1)+1; j++){
                chromosome1.set(j-1, chromosome2.get(j-1));
            }
            pointInList += 2;
        }
        pointInList = 0;
        for(int i = 0; i < intervals; i++){
            for(int j = crossingPoints.get(pointInList); j < crossingPoints.get(pointInList + 1) + 1; j++){
                chromosome2.set(j-1, first.getChromosome().get(j-1));
            }
            pointInList += 2;
        }
        first.setChromosome(chromosome1);
        second.setChromosome(chromosome2);
        refreshData(first);
        refreshData(second);
        crossingPoints.set(0, crossingPoints.get(0) - 1);
        if(addedLastPoint){
            crossingPoints.remove(crossingPoints.size()-1);
        }
    }

    /**
     * Calculate and set new weight and utility
     */
    private void refreshData(Parent parent){
        List<Integer> chromosome = parent.getChromosome();
        int weight = 0;
        int utility = 0;
        for(int i = 0; i < chromosome.size(); i++){
            if(chromosome.get(i) == 1){
                weight += task.getThings().get(i).getWeight();
                utility += task.getThings().get(i).getUtility();
            }
            }
        parent.setWeight(weight);
        parent.setUtility(utility);
    }

    private void inversMutation(List<Integer> mutationPoints, Parent first, Parent second){
        ObservableList<Integer> chromosome1 = first.getChromosome();
        ObservableList<Integer> chromosome2 = second.getChromosome();
        Collections.sort(mutationPoints);
        int x1 = mutationPoints.get(0) - 1;
        int x2 = mutationPoints.get(1) - 1;
        int buffer1, buffer2;
        for(int i = 0; i < (x2-x1)/2 + 1; i++){
            buffer1 = chromosome1.get(x2);
            buffer2 = chromosome2.get(x2);
            chromosome1.set(x2, chromosome1.get(x1));
            chromosome1.set(x1, buffer1);
            chromosome2.set(x2, chromosome2.get(x1));
            chromosome2.set(x1, buffer2);
            x1++;
            x2--;
        }
        first.setChromosome(chromosome1);
        second.setChromosome(chromosome2);
        refreshData(first);
        refreshData(second);
    }

    private void mutation(List<Integer> mutationPoints, Parent first, Parent second){
        ObservableList<Integer> chromosome1 = first.getChromosome();
        ObservableList<Integer> chromosome2 = second.getChromosome();
        int currentPoint;
        for(int i = 0; i < mutationPoints.size(); i++){
            currentPoint = mutationPoints.get(i) -1;
            if(chromosome1.get(currentPoint) == 0){
                chromosome1.set(currentPoint, 1);
            }else{
                chromosome1.set(currentPoint, 0);
            }

            if(chromosome2.get(currentPoint) == 0){
                chromosome2.set(currentPoint, 1);
            }else{
                chromosome2.set(currentPoint, 0);
            }
        }
        first.setChromosome(chromosome1);
        second.setChromosome(chromosome2);
        refreshData(first);
        refreshData(second);
    }

    /**
     * Selection method
     */
    private void selection(Parent parent){
        int weight = task.getBackpackMaxWeight() - parent.getWeight();
        Thing forFirst = getBestThing(weight, parent.getChromosome());
        int index;
        if(forFirst != null){
            index = task.getThings().indexOf(forFirst);
            parent.getChromosome().set(index, 1);
        }
        refreshData(parent);
    }

    /**
     * Return best thing, that can add to parent
     */
    private Thing getBestThing(int weight, ObservableList<Integer> chromosome){
        Thing thing = null;
        int maxWeight = 0;
        for(int i = 0; i < task.getThings().size(); i++){
            if(chromosome.get(i) == 0 && task.getThings().get(i).getWeight() <= weight && maxWeight <= task.getThings().get(i).getWeight()){
                thing = task.getThings().get(i);
                maxWeight = thing.getWeight();
            }
        }
        return thing;
    }

    /**
     * Return string with task data
     */
    private String printTask(){
        String printedTask = new String();

        printedTask += "Максимальна вага рюкзака: " + task.getBackpackMaxWeight() + "\n";
        printedTask += "Оператор вибору батьків: " + task.getParentsChoiceMethod() + "\n";
        if(task.getStaticCrossingPoints()) {
            printedTask += "Точки кросинговеру: ";
            List<Integer> crossingPoints = task.getCrossingPointsList();
            for (int i = 0; i < crossingPoints.size(); i++) {
                printedTask += crossingPoints.get(i) + " ";
            }
            printedTask += "\n";
        }else {
            printedTask += "Постійність точок кросинговеру: " + task.getStaticCrossingPoints() + "\n";
        }
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

    /**
     * Return result of task solving in String
     */
    private String printResult(Parent parent){
        String result = new String();

        result += "Кращим на останній ітерації є\n \n";
        result += parent.toString() + "\n\n";
        result += "Результуюча таблиця:\n";
        result += printParentsTable();

        return result;
    }

    public void setParentsChoice(ParentsChoice parentsChoice) {
        this.parentsChoice = parentsChoice;
    }

    public Task getTask() { return task; }

    public ParentsChoice getParentsChoice() {
        return parentsChoice;
    }

    public void setTask(Task task) {
        this.task = new Task(task);
    }
}
