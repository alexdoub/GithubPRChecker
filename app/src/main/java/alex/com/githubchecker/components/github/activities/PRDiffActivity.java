package alex.com.githubchecker.components.github.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import alex.com.githubchecker.components.app.BaseActivity;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.components.github.pullrequest.PRDiffPresenter;
import alex.com.githubchecker.components.github.pullrequest.PRDiffView;
import alex.com.githubchecker.components.github.repo.GithubModel;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/17/2017.
 */

public class PRDiffActivity extends BaseActivity {

    private static String KEY_PR_ID = "id";
    PRDiffView view;
    PRDiffPresenter presenter;
    @Inject GithubModel githubModel;

    public static void Show(Context context, Integer pullRequestId) {
        Intent in = new Intent(context, PRDiffActivity.class);
        in.putExtra(KEY_PR_ID, pullRequestId);
        context.startActivity(in);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer pullRequestId = getIntent().getIntExtra(KEY_PR_ID, 0);

        ButterKnife.bind(this);
        GithubCheckerApp.getGithubComponent().inject(this);

        view = new PRDiffView(this);
        presenter = new PRDiffPresenter(githubModel, view, pullRequestId);

        setContentView(view.view());
        presenter.onCreate();
    }
}
