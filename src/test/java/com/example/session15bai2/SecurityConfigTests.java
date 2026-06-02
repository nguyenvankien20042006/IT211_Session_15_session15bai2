package com.example.session15bai2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class SecurityConfigTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void loginEndpointIsPublic() throws Exception {
        mockMvc.perform(post("/api/auth/login"))
                .andExpect(status().isNotFound());
    }

    @Test
    void registerEndpointIsPublic() throws Exception {
        mockMvc.perform(post("/api/auth/register"))
                .andExpect(status().isNotFound());
    }

    @Test
    void apiEndpointRequiresAuthentication() throws Exception {
        mockMvc.perform(get("/api/customers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void apiPostWithoutCsrfTokenIsNotRejectedByCsrf() throws Exception {
        mockMvc.perform(post("/api/customers"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void authenticatedApiRequestPassesSecurityLayer() throws Exception {
        mockMvc.perform(get("/api/customers").with(user("mobile-user")))
                .andExpect(status().isNotFound());
    }
}
