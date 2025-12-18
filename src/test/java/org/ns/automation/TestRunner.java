package org.ns.automation;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        plugin = {"pretty:target/cucumber/cucumber.txt",
//  for better reporting    "com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:",
                "html:target/cucumber/report",
                "json:target/cucumber/cucumber.json"
        }
        ,features= {"src/test/resources/features"}
        ,glue = {"org.ns.automation.stepdefinition"}
)
public class TestRunner {

}
