package io.swagger.steps;

import io.restassured.response.ValidatableResponse;
import io.swagger.constants.EndPoints;
import io.swagger.constants.Path;
import io.swagger.model.PetPojo;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

public class PetSteps {
    @Step("Creating new pet with id:{0},name:{1},status:{2}")
    public ValidatableResponse createPet( String name, String status) {
        PetPojo petPojo = new PetPojo();
        petPojo.setName(name);
        petPojo.setStatus(status);
        return SerenityRest.given()
                .basePath(Path.PET)
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .body(petPojo)
                .when()
                .post(EndPoints.GET_ALL_USER)
                .then().log().all();

    }

    @Step("Getting existing single pet with id:{0}")
    public ValidatableResponse getSinglePet(int id) {
        return SerenityRest.given()
                .basePath(Path.PET)
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .pathParam("id", id)
                .when()
                .get(EndPoints.GET_SINGLE_PET_BY_ID)
                .then().log().all();

    }
@Step("Update record with id:{0},name:{1},status:{2}")
    public ValidatableResponse updatePetWithId( int id,String name, String status){
    PetPojo petPojo = new PetPojo();
    petPojo.setName(name);
    petPojo.setStatus(status);
    return SerenityRest.given()
            .basePath(Path.PET)
            .header("Connection", "keep-alive")
            .header("Content-Type", "application/json")
            .pathParam("id",id)
            .body(petPojo)
            .when().put(EndPoints.UPDATE_PET_BY_ID)
            .then().log().all().statusCode(200);
}
@Step("Deleting existing pet with id:{0}")
 public ValidatableResponse deletePetID(int id ){
    return SerenityRest.given().log().all()
            .header("Connection", "keep-alive")
            .header("Content-Type", "application/json")
            .pathParam("id", id)
            .when().delete(EndPoints.DELETE_PET_BY_ID)
            .then().log().all();
}


    @Step("Getting pet info by ID")
    public ValidatableResponse getPetInfoByID() {
        return SerenityRest.given()
                .when()
                .get()
                .then().statusCode(200);
    }







}
