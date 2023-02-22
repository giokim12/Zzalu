<template>
  <div class="mt-20">
    <h2 class="find-id-input-title" v-if="$route.name == 'find-id'">아이디 찾기</h2>
    <h2 class="find-id-input-title" v-if="$route.name == 'input-reset-email'">비밀번호 분실

      
    </h2>
    <font-awesome-icon icon="fa-solid fa-envelope" class='icon-aligned-left'/>
      <input
        type="text"
        class="account-input"
        placeholder="이메일을 입력하세요"
        v-model="state.credentials.email"
      />

      <div class="signup-error" v-if="errorMsgs.err.email" >{{ errorMsgs.err.email }}</div>
      <div class="signup-error-emailgo" v-if="errorMsgs.err.emailSend" >{{ errorMsgs.err.emailSend }}</div>
    <div class="flex float-right mt-10" v-if="$route.name == 'input-signup-email'">
      <button class="go-next-button" @click="sendEmailCode">다음</button>
    </div>
    <div class="flex float-right mt-10" v-if="$route.name == 'find-id-input-email'">
      <button class="go-next-button" @click="sendUsername">다음</button>
    </div>
  </div>
  
</template>

<script>
import { useStore } from 'vuex';
import { useRouter } from 'vue-router';
import { reactive, watch } from 'vue'
import SignupEmailValidations from '../../services/SignupEmailValidations'
import Swal from 'sweetalert2'

export default {
  name: 'InputEmail',
  setup() {
    const store = useStore();
    const router = useRouter();
    const state = reactive({
      credentials: {
        email: '',
        code: ''
      }
    })

    const errorMsgs = reactive({
      err: {
        email: "",
        emailSend: "",
      }
    })
    // 이메일 네이밍규칙
    const onSubmit = () => {
      const validations = new SignupEmailValidations(
        state.credentials.email,
        );
      const errors = validations.checkValidations();
      if ('email' in errors) {
        errorMsgs.err.email = errors['email']
      } else {
        errorMsgs.err.email = null
      }
    }
    watch(() => state.credentials.email, (newValue, oldValue) => {
      if (newValue != oldValue) {
        errorMsgs.err.email= null
        }
    })
    // 이메일 중복확인 및 코드 요청 보내기
    const sendEmailCode = async function () {
      const validations = new SignupEmailValidations(state.credentials.email);
      const errors = validations.checkValidations();
      if ('email' in errors) {
        errorMsgs.err.email = errors['email']
      } else {
        errorMsgs.err.emailSend = '이메일을 보낼 때 시간이 소요됩니다...'
        const result = await store.dispatch('userStore/sendEmailAction', state.credentials.email )
        if (result.status == 400) {
          Swal.fire({
            icon: "error",
            html: "이미 사용중인 이메일입니다.<br>다른 이메일을 입력해주세요."
          })
        } else if (result.status == 200) {
          const credentialsEmailCode = {
            email: state.credentials.email,
            code: result.data.authKey,
          }
          // 회원가입 등록
          const email_code = await store.dispatch('userStore/signupSecondAction', credentialsEmailCode)
          if (email_code) {
            router.push({name: 'input-signup-code'})
          }
        }
      }
    }


    // 내가 잊은 아이디를 메일로 보내기
    const sendUsername = async function () {
      const result = await store.dispatch('userStore/sendUsernameAction', state.credentials.email )
      if (result.status == 200 ) {
        router.push({name: 'check-email'})
      } else {
        Swal.fire({
          icon: "error",
          text:"해당 이메일에 대한 정보가 없습니다."
          })
      }
    }
    return {
      state,
      errorMsgs,
      sendEmailCode,
      sendUsername,
      onSubmit,
    }
  },
  data() {
    return {
      // 회원 정보
      email: '',
    };
  },
  }
// }
</script>

<style lang="postcss" scoped>
.find-id-input-title {
  @apply dark:text-white mb-1 font-spoq mx-2
}

.signup-error-emailgo {
  @apply text-lg dark:text-white font-spoq
}
</style>