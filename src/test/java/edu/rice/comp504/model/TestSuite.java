package edu.rice.comp504.model;

import edu.rice.comp504.model.gameobj.GameObjTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import edu.rice.comp504.model.strategy.StrategyTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GameObjTest.class,
        DispatcherAdapterTest.class,
        StrategyTest.class
})
public class TestSuite {
}
