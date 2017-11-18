package alex.com.githubchecker.components.github.repo.list;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.jakewharton.rxbinding2.view.RxView;

import alex.com.githubchecker.R;
import alex.com.githubchecker.models.api.PullRequest;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.subjects.PublishSubject;

/**
 * Created by Alex on 11/13/2017.
 */

class PRViewHolder extends RecyclerView.ViewHolder {

    private View view;

    @BindView(R.id.title) TextView titleTv;
    @BindView(R.id.status) TextView statusTv;

    private Integer _pullRequestNumber;

    PRViewHolder(View itemView, PublishSubject<Integer> selectedPRSubject) {
        super(itemView);
        this.view = itemView;
        ButterKnife.bind(this, view);

        RxView.clicks(view).subscribe(click -> {selectedPRSubject.onNext(_pullRequestNumber);});
    }

    void bind(PullRequest pullRequest) {

        _pullRequestNumber = pullRequest.getNumber();

        String userLogin = pullRequest.getUserLogin();
        Integer number = pullRequest.getNumber();
        String createdAt = pullRequest.getCreatedAt();
        String status = view.getContext().getResources().getString(R.string.pr_content, number, createdAt, userLogin);

        titleTv.setText(pullRequest.getTitle());
        statusTv.setText(status);
    }

}

