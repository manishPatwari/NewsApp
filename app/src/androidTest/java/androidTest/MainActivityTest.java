package androidTest;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.test.ActivityInstrumentationTestCase2;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.flipkart.newsapp.MainActivity;
import com.flipkart.newsapp.R;

/**
 * Created by manish.patwari on 5/15/15.
 */
public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {

    MainActivity mMainActivity;
    DrawerLayout mDrawerLayout;
    ListView mDrawerList;
    Toolbar mToolbar;
    ImageButton navigationIcon;
    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        mMainActivity = getActivity();
        mToolbar = (Toolbar) mMainActivity.findViewById(R.id.tool_bar);
        mDrawerLayout = (DrawerLayout) mMainActivity.findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) mMainActivity.findViewById(R.id.left_drawer);
    }

    public void testPreconditions(){
        assertNotNull("ManinActivity is null" , mMainActivity);
        assertNotNull("ToolBar Obj is null" , mDrawerLayout);
        assertNotNull("DrawerLayout Obj is null" , mDrawerLayout);
        assertNotNull("DrawerList Obj is null" , mDrawerList);
    }

    public void testDrawerDefaultClose(){
        assertFalse("Drawer is open by default", mDrawerLayout.isDrawerOpen(mDrawerList));
    }

    public void testDrawerOpenAndCloseOnClickOfNavigationIcon() throws InterruptedException {
        for(int i = 0; i < mToolbar.getChildCount(); i++) {
            final View v = mToolbar.getChildAt(i);
            if (v instanceof ImageButton  && v.getContentDescription() == mMainActivity.getResources().getString(R.string.navigation_icon_desc)) {
                navigationIcon = ((ImageButton) v);
                break;
            }
        }
        assertNotNull("Navigation Icon Object is null", navigationIcon);

        assertTrue("Navigation Icon is not visible" ,navigationIcon.isShown());

        //Check for Opening Navigation Drawer.
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navigationIcon.performClick();
            }
        });
        Thread.sleep(1000);
        assertTrue("Drawer is not opening on click of NaviagationIcon", mDrawerLayout.isDrawerOpen(mDrawerList));


        // Closing Navigation Drawer.
        mMainActivity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                navigationIcon.performClick();
            }
        });
        Thread.sleep(1000);

        assertFalse("Drawer is not Closing on click of NaviagationIcon", mDrawerLayout.isDrawerOpen(mDrawerList));
    }
}
