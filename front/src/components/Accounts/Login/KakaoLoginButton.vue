<template>
  <img src="@/views/Accounts/assets/naver_icon.png" class="login-icon" @click="loginWithKakao" />
</template>

<script>
// src='https://developers.kakao.com/sdk/js/kakao.js'
  // window.Kakao.init("8bc4643075801a17f6d917d555e0a86c")
export default {
  name: 'KakaoLoginButton',
  // methods: {
  //   kakaoLogin = function () {
  //       window.kakao.Auth.login({
  //         scope: 'profile, account_email, profile_image',
  //         success: function(authObj) {
  //           console.log(authObj);
  //           window.Kakao.API.request({
  //             url:'v2/user/me',
  //             success: res => {
  //               const kakao_account = res.kakao_account;
  //               console.log(kakao_account)
  //             }
  //           })
  //         }
  //       })
  //     }
    
  //   return {
  //     kakaoLogin
  //   }
  // }
//   methods: {
//     kakaoInit () {
//       Kakao.init('329a6a74...')// KaKao client key
//       Kakao.isInitialized()
//     },
//     async loginWithKakao () {
//       await Kakao.Auth.authorize({
//         redirectUri: `${window.location.origin}/kakao-callback`
//       })
//     }
//   },
//   mounted () {
//     this.kakaoInit()
//   }
}
</script>

<style>

</style>