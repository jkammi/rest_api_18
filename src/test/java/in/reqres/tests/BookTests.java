package in.reqres.tests;

import in.reqres.lombok.Book;
import in.reqres.lombok.BookData;
import in.reqres.lombok.ListBooks;
import org.junit.jupiter.api.Test;

import static in.reqres.Specs.*;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookTests {

    @Test
    void singleBookTest() {
        int expectedId = 2;
        int expectedYear = 2001;
        String expectedName = "fuchsia rose";
        String expectedColor = "#C74375";
        String expectedPantoneValue = "17-2031";

        BookData response = given()
                .spec(request)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract()
                .as(BookData.class);

        Book book = response.getBook();
        assertEquals(expectedId, book.getId());
        assertEquals(expectedYear, book.getYear());
        assertEquals(expectedName, book.getName());
        assertEquals(expectedColor, book.getColor());
        assertEquals(expectedPantoneValue, book.getPantoneValue());
    }

    @Test
    void listOfBooksTest() {
        int expectedPage = 1;
        int expectedPerPage = 6;
        int expectedTotal = 12;
        int expectedTotalPages = 2;

        ListBooks listBooks = given()
                .spec(request)
                .when()
                .get("/unknown")
                .then()
                .spec(responseSpec)
                .log().body()
                .extract()
                .as(ListBooks.class);

        assertEquals(expectedPage, listBooks.getPage());
        assertEquals(expectedPerPage, listBooks.getPerPage());
        assertEquals(expectedTotal, listBooks.getTotal());
        assertEquals(expectedTotalPages, listBooks.getTotalPages());
    }

    @Test
    void bookNotFoundTest() {

                given()
                .spec(request)
                .when()
                .get("/unknown/23")
                .then()
                .spec(responseNotFoundSpec)
                .log().body();

    }

}