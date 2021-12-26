<template>
  <form action="/main" class="sign-in-form" @submit.prevent="doLogin">
    <h2 class="title">Sign in</h2>
    <div class="input-field">
      <i class="fas fa-user"></i>
      <input type="text" v-model="login.memberName" placeholder="Username" />
    </div>
    <div class="input-field">
      <i class="fas fa-lock"></i>
      <input type="password" v-model="login.password" placeholder="Password" />
    </div>
    <input type="submit" value="Login" class="btn solid" :disabled="isEmpty" />
    <p class="social-text">Or Sign in with social platforms</p>
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

import {reactive, ref, watchEffect} from "vue";
import http from "../../../util/http";

interface Login {
  memberName: string,
  password: string
}

const isEmpty = ref(true)

const login = reactive<Login>({
  memberName: '',
  password: ''
})

// 하나라도 값이 없으면 disabled
watchEffect(() => {
  isEmpty.value = Object.values(login).some(k => !k);
})


async function doLogin() {
  const token = await http.post('/login', login);

  if(typeof token === 'string') {
    sessionStorage.setItem("token", token);
  }
}

</script>

<style lang="scss" scoped>
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

form.sign-in-form {
  z-index: 2;
}

.title {
  font-size: 2.2rem;
  color: #444;
  margin-bottom: 10px;
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

  &:hover {
     background-color: #4d84e2;
   }

  &:disabled {
     background-color: #5995fd78;
     cursor: default;
   }
}

//.btn:hover {
//  background-color: #4d84e2;
//}

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