<template>
  <div>
    <only-go-back-top-nav-bar></only-go-back-top-nav-bar>
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
    <router-view></router-view>
  </div>
</template>

<script>
import OnlyGoBackTopNavBar from '../../components/Common/NavBar/OnlyGoBackTopNav.vue'
import { useDark } from '@vueuse/core';
const isDark = useDark();
export default {
  name: 'FindIdView',
  components: {
    OnlyGoBackTopNavBar,
  },
  data() {
    return {
      isDark,
    };
  }
}
</script>

<style>

</style>