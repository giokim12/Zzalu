<template>
  <only-go-back-top-nav class="z-50"></only-go-back-top-nav>
  <div>
    <h1 class="profile-title flex justify-center pt-2 text-xl">이런 짤들이 승인을 기다리고 있어요!</h1>
    <br>
    <div class="grid-cols-2">
      <div v-for="(tempGif,i) in tempGifList" :key="i">
        <admin-list-item
        :tempGif="tempGif"/>
      </div>

    </div>
  </div>
  <div class="pb-14"></div>
  <main-bottom-nav></main-bottom-nav>
</template>

<script>
import { useStore } from "vuex";
import { computed } from "@vue/runtime-core";
import AdminListItem from "@/components/Accounts/Admin/AdminListItem.vue"
import OnlyGoBackTopNav from "@/components/Common/NavBar/OnlyGoBackTopNav.vue";
import MainBottomNav from "@/components/Common/NavBar/MainBottomNav.vue"


export default {
  name: "AdminInfoView",
  components: {
    AdminListItem,
    OnlyGoBackTopNav,
    MainBottomNav
  },
  setup () {
    const store = useStore();
    const tempGifList = computed(
      () => store.state.tempGifStore.temp_gif_list
    );
    return {
      tempGifList
      // follow_request
    }
  }
  
}
</script>

<style>

</style>