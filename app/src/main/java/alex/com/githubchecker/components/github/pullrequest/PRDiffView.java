package alex.com.githubchecker.components.github.pullrequest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import javax.inject.Inject;

import alex.com.githubchecker.R;
import alex.com.githubchecker.models.Diff;
import alex.com.githubchecker.models.api.PullRequest;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/11/2017.
 */

public class PRDiffView {

    @BindView(R.id.subtractions_tv) TextView subtractionsTv;
    @BindView(R.id.additions_tv) TextView additionsTv;
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
        subtractionsTv.setText(diff.getSubtractionsSpan(), TextView.BufferType.SPANNABLE);
        additionsTv.setText(diff.getAdditionsSpan(), TextView.BufferType.SPANNABLE);
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
