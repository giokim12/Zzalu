<template>
  <div>
    <only-go-back-top-nav-bar></only-go-back-top-nav-bar>
    <div class="text-center-container ">
    <img
      alt="ZZalu Light logo"
      class="logo"
      src="../../assets/zzalu_logo_light.png"
      v-if="isDark==false"
    />
    <img
      alt="ZZalu Dark logo"
      class="logo"
      src="../../assets/zzalu_logo_dark.png"
      v-if="isDark==true"
    />
    <div class="account-title mb-10">Reset Password</div>
  </div>
      <!-- <input-id-email></input-id-email> -->
      <!-- <input-code-form></input-code-form> -->
      <!-- <change-password></change-password>
      <reset-complete></reset-complete> -->
      <router-view></router-view>
  </div>
</template>

<script>
import { useDark } from '@vueuse/core';
const isDark = useDark();
import OnlyGoBackTopNavBar from '../../components/Common/NavBar/OnlyGoBackTopNav.vue'
// import InputCodeForm from '../../components/Accounts/InputCodeForm.vue'
// import ChangePassword from '../../components/Accounts/ChangePassword.vue'
// import ResetComplete from '../../components/Accounts/ResetPassword/ResetComplete.vue'





export default {
  name: 'ResetPasswordView',
  components: {
    OnlyGoBackTopNavBar,
    // InputCodeForm,
    // ChangePassword,
    // ResetComplete,
  },
  data() {
    return {
      isDark,
    };
  },
}
</script>

<style>

</style>