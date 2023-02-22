<template>
  <div>
    <only-go-back-top-nav></only-go-back-top-nav>
    <!-- 로고랑 제목 -->
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
    <h1 class="account-title">Login</h1>
    <!-- 아이디 비번 입력창 -->
    
    <h2 class="input-title">아이디</h2>
    <div>
      <font-awesome-icon icon="fa-solid fa-user" class="icon-aligned-left" />
      <input
        type="text"
        class="account-input"
        placeholder="아이디를 입력하세요"
        v-model="state.creds.username"
        autocomplete="off"
      />
    </div>
    <!-- <div class="error" v-if = "errors.username"> {{ errors.username }} </div> -->
    <h2 class="input-title">비밀번호</h2>
    <div>
      <font-awesome-icon icon="fa-solid fa-lock" class="icon-aligned-left" />
      <input
        type="password"
        class="account-input"
        id="password"
        placeholder="비밀번호를 입력하세요"
        v-model='state.creds.password'
        autocomplete="off"
      />
      <font-awesome-icon icon="fa-solid fa-eye" class="icon-aligned-right" @click="showPwd" />
      <!-- <font-awesome-icon icon="fa-solid fa-eye-slash" class='icon-aligned-left'/> -->
    </div>
    <!-- <div class="error" v-if = "errors.password"> {{ errors.password }} </div> -->
    <!-- <h1 :error="error">{{error}}</h1> -->

    <!-- 아이디 비번찾기 -->
    <div class="redir-accounts">
      <router-link to="/find-id" class="find-id">아이디를 잊으셨다면?</router-link>
      <!-- <router-link to="/reset-password">비밀번호 찾기</router-link> -->
    </div>
    <!-- 소셜로그인 -->
    <!-- <div class="divide-social">
      <hr class="div-line" />
      <div class="or-start-with">Or Start With</div>
    </div> -->
  </div>
  <!-- <div class="center-containers">
    <kakao-login-button></kakao-login-button>
    <img src="./assets/kakao_icon.png" class="login-icon" alt="" />
  </div> -->
  <!-- 로그인 버튼 -->
  <div class="ml-20">
    <button class="submit-button" @click="loginSubmit">로그인</button>
  </div>
  <sign-up-bottom-nav></sign-up-bottom-nav>
</template>

<script>
  import SignUpBottomNav from '../../components/Common/NavBar/SignUpBottomNav.vue';
  import OnlyGoBackTopNav from '@/components/Common/NavBar/OnlyGoBackTopNav.vue';
  // import KakaoLoginButton from '@/components/Accounts/Login/KakaoLoginButton.vue'
  import { useStore } from 'vuex';
  import { useDark } from '@vueuse/core';
  import { reactive } from 'vue'
  import { useRouter } from 'vue-router';
  import Swal from 'sweetalert2'

  const isDark = useDark();
  export default {
    name: "LoginView",
    components: {
      OnlyGoBackTopNav,
      SignUpBottomNav,
      // KakaoLoginButton
    },
    setup() {
      const store = useStore();
      const router = useRouter();
      const state = reactive({
        creds: {
          username: null,
          password: null,
        }

      })

      const loginSubmit = async function () {
          const loginData = {
            username: state.creds.username,
            password: state.creds.password,
          }
        
        if (!loginData.username | !loginData.password) {
          Swal.fire({
            icon: "error",
            text:"아이디와 비밀번호를 모두 입력해주세요."
            })
        } else {
        // console.log('로그인 데이터', loginData)
        const res = await store.dispatch('userStore/loginAction', loginData)
        if (res.status==200) {
          router.push({name: 'main'})
          } else {
          Swal.fire({
            icon: "error",
            html: "회원이 존재하지 않습니다. <br> 회원정보를 확인해주세요."
            })
        }
        }
        
      }

      const showPwd = function() {
        var pwd = document.getElementById("password");
        if (pwd.type=="text") {
          pwd.type = "password"
        } else {
          pwd.type="text"
        }
      }
      return {
        state,
        loginSubmit,
        showPwd
      }
    },
    data() {
      return {
        isDark,
      };
    }
  }
</script>


<style scoped lang="postcss">
.find-id {
  @apply mx-1;
}

/* 소셜로그인 */
.divide-social {
  @apply inline-flex items-center justify-center w-full;
}

.div-line {
  @apply w-64 h-0.5 my-6 bg-zz-p border-0 rounded;
}

.or-start-with {
  @apply absolute px-3 text-sm -translate-x-1/2 bg-white font-spoq text-zz-darkgray dark:bg-zz-bd;
}

.login-icon {
  @apply w-12 mx-2 border-8 border-gray-100 bg-gray-100 rounded-full dark:border-zz-darkgray;
}
</style>
