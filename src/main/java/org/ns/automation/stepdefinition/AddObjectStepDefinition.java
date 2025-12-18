package org.ns.automation.stepdefinition;

import io.cucumber.core.internal.com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MediaType;
import org.ns.automation.model.RequestDataPojo;
import org.ns.automation.model.RequestPojo;
import org.ns.automation.utils.TestContext;

import java.io.File;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class AddObjectStepDefinition {

    private final TestContext context;

    private ObjectMapper objectMapper;

    public static final String CREATE_REQUEST = "create_request";

    public AddObjectStepDefinition(TestContext context){
        this.context = context;
        this.objectMapper = new ObjectMapper();
    }

    @Before
    public static void setup(){
        Response response = RestAssured.given()
                .baseUri("https://restful-api.dev/")
                .contentType(MediaType.APPLICATION_JSON.toString())
                .accept(MediaType.APPLICATION_JSON.toString()).get();
        assertTrue(response.statusCode() == 200);
    }

    @Given("a {string} item is created")
    public void a_item_is_created(String name) {
        RequestPojo requestPojo = RequestPojo.builder().name(name).build();
        context.session.put(CREATE_REQUEST, requestPojo);
    }

    @Given("is a {string} CPU model with hard disk {string}")
    public void is_a_cpu_model(String model, String diskSize) {
        RequestDataPojo dataPojo = RequestDataPojo.builder().cpuModel(model).hardDiskSize(diskSize).build();
        ((RequestPojo)context.session.get(CREATE_REQUEST)).setData(dataPojo);
        context.session.put(CREATE_REQUEST, context.session.get("create_request"));
    }

    @Given("has a price of {string} manufactured in {string}")
    public void has_a_price_of(String price, String year) {
        RequestDataPojo dataPojo = ((RequestPojo)context.session.get(CREATE_REQUEST)).getData();
        dataPojo.setPrice(price);
        dataPojo.setYear(year);
        ((RequestPojo)context.session.get(CREATE_REQUEST)).setData(dataPojo);
    }

    @Given("item to create is from a file")
    public void item_to_create_is_from_a_file() throws Exception {
        RequestPojo requestPojo = objectMapper.readValue(new File("src/main/resources/create_item.json"),RequestPojo.class);
        context.session.put(CREATE_REQUEST, requestPojo);
    }

    @Given("item to create is from cucumber data table")
    public void item_to_create_is_from_cucumber_data_table(DataTable dataTable) {
        Map<String,String> inputData = dataTable.asMaps().get(0);
        RequestDataPojo requestDataPojo = RequestDataPojo.builder().cpuModel(inputData.get("model"))
                .price(inputData.get("price"))
                .year(inputData.get("year"))
                .hardDiskSize(inputData.get("harddisk")).build();
        RequestPojo requestPojo = RequestPojo.builder().name(inputData.get("name")).data(requestDataPojo).build();
        context.session.put(CREATE_REQUEST, requestPojo);

    }

    @When("the request to add the item is made")
    public void the_request_to_add_the_item_is_made() throws Exception {
        RequestPojo addItemRequest = ((RequestPojo)context.session.get(CREATE_REQUEST));
        String requestAsStr = objectMapper.writeValueAsString(addItemRequest);
//        log.info(requestAsStr);
//        JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
        context.response = context.requestSetup().given().body(requestAsStr).post();
        context.session.put("created_object_id", context.response.jsonPath().get("id"));
    }

    @Then("an item having name {string} is created")
    public void an_item_having_name_is_created(String name) {
        context.response = context.requestSetup().get((String)context.session.get("created_object_id"));
        assertEquals(context.response.jsonPath().get("name"), name);
        context.response.body().prettyPrint();
        log.info("An object having name " + name + " is created");
    }
}
