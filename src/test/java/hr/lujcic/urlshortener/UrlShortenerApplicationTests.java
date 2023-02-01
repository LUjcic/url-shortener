package hr.lujcic.urlshortener;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@ActiveProfiles(profiles = "test")
@AutoConfigureMockMvc
@EnableAutoConfiguration(exclude = { SecurityAutoConfiguration.class })
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UrlShortenerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Order(1)
    public void test_post_to_account_ok() throws Exception {
        mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON).content("{" +
            "\"AccountId\": \"test\"" +
            "}")).andExpect(status().isOk())
            .andExpect(jsonPath("$.success", Matchers.equalTo(true)))
            .andExpect(jsonPath("$.password", Matchers.hasLength(8)))
            .andExpect(jsonPath("$.description", Matchers.equalTo("Account with id test opened")));
    }

    @Test
    @Order(2)
    public void test_post_to_account_bad_request() throws Exception {
        mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON).content("{" +
                "\"wrongJson\": \"test\"" +
                "}")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(3)
    public void test_post_to_account_id_exists() throws Exception {
        mockMvc.perform(post("/account").contentType(MediaType.APPLICATION_JSON).content("{" +
            "\"AccountId\": \"test\"" +
            "}")).andExpect(status().isBadRequest())
            .andExpect(jsonPath("$.success", Matchers.equalTo(false)))
            .andExpect(jsonPath("$.description", Matchers.equalTo("Account with given id already exists!")));
    }

    @Test
    @Order(4)
    @WithMockUser(username = "test", roles = "USER")
    public void test_post_to_register_ok() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content("{" +
            "\"url\": \"https://google.com\",\n" +
            "\"redirectType\": 301" +
            "}")).andExpect(status().isOk())
            .andExpect(jsonPath("$.shortUrl", Matchers.hasLength("http://localhost:8080/short/".length() + 6)));
    }

    @Test
    @Order(5)
    public void test_post_to_register_no_authentication() throws Exception {
        mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("{" +
                "\"url\": \"https://google.com\"" +
                "}")).andExpect(status().is3xxRedirection());
    }

    @Test
    @Order(6)
    @WithMockUser(username = "test", roles = "USER")
    public void test_post_to_register_bad_request() throws Exception {
        mockMvc.perform(post("/register")
            .contentType(MediaType.APPLICATION_JSON).content("{" +
                "\"wrong\": \"https://bad-request\"" +
                "}")).andExpect(status().isBadRequest());
    }

    @Test
    @Order(7)
    @WithMockUser(username = "test", roles = "USER")
    public void test_post_to_register_no_status_code_ok() throws Exception {
        mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content("{" +
                "\"url\": \"https://youtube.com\"" +
                "}")).andExpect(status().isOk())
            .andExpect(jsonPath("$.shortUrl", Matchers.hasLength("http://localhost:8080/short/".length() + 6)));
    }

    @Test
    @Order(8)
    @WithMockUser(username = "test", roles = "USER")
    public void test_get_short_url_ok() throws Exception {
         String result = mockMvc.perform(post("/register").contentType(MediaType.APPLICATION_JSON).content("{" +
                "\"url\": \"https://google.hr\"" +
                "}")).andExpect(status().isOk())
            .andReturn().getResponse().getContentAsString();
        mockMvc.perform(get(result.substring(13, 47))).andExpect(status().is3xxRedirection());
    }

    @Test
    @Order(9)
    @WithMockUser(username = "test", roles = "USER")
    public void test_get_to_statistics_ok() throws Exception {
        mockMvc.perform(get("/statistic/test"))
            .andExpect(status().isOk());
    }

    @Test
    @Order(10)
    public void test_get_to_statistics_unauthorized() throws Exception {
        mockMvc.perform(get("/statistic/test"))
            .andExpect(status().is3xxRedirection());
    }

    @Test
    @Order(11)
    public void test_get_to_help() throws Exception {
        mockMvc.perform(get("/help"))
            .andExpect(status().isOk());
    }
}
