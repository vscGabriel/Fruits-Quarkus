package com.vscgabriel;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsInAnyOrder;

@QuarkusTest
public class FruitResourceTest {
    @Test
    public void testList() {
        given()
                .when().get("/fruits/get-all")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("Apple", "Pineapple"),
                        "quantity", containsInAnyOrder(5,2),
                        "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }

    @Test
    public void testAdd() {
        given()
                .body("{\"name\": \"Pear\",\"quantity\": 1, \"description\": \"Winter fruit\"}")
                .header("Content-Type", ContentType.JSON)
                .when()
                .post("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(3),
                        "name", containsInAnyOrder("Apple", "Pineapple", "Pear"),
                        "quantity", containsInAnyOrder(5,2,1),
                        "description", containsInAnyOrder("Winter fruit", "Tropical fruit", "Winter fruit"));

        given()
                .body("{\"name\": \"Pear\",\"quantity\": 1, \"description\": \"Winter fruit\"}")
                .header("Content-Type", ContentType.JSON)
                .when()
                .delete("/fruits")
                .then()
                .statusCode(200)
                .body("$.size()", is(2),
                        "name", containsInAnyOrder("Apple", "Pineapple"),
                        "quantity", containsInAnyOrder(5,2),
                        "description", containsInAnyOrder("Winter fruit", "Tropical fruit"));
    }
}
