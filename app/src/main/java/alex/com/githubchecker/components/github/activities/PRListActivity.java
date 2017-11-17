package alex.com.githubchecker.components.github.activities;

import android.os.Bundle;

import javax.inject.Inject;

import alex.com.githubchecker.components.app.BaseActivity;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.components.github.core.GithubRepoModel;
import alex.com.githubchecker.components.github.core.PRListPresenter;
import alex.com.githubchecker.components.github.core.PRListView;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/17/2017.
 */

public class PRListActivity extends BaseActivity {

    PRListView view;
    PRListPresenter presenter;

    @Inject GithubRepoModel githubRepoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        GithubCheckerApp.getGithubComponent().inject(this);

        view = new PRListView(this);
        presenter = new PRListPresenter(githubRepoModel, view);

        setContentView(view.view());
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }
}
