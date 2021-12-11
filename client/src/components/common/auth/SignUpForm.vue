<template>
  <form action="#" class="sign-up-form" @submit.prevent="signUpUser">
    <h2 class="title">Sign up</h2>
    <div class="input-field">
      <i class="fas fa-user"></i>
      <input type="text" v-model="joinMember.username" @blur="validateUsername" placeholder="Username" />
      <div></div>
      <span>{{ validateTxt.username }}</span>
    </div>
    <div class="input-field">
      <i class="fas fa-envelope"></i>
      <input type="email" v-model="joinMember.email" @blur="validateEmail" placeholder="Email" />
      <div></div>
      <span>{{ validateTxt.email }}</span>
    </div>
    <div class="input-field">
      <i class="fas fa-lock"></i>
      <input type="password" v-model="joinMember.password" @blur="validatePassword" placeholder="Password" />
      <div></div>
      <span>{{ validateTxt.password }}</span>
    </div>
    <input type="submit" class="btn" value="Sign up" />
    <p class="social-text">Or Sign up with social platforms</p>
    <div class="social-media">
      <a href="#" class="social-icon">
        <i class="fab fa-facebook-f"></i>
      </a>
      <a href="#" class="social-icon">
        <i class="fab fa-twitter"></i>
      </a>
      <a href="#" class="social-icon">
        <i class="fab fa-google"></i>
      </a>
      <a href="#" class="social-icon">
        <i class="fab fa-linkedin-in"></i>
      </a>
    </div>
  </form>
</template>

<script setup lang="ts">
import {ComponentInternalInstance, getCurrentInstance, reactive, ref} from "vue";
import {emailReg, passwordReg, usernameReg} from "../../../util/regexp";
import http from "../../../util/http";

const vm = getCurrentInstance() as ComponentInternalInstance;

interface SignUpForm {
  [index: string]: string;
  username: string;
  email: string;
  password: string;
}

const showSignUpMode = ref<Boolean>(false);
const validateTxt = reactive<SignUpForm>({
  username: '',
  email: '',
  password: ''
})

const joinMember = reactive<SignUpForm>({
  username: '',
  email: '',
  password: ''
})

const validateYn = reactive({
  username: false,
  email: false,
  password: false
})

/** 유저이름 validator */
async function validateUsername() {
  const { username } = joinMember;

  if(!username) {
    validateTxt.username = '내용을 입력해주세요.';
    validateYn.username = false;
    return;
  }

  if(usernameReg.test(username)) {
    validateTxt.username = '';
  } else {
    validateTxt.username = '한글: 2~4자리, 영어: 2~15자리까지 입력가능';
    validateYn.username = false;
    return;
  }

  try {
    const isDupl = await http.post('/member/check/dupl/username', joinMember);

    if(isDupl) {
      validateTxt.username = '사용가능한 이름입니다.';
      validateYn.username = true;
    }
  } catch (e: any) {
    validateTxt.username = e.response.data.message;
    validateYn.username = false;
  }
}

/** 이메일 validator */
async function validateEmail() {
  const { email } = joinMember;

  if(!email) {
    validateTxt.email = '내용을 입력해주세요.';
    validateYn.email = false;
    return;
  }

  if(emailReg.test(email)) {
    validateTxt.email = '';
  } else {
    validateTxt.email = '한글: 2~4자리, 영어: 2~15자리까지 입력가능';
    validateYn.email = false;
    return;
  }

  try {
    const isDupl = await http.post('/member/check/dupl/email', joinMember);

    if(isDupl) {
      validateTxt.email = '사용가능한 이메일입니다.';
      validateYn.email = true;
    }
  } catch (e: any) {
    validateTxt.email = e.response.data.message;
    validateYn.email = false;
  }
}

/** 비밀번호 validator */
function validatePassword() {
  const { password } = joinMember;

  if(!password) {
    validateTxt.password = '내용을 입력해주세요.'
    validateYn.password = false;
    return;
  }

  if(!passwordReg.test(password)) {
    validateTxt.password = '한글: 2~4자리, 영어: 2~15자리까지 입력가능';
    validateYn.password = false;
  } else {
    validateTxt.password = '';
    validateYn.password = true;
  }
}

function isDisabled(): boolean {
  console.log(Object.values(validateYn).some((v) => !v))
  // 한 항목이라도 안내문구가 있으면 submit 막음
  return Object.values(validateYn).some((v) => !v);
}

async function signUpUser() {
  try {
    const isCreated = await http.post('/member/signup/submit', joinMember);

    if(isCreated) {
      alert('유저생성이 완료되었습니다.');
      vm.appContext.config.globalProperties.$router.push('/email/auth');
    }

  } finally {
    return;
  }

}
</script>

<style scoped>
form {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  padding: 0 5rem;
  transition: all 0.2s 0.7s;
  overflow: hidden;
  grid-column: 1 / 2;
  grid-row: 1 / 2;
}

form.sign-up-form {
  opacity: 0;
  z-index: 1;
}

.title {
  font-size: 2.2rem;
  color: #444;
  margin-bottom: 10px;
}

i {
  text-align: center;
  line-height: 55px;
  color: #acacac;
  transition: 0.5s;
  font-size: 1.1rem;
}

.input-field {
  max-width: 380px;
  width: 100%;
  background-color: #f0f0f0;
  margin: 20px 0;
  height: 55px;
  border-radius: 55px;
  display: grid;
  grid-template-columns: 15% 85%;
  padding: 0 1.4rem;
  position: relative;
}

.input-field i {
  text-align: center;
  line-height: 55px;
  color: #acacac;
  transition: 0.5s;
  font-size: 1.1rem;
}

.input-field input {
  background: none;
  outline: none;
  border: none;
  line-height: 1;
  font-weight: 600;
  font-size: 1.1rem;
  color: #333;
}

.input-field span {
  color: #eb8c31;
  font-size: 12px;
}

.input-field input::placeholder {
  color: #aaa;
  font-weight: 500;
}

.btn {
  width: 150px;
  background-color: #5995fd;
  border: none;
  outline: none;
  height: 49px;
  border-radius: 49px;
  color: #fff;
  text-transform: uppercase;
  font-weight: 600;
  margin: 10px 0;
  cursor: pointer;
  transition: 0.5s;
}

.btn:hover {
  background-color: #4d84e2;
}

.social-text {
  padding: 0.7rem 0;
  font-size: 1rem;
}

.social-media {
  display: flex;
  justify-content: center;
}

.social-icon {
  height: 46px;
  width: 46px;
  display: flex;
  justify-content: center;
  align-items: center;
  margin: 0 0.45rem;
  color: #333;
  border-radius: 50%;
  border: 1px solid #333;
  text-decoration: none;
  font-size: 1.1rem;
  transition: 0.3s;
}

.social-icon:hover {
  color: #4481eb;
  border-color: #4481eb;
}

@media (max-width: 570px) {
  form {
    padding: 0 1.5rem;
  }
}
</style>