package net.darkwire.example.test.util;
import android.test.InstrumentationTestRunner;
import android.test.InstrumentationTestSuite;
import net.darkwire.test.MainActivityTest;
import junit.framework.TestSuite;

public class Runner extends InstrumentationTestRunner {

    @Override
    public TestSuite getAllTests(){
        InstrumentationTestSuite suite = new InstrumentationTestSuite(this);
        suite.addTestSuite(MainActivityTest.class);
        return suite;
    }

    @Override
    public ClassLoader getLoader() {
        return Runner.class.getClassLoader();
    }


}