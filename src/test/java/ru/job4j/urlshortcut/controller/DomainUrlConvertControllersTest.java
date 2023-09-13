package ru.job4j.urlshortcut.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ru.job4j.urlshortcut.Job4jUrlShortcutApplication;
import ru.job4j.urlshortcut.dto.ConvertUrlDTO;
import ru.job4j.urlshortcut.dto.DomainAuthDTO;
import ru.job4j.urlshortcut.dto.DomainNameDTO;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = Job4jUrlShortcutApplication.class)
@TestPropertySource(locations = "classpath:application-test.properties")
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class DomainUrlConvertControllersTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    private final String url = "https://job4j.ru/profile/exercise/106/task-view/532";

    private static String login;
    private static String password;
    private static String token;
    private static String code;

    @Test
    @Order(1)
    public void whenSendDomainNameThenGetRegDataAndStatusCreated() throws Exception {
        DomainNameDTO request = new DomainNameDTO();
        request.setSite("job4j.ru");
        MvcResult result = mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("registration").value(true))
                .andExpect(jsonPath("login").isNotEmpty())
                .andExpect(jsonPath("password").isNotEmpty())
                .andReturn();
        login = JsonPath.read(result.getResponse().getContentAsString(), "$.login");
        password = JsonPath.read(result.getResponse().getContentAsString(), "$.password");
    }

    @Test
    @Order(2)
    public void whenSendEmptyDomainNameThenGetStatusBadRequest() throws Exception {
        DomainNameDTO request = new DomainNameDTO();
        mockMvc.perform(post("/registration")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    @Order(3)
    public void whenSendAuthDataThenGetTokenAndStatusOk() throws Exception {
        DomainAuthDTO authData = new DomainAuthDTO();
        authData.setLogin(login);
        authData.setPassword(password);
        MvcResult result = mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authData)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
        token = result.getResponse().getHeader("Authorization");
    }

    @Test
    @Order(4)
    public void whenSendEmptyAuthDataThenGetStatusUnauthorized() throws Exception {
        DomainAuthDTO authData = new DomainAuthDTO();
        mockMvc.perform(post("/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(authData)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    @Order(5)
    public void whenSendUrlWithTokenThenGetShortCodeAndStatusOk() throws Exception {
        ConvertUrlDTO urlDTO = new ConvertUrlDTO();
        urlDTO.setUrl(url);
        MvcResult result = mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("Authorization", "Bearer " + token)
                        .content(objectMapper.writeValueAsString(urlDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("code").isNotEmpty())
                .andReturn();
        code = JsonPath.read(result.getResponse().getContentAsString(), "$.code");
    }

    @Test
    @Order(6)
    public void whenSendUrlWithoutTokenThenGetStatusForbidden() throws Exception {
        ConvertUrlDTO urlDTO = new ConvertUrlDTO();
        urlDTO.setUrl(url);
        mockMvc.perform(post("/convert")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(urlDTO)))
                .andDo(print())
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    @Order(7)
    public void whenSendShortCodeThenRedirectToUrlAndGetStatusFound() throws Exception {
        mockMvc.perform(get("/redirect/" + code))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(header().stringValues("Location", url));
    }

    @Test
    @Order(8)
    public void whenSendEmptyShortCodeThenGetStatusNotFound() throws Exception {
        mockMvc.perform(get("/redirect/"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @Order(9)
    public void whenAskStatisticWithTokenThenGetCallingInfoData() throws Exception {
        mockMvc.perform(get("/statistic")
                .header("Authorization", "Bearer " + token))
                .andDo(print())
                .andExpect(jsonPath("$[0].url").value(url))
                .andExpect(jsonPath("$[0].total").value(1));
    }

    @Test
    @Order(10)
    public void whenAskStatisticWithoutTokenThenGetStatusForbidden() throws Exception {
        mockMvc.perform(get("/statistic"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }
}
