package com.we.app.account;

import com.we.app.account.model.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
@RequiredArgsConstructor // final로 되있는 인스턴스만 따로 묶어서 생성자를 만들어줌
public class SignUpFormValidator implements Validator {

    private final AccountRepository accountRepository;
    

    @Override
    public boolean supports(Class<?> aClass) {
        // 어떤 타입의 인스턴스를 검증할 것인지
        return aClass.isAssignableFrom(SignUpForm.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        // email, username이 중복되는지 검사
        SignUpForm signUpForm = (SignUpForm) target;
        if(accountRepository.existsByEmail(signUpForm.getEmail())) {
            errors.rejectValue("email", "invalid email", new Object[]{signUpForm.getEmail()}, "중복된 이메일입니다.");
        }

        if(accountRepository.existsByUsername(signUpForm.getEmail())) {
            errors.rejectValue("username", "invalid username", new Object[]{signUpForm.getUsername()}, "중복된 이름입니다.");
        }
    }
}
