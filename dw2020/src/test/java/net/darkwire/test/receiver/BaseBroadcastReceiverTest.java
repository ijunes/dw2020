package be.acuzio.mrta.test.receiver;

import android.content.BroadcastReceiver;
import android.content.Intent;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowApplication;
import org.robolectric.shadows.ShadowLog;

import java.util.List;

import net.darkwire.example.receiver.BaseBroadcastReceiver;
import net.darkwire.example.service.BaseBroadcastIntentService;

/**
 * Created by ijunes on 02/05/2015.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class BaseBroadcastReceiverTest {
    static {
        // redirect the Log.x output to stdout. Stdout will be recorded in the test result report
        ShadowLog.stream = System.out;
    }

    @Before
    public void setup() {

    }

    /**
     * Let's first test if the BroadcastReceiver, which was defined in the manifest, is correctly
     * load in our tests
     */
    @Test
    public void testBroadcastReceiverRegistered() {
        List<ShadowApplication.Wrapper> registeredReceivers = Robolectric.getShadowApplication().getRegisteredReceivers();

        Assert.assertFalse(registeredReceivers.isEmpty());

        boolean receiverFound = false;
        for (ShadowApplication.Wrapper wrapper : registeredReceivers) {
            if (!receiverFound)
                receiverFound = BaseBroadcastReceiver.class.getSimpleName().equals(wrapper.broadcastReceiver.getClass().getSimpleName());
        }

        Assert.assertTrue(receiverFound); //will be false if the container did not register the broadcast receiver we want to test
    }

    @Test
    public void testIntentHandling() {
        /** TEST 1
         ----------
         We defined the Broadcast receiver with a certain action, so we should check if we have
         receivers listening to the defined action
         <intent-filter>
         <action android:name="com.google.android.c2dm.intent.RECEIVE" />
         <category android:name="be.acuzio.mrta" />
         </intent-filter>
         */
        Intent intent = new Intent("com.google.android.c2dm.intent.RECEIVE");

        ShadowApplication shadowApplication = Robolectric.getShadowApplication();
        Assert.assertTrue(shadowApplication.hasReceiverForIntent(intent));

        /**
         * TEST 2
         * ----------
         * Lets be sure that we only have a single receiver assigned for this intent
         */
        List<BroadcastReceiver> receiversForIntent = shadowApplication.getReceiversForIntent(intent);

        Assert.assertEquals("Expected one broadcast receiver", 1, receiversForIntent.size());

        /**
         * TEST 3
         * ----------
         * Fetch the Broadcast receiver and cast it to the correct class.
         * Next call the "onReceive" method and check if the BaseBroadcastIntentService was started
         */
        BaseBroadcastReceiver receiver = (BaseBroadcastReceiver) receiversForIntent.get(0);
        receiver.onReceive(Robolectric.getShadowApplication().getApplicationContext(), intent);

        Intent serviceIntent = Robolectric.getShadowApplication().peekNextStartedService();
        Assert.assertEquals("Expected the BaseBroadcast service to be invoked",
                BaseBroadcastIntentService.class.getCanonicalName(),
                serviceIntent.getComponent().getClassName());

    }
}
