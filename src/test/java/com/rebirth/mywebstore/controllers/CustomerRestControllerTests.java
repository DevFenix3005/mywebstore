package com.rebirth.mywebstore.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rebirth.mywebstore.MyWebStoreApplication;
import com.rebirth.mywebstore.services.dtos.AddressSchema;
import com.rebirth.mywebstore.services.dtos.CustomerSchema;
import com.rebirth.mywebstore.services.dtos.ProductSchema;
import com.rebirth.mywebstore.services.dtos.PurchaseOrderSchema;
import com.rebirth.mywebstore.services.utils.OperationsUtilComponent;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = MyWebStoreApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(
        locations = "classpath:application-integrationtest.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CustomerRestControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    OperationsUtilComponent operationsUtilComponent;
    private static Storage STORAGE;

    private static class Storage {
        CustomerSchema.CustomerDto customerDto;
        AddressSchema.AddressDto addressDto;
        PurchaseOrderSchema.PurchaseOrderDto purchaseOrderDto;
        final List<ProductSchema.ProductDto> productDtoList = new ArrayList<>();

        private static volatile Storage INSTANCE;

        private Storage() {
        }

        public static Storage getInstance() {
            if (INSTANCE == null) {
                synchronized (Storage.class) {
                    if (INSTANCE == null) {
                        INSTANCE = new Storage();
                    }
                }
            }
            return INSTANCE;
        }
    }

    @BeforeAll
    static void beforeAll() {
        STORAGE = Storage.getInstance();
    }

    @Test
    @Order(100)
    void createNewUser() throws Exception {
        MvcResult result = mockMvc.perform(post("/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "name": "Roberto",
                                    "lastname": "Cazarin",
                                    "birthday": "1989-05-30",
                                    "email": "robertodmngzczrn@gmail.com"
                                }
                                """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is("Roberto")))
                .andExpect(jsonPath("$.lastname", is("Cazarin")))
                .andExpect(jsonPath("$.birthday", is("1989-05-30")))
                .andExpect(jsonPath("$.email", is("robertodmngzczrn@gmail.com")))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        STORAGE.customerDto = objectMapper.readValue(response, CustomerSchema.CustomerDto.class);

    }

    @Test
    @Order(200)
    void createNewAddress() throws Exception {
        MvcResult result = mockMvc.perform(post("/address")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "street": "27 Middlesex Cir",
                                    "city": "Waltham",
                                    "state": "Massachuset",
                                    "country": "United States",
                                    "zipcode": "02786",
                                    "customerId": %d
                                }
                                    """.formatted(STORAGE.customerDto.getCustomerId()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.street", is("27 Middlesex Cir")))
                .andExpect(jsonPath("$.city", is("Waltham")))
                .andExpect(jsonPath("$.state", is("Massachuset")))
                .andExpect(jsonPath("$.country", is("United States")))
                .andExpect(jsonPath("$.zipcode", is("02786")))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        STORAGE.addressDto = objectMapper.readValue(response, AddressSchema.AddressDto.class);
    }

    @Test
    @Order(300)
    void createNewOrder() throws Exception {
        MvcResult result = mockMvc.perform(post("/purchaseOrder")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                    {
                                        "customerId": %d
                                    }
                                """.formatted(STORAGE.customerDto.getCustomerId()))
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.state", is("CART")))
                .andExpect(jsonPath("$.total", is(0.0)))
                .andExpect(jsonPath("$.generatedPoints", is(0)))
                .andExpect(jsonPath("$.customer.name", is(STORAGE.customerDto.getName())))
                .andReturn();

        String response = result.getResponse().getContentAsString();
        STORAGE.purchaseOrderDto = objectMapper.readValue(response, PurchaseOrderSchema.PurchaseOrderDto.class);
    }

    @Order(400)
    @ParameterizedTest()
    @MethodSource("productJsons")
    void createNewProducts(String json) throws Exception {
        MvcResult result = mockMvc.perform(post("/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(status().isCreated())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        System.out.println(response);
        ProductSchema.ProductDto productDto = objectMapper.readValue(response, ProductSchema.ProductDto.class);
        STORAGE.productDtoList.add(productDto);
    }

    @Order(500)
    @Test()
    void countProducts() {
        Assertions.assertEquals(8, STORAGE.productDtoList.size());
    }

    @Test()
    @Order(600)
    void createNewPurchaseOrders() throws Exception {
        PurchaseOrderSchema.PurchaseOrderDto purchaseOrderDto = STORAGE.purchaseOrderDto;

        Random random = new Random();
        for (ProductSchema.ProductDto productDto : STORAGE.productDtoList) {
            MvcResult result = mockMvc.perform(post("/purchaseOrderProduct")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {
                                        "orderId": %d,
                                        "productId": %d,
                                        "quantity": %d
                                    }
                                    """.formatted(purchaseOrderDto.getPurchaseOrderId(), productDto.getProductId(), random.nextInt(2, 5)))
                    )
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.productName", is(productDto.getName())))
                    .andReturn();
            MockHttpServletResponse response = result.getResponse();
            System.out.println(response.getContentAsString());
        }
    }

    @Test()
    @Order(700)
    void updatePurchaseOrderToGenerateAwardPoints() throws Exception {
        PurchaseOrderSchema.PurchaseOrderDto purchaseOrderDto = STORAGE.purchaseOrderDto;

        MvcResult result = mockMvc.perform(put("/purchaseOrder/" + purchaseOrderDto.getPurchaseOrderId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "state": "PURCHASED",
                                    "addressId": %d
                                }
                                """.formatted(STORAGE.addressDto.getAddressId()))
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        PurchaseOrderSchema.PurchaseOrderDto purchaseOrderDtoUp = objectMapper.readValue(response.getContentAsString(), PurchaseOrderSchema.PurchaseOrderDto.class);

        Float testTotal = 0.0f;
        for (ProductSchema.ProductDto product : purchaseOrderDtoUp.getProducts()) {
            testTotal += (product.getQuantity() * product.getPrice());
        }
        int testAwardsPoints = operationsUtilComponent.calculateAwardsPoints(testTotal.intValue());

        Float total = purchaseOrderDtoUp.getTotal();
        int awardsPoints = purchaseOrderDtoUp.getGeneratedPoints();

        Assertions.assertEquals(total, testTotal);
        Assertions.assertEquals(awardsPoints, testAwardsPoints);

        STORAGE.purchaseOrderDto = purchaseOrderDtoUp;
    }


    @Test()
    @Order(800)
    void getAwardsPointsByUser() throws Exception {
        CustomerSchema.CustomerDto customerDto = STORAGE.customerDto;
        PurchaseOrderSchema.PurchaseOrderDto purchaseOrderDto = STORAGE.purchaseOrderDto;
        MvcResult result = mockMvc.perform(get("/customer/%d/info?month=JANUARY".formatted(customerDto.getCustomerId()))
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andReturn();
        MockHttpServletResponse response = result.getResponse();
        CustomerSchema.CustomerInfo customerInfo = objectMapper.readValue(response.getContentAsString(), CustomerSchema.CustomerInfo.class);
        Assertions.assertEquals(purchaseOrderDto.getGeneratedPoints(), customerInfo.getTotalAwardPoints().intValue());
    }

    public static Stream<Arguments> productJsons() {
        return Stream.of(
                Arguments.arguments("""
                        {
                            "name": "Towels",
                            "price": 159.89,
                            "description": "If we reboot the card, we can get to the SAS sensor through the neural CSS sensor!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Soap",
                            "price": 678.47,
                            "description": "I'll override the 1080p XML feed, that should array the SCSI microchip!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Salad",
                            "price": 666.46,
                            "description": "Try to parse the JBOD bus, maybe it will quantify the solid state card!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Computer",
                            "price": 646.14,
                            "description": "If we quantify the array, we can get to the SMS firewall through the cross-platform SCSI matrix!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Car",
                            "price": 850.51,
                            "description": "I'll navigate the wireless EXE protocol, that should array the CSS bandwidth!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Cheese",
                            "price": 449.40,
                            "description": "quantifying the bandwidth won't do anything, we need to parse the multi-byte USB card!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Tuna",
                            "price": 975.87,
                            "description": "You can't index the port without overriding the haptic AGP alarm!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """),
                Arguments.arguments("""
                        {
                            "name": "Bike",
                            "price": 318.92,
                            "description": "If we compress the application, we can get to the SMS array through the virtual JBOD pixel!",
                            "image": "http://placeimg.com/640/480"
                        }
                        """)
        );
    }


}
