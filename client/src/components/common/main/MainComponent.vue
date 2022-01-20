<template>
  <SideBar />

  <h1>Hello World!</h1>

</template>

<script setup lang="ts">
import {onBeforeMount} from "vue";
import {useRouter} from "vue-router";
import http from "../../../util/http";
import SideBar from "./SideBar.vue";


onBeforeMount(async () => {
  const token: string | null = sessionStorage.getItem("token");

  if (!token) {
    useRouter().push('Login');
  } else {
    // 토큰있으면 검증시작
    // 이때 token을 header에다가 넣기위해 config에 token을 넣어준다.
    const config = {
      headers: { Authorization: `Bearer ${token}`}
    }

    await http.post('/checkToken', {token}, config);
  }
})
</script>

<style lang="scss" scoped>

</style>