package alex.com.githubchecker.components.github.repo

import alex.com.githubchecker.components.app.BaseActivity
import alex.com.githubchecker.components.app.GithubCheckerApp
import alex.com.githubchecker.models.dagger.GithubModel
import android.os.Bundle
import javax.inject.Inject

/**
 * Created by Alex on 11/17/2017.
 */

class RepoPRListActivity : BaseActivity() {

    private lateinit var view: RepoPRListView
    private lateinit var presenter: RepoPRListPresenter

    @Inject
    lateinit var githubModel: GithubModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        GithubCheckerApp.githubComponent.inject(this)

        view = RepoPRListView(this)
        presenter = RepoPRListPresenter(githubModel!!, view)
        presenter.onCreate()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }
}
