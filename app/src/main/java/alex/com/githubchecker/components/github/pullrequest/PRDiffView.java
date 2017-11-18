package alex.com.githubchecker.components.github.pullrequest;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import alex.com.githubchecker.R;
import alex.com.githubchecker.components.app.api.model.Diff;
import alex.com.githubchecker.components.app.api.model.PullRequest;
import alex.com.githubchecker.components.github.activities.PRDiffActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/11/2017.
 */

public class PRDiffView {

    @BindView(R.id.removed_tv) TextView subtractionsTv;
    @BindView(R.id.added_tv) TextView additionsTv;
    @BindView(R.id.container) ScrollView containerSv;
    @BindView(R.id.loading) ProgressBar loadingView;

    private View view;

    @Inject
    public PRDiffView(PRDiffActivity context) {
        FrameLayout parent = new FrameLayout(context);
        parent.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        view = LayoutInflater.from(context).inflate(R.layout.activity_prdiff, parent, true);
        ButterKnife.bind(this, view);

        subtractionsTv.setHorizontallyScrolling(true);
        additionsTv.setHorizontallyScrolling(true);
    }

    void bindPR(PullRequest pullRequest) {
        ((PRDiffActivity) view.getContext()).getSupportActionBar().setTitle(view.getContext().getString(R.string.activity_pr_diff, pullRequest.getNumber()));
    }

    void bindDiff(Diff diff) {
        showLoading(false);
        subtractionsTv.setText(diff.getSubtractions());
        additionsTv.setText(diff.getAdditions());
    }

    void showLoading(boolean loading) {
        containerSv.setVisibility(!loading ? View.VISIBLE : View.GONE);
        loadingView.setVisibility(loading ? View.VISIBLE : View.GONE);
    }

    public View view() {
        return view;
    }

    //Helpers

}
