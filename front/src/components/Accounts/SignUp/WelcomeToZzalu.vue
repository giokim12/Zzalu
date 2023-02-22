<template>
  <div class="mt-10" id="WelcomeToZzalu">
    <div class="text-center-container">
      <div class="text-oneline">
        <div class="error-sub-title">환영합니다!</div>
        <font-awesome-icon icon="fa-solid fa-face-smile-wink" class="my-1.5 mx-2 dark:text-white"/>
      </div>
      <!-- 로고랑 제목 -->
        <img
          alt="Celebrate Cat"
          class="celebrate"
          src="../../../assets/celebrate.gif"
        />

      <div class="error-content-bold">{{ username }}님, 회원가입을 진심으로 환영합니다.</div>
      <div class="error-content-bold">짤루에서의 새로운 닉네임은 {{ nickname }}입니다.</div>
      <button class="submit-button" @click="deleteTemp">
        <router-link to="/login">
          로그인 하러 가기
        </router-link>
      </button>
    </div>
  </div>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";

export default {
  name: 'WelcomeToZzalu',
  setup () {
    const store = useStore();

    const username = computed(
      () => store.state.userStore.temp.username
    );
    const nickname = computed(
      () => store.state.userStore.temp.nickname
    );

    const deleteTemp = store.dispatch('userStore/signupInfoDelete')


    return{
      username,
      nickname,
      deleteTemp

    }
  }
}
</script>

<style lang="postcss" scoped>
</style>