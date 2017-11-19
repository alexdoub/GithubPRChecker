package alex.com.githubchecker.components.github.pullrequest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import javax.inject.Inject;

import alex.com.githubchecker.components.app.BaseActivity;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.models.Diff;
import alex.com.githubchecker.models.api.PullRequest;
import alex.com.githubchecker.models.dagger.GithubModel;
import alex.com.githubchecker.utils.SchedulerUtils;
import butterknife.ButterKnife;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;

/**
 * Created by Alex on 11/11/2017.
 */

public class PRDiffPresenter {

    private PRDiffView view;
    private GithubModel model;
    private Integer pullRequestId;

    public PRDiffPresenter(GithubModel model, PRDiffView view, Integer pullRequestId) {
        this.model = model;
        this.view = view;
        this.pullRequestId = pullRequestId;
    }

    public void onCreate() {

        view.showLoading(true);
        model.getPullRequest(pullRequestId)
                .observeOn(SchedulerUtils.main())
                .subscribe(pullRequest -> {
                    view.bindPR(pullRequest);
                    getDiffForPr(pullRequest);
                });
    }

    private void getDiffForPr(PullRequest pullRequest) {
        view.showLoading(true);
        model.getDiffForPr(pullRequest)
                .observeOn(SchedulerUtils.main())
                .subscribe(view::bindDiff);
    }

    /**
     * Created by Alex on 11/17/2017.
     */

    public static class PRDiffActivity extends BaseActivity {

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
}
