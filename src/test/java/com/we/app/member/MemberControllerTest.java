package com.we.app.member;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.app.member.model.JoinMember;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class MemberControllerTest {

    @Autowired private MockMvc mockMvc;
    @Autowired private WebApplicationContext context;
    @Autowired private ObjectMapper objectMapper;

    @DisplayName("회원가입 시 비밀번호 인코딩 테스트")
    @Test
    void create_member_encoding_check() throws Exception {
        this.mockMvc = getMockMvc();

        JoinMember joinMember = JoinMember.builder()
                .username("aa")
                .email("aaa@gmail.com")
                .password("aa")
                .build();

        mockMvc.perform(post("/account/signup/submit")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinMember))
                )
                .andExpect(status().isCreated())
        ;
    }

    private MockMvc getMockMvc() {
        return MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();
    }

}