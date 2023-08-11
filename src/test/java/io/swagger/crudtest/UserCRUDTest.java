package io.swagger.crudtest;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.swagger.constants.EndPoints;
import io.swagger.steps.UserSteps;
import io.swagger.testbase.TestBase;
import io.swagger.utils.TestUtils;
import net.serenitybdd.junit.runners.SerenityRunner;
import net.thucydides.core.annotations.Steps;
import net.thucydides.core.annotations.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;

import static net.serenitybdd.rest.RestRequests.given;
import static org.hamcrest.Matchers.hasValue;

@RunWith(SerenityRunner.class)
public class UserCRUDTest extends TestBase {

   // static String id = "23";
    static String username = "Romi";
    static String firstName = "Rima";
    static String lastName = "Rudd";
    static String email = "rima1@gmail.com"+ TestUtils.getRandomValue();
    static String password = "rome2356";
    static String phone = "09876543221";
    static int userStatus = 2;
    static  int id;
    @Steps
    UserSteps userSteps;

    @Title("This will create a new user")
    @Test
    public void test001() {
        ValidatableResponse response = userSteps.createUser(username, firstName, lastName, email,
                password, phone, userStatus);
        response.log().all().statusCode(200);
    }

    @Title("This will get all user")
    @Test
    public void test002() {
        Response response = given().log().all()
                .when()
                .get(EndPoints.GET_ALL_USER);
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Title("Verify if the user was added to the application")
    @Test
    public void test003() {
        HashMap<String, Object> userMap = userSteps.getUserInfoByName(username);
        Assert.assertThat(userMap, hasValue(username));
        id = (int) userMap.get("id");
    }

    @Title("Update the user information and verify the updated information")
    @Test()
    public void test004() {
        firstName = firstName + TestUtils.getRandomValue();
        userSteps.updateUser(username,firstName,lastName,email,password,phone,userStatus);
        HashMap<String,Object> userMap = userSteps.getUserInfoByName(username);
        Assert.assertThat(userMap,hasValue(username));


    }
    @Title("Delete the user and verify if the user is deleted")
    @Test
    public void test005() {
        userSteps.deleteUser(username);
        userSteps.getUserInfoByName(username);
    }

}
