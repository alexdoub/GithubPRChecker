package alex.com.githubchecker.components.github.list

import alex.com.githubchecker.GithubCheckerApp
import alex.com.githubchecker.components.app.base.BaseActivity
import alex.com.githubchecker.components.github.GithubModel
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

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        GithubCheckerApp.githubComponent.inject(this)

        view = RepoPRListView(this)
        presenter = RepoPRListPresenter(githubModel, view)
        presenter.onCreate()
    }

    override fun onStart() {
        super.onStart()
        presenter.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.onDestroy()
    }
}
