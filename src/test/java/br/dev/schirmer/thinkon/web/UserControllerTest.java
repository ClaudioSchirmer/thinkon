package br.dev.schirmer.thinkon.web;

import br.dev.schirmer.thinkon.application.commands.InsertUserCommand;
import br.dev.schirmer.thinkon.web.dtos.UserRequestDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

// This contains only a few test cases and covers only the web layer.
// The most complete process would involve having extensive test cases for the web layer, along with thorough tests for the domain, infrastructure, and application layers.
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static UUID userId;

    @Test
    @Order(1)
    public void testInsertUser() throws Exception {
        InsertUserCommand insertUserCommand = new InsertUserCommand(
                "Claudio",
                "Schirmer",
                "email@icloud.com",
                "+15191234567"
        );
        MvcResult result = mockMvc.perform(
                        post("/users")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(insertUserCommand)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.id").exists())
                .andReturn();


        String contentAsString = result.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        userId = UUID.fromString(jsonNode.get("data").get("id").asText());
    }

    @Test
    @Order(2)
    public void testUpdateUser() throws Exception {
        UserRequestDTO userRequestDTO = new UserRequestDTO(
                null,
                null,
                "email@gmail.com",
                null
        );
        mockMvc.perform(
                        put("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(userRequestDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Claudio"))
                .andExpect(jsonPath("$.data.lastName").value("Schirmer"))
                .andExpect(jsonPath("$.data.phoneNumber").value("+15191234567"))
                .andExpect(jsonPath("$.data.id").value(userId.toString()));
    }

    @Test
    @Order(3)
    public void testGetUser() throws Exception {
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.email").value("email@gmail.com"))
                .andExpect(jsonPath("$.data.firstName").value("Claudio"))
                .andExpect(jsonPath("$.data.lastName").value("Schirmer"))
                .andExpect(jsonPath("$.data.phoneNumber").value("+15191234567"))
                .andExpect(jsonPath("$.data.id").value(userId.toString()));
    }

    @Test
    @Order(4)
    public void testGetAllUsers() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data").exists())
                .andExpect(jsonPath("$.data.users", hasSize(greaterThan(0))));
    }

    @Test
    @Order(5)
    public void testDeleteUser() throws Exception {
        mockMvc.perform(
                        delete("/users/" + userId)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(6)
    public void testGetUserAfterDelete() throws Exception {
        mockMvc.perform(get("/users/" + userId))
                .andExpect(status().isNotFound());
    }
}
