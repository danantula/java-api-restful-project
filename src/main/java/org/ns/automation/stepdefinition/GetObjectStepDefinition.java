package org.ns.automation.stepdefinition;

import com.google.gson.internal.LinkedTreeMap;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.ns.automation.utils.TestContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
public class GetObjectStepDefinition {

    private final TestContext context;

    public GetObjectStepDefinition(TestContext context){
        this.context = context;
    }

    @When("the request to return the item by {string} is made")
    public void the_request_to_return_the_item_by_is_made(String id) {
        context.response = context.requestSetup().get(id);
    }

    @Then("a {int} response code is returned")
    public void a_response_code_is_returned(Integer statusCode) {
        context.response.then().statusCode(statusCode);
    }

    @Then("an item having id {string} and name {string} is returned")
    public void an_item_having_id_is_returned(String id, String name) {
        assertEquals(context.response.jsonPath().get("id"),id);
        assertEquals(context.response.jsonPath().get("name"),name);
        log.info("An object having id " + id + " and name " + name + " is returned");
    }

    @When("the request to list multiple items is made")
    public void the_request_to_list_multiple_items_is_made() {
        context.response = context.requestSetup().get();
    }

    @Then("all the list is returned")
    public void all_the_list_is_returned() {
        List<LinkedTreeMap<String, String>> list = context.response.then().extract().as(List.class);
        assertNotNull(list);
        assertFalse(list.isEmpty());
        assertTrue(list.stream().anyMatch(responsePojo -> responsePojo.get("name").equals("Google Pixel 6 Pro")));
        assertFalse(list.stream().anyMatch(responsePojo -> responsePojo.get("id").equals("100")));
        log.info("List of objects returned with size " + list.size());
    }

}
