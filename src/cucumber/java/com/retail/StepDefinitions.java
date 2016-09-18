package com.retail;

import com.github.tomakehurst.wiremock.WireMockServer;
import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.junit.Cucumber;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;

@RunWith(Cucumber.class)
@CucumberOptions(features = "classpath:resources")
public class StepDefinitions {
    private WireMockServer wireMockServer;
    private CloseableHttpClient httpClient;

    private static final String ADD_SHOP_JSON = "{\"name\":\"Whittard\", \"houseNumber\": 67, \"postCode\":\"W1B 4DZ\"}";
    private static final String GET_SHOP_JSON = "{\"name\":\"Whittard\", \"houseNumber\": 67, \"postCode\":\"W1B 4DZ\"}";
    private HttpResponse response;

    @Before
    public void beforeScenario() {
        wireMockServer = new WireMockServer();
        wireMockServer.start();
    }

    @Given("^Shop API started$")
    public void shopApiStarted() {

        httpClient = HttpClients.createDefault();

        configureFor("localhost", 8080);
        stubFor(post(urlEqualTo("/shop/add"))
                .withHeader("content-type", equalTo("application/json"))
                .withRequestBody(containing("postCode"))
                .willReturn(aResponse().withStatus(200)));

        stubFor(get(urlEqualTo("/shop/get-nearest"))
                .withHeader("accept", equalTo("application/json"))
//                .withRequestBody(containing("customerLongitude")).withRequestBody(containing("customerLatitude"))
                .willReturn(aResponse().withBody(GET_SHOP_JSON)));
    }

    @When("^Retail Manager adds a shop$")
    public void retailManagerAddsAshop() throws IOException {
        HttpPost request = new HttpPost("http://localhost:8080/shop/add");
        StringEntity entity = new StringEntity(ADD_SHOP_JSON);
        request.addHeader("content-type", "application/json");
        request.setEntity(entity);
        response = httpClient.execute(request);
    }

    @Then("^the server should handle it and return a success status$")
    public void serverReturnsSuccess() {
        assertEquals(200, response.getStatusLine().getStatusCode());
        verify(postRequestedFor(urlEqualTo("/shop/add"))
                .withHeader("content-type", equalTo("application/json")));
    }

    //---------------------------------------------------------------

    @When("^Customer requests the nearest shop$")
    public void customerRequestsTheNearestShop() throws IOException {
        HttpGet request = new HttpGet("http://localhost:8080/shop/get-nearest");
        request.addHeader("accept", "application/json");
        response = httpClient.execute(request);
    }

    @Then("^the server should handle it and return the nearest shop$")
    public void serverReturnsSTheNearestShop() {
        assertEquals(200, response.getStatusLine().getStatusCode());
//        assertEquals(200, response.get().getStatusCode());
        verify(getRequestedFor(urlEqualTo("/shop/get-nearest"))
                .withHeader("accept", equalTo("application/json")));
    }

    @After
    public void afterScenario() {
        wireMockServer.stop();
    }
}
