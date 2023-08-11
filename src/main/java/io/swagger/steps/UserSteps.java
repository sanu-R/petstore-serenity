package io.swagger.steps;

import io.restassured.response.ValidatableResponse;
import io.swagger.constants.EndPoints;
import io.swagger.constants.Path;
import io.swagger.model.UserPojo;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.HashMap;


public class UserSteps {

  @Step("Creating new user with id:,{0} username:{1},firstName:{1},lastName:{2},email:{3},password:{4},phone:{5},userStatus: {6}")
public ValidatableResponse createUser(String username,String firstName,String lastName,String email
                                      ,String password,String phone,int userStatus ){
    UserPojo userPojo= new UserPojo();
   // userPojo.setId(id);
    userPojo.setUsername(username);
    userPojo.setFirstName(firstName);
    userPojo.setLastName(lastName);
    userPojo.setEmail(email);
    userPojo.setPassword(password);
    userPojo.setPhone(phone);
    userPojo.setUserStatus(userStatus);
    return SerenityRest.given()
            .basePath(Path.USER)
            .header("Connection", "keep-alive")
            .header("Content-Type", "application/json")
            .body(userPojo)
            .when()
            .post(EndPoints.GET_ALL_USER)
            .then();

  }
    @Step("Getting the user information with firstName : {0}")
    public HashMap<String, Object> getUserInfoByName(String name) {
        String s1 = "findAll{it.name == '";
        String s2 = "'}.get(0)";
        return SerenityRest.given()
                .basePath(Path.USER)
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .when()
                .get(EndPoints.GET_SINGLE_USER_BY_USERNAME)
                .then().statusCode(200)
                .extract()
                .path(s1 + name + s2);
    }

    @Step("Updating user information with username:{0},firstName:{1},lastName:{2},email:{3},password:{4},phone:{5},userStatus: {6}")
    public ValidatableResponse updateUser(String username,String firstName,String lastName,String email
            ,String password,String phone,int userStatus) {
      UserPojo userPojo = UserPojo.getUserPojo(username,firstName,lastName,email,password,phone,userStatus);
        return SerenityRest.given()
                .basePath(Path.USER)
                .header("Content-Type", "application/json")
                .header("Connection", "keep-alive")
                .pathParam("username", username)
                .when()
                .body(userPojo)
                .put(EndPoints.UPDATE_USER_BY_USERNAME)
                .then().statusCode(200);
    }

    @Step("Deleting user information with username : {0}")
    public ValidatableResponse deleteUser(String username) {
        return SerenityRest.given()
                .basePath(Path.USER)
                .header("Connection", "keep-alive")
                .header("Content-Type", "application/json")
                .pathParam("username", username)
                .when()
                .delete(EndPoints.DELETE_USER_BY_USERNAME)
                .then().statusCode(404);
  }

}
