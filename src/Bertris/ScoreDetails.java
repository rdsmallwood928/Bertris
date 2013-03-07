package Bertris;

/**
 * Created with IntelliJ IDEA.
 * User: bigwood928
 * Date: 2/23/13
 * Time: 2:16 PM
 * To change this template use File | Settings | File Templates.
 */
public class ScoreDetails {

    private String name = "";
    private Long score = 0l;

    public ScoreDetails(String name, Long score) {
        this.name = name;
        this.score = score;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }
}
