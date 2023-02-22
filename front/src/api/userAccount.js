import { apiInstance, authApiInstance, formApiInstance } from './index.js';
const api = apiInstance();
const auth_api = authApiInstance();
const form_api = formApiInstance();

// 회원가입 (1)
const requestRegister = (payload,res,err) => {
    return api.post(`/members/signup`, payload)
    .then(res).catch(err)
}

// 로그인 (2)
const requestLogin = (payload, res, err) => {
    return api.post(`/members/login`, payload)
    .then(res).catch(err)
}

// 비번변경 (3)
const requestChangePassword  = (payload, res, err) => {
    return api.patch(`/members/pass`, payload)
    .then(res).catch(err)
}

// 프로필 보기 (4)
const getProfileUser = (username, success, fail) => {
    api.get(`/members?username=${username}`).then(success).catch(fail);
}

// 아이디 중복체크 (5)
const checkUsername = (payload) => {
    return api.get(`/members/exists`, {params: payload})
}

// 닉네임 중복체크 (6)
const checkNickname = (payload) => {
    return api.get(`/members/exists`, {params: payload})
}

// 회원정보변경 (7)
const requestChangeInfo = (params, form, res, err) => {
    const tempData = new FormData();
    if (form.newNickname != null) {
        tempData.append("nickname", form.newNickname)
    }
    if (form.profileIntro != null) {
        tempData.append("profileMessage", form.profileIntro)
    }
    if (form.send_image != null) {
        tempData.append("profileMultipartFile", form.send_image)
    }
    form_api.patch(`/members`, tempData).then(res).catch(err)
}

// 계정 삭제 (8)
const requestDelete = (pwd, res, err) => {
    const data = JSON.stringify({"rawPassword": pwd})
    auth_api.delete(`/members`, {data: data}).then(res).catch(err)
}

// 매니저권한 요청 (12)
const requestManager = (res, err) => {
    auth_api.post(`/members/manager`).then(res).catch(err)
}

// -----------------------------------------------------------
// 회원가입시 이메일인증 (2-1)
const checkEmail = (data,res,err) => {
    return api.post(`/mail/signup`, data )
    .then(res).catch(err)
}

// 비번변경시 이메일인증 (2-2)
const changePasswordEmail  = (payload, res, err) => {
    return api.post(`/members/pass`, payload)
    .then(res).catch(err)
}

// 아이디찾기시 이메일 인증 (2-3)
const requestUsername = (data,res,err) => {
    return api.post(`/mail/username`, data )
    .then(res).catch(err)
}



export { checkUsername, checkNickname, checkEmail, requestRegister, requestLogin, requestUsername, getProfileUser, requestDelete, requestChangePassword, requestChangeInfo, requestManager, changePasswordEmail }
