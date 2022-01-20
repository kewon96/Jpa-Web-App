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
    await http.get('/checkToken', {token})
  }
})
</script>

<style lang="scss" scoped>

</style>