package org.ns.automation.stepdefinition;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ns.automation.utils.TestContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class DeleteObjectStepDefinition {

    private final TestContext context;
    public static final String CREATED_OBJECT_ID = "created_object_id";

    public DeleteObjectStepDefinition(TestContext context){
        this.context = context;
    }

    @When("the request to delete the item by {string} is made")
    public void the_request_to_delete_the_item_by_is_made(String id) {
        if(StringUtils.isBlank(id)){
            context.response = context.requestSetup().delete((String)context.session.get(CREATED_OBJECT_ID));
            assertEquals(context.response.jsonPath().get("message"),
                    String.format("Object with id = %s has been deleted.", context.session.get(CREATED_OBJECT_ID)));
            log.info(String.format("Object with id = %s has been deleted.", context.session.get(CREATED_OBJECT_ID)));
        } else {
            context.response = context.requestSetup().delete(id);
        }
    }

    @Then("verify the object is deleted")
    public void verify_the_object_is_deleted() {
        context.response = context.requestSetup().get((String)context.session.get(CREATED_OBJECT_ID));
        assertTrue(((String)context.response.jsonPath().get("error")).contains("was not found"));
    }

    @Then("error message containing {string} is returned")
    public void error_message_containing_is_returned(String errorMsg) {
        assertTrue(((String)context.response.jsonPath().get("error")).contains(errorMsg));
    }

}
