import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


public class TestesProvaTecnicaApi {

    @BeforeClass
    public static void setup() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        baseURI = "http://localhost:8080";
        basePath = "/api/v1";
    }

    // SESSÃO DE TESTES REFERENTE À RESTRIÇÕES NO CPF

    @Test public void testDadoConsultaCpfQuandoPossuiRestricaoEntaoObtenhoStatusCode200(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restricoes/97093236014")
        .then()
                .statusCode(200)
                .body("mensagem", Matchers.is("O CPF 97093236014 tem problema"));

    }

    @Test public void testDadoConsultaCpfQuandoNaoPossuiRestricaoEntaoObtenhoStatusCode204(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/restricoes/05672626343")
        .then()
                .statusCode(204)
                .body(isEmptyOrNullString());

    }

    // SESSÃO DE TESTES REFERENTE À SIMULAÇÕES E AÇÕES CORRELATAS
    @Test public void testConsultarTodasAsSimulacoes(){

        when()
                .get("/simulacoes")
        .then()
                .statusCode(200);

    }

    @Test public void testDadoConsultarSimulacaoPorCpfQuandoExistenteEntaoObtenhoStatusCode200(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/simulacoes/66414919004")
        .then()
                .statusCode(200)
                .body("cpf", Matchers.is("66414919004"));

    }

    @Test public void testDadoConsultarSimulacaoQuandoNaoExistenteEntaoObtenhoStatusCode404(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .get("/simulacoes/66414919027")
        .then()
                .statusCode(404)
                .body("mensagem", Matchers.is("CPF 66414919027 não encontrado"));

    }
    @Test public void testDadoCriarNovaSimulacaoQuandoComSucessoEntaoObtenhaStatusCode201(){

        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "        \"id\": 12,\n" +
                        "        \"nome\": \"Gilberto\",\n" +
                        "        \"cpf\": \"05672626367\",\n" +
                        "        \"email\": \"gilbertosouza@gmail.com\",\n" +
                        "        \"valor\": 30000.00,\n" +
                        "        \"parcelas\": 7,\n" +
                        "        \"seguro\": true\n" +
                        "    }")
        .when()
                .post("/simulacoes")
        .then()
                .statusCode(201)
                .body("nome", Matchers.is("Gilberto"));

    }

    @Test public void testDadoAlterarSimulacaoQuandoComSucessoEntaoObtenhaStatusCode200(){

        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "        \"id\": 11,\n" +
                        "        \"nome\": \"FulanoDeTal\",\n" +
                        "        \"cpf\": \"66414919004\",\n" +
                        "        \"email\": \"fulano@gmail.com\",\n" +
                        "        \"valor\": 11000.00,\n" +
                        "        \"parcelas\": 3,\n" +
                        "        \"seguro\": true\n" +
                        "    }")
        .when()
                .put("/simulacoes/66414919004")
        .then()
                .statusCode(200)
                .body("nome", Matchers.is("FulanoDeTal"));;

    }

    @Test public void testDadoAlterarSimulacaoQuandoNaoExistenteEntaoObtenhaStatusCode404(){

        given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "        \"id\": 11,\n" +
                        "        \"nome\": \"FulanoDeTal\",\n" +
                        "        \"cpf\": \"66414919004\",\n" +
                        "        \"email\": \"fulano@gmail.com\",\n" +
                        "        \"valor\": 11000.00,\n" +
                        "        \"parcelas\": 3,\n" +
                        "        \"seguro\": true\n" +
                        "    }")
        .when()
                .put("/simulacoes/05672626343")
        .then()
                .statusCode(404)
                .body("mensagem", Matchers.is("CPF 05672626343 não encontrado"));

    }

    @Test public void testDadoRemoverSimulacaoQuandoComSucessoEntaoObtenhaStatusCode200(){

        given()
                .contentType(ContentType.JSON)
        .when()
                .delete("/simulacoes/12")
        .then()
                .statusCode(200);

    }

}
