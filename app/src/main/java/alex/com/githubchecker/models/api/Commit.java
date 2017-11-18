package alex.com.githubchecker.models.api;

/**
 * Created by Alex on 11/17/2017.
 */

public class Commit extends BaseModel {

    private User user;

    public String getUserLogin() {
        if (user != null) {
            return user.getLogin();
        }
        return null;
    }
}
