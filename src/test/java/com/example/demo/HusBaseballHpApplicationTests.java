package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import com.example.jyunko.repository.InquiryRepository;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = com.example.jyunko.HusBaseballHpApplication.class, properties = {
    "spring.datasource.url=jdbc:h2:mem:baseball;MODE=MySQL;DB_CLOSE_DELAY=-1",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.jpa.hibernate.ddl-auto=create-drop"
})
@AutoConfigureMockMvc
class HusBaseballHpApplicationTests {
    @Autowired MockMvc mvc;
    @Autowired InquiryRepository inquiries;

    @Test
    void publicPagesRender() throws Exception {
        for (String path : new String[]{"/", "/about", "/news", "/members", "/matches", "/inquiry", "/login"}) {
            mvc.perform(get(path)).andExpect(status().isOk());
        }
    }

    @Test
    void adminRequiresLogin() throws Exception {
        mvc.perform(get("/admin/inquiries")).andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void inquiryIsSavedAndVisibleToAdministrator() throws Exception {
        mvc.perform(post("/inquiry").with(csrf()).param("name", "Test Visitor")
            .param("email", "test@example.com").param("subject", "Test inquiry")
            .param("content", "Deployment test"))
            .andExpect(status().is3xxRedirection()).andExpect(redirectedUrl("/inquiry"));
        assertThat(inquiries.findAll()).anySatisfy(inquiry -> {
            assertThat(inquiry.getSubject()).isEqualTo("Test inquiry");
            assertThat(inquiry.getStatus()).isZero();
        });
        mvc.perform(get("/admin/inquiries").with(user("admin").roles("ADMIN")))
            .andExpect(status().isOk());
    }

    @Test
    void inquiryWithoutCsrfIsRejected() throws Exception {
        mvc.perform(post("/inquiry")).andExpect(status().isForbidden());
    }
}
