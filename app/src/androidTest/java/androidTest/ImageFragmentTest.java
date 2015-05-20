package androidTest;

import android.content.Intent;
import android.test.ActivityInstrumentationTestCase2;

import com.flipkart.newsapp.MainActivity;
import com.flipkart.newsapp.fragments.ImageFragment;

/**
 * Created by kumar.samdwar on 20/05/15.
 */
public class ImageFragmentTest extends ActivityInstrumentationTestCase2<ImageFragment> {
    public ImageFragmentTest(Class<ImageFragment> activityClass) {
        super(activityClass);
    }

    @Override
    public ImageFragment getActivity() {
        return super.getActivity();
    }

    @Override
    public void setActivityIntent(Intent i) {
        super.setActivityIntent(i);
    }

    @Override
    public void setActivityInitialTouchMode(boolean initialTouchMode) {
        super.setActivityInitialTouchMode(initialTouchMode);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
