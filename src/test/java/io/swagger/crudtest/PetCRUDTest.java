package io.swagger.crudtest;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.swagger.constants.EndPoints;
import io.swagger.steps.PetSteps;
import io.swagger.testbase.TestBase;
import io.swagger.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class PetCRUDTest extends TestBase {
    static int  id ;
    static String name = "coca";
    static String status = "available";

    @Steps
    PetSteps petSteps;

    @Title("This will create a new pet")
    @Test
    public void test001() {
        ValidatableResponse response = petSteps.createPet(name,status);
        response.log().all().statusCode(200);
    }

    @Title("This will get all the pet")
    @Test
    public void test002() {
        Response response = given().log().all()
                .when()
                .get(EndPoints.GET_ALL_PET);
        response.then().statusCode(200);
        response.prettyPrint();
    }
    @Title("Verify if the pet is added to the application")
    @Test
    public void test003() {
        ValidatableResponse response = petSteps.getPetInfoByID();
        ArrayList<?> booking = response.extract().path("petID");
        Assert.assertTrue(booking.contains(id));
    }

    @Title("This method will get pet with Id")
    @org.junit.Test
    public void test004() {
        petSteps.getSinglePet(id).statusCode(200);
    }

    @Title("This method will updated a booking with ID")
    @Test
    public void test005() {
        name = name + TestUtils.getRandomValue();
        petSteps.updatePetWithId(id,name,status);
        ValidatableResponse response = petSteps.getPetInfoByID();
        HashMap<String, ?> update = response.extract().path("/v2/pet");
        Assert.assertThat(update,hasValue("coca"));

    }
    @Title("This method will delete the pet with ID")
    @Test
    public void test006() {
       petSteps.deletePetID(id).statusCode(201);
       petSteps.getSinglePet(id).statusCode(404);
    }



















}
