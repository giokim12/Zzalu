import { checkUsername, checkNickname, checkEmail, requestRegister, requestLogin, requestUsername, requestChangeInfo, requestDelete, requestManager } from "@/api/userAccount";
import Swal from 'sweetalert2'
const userStore = {
  namespaced: true,
  state:()=> ({
    temp: {
      username: '',
      nickname: '',
      password: '',
      passwordCheck: '',
      email: '',
      code: '',
    },
    user: null,
    accessToken: "",
    refreshToken: "",
    nickname: "",
    isManager: false,
    pk: null
  }),
  mutations: {
    SAVE_USER_TEMP(state, credentialsData) {
      state.temp.username = credentialsData.username
      state.temp.nickname = credentialsData.nickname
      state.temp.password = credentialsData.password
      state.temp.passwordCheck = credentialsData.passwordCheck
    },
    SAVE_EMAIL_TEMP(state, credentialsEmailCode) {
      state.temp.email = credentialsEmailCode.email
      state.temp.code = credentialsEmailCode.code
      window.localStorage.setItem("temp_email", credentialsEmailCode.email)
    },
    SAVE_CURRENT_USER(state, loginData ) {
      state.user = loginData.data.username
      state.accessToken = loginData.data.accessToken
      state.refreshToken = loginData.data.refreshToken
      state.nickname = loginData.data.nickname
      state.isManager = loginData.data.isManager
      state.pk = loginData.data.id
      localStorage.setItem('current_userid', loginData.data.username)
      localStorage.setItem('current_nickname', loginData.data.nickname)
      localStorage.setItem('current_pk', loginData.data.id)
      localStorage.setItem('token', loginData.data.accessToken)
      localStorage.setItem('isManager', loginData.data.isManager)
      localStorage.setItem('firstlogin', false)
    },
    DELETE_TEMP_USER(state) {
      state.temp.username = ''
      state.temp.nickname = ''
      state.temp.password = ''
      state.temp.passwordCheck = ''
      state.temp.email = ''
      state.temp.code = ''
    },
    LOGOUT (state) {
      localStorage.removeItem('id')
      localStorage.removeItem('token')
      state.user = null
      state.accessToken = ''
      state.refreshToken = ''
      state.isLogin = false
    },
    // PATCH_USER_INFO (state, changedData) {
    //   if (changedData.profileIntro) {
    //     console.log('?????????')
    //     state.myIntro = changedData.profileIntro
    //   }
    //   if (changedData.profileImg) {state.myImg = changedData.profileImg}
    //   if (changedData.newNickname) {
    //     state.nickname = changedData.newNickname
    //     localStorage.setItem('current_nickname', changedData.newNickname)
    //   }
    //   // state.myImg = changedData.profileImg,
    //   // state.nickname = changedData.newNickname
    //   console.log('????????? ?????????', changedData)
    //   console.log(state.nickname)
    //   console.log(state.myImg)
    // }
    SAVE_MANAGER_STATE (state) {
      state.isManager = true
      localStorage.removeItem("isManager")
      localStorage.setItem("isManager", true)
    }
  },
  getters: {
    // signupTempInfoGet(state) {
    //   console.log(state.temp)
    //   return state.temp
    
    // },
    signupTempInfoGet: (state) => state.temp,
    // return signupTempInfoGet
  },
  actions: {
    // -------------------------------------------------------------------
    // ????????????
    // ????????? ????????????
    uniqueUsernameAction: async (commit, username) => {
        // console.log(username);
        // console.log('??????????????? ??? ???. ?????? api??? ??????')
        const response = await checkUsername({"username": username});
        // console.log(response)
        // console.log('api ?????? ??? ???')
        return response
    },
    // ????????? ????????????
    uniqueNicknameAction: async (commit, nickname) => {
        const response = await checkNickname({"nickname": nickname});
        return response
    },
    // ????????? ????????????
    sendEmailAction: async (commit, email) => {
      
      const data = JSON.stringify({"userEmail": email})
      const response = await checkEmail(
        data,
        (res) => {
          return res
        },
        (err) => {
          return err.response
      }
        );
      // console.log("????????? ????????????",response)
      return response
    },
    // ???????????? ?????? ?????????
    signupFirstAction(context, credentialsData) {
      // console.log(credentialsData)
      context.commit('SAVE_USER_TEMP', credentialsData)
      return true
    },
    // ???????????? ????????? 2
    signupSecondAction: async (context, credentialsEmailCode) => {
      console.log(credentialsEmailCode)
      context.commit('SAVE_EMAIL_TEMP', credentialsEmailCode)
      
      return true
    },
    signupFinalAction: async (context, signupUser ) => {
        const response = await requestRegister(
          signupUser,
          (res) => {
            return res
          },
          (err) => {
            return err.response
        })
        return response
    },
    signupInfoDelete: (context) => {
      context.commit('DELETE_USER_TEMP')
    },
    // -----------------------------------------------------------
    // ?????????
    loginAction: async (context, loginData ) => {
      // console.log("store??? ?????????", loginData)
      const response = await requestLogin(
        loginData,
        (res) => {
          context.commit('SAVE_CURRENT_USER', res)
          return res
        },
        (error) => {
          return error.response
        }
        )
      // console.log("store ?????? ??? ?????????", response)
      

      // localStorage.setItem('id', response.data.username)
      // localStorage.setItem('token', response.data.accessToken)
      return response

    },

    // ????????????
    logoutAction: async (context) => {
      context.commit('LOGOUT')
      localStorage.removeItem('current_userid')
      localStorage.removeItem('current_nickname')
      localStorage.removeItem('stat_title')
      localStorage.removeItem('temp_email')
      localStorage.removeItem('profile_id')
      localStorage.removeItem('profile_user')
      localStorage.removeItem('isManager')
      localStorage.removeItem('stat_num')
      localStorage.removeItem('firstlogin')
    },
    // ----------------------------------------------------------
    // ???????????????
    sendUsernameAction: async (commit, email) => {
      localStorage.setItem('temp_email', email)
      const data = JSON.stringify({"userEmail": email})
      const response = await requestUsername(
        data,
        (res) => {
          return res
        },
        (error) => {
          return error.response
        }
      );
      return response
      // console.log("????????? ????????????",response)
    },
    // ----------------------------------------------------------
    // ??????????????????
    patchUserInfoAction: (params, form) => {
      requestChangeInfo(
        params,
        form,
        ({data}) => {
          if (data.nickname) {
            localStorage.removeItem('current_nickname')
            localStorage.setItem('current_nickname', data.nickname)
          }
          Swal.fire({
            icon: "success",
            html: "????????? ??????????????? ?????????????????????."
          })
        },
        (err) => {
          console.log(err)
          Swal.fire({
            icon: "error",
            html: "???????????? ??? ?????? ??????????????????."
          })
      })
    },
    // ----------------------------------------------------------
    // ?????????
    managerApplyAction:  (context) => {
      // let test = null
      // return new Promise((resolve, reject) => {
        requestManager(
          (res) => {
            console.log(res)
            context.commit('SAVE_MANAGER_STATE')
            Swal.fire (
              {
                icon: "success",
                title: "???????????????!",
                html: "?????? ????????? ???????????? ????????? ??? ????????????."
              }
            )
            // resolve(200)
          },
          (error) => {
            console.log(error.response)
            // reject(500)
            Swal.fire (
              {
                icon: "warning",
                html: "?????? ????????? ???????????? ???????????????. <br> ??? ????????? ????????? ????????????."
              }
            )
          }
        );
        // setTimeout(() => {        
        //   console.log('?????????', test)
        //   return
        // }, 100);
      // }
      // )
      // console.log(result)
      // return result

    },
  // --------------------------------------------------------------
  // ????????????
    userDeleteAction: async (context, pwd) => {
      const response = requestDelete(
        pwd,
        (res) => {
          window.localStorage.clear()
          console.log(res)
          return res
        },
        (err) => {
          console.log(err.response.status)
          return 400
        })
      return response
    }
  },

};

export default userStore;
