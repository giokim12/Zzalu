<template>
  <div class="text-center-container mt-10">
    <div class="almost-done">
      <div class="error-sub-title">{{this.email}} 으로</div>
      <div class="error-sub-title">아이디를 보냈어요.</div>
      <div class="error-sub-title">이메일로 전송된 아이디를 확인해주세요.</div>
    </div>
  </div>
  <div class="text-center-container mt-10">
    <div class="didnt-get-mail mb-6">
      <div class="mail-error">메일을 받지 못하셨나요?</div>
      <button class="send-again-button" @click="sendEmailAgain">인증 메일 재전송</button>
    </div>
    
      <button class="submit-button" @click="removeTemp">
        <router-link to="/login">
          로그인 하러 가기
          </router-link>
        </button>
    
    
  </div>

</template>

<script>
import { useStore } from 'vuex';
export default {
  name: 'CheckEmail',
  data () {
    return {
      email: localStorage.getItem('temp_email')
    }
  },
  setup() {
    const store = useStore();
    const removeTemp = function() {
      localStorage.removeItem('temp_email')
    }
    const sendEmailAgain = function() {
      store.dispatch('userStore/sendUsernameAction', this.email )
    }
    return {
    removeTemp, sendEmailAgain
  }
  }

}
</script>

<style lang="postcss" scoped>

.mail-error {
  word-break: keep-all;
  @apply font-spoq text-xs mt-3;
}
</style>