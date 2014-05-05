package net.darkwire.example.test;

import net.darkwire.example.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity> {
    private Solo solo;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation());
        getActivity();
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
    }

    public void testRun() {
        // Wait for activity: 'com.eharmony.ui.SplashActivity'
        solo.waitForActivity(net.darkwire.example.MainActivity.class, 2000);
        // Wait for activity: 'com.eharmony.ui.registration.initial.RegistrationChoiceActivity'
        assertTrue("net.darkwire.example.MainActivity not Found!", solo.waitForActivity(et.darkwire.example.MainActivity.class));

        solo.takeScreenshot();

}
