
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class PetStoreAPITest {

    private static final String BASE_URL = "https://petstore.swagger.io/v2/pet";
    private static int petId = 2001;


    @Test
    @Order(1)
    @DisplayName("Adding new pet-positive case")
    public void testCreateNewPet() {
        String petData = "{ \"id\": " + petId + ", \"name\": \"Azman\", \"status\": \"available\" }";

        given()
                .contentType(ContentType.JSON)
                .body(petData)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(200)
                .body("name", equalTo("Azman"))
                .body("status", equalTo("available"));
    }

    @Test
    @Order(2)
    @DisplayName("Adding new pet-negative case")
    public void testCreatePetWithInvalidData() {
        String petData = "";

        given()
                .contentType(ContentType.JSON)
                .body(petData)
                .when()
                .post(BASE_URL)
                .then()
                .statusCode(405);

    }

    @DisplayName("Get pet data-positive case")
    @Order(3)
    @Test
    public void testGetExistingPet() {
        given()
                .when()
                .get(BASE_URL + "/" + petId)
                .then()
                .statusCode(200)
                .body("id", equalTo(2001))
                .body("name", equalTo("Azman Updated"));
    }

    @DisplayName(value = "Get pet data-negative case")
    @Order(4)
    @Test
    public void testGetNonExistingPet() {
        try {
            given()
                    .when()
                    .get(BASE_URL + "/" + 10010000)
                    .then()
                    .statusCode(404);

            ;
        } catch (Exception e) {
            System.out.println("Exception caught: " + e.getMessage());

        }
    }

    @DisplayName(value = "update pet data-positive case")
    @Order(5)
    @Test
    public void testUpdatePet() {
        String updatedData = "{ \"id\": " + petId + ", \"name\": \"Azman Updated\", \"status\": \"pending\" }";

        given()
                .contentType(ContentType.JSON)
                .body(updatedData)
                .when()
                .put(BASE_URL)
                .then()
                .statusCode(200)
                .body("name", equalTo("Azman Updated"))
                .body("status", equalTo("pending"));
    }

    @DisplayName(value = "update pet data-negative case")
    @Order(6)
    @Test
    public void testUpdateNonExistingPet() {
        String nonExistentPet = "{ \"id\": 43543534534543, \"status\": \"available\" }";
        given()
                .contentType(ContentType.JSON)
                .body(nonExistentPet)
                .when()
                .put(BASE_URL)
                .then()
                .statusCode(200);
    }

    @DisplayName(value = "update pet with form data-positive case")
    @Order(7)
    @Test
    public void testUpdatePetFormData() {

        given()
                .contentType("application/x-www-form-urlencoded")
                .accept(ContentType.JSON)
                .formParam("name", "ewfwef") // Form parametreleri
                .formParam("status", "pending")
                .when()
                .post(BASE_URL+"/1001")
                .then()
                .statusCode(200)
                .body("message", equalTo("1001"));
    }

    @DisplayName(value = "update pet with form data-negative case")
    @Order(8)
    @Test
    public void testUpdatePetFormDataNegativeCase() {

        given()
                .contentType("application/x-www-form-urlencoded")
                .accept(ContentType.JSON)
                .formParam("name", "ewfwef") // Form parametreleri
                .formParam("status", "pending")
                .when()
                .post(BASE_URL+"/100145435")
                .then()
                .statusCode(404);

    }

    @DisplayName(value = "delete pet data-positive case")
    @Order(9)
    @Test
    public void testDeletePet() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .delete(BASE_URL + "/" + petId)
                .then()
                .statusCode(200);
    }

    @DisplayName(value = "delete pet data-negative case")
    @Order(10)
    @Test
    public void testDeleteNonExistingPet() {
        try {
            given()
                    .contentType(ContentType.JSON)
                    .when()
                    .delete(BASE_URL + "/" + 10010000)
                    .then()
                    .statusCode(404);
        }
         catch(Exception e){
            System.out.println("Exception caught: " + e.getMessage());

        }
    }

    @DisplayName("Get pet data by status-positive case")
    @Order(11)
    @Test
    public void testGetExistingPetByStatus() {
             given()
             .when()
             .queryParam("status","sold")
             .get(BASE_URL + "/findByStatus")
             .then()
             .statusCode(200);
    }

}