package de.dofusdu;

import io.quarkus.test.junit.QuarkusTest;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class OfferingResourceTest {
/*
    private static OfferingDTO toDto(CreateOfferingDTO createOfferingDTO) {
        OfferingDTO offeringDTO = new OfferingDTO();
        offeringDTO.itemPicture = createOfferingDTO.itemPicture;
        offeringDTO.date = createOfferingDTO.date;
        offeringDTO.bonus = createOfferingDTO.bonus;
        offeringDTO.item = createOfferingDTO.item;
        offeringDTO.bonusType = createOfferingDTO.bonusType;
        offeringDTO.itemQuantity = createOfferingDTO.itemQuantity;
        return offeringDTO;
    }

    private static CreateOfferingDTO firstDay() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "en";
        createOfferingDTO.bonus = "englishbonusfight this thing";
        createOfferingDTO.bonusType = "enFighting";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "enWooden Sword";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "enpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO firstDayDe() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "de";
        createOfferingDTO.bonus = "german bonus kaempfeß das da";
        createOfferingDTO.bonusType = "deKämpfen";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "deHölzernes Schwert";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "depic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO firstDayFr() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "fr";
        createOfferingDTO.bonus = "frenchbœnusfeeEe@åîî";
        createOfferingDTO.bonusType = "frblaasawr";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "frsword holziggcke";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "frpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO firstDayPt() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "pt";
        createOfferingDTO.bonus = "ptbonus feeEe@åããí";
        createOfferingDTO.bonusType = "pt blaasawr";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "pt swordkueste";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "ptpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO firstDayEs() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "es";
        createOfferingDTO.bonus = "esbonus feeEe@å";
        createOfferingDTO.bonusType = "es blaasawr";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "es swordkueste";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "espic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO firstDayIt() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "it";
        createOfferingDTO.bonus = "itbonus feeEe@å";
        createOfferingDTO.bonusType = "it blaasawr";
        createOfferingDTO.date = LocalDate.now().toString();
        createOfferingDTO.item = "it swordkueste";
        createOfferingDTO.itemQuantity = 2;
        createOfferingDTO.itemPicture = "itpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO secondDay() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "en";
        createOfferingDTO.bonus = "Dies und das";
        createOfferingDTO.bonusType = "Quests and stuff";
        createOfferingDTO.date = LocalDate.now().plusDays(1).toString();
        createOfferingDTO.item = "enWooden Sword"; // same for test
        createOfferingDTO.itemQuantity = 3;
        createOfferingDTO.itemPicture = "enpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO thirdDay() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "en";
        createOfferingDTO.bonus = "hlrkzs";
        createOfferingDTO.bonusType = "Blub und";
        createOfferingDTO.date = LocalDate.now().plusDays(2).toString();
        createOfferingDTO.item = "Zeugs";
        createOfferingDTO.itemQuantity = 64;
        createOfferingDTO.itemPicture = "thirdpic";
        return createOfferingDTO;
    }
    private static CreateOfferingDTO forthDay() {
        CreateOfferingDTO createOfferingDTO = new CreateOfferingDTO();
        createOfferingDTO.clientSecret = "19fefac5-2065-44a1-b7bf-eaedec00f651";
        createOfferingDTO.language = "en";
        createOfferingDTO.bonus = "Dies und das";
        createOfferingDTO.bonusType = "Fighting";
        createOfferingDTO.date = LocalDate.now().plusDays(3).toString();
        createOfferingDTO.item = "Arme";
        createOfferingDTO.itemQuantity = 4;
        createOfferingDTO.itemPicture = "forpic";
        return createOfferingDTO;
    }

    private void createDummyData() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDay())
                .post("/almanax")
                .then()
                .statusCode(200);
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(secondDay())
                .post("/almanax")
                .then()
                .statusCode(200);
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(thirdDay())
                .post("/almanax")
                .then()
                .statusCode(200);
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(forthDay())
                .post("/almanax")
                .then()
                .statusCode(200);
    }

    private void AssertEqualOfferingDTO(OfferingDTO shouldBe, OfferingDTO is) {
        Assertions.assertEquals(shouldBe.itemQuantity, is.itemQuantity);
        Assertions.assertEquals(shouldBe.date, is.date);
        Assertions.assertEquals(shouldBe.item, is.item);
        Assertions.assertEquals(shouldBe.bonus, is.bonus);
        Assertions.assertEquals(shouldBe.itemPicture, is.itemPicture);
        Assertions.assertEquals(shouldBe.bonusType, is.bonusType);
    }

    private void AssertEqualOfferingDTOOtherLanguage(OfferingDTO shouldBe, OfferingDTO is, OfferingDTO en) {
        Assertions.assertEquals(en.itemQuantity, is.itemQuantity); // quantity never changes
        Assertions.assertEquals(shouldBe.itemQuantity, en.itemQuantity); // never change between languages

        Assertions.assertEquals(shouldBe.date, is.date);
        Assertions.assertEquals(shouldBe.item, is.item);
        Assertions.assertEquals(shouldBe.bonus, is.bonus);
        Assertions.assertEquals(en.itemPicture, is.itemPicture); // picturelink does not change with language, stays english
        Assertions.assertEquals(shouldBe.bonusType, is.bonusType);
    }


    private void AssertEqualOffering(SingleOfferingResponse shouldBe, SingleOfferingResponse is) {
        Assertions.assertEquals(shouldBe.language, is.language);
        Assertions.assertEquals(shouldBe.version, is.version);
        AssertEqualOfferingDTO(shouldBe.data, is.data);
    }

    private void AssertEqualOfferingOtherLang(SingleOfferingResponse shouldBe, SingleOfferingResponse is, OfferingDTO en) {
        Assertions.assertEquals(shouldBe.language, is.language);
        Assertions.assertEquals(shouldBe.version, is.version);
        AssertEqualOfferingDTOOtherLanguage(shouldBe.data, is.data, en);
    }

    @Test
    public void createToday() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDay())
                .post("/almanax")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingCreatedResponse.class);
    }

    @Test
    public void createDayPlus1() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(secondDay())
                .post("/almanax")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingCreatedResponse.class);
    }

    @Test
    public void createDayPlus2() {
        given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(thirdDay())
                .post("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingCreatedResponse.class);
    }


    @Test
    public void getDay() {
        createToday();
        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/en/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDay());

        AssertEqualOffering(shouldBe, is);
    }


    @Test
    public void addTodayDe() {
        createToday();
        OfferingUpdatedResponse up = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDayDe())
                .put("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingUpdatedResponse.class);

        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/de/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "de";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDayDe());

        AssertEqualOfferingOtherLang(shouldBe, is, toDto(firstDay()));
    }

    @Test
    public void addTodayDeFr() {
        addTodayDe();
        OfferingUpdatedResponse up = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDayFr())
                .put("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingUpdatedResponse.class);

        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/fr/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "fr";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDayFr());

        AssertEqualOfferingOtherLang(shouldBe, is, toDto(firstDay()));
    }

    @Test
    public void addTodayDeFrPt() {
        addTodayDeFr();
        OfferingUpdatedResponse up = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDayPt())
                .put("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingUpdatedResponse.class);

        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/pt/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "pt";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDayPt());

        AssertEqualOfferingOtherLang(shouldBe, is, toDto(firstDay()));
    }

    @Test
    public void addTodayDeFrPtEs() {
        addTodayDeFrPt();
        OfferingUpdatedResponse up = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDayEs())
                .put("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingUpdatedResponse.class);

        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/es/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "es";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDayEs());

        AssertEqualOfferingOtherLang(shouldBe, is, toDto(firstDay()));
    }

    @Test
    public void addTodayDeFrPtEsIt() {
        addTodayDeFrPtEs();
        OfferingUpdatedResponse up = given()
                .when()
                .contentType(MediaType.APPLICATION_JSON)
                .body(firstDayIt())
                .put("/almanax/")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingUpdatedResponse.class);

        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/it/" + LocalDate.now().toString())
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "it";
        shouldBe.version = 1;
        shouldBe.data = toDto(firstDayIt());

        AssertEqualOfferingOtherLang(shouldBe, is, toDto(firstDay()));
    }

    @Test
    public void getNextBonusWithTypeTest() {
        addTodayDeFr();
        createDayPlus1();
        createDayPlus2();
        SingleOfferingResponse is = given()
                .when()
                .get("/almanax/en/bonus/blub-und/next")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(SingleOfferingResponse.class);

        SingleOfferingResponse shouldBe = new SingleOfferingResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = toDto(thirdDay());

        AssertEqualOffering(shouldBe, is);
    }

    @Test
    public void daysAheadRawTest() {
        addTodayDeFr();
        createDayPlus1();
        createDayPlus2();
        OfferingResponse is = given()
                .when()
                .get("/almanax/en/ahead/1")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingResponse.class);

        OfferingResponse shouldBe = new OfferingResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = new ArrayList<>();
        shouldBe.data.add(toDto(firstDay()));
        shouldBe.data.add(toDto(secondDay()));

        Assertions.assertEquals(2, is.data.size());
        AssertEqualOfferingDTO(shouldBe.data.get(0), is.data.get(0));
        AssertEqualOfferingDTO(shouldBe.data.get(1), is.data.get(1));
    }

    @Test
    public void daysAheadBonusFilterTest() {
        addTodayDeFr();
        createDayPlus1();
        createDayPlus2();
        String bonusType = secondDay().bonusType.toLowerCase().replace(' ', '-');
        OfferingResponse is = given()
                .when()
                .get("/almanax/en/ahead/1/bonus/" + bonusType)
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(OfferingResponse.class);

        OfferingResponse shouldBe = new OfferingResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = new ArrayList<>();
        shouldBe.data.add(toDto(secondDay()));

        Assertions.assertEquals(1, is.data.size());
        AssertEqualOfferingDTO(shouldBe.data.get(0), is.data.get(0));
    }

    @Test
    public void daysAheadItemsTestSame() {
        addTodayDeFr();
        createDayPlus1();
        createDayPlus2();
        String bonusType = secondDay().bonusType.toLowerCase().replace(' ', '-');
        ItemsResponse is = given()
                .when()
                .get("/almanax/en/ahead/1/items")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(ItemsResponse.class);

        ItemsResponse shouldBe = new ItemsResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = new ArrayList<>();
        ItemPositionDTO itemPositionDTO1 = new ItemPositionDTO();
        itemPositionDTO1.name = firstDay().item;
        itemPositionDTO1.quantity = firstDay().itemQuantity + secondDay().itemQuantity;
        shouldBe.data.add(itemPositionDTO1);

        Assertions.assertEquals(1, is.data.size());
        Assertions.assertEquals(shouldBe.data.get(0).quantity, is.data.get(0).quantity);
        Assertions.assertEquals(shouldBe.data.get(0).name, is.data.get(0).name);
    }

    @Test
    public void daysAheadItemsTestDiff() {
        addTodayDeFr();
        createDayPlus1();
        createDayPlus2();
        ItemsResponse is = given()
                .when()
                .get("/almanax/en/ahead/2/items")
                .then()
                .contentType(MediaType.APPLICATION_JSON)
                .statusCode(200)
                .extract()
                .as(ItemsResponse.class);

        ItemsResponse shouldBe = new ItemsResponse();
        shouldBe.language = "en";
        shouldBe.version = 1;
        shouldBe.data = new ArrayList<>();
        ItemPositionDTO itemPositionDTO1 = new ItemPositionDTO();
        itemPositionDTO1.name = firstDay().item;
        itemPositionDTO1.quantity = firstDay().itemQuantity + secondDay().itemQuantity;

        ItemPositionDTO itemPositionDTO2 = new ItemPositionDTO();
        itemPositionDTO2.name = thirdDay().item;
        itemPositionDTO2.quantity = thirdDay().itemQuantity;

        shouldBe.data.add(itemPositionDTO2); // map sorts it, so it changed
        shouldBe.data.add(itemPositionDTO1);

        Assertions.assertEquals(2, is.data.size());
        Assertions.assertEquals(shouldBe.data.get(0).quantity, is.data.get(0).quantity);
        Assertions.assertEquals(shouldBe.data.get(0).name, is.data.get(0).name);
        Assertions.assertEquals(shouldBe.data.get(1).quantity, is.data.get(1).quantity);
        Assertions.assertEquals(shouldBe.data.get(1).name, is.data.get(1).name);
    }
 */
}