package be.acuzio.mrta.test.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.shadows.ShadowLog;
import org.robolectric.shadows.ShadowNotification;
import org.robolectric.shadows.ShadowNotificationManager;

import net.darkwire.example.service.BaseBroadcastIntentService;

/**
 * Created by ijunes on 02/05/2015.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class BaseBroadcastIntentServiceTest {
    static {
        ShadowLog.stream = System.out;
    }

    @Before
    public void setup() {}

    @Test
    public void testNoBundleExtrasFound() {
        Intent serviceIntent = new Intent(Robolectric.application, BaseBroadcastIntentServiceMock.class);
        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        //Robolectric.getShadowApplication().startService(serviceIntent);
        BaseBroadcastIntentServiceMock service = new BaseBroadcastIntentServiceMock();
        service.onCreate();
        service.onHandleIntent(serviceIntent);

        Assert.assertEquals("Expected no notifications", 0, Robolectric.shadowOf(notificationManager).size());
    }

   // @Test
    public void testWithBundleExtrasFound() {
        Intent serviceIntent = new Intent(Robolectric.application, BaseBroadcastIntentServiceMock.class);
        Bundle bundle = new Bundle();
        bundle.putString("ACTION", "eat an apple");
        serviceIntent.putExtras(bundle);

        NotificationManager notificationManager = (NotificationManager) Robolectric.application.getSystemService(Context.NOTIFICATION_SERVICE);

        //Robolectric.getShadowApplication().startService(serviceIntent);
        BaseBroadcastIntentServiceMock service = new BaseBroadcastIntentServiceMock();
        service.onCreate();
        service.onHandleIntent(serviceIntent);


        ShadowNotificationManager manager = Robolectric.shadowOf(notificationManager);
        Assert.assertEquals("Expected one notification", 1, manager.size());

        Notification notification = manager.getNotification(BaseBroadcastIntentService.NOTIFICATION_TAG, BaseBroadcastIntentService.NOTIFICATION_ID);
        Assert.assertNotNull("Expected notification object", notification);

        ShadowNotification shadowNotification = Robolectric.shadowOf(notification);
        Assert.assertNotNull("Expected shadow notification object", shadowNotification);
        Assert.assertNotNull("Expected to have event information", shadowNotification.getLatestEventInfo());

        Assert.assertEquals("You are going to eat an apple", shadowNotification.getLatestEventInfo().getContentText());
    }

    class BaseBroadcastIntentServiceMock extends BaseBroadcastIntentService {
        @Override
        public void onHandleIntent(Intent intent) {
            super.onHandleIntent(intent);
        }
    }
}
