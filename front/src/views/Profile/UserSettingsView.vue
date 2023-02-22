<template>
  <only-go-back-top-nav></only-go-back-top-nav>
  <h1 class="account-title">Settings</h1>
  <div class="mt-6 dark:bg-zz-bd">
    <div class="flex">
      <font-awesome-icon icon="fa-solid fa-user" class='settings-icon-aligned-left'/>
      <button class="settings-button" @click="goProfileEdit">
        <div class="text-zz-light-p dark:text-white">내 정보 수정</div>
          
      </button>
    </div >
    <!-- <div class="flex">
      <font-awesome-icon icon="fa-solid fa-lock" class='settings-icon-aligned-left'/>
      <button class="settings-button" @click="goChangePwd">
        <div class="text-zz-light-p dark:text-white">비밀번호 변경</div>
        </button>
    </div> -->


    <div class="flex" v-if="this.isManager=='true'">
      <font-awesome-icon icon="fa-solid fa-crown" class='settings-icon-crown'/>
      <button class="settings-button" @click="[GetTempGif(), goAdmin()]">
        <div class="text-zz-light-p dark:text-white">짤 승인하러 가기</div>
        </button>
    </div>
    <div class="flex" v-if="this.isManager=='false'">
      <font-awesome-icon icon="fa-solid fa-crown" class='settings-icon-crown'/>
      <button class="settings-button" @click="managerApply">
        <div class="text-zz-light-p dark:text-white">매니저 권한 신청하기</div>
        </button>
    </div>
    <!-- <div class="flex" v-if="this.isManager">
      <font-awesome-icon icon="fa-solid fa-crown" class='settings-icon-crown'/>
      <button class="settings-button" @click="goAdmin">
        <div class="text-zz-light-p dark:text-white">짤 승인하러 가기</div>
        </button>
    </div> -->
    <div class="flex" @click="toggleDark()" v-if="isDark==true">
      <font-awesome-icon icon="fa-regular fa-moon" class='settings-icon-aligned-left'/>
      <button class="settings-button">
        <div class="text-zz-light-p dark:text-white">다크모드</div>
        </button>
      <font-awesome-icon icon="fa-solid fa-toggle-off" class='settings-toggle-right btn1'/>
      <div class="settings-right-letter-off">OFF</div>
    </div>
    <div class="flex" @click="toggleDark()" v-if="isDark==false">
      <font-awesome-icon icon="fa-regular fa-moon" class='settings-icon-aligned-left'/>
      <button class="settings-button">다크모드</button>
      <font-awesome-icon icon="fa-solid fa-toggle-on" class='settings-toggle-right btn2'/>
      <div class="settings-right-letter-on">ON</div>
    </div>
  </div>

  <div class="logout">
    <button @click="logoutSubmit">로그아웃하기</button>
  </div>
  
  <main-bottom-nav></main-bottom-nav>

</template>

<script>
import OnlyGoBackTopNav from "../../components/Common/NavBar/OnlyGoBackTopNav.vue"
import MainBottomNav from "../../components/Common/NavBar/MainBottomNav.vue"
import { useDark, useToggle } from "@vueuse/core";
const isDark = useDark();
import { useStore } from "vuex";
import { useRouter } from 'vue-router';
const toggleDark = useToggle(isDark);

export default {
  name: "UserSettingsView",
  components: {
    OnlyGoBackTopNav,
    MainBottomNav
  },
  data() {
    return {
      isDark,
      toggleDark,
    };
  },
  setup() {
    const isManager = localStorage.getItem("isManager")
    const store = useStore();
    const router = useRouter();
    const logoutSubmit = async function() {
      await store.dispatch('userStore/logoutAction')
      router.push({name: 'main'})
    }
    const goProfileEdit = () => {
      router.push({name: 'edit-profile'})
    }
    const goChangePwd = () => {
      router.push({name: 'change-password'})
    }
    const managerApply = async () => {
      await store.dispatch('userStore/managerApplyAction')
    }
    const goAdmin = () => {
      router.push({name: 'admin'})
    }
    const GetTempGif = () => {
      store.dispatch('tempGifStore/getAllTempGif')
    }
    return {
      logoutSubmit,goProfileEdit,managerApply,goChangePwd, goAdmin, GetTempGif,
      isManager
    }
  },

}
</script>

<style lang="postcss" scoped>
.logout {
  @apply fixed inset-x-0 flex flex-wrap bottom-16 border-t text-zz-light-p font-spoq text-xl pt-3 px-4 dark:text-zz-light-s
}

.settings-icon-crown {
  @apply absolute mx-4 my-5 h-8 text-zz-light-p dark:text-white
}

@keyframes btn_move {
  0% {
    @apply absolute mx-5 my-5 h-8 dark:text-white
  }
  100% {
    @apply absolute right-6 mx-5 my-2 h-14 dark:text-white
  }
}
.btn1 {
  animation: btn_move;
  animation-duration: 0.2s;
  animation-fill-mode: both;
}

.btn2 {
  animation: btn_move;
  animation-duration: 0.2s;
  animation-fill-mode: both;
}
</style>