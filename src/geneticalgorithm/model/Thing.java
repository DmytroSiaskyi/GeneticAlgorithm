package geneticalgorithm.model;

/**
 * @author Dmytro Siaskyi dmitry.syaskiy@gmail.com.
 */
public class Thing {

    private String name;
    private Integer weight;
    private Integer utility;

    public Thing(){
        name = "Undefined";
        weight = 0;
        utility = 0;
    }
    public Thing(String name, int weight, int utility){
        this.name = name;
        this.weight = weight;
        this.utility = utility;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getUtility() {
        return utility;
    }

    public void setUtility(Integer utility) {
        this.utility = utility;
    }
}
