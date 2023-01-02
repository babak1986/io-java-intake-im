package com.babak.iojavaintake;

import org.json.JSONObject;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("io-java-intake-test")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class IoJavaIntakeApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    String token;

    @Test
    void contextLoads() {
    }

    @Test
    @BeforeEach
    void login() throws Exception {
        token = mockMvc.perform(post("/user/login")
                .content(
                        new JSONObject()
                                .put("username", "test")
                                .put("password", "test")
                                .toString()
                )
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString(StandardCharsets.UTF_8);
        System.out.println(token);
    }

    @Test
    @Order(2)
    void create() throws Exception {
        mockMvc.perform(post("/tedtalk/v1/create")
                .content(
                        new JSONObject()
                                .put("title", "Test mock")
                                .put("author", "mockito")
                                .put("link", "http://ssssss")
                                .toString()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test mock"));
    }

    @Test
    @Order(3)
    void update() throws Exception {
        mockMvc.perform(post("/tedtalk/v1/update")
                .content(
                        new JSONObject()
                                .put("id", "3")
                                .put("title", "Test mock")
                                .put("author", "mockito")
                                .put("link", "http://ssssss")
                                .toString()
                )
                .contentType(MediaType.APPLICATION_JSON)
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Test mock"));
    }

    @Test
    @Order(4)
    void delete() throws Exception {
        mockMvc.perform(post("/tedtalk/v1/delete/8")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @Order(5)
    void search() throws Exception {
        mockMvc.perform(get("/tedtalk/v1/search?title=Tec")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(status().isOk());
    }

}
