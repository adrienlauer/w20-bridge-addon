package org.seedstack.w20;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.seedstack.seed.Configuration;
import org.seedstack.seed.testing.junit4.SeedITRunner;
import org.seedstack.seed.undertow.LaunchWithUndertow;

@RunWith(SeedITRunner.class)
@LaunchWithUndertow
public class Html5RedirectIT {
    @Configuration("runtime.web.baseUrl")
    private String baseUrl;

    @Test
    public void masterPageShouldBeServed() {
        expectMasterpage(baseUrl);
    }

    @Test
    public void nonExistingResourceShouldRedirectToMasterpage() {
        expectMasterpage(baseUrl + "/toto");
    }

    @Test
    public void apiReturning404ShouldNotRedirect() {
        given()
                .auth().basic("ThePoltergeist", "bouh")
                .expect()
                .statusCode(404)
                .when()
                .get(baseUrl + "/api/not-found");
    }

    @Test
    public void servletShouldBeReachable() {
        given()
                .auth().basic("ThePoltergeist", "bouh")
                .expect()
                .statusCode(200)
                .body(Matchers.equalTo("Hello World!"))
                .when()
                .get(baseUrl + "/test-servlet");
    }

    private void expectMasterpage(String s) {
        given()
                .auth().basic("ThePoltergeist", "bouh")
                .header(HttpHeaders.ACCEPT, MediaType.TEXT_HTML)
                .expect()
                .statusCode(200)
                .header(HttpHeaders.CONTENT_TYPE, Matchers.startsWith(MediaType.TEXT_HTML))
                .when()
                .get(s);
    }
}
