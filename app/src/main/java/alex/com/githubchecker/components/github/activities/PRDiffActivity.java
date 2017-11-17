package alex.com.githubchecker.components.github.activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import alex.com.githubchecker.components.app.BaseActivity;

/**
 * Created by Alex on 11/17/2017.
 */

public class PRDiffActivity extends BaseActivity {

    private static String KEY_PR_ID = "id";

    public static void Show(Context context, Integer pullRequestId) {
        Intent in = new Intent(context, PRDiffActivity.class);
        in.putExtra(KEY_PR_ID, pullRequestId);
        context.startActivity(in);
    }

}
