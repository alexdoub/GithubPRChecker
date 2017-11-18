package alex.com.githubchecker.models.api;

/**
 * Created by Alex on 11/17/2017.
 */

public class PullRequest extends BaseModel {

    private String title;
    private Integer number;
    private Commit head;
    private String created_at;
    private String diff_url;


    //Accessors methods
    public String getTitle() {
        return title;
    }

    public String getUserLogin() {
        if (head != null) {
            return head.getUserLogin();
        }
        return null;
    }

    public Integer getNumber() {
        return number;
    }

    public String getCreatedAt() {
        return created_at;
    }

    public String getDiffUrl() {
        return diff_url;
    }
}
