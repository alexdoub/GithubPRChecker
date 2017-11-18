package alex.com.githubchecker.components.github.activities;

import android.os.Bundle;

import javax.inject.Inject;

import alex.com.githubchecker.components.app.BaseActivity;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.components.github.repo.GithubModel;
import alex.com.githubchecker.components.github.repo.RepoPRListView;
import alex.com.githubchecker.components.github.repo.RepoPRListPresenter;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/17/2017.
 */

public class PRListActivity extends BaseActivity {

    RepoPRListView view;
    RepoPRListPresenter presenter;

    @Inject GithubModel githubModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ButterKnife.bind(this);
        GithubCheckerApp.getGithubComponent().inject(this);

        view = new RepoPRListView(this);
        presenter = new RepoPRListPresenter(githubModel, view);

        setContentView(view.view());
        presenter.onCreate();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }
}
