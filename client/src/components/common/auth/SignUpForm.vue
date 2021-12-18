<template>
  <div class="sign-up-form" :class="{ 'auth-mode': showAuthMode }">
    <div class="info">
      <h2 class="title">Sign up</h2>
      <div class="input-field">
        <i class="fas fa-envelope"></i>
        <input type="email" v-model="joinMember.email" @blur="validateEmail" placeholder="이메일" />
        <div></div>
        <span>{{ validateTxt.email }}</span>
      </div>
      <div class="input-field">
        <i class="fas fa-lock"></i>
        <input type="password" v-model="joinMember.password" @blur="validatePassword" placeholder="비밀번호" />
        <div></div>
        <span>{{ validateTxt.password }}</span>
      </div>
      <div class="input-field">
        <i class="fas fa-user"></i>
        <input type="text" v-model="joinMember.memberName" @blur="validateMemberName" placeholder="이름" />
        <div></div>
        <span>{{ validateTxt.memberName }}</span>
      </div>
      <input type="button" class="btn create-btn" value="Create" @click="createMember" :disabled="isInspectionCompleted" />
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
    </div>
    <div class="auth">
      <h2 class="title">Authorization</h2>
      <div class="input-field">
        <i class="fas fa-user"></i>
        <input type="text" v-model="certifiedEmail.certifiedCode" />
        <div>{{ certifiedEmail.timer }}</div>
        <span>{{ certifiedEmail.validTxt }}</span>
      </div>
      <input type="button" class="btn auth-btn" value="인증하기" @click="tryAuthenticate" />
    </div>
  </div>
</template>

<script setup lang="ts">
import {ComponentInternalInstance, computed, getCurrentInstance, onMounted, reactive, ref, watchEffect} from "vue";
import {emailReg, passwordReg, memberNameReg} from "../../../util/regexp";
import http from "../../../util/http";

const vm = getCurrentInstance() as ComponentInternalInstance;

interface SignUpForm {
  [index: string]: string;
  memberName: string;
  email: string;
  password: string;
}

interface CertifiedEmail {
  timer: string,
  timerIntervalId: ReturnType<typeof setInterval> | null,
  certifiedCode: string,
  validTxt: string,
  targetEmail: string
}

const validateTxt = reactive<SignUpForm>({
  memberName: '',
  email: '',
  password: ''
})

const joinMember = reactive<SignUpForm>({
  memberName: '',
  email: '',
  password: ''
})

const validateYn = reactive({
  memberName: false,
  email: false,
  password: false
})

const certifiedEmail = reactive<CertifiedEmail>({
  timer: '',
  timerIntervalId: null,
  certifiedCode: '',
  validTxt: '',
  targetEmail: joinMember.email
})

const isInspectionCompleted = ref<boolean>(true);
const showAuthMode = ref<boolean>(false);


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

  const isNotDupl = await http.get('/member/checkDuplEmail', {email});

  let txt: string;
  if(isNotDupl) {
    txt = '사용가능한 이메일입니다.';
  } else {
    txt = '이미 존재하는 이메일입니다.';
  }

  if(typeof isNotDupl === 'boolean') {
    validateTxt.email = txt;
    validateYn.email = isNotDupl;
  }
}

/** 유저이름 validator */
async function validateMemberName() {
  const { memberName } = joinMember;

  if(!memberName) {
    validateTxt.memberName = '내용을 입력해주세요.';
    validateYn.memberName = false;
    return;
  }

  if(memberNameReg.test(memberName)) {
    validateTxt.memberName = '';
  } else {
    validateTxt.memberName = '한글: 2~4자리, 영어: 2~15자리까지 입력가능';
    validateYn.memberName = false;
    return;
  }
  const isNotDupl = await http.get('/member/checkDuplMemberName', {memberName});

  let txt: string;
  if(isNotDupl) {
    txt = '사용가능한 이름입니다.';
  } else {
    txt = '이미 존재하는 이름입니다.';
  }

  if(typeof isNotDupl === 'boolean') {
    validateTxt.memberName = txt;
    validateYn.memberName = isNotDupl;
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

// 사용자생성버튼 활성화여부
watchEffect(() => {
  isInspectionCompleted.value = Object.values(validateYn).some(k => !k);
})

// 이메일인증버튼 활성화여부
// watchEffect(() => {
//   // 시간이 다됐으면 비활성화
//   if(certifiedEmail.timer === '0 : 0') {
//     if(!vm.vnode.el) return;
//
//     vm.vnode.el!.querySelector('.create-btn').disabled = true;
//   } else {
//     if(certifiedEmail.certifiedCode.length !== 6) {
//       if(!vm.vnode.el) return;
//
//       vm.vnode.el!.querySelector('.create-btn').disabled = true;
//     } else {
//       vm.vnode.el!.querySelector('.create-btn').disabled = false;
//     }
//   }
// })

/**
 * 회원생성
 * 생성 후 인증시작
 */
async function createMember() {
  const authCode = await http.post('/member/createMember', joinMember);

  if(typeof authCode === 'string') {
    startAuthenticate(authCode);
  }
}

function startAuthenticate(authCode: string) {

  const authMail = {
    message: authCode,
    address: joinMember.email
  }

  // Then
  http.post('/member/sendAuthenticationCode', authMail)

  // 인증화면 보여지게
  showAuthMode.value = !showAuthMode.value
}

async function tryAuthenticate() {
  const { certifiedCode } = certifiedEmail;

  const certifiedMap = {
    certifiedCode: certifiedCode,
    targetEmail: joinMember.email
  }
  const msg = await http.post('/member/checkAuthCode', certifiedMap);

  console.log(msg)
}

</script>

<style lang="scss" scoped>

.sign-up-form {
  padding: 0 5rem;
  transition: all 0.2s 0.7s;
  overflow: hidden;
  grid-column: 1 / 2;
  grid-row: 1 / 2;

  opacity: 0;
  z-index: 1;
}

.info {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;

  z-index: 5;
  transition: 0.5s ease-in-out;
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

  i {
    text-align: center;
    line-height: 55px;
    color: #acacac;
    transition: 0.5s;
    font-size: 1.1rem;
  }

  input {
    background: none;
    outline: none;
    border: none;
    line-height: 1;
    font-weight: 600;
    font-size: 1.1rem;
    color: #333;

    &::placeholder {
      color: #aaa;
      font-weight: 500;
    }
  }

  span {
    color: #eb8c31;
    font-size: 12px;
  }
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

  &:hover {
    background-color: #4d84e2;
  }

  &:disabled {
    background-color: #5995fd78;
  }
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

.auth {
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  position: absolute;
  overflow: hidden;
  background-color: #fff;
  width: 81%;
  top: 28%;
  transition: 0.5s ease-in-out;

  z-index: -1;
  opacity: 0;
}

//.auth:before {
//  content: "";
//  position: absolute;
//  top: -45%;
//  transform: translateY(-50%);
//
//  width: 800px;
//  height: 200px;
//  transition: 0.5s ease-in-out;
//  z-index: -1;
//}

/* ANIMATION */
.sign-up-form.auth-mode .info {
  opacity: 0;
  z-index: -1;
}

.sign-up-form.auth-mode .auth {
  opacity: 1;
  z-index: 1;
}


@media (max-width: 570px) {
  .sign-up-form {
    padding: 0 1.5rem;
  }
}
</style>