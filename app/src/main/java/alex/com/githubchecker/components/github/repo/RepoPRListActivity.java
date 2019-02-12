package alex.com.githubchecker.components.github.repo;

import android.os.Bundle;

import javax.inject.Inject;

import alex.com.githubchecker.components.app.BaseActivity;
import alex.com.githubchecker.components.app.GithubCheckerApp;
import alex.com.githubchecker.models.dagger.GithubModel;
import butterknife.ButterKnife;

/**
 * Created by Alex on 11/17/2017.
 */

public class RepoPRListActivity extends BaseActivity {

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

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }
}
