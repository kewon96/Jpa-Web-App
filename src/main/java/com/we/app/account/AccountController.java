package com.we.app.account;

import com.we.app.account.model.SignUpForm;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.Errors;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final SignUpFormValidator signUpFormValidator;

    @InitBinder("signUpForm") // CamelCase를 따라감
    public void initBinder(WebDataBinder webDataBinder) {
        // validator추가
        webDataBinder.addValidators(signUpFormValidator);
    }

    @PostMapping("/signup/submit")
    public int signUpSubmit(@RequestBody @Valid SignUpForm signUpForm, Errors errors) {
        if(errors.hasErrors()) {
            return 0;
        }

        /* 아래의 코드를 나름 줄이는 방법으로 InitBinder를 사용한다.(정확하겐 적절한 위치로 조정해주는 것)
        signUpFormValidator.validate(signUpForm, errors);
        // 위의 validate에서 충족하지 않는다면 errors에 rejectValue가 추가되어 아래의 hasErrors()에 걸리게 된다.
        if(errors.hasErrors()) {
            return 0;
        }
        */


        return 1;
    }

}
