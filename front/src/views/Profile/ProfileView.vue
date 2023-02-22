<template>
  <div>
    <div v-if="this.current_user == this.userName">
      <my-profile-top-nav class="z-50"></my-profile-top-nav>
    </div>
    <div v-if="this.current_user != this.userName">
      <only-go-back-top-nav class="z-50"></only-go-back-top-nav>
    </div>
    <profile-info></profile-info>
    <user-stats></user-stats>
    <user-award></user-award>
    <user-board
    :username="username"
    ></user-board>
    
    <div class="pb-12"></div>
    <main-bottom-nav></main-bottom-nav>
  </div>
</template>

<script>
import MainBottomNav from '../../components/Common/NavBar/MainBottomNav.vue';
import MyProfileTopNav from '../../components/Common/NavBar/MyProfileTopNav.vue';
import ProfileInfo from '../../components/Profile/ProfileInfo.vue';
import UserStats from '../../components/Profile/UserStats.vue';
import UserAward from '../../components/Profile/UserAward.vue';
import UserBoard from '../../components/Profile/UserBoard.vue';
import OnlyGoBackTopNav from '@/components/Common/NavBar/OnlyGoBackTopNav.vue';
import { useStore } from 'vuex';
import { useRoute } from 'vue-router';
// import { computed } from "@vue/runtime-core";

export default {
  name: 'ProfileView',
  components: {
    MainBottomNav,
    MyProfileTopNav,
    OnlyGoBackTopNav,
    ProfileInfo,
    UserStats,
    UserAward,
    UserBoard,
  },
  data() {
    return {
      userName: this.$route.params.username,
    };
  },
  setup() {
    const store = useStore();
    const route = useRoute();
    const username = route.params.username;
    store.dispatch('profileStore/getProfileUser', username);
    store.dispatch('boardListStore/getUserBoardList', username);
    store.dispatch('profileStore/getProfileStats', username);
    const current_user = localStorage.getItem('current_userid');
    return {
      current_user,
      username,
    };
  },
};
</script>

<style lang="postcss" scoped>
.follower-and-following {
  @apply mt-4 text-center mx-2 text-zz-s font-spoq;
}
</style>