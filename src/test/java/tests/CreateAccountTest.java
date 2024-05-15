package tests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import objectData.request.RequestAccount;
import objectData.response.ResponseAccountSuccess;
import objectData.response.ResponseTokenSuccess;
import objectData.restClient.RestClient;
import objectData.restClient.RestEndPoint;
import objectData.restClient.RestStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import propertiesUtility.PropertiesUtility;
import service.AccountService;

public class CreateAccountTest {

    public RequestAccount requestAccountBody;
    public String token;
    public String userID;
    public AccountService accountService;

    RestClient restClient = new RestClient();

    @Test
    public void testMethod() {
        System.out.println("===STEP 1: CREATE NEW ACCOUNT===");
        createAccount();

        System.out.println("===STEP 2: GENERATE TOKEN===");
        generateToken();

        System.out.println("===STEP 3: CHECK ACCOUNT===");
        checkAccPresence();

        System.out.println("===STEP 4: DELETE ACCOUNT===");
        deleteUser();

        System.out.println("===STEP 5: RECHECK ACCOUNT===");
        checkAccPresence();

    }

    public void createAccount(){
        // pregatim request-ul
        PropertiesUtility propertiesUtility = new PropertiesUtility("Request/CreateAccountData");
        requestAccountBody = new RequestAccount(propertiesUtility.getAllData());

        accountService = new AccountService();

        ResponseAccountSuccess responseAccountSuccess = accountService.createAccount(requestAccountBody);
        userID = responseAccountSuccess.getUserId();
    }

    public void generateToken(){

        ResponseTokenSuccess responseTokenSuccess = accountService.generateToken(requestAccountBody);
        token = responseTokenSuccess.getToken();
    }

    public void checkAccPresence(){
        accountService.checkAccPresence(userID, token);
    }

    public void deleteUser(){
        accountService.deleteAccount(userID, token);
    }


}
