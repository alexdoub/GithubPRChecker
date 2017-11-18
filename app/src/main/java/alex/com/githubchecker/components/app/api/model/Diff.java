package alex.com.githubchecker.components.app.api.model;

/**
 * Created by Alex on 11/17/2017.
 */

public class Diff extends BaseModel {

    String content;

    public Diff(String content) {
        this.content = content;
    }

    public String getAdditions() {
        return content;
    }

    public String getSubtractions() {
        return content;
    }

}
