package xyz.dcme.agg.ui.personal;

public class PersonalInfoDetailPresenter implements PersonalInfoDetailContract.Presenter {

    private final PersonalInfoDetailContract.View mView;

    PersonalInfoDetailPresenter(PersonalInfoDetailContract.View v) {
        mView = v;
        v.setPresenter(this);
    }

    @Override
    public void start() {
        mView.setLoadingIndicator(true);

        // TODO: load details
    }
}
