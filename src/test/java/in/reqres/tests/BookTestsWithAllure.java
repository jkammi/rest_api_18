package in.reqres.tests;

import in.reqres.lombok.Book;
import in.reqres.lombok.BookData;
import in.reqres.lombok.ListBooks;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static in.reqres.Specs.*;
import static in.reqres.helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTestsWithAllure {

    @Test
    @Tag("allure")
    void singleBookWithAllureStepsTest() {
        step("Prepare test data");

        int expectedId = 2;
        int expectedYear = 2001;
        String expectedName = "fuchsia rose";
        String expectedColor = "#C74375";
        String expectedPantoneValue = "17-2031";

        BookData response = step("Make request", () ->
            given()
                .filter(withCustomTemplates())
                .spec(request)
            .when()
                .get("/unknown/2")
            .then()
                .spec(responseSpec)
                .log().body()
                .extract().as(BookData.class)
        );

        Book book = response.getBook();

        step("Verify response", () -> {
            assertEquals(expectedId, book.getId());
            assertEquals(expectedYear, book.getYear());
            assertEquals(expectedName, book.getName());
            assertEquals(expectedColor, book.getColor());
            assertEquals(expectedPantoneValue, book.getPantoneValue());
        });
    }

    @Test
    @Tag("allure")
    void listOfBooksWithAllureStepsTest() {
        step("Prepare test data");
        int expectedPage = 1;
        int expectedPerPage = 6;
        int expectedTotal = 12;
        int expectedTotalPages = 2;

        ListBooks listBooks = step("Make request", () ->
                given()
                .spec(request)
                .when()
                .get("/unknown")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract()
                .as(ListBooks.class)
        );

        step("Verify response", () -> {
        assertEquals(expectedPage, listBooks.getPage());
        assertEquals(expectedPerPage, listBooks.getPerPage());
        assertEquals(expectedTotal, listBooks.getTotal());
        assertEquals(expectedTotalPages, listBooks.getTotalPages());
        });
    }

    @Test
    @Tag("allure")
    void bookNotFoundWithAllureStepsTest() {
        step("Make request", () ->
                given()
                .spec(request)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseNotFoundSpec)
                .log().body()
        );

    }

}