<template>
  <div class="input-without-title">
    <font-awesome-icon icon="fa-solid fa-user" class="icon-aligned-left" />
    <input
      type="text"
      class="account-input"
      placeholder="아이디를 입력하세요"
      v-model="state.credentials.username"
    />
    <button class="button-in-input" @click="uniqueUsername">중복확인</button>
  </div>
  <div class="signup-error" v-if="errorMsgs.err.username" > {{ errorMsgs.err.username }} </div>
  <div class="input-without-title">
    <font-awesome-icon icon="fa-solid fa-user" class="icon-aligned-left" />
    <input
      type="text"
      class="account-input"
      placeholder="닉네임을 입력하세요"
      v-model="state.credentials.nickname"
    />
    <button class="button-in-input" @click="uniqueNickname" >중복확인</button>
  </div>
  <div class="signup-error" v-if="errorMsgs.err.nickname" > {{ errorMsgs.err.nickname }} </div>
  <div class="input-without-title">
    <font-awesome-icon icon="fa-solid fa-lock" class="icon-aligned-left" />
    <input
      type="password"
      class="account-input"
      placeholder="영어, 숫자, 특수문자 포함 8~20자여야 합니다."
      v-model="state.credentials.password"
    />
  </div>
  <div class="signup-error" v-if="errorMsgs.err.password" > {{ errorMsgs.err.password }} </div>
  <div class="input-without-title">
    <font-awesome-icon icon="fa-solid fa-lock" class="icon-aligned-left" />
    <input
      type="password"
      class="account-input"
      placeholder="비밀번호를 다시 입력하세요"
      v-model="state.credentials.passwordCheck"
    />
  </div>
  <div class="signup-error" v-if="errorMsgs.err.passwordCheck" > {{ errorMsgs.err.passwordCheck }} </div>
  <div class="account-right">
    <div class="redir-accounts">
      <div>이미 계정이 있으신가요?</div>
      <router-link to="/login" class="redir-accounts-click">로그인</router-link>
    </div>
    <button class="go-next-button" @click.prevent="[submitRules(), sendSignupInfo()]">다음</button>
  </div>
</template>




<script>
// import useVuelidate from "@vuelidate/core";
// import { required } from "@vuelidate/validators";
import SignupInfoValidations from '../../../services/SignupValidations'
import SignupUsernameValidations from '../../../services/SignupUsernameValidations'
import SignupNicknameValidations from '../../../services/SignupNicknameValidations'
import { useStore } from 'vuex';
import { reactive, watch } from 'vue'
import { useRouter } from 'vue-router';
// import {mapActions} from 'vuex';
import Swal from 'sweetalert2'

export default {
  name: "InputUserInfo",
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      credentials: {
        password: "",
        passwordCheck: "",
        username: '',
        nickname: '',
        email: "",
        code: '',
      },
      nicknameState: false,
      usernameState: false,
      pwdState: false
    })
    const errorMsgs = reactive({
      err: {
        password: "",
        passwordCheck: "",
        username: '',
        nickname: '',
        email: "",
        code: '',
      }
    })
    // 아이디 닉네임 바꾸는지 확인
    watch(() => state.credentials.username, (newValue, oldValue) => {
      if (newValue != oldValue) {
        state.usernameState = false
        errorMsgs.err.username = null
        }
    })
    watch(() => state.credentials.nickname, (newValue, oldValue) => {
      if (newValue != oldValue) {
        state.nicknameState = false
        errorMsgs.err.nickname= null
        }

    })
    watch(() => state.credentials.password, (newValue, oldValue) => {
      if (newValue != oldValue) {
        state.pwdState = false
        errorMsgs.err.password= null
        errorMsgs.err.passwordCheck= null
        }

    })
    // 아이디 중복확인
    const uniqueUsername = async function () {
      // 중복확인 전에 네이밍규칙 확인 ㄱㄱ
      const validations = new SignupUsernameValidations(
        state.credentials.username
        );
      const errors = validations.checkValidations();
      if ('username' in errors) {
        errorMsgs.err.username = errors['username']
        this.state.usernameState = false
      } else {
        // 네이밍 규칙 맞추면 이제 진짜 중복확인
        const result = await store.dispatch('userStore/uniqueUsernameAction', state.credentials.username )
      if (result.data.unique == true) {
        state.usernameState = true
        // alert("사용 가능한 아이디입니다.")
        Swal.fire({
          icon: "success",
          text:"사용 가능한 아이디입니다."
          })
      } else {
        state.usernameState = false
        Swal.fire({
          icon: "error",
          html:"사용 중인 아이디입니다. <br>다른 아이디를 등록해주세요."
          })
      } }
    }


    // 닉네임 중복확인
    const uniqueNickname = async function () {
      // 중복확인 전에 네이밍규칙 확인 ㄱㄱ
      const validations = new SignupNicknameValidations(
        state.credentials.nickname
        );
      const errors = validations.checkValidations();

      if ('nickname' in errors) {
        errorMsgs.err.nickname = errors['nickname']
        this.state.nicknameState = false
      } else {
      const result = await store.dispatch('userStore/uniqueNicknameAction', state.credentials.nickname )
      if (result.data.unique==true) {
        state.nicknameState = true
        Swal.fire({
          icon: "success",
          text:"사용 가능한 닉네임입니다."
          })
      } else {
        state.nicknameState = false
        Swal.fire({
          icon: "error",
          html:"이미 사용 중인 닉네임입니다. <br>다른 닉네임을 등록해주세요."
          })
      }
    }}

    // 네이밍규칙
    const submitRules = function () {

      const validations = new SignupInfoValidations(
        state.credentials.username,
        state.credentials.nickname,
        state.credentials.password,
        state.credentials.passwordCheck,
        );

      const errors = validations.checkValidations();

      if ('username' in errors) {
        errorMsgs.err.username = errors['username']
        this.state.usernameState = false
      }
      if ('nickname' in errors) {
        errorMsgs.err.nickname = errors['nickname']
        this.state.nicknameState = false
      }
      if ('password' in errors) {
        errorMsgs.err.password = errors['password']
        this.state.pwdState = false
      }
      else {
        if ('passwordCheck' in errors) {
        errorMsgs.err.passwordCheck = errors['passwordCheck']
        this.state.pwdState = false
        }
        else {
          this.state.pwdState = true
        }
      }
      
    }



    // 가입요청1
    const sendSignupInfo = async function () {
      // 아이디 닉네임 중복확인 다 했는지 확인
      if (!state.usernameState){
        Swal.fire({
          icon: "error",
          text:"아이디 중복확인이 필요합니다."
          })
        return
      } else if (!state.nicknameState){
        Swal.fire({
          icon: "error",
          text:"닉네임 중복확인이 필요합니다."
          })
        return
      } else if (!state.pwdState){
        Swal.fire({
          icon: "error",
          text:"비밀번호를 확인해주세요."
          })
        return
      } else {
        const credentialsData = {
          username: state.credentials.username,
          nickname: state.credentials.nickname,
          password: state.credentials.password,
          passwordCheck: state.credentials.passwordCheck,
          email: state.credentials.email,
          code: state.credentials.code,
        }
      // 회원가입 등록
      const res = await store.dispatch('userStore/signupFirstAction', credentialsData)
      if (res) {
        router.push({name: 'input-signup-email'})
      }
      }
    }

  return {

    state,
    errorMsgs,
    uniqueUsername,
    uniqueNickname,
    submitRules,
    sendSignupInfo,
  }
  },
  data() {
    return {
      // 에러
      errors: [],
    };
  },
};
</script>

<style lang="postcss" scoped>
/* 오른쪽정렬 바깥 컨테이너 */
.account-right {
  @apply grid grid-rows-2 justify-end;
}
</style>
