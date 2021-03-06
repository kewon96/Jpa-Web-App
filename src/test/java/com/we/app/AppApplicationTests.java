package com.we.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.we.app.member.model.JoinMember;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
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

@ExtendWith(SpringExtension.class)
@SpringBootTest @AutoConfigureMockMvc
class AppApplicationTests {

    @Autowired private WebApplicationContext context;

    @Autowired private MockMvc mockMvc;

    @Autowired ObjectMapper objectMapper;

    @DisplayName("회원가입 처리 - 입력값 오류")
    @Test
    void signUpSubmit_with_wrong_input() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        JoinMember joinMember = JoinMember.builder()
                .memberName("aa")
                .email("aaa@gmail.com")
                .password("aa")
                .build();

        mockMvc.perform(post("/account/createUser")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinMember))
                )
                .andExpect(status().isCreated())
        ;
    }

    @Test
    void encryptor() {
        StandardPBEStringEncryptor pbeEnc = new StandardPBEStringEncryptor();
        pbeEnc.setAlgorithm("PBEWithMD5AndDES");
        pbeEnc.setPassword("kewon"); //2번 설정의 암호화 키를 입력

        String enc = pbeEnc.encrypt("gtrqcwfgflwogndq"); //암호화 할 내용
        System.out.println("enc = " + enc); //암호화 한 내용을 출력

        //테스트용 복호화
        String des = pbeEnc.decrypt(enc);
        System.out.println("des = " + des);
    }

}
