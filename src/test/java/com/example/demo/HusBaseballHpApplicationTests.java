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
    @Autowired com.example.jyunko.service.PhotoStorageService photoStorage;
    @Autowired com.example.jyunko.repository.StoredPhotoRepository photos;

    @Test
    void photoIsStoredInDatabaseAndServedWithoutLogin() throws Exception {
        var output = new java.io.ByteArrayOutputStream();
        javax.imageio.ImageIO.write(new java.awt.image.BufferedImage(2, 2,
            java.awt.image.BufferedImage.TYPE_INT_RGB), "png", output);
        byte[] bytes = output.toByteArray();
        String url = photoStorage.save(new org.springframework.mock.web.MockMultipartFile(
            "photo", "image.png", "image/png", bytes));
        assertThat(photos.findById(url.substring("/photos/".length()))).isPresent();
        mvc.perform(get(url)).andExpect(status().isOk())
            .andExpect(content().contentType("image/png"))
            .andExpect(content().bytes(bytes))
            .andExpect(header().string("X-Content-Type-Options", "nosniff"));
        mvc.perform(get("/photos/missing")).andExpect(status().isNotFound());
    }

    @Test
    void nonImagesAndOversizedUploadsAreNotStored() {
        long before = photos.count();
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> photoStorage.save(
            new org.springframework.mock.web.MockMultipartFile("photo", "fake.png", "image/png",
                "<script>alert(1)</script>".getBytes(java.nio.charset.StandardCharsets.UTF_8))))
            .isInstanceOf(java.io.IOException.class);
        org.assertj.core.api.Assertions.assertThatThrownBy(() -> photoStorage.save(
            new org.springframework.mock.web.MockMultipartFile("photo", "large.png", "image/png",
                new byte[4 * 1024 * 1024 + 1])))
            .isInstanceOf(java.io.IOException.class);
        assertThat(photos.count()).isEqualTo(before);
    }

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
