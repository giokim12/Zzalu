import { getProfileUser } from "@/api/userAccount";
import { getProfileStat } from "@/api/profileAccount";
// import { reject } from "core-js/fn/promise";

const profileStore = {
  namespaced: true,
  state:()=> ({
    profile_user: {
      id: '',
      username: '',
      nickname: "",
      userEmail: "",
      enrollDate: "",
      isManager: "",
      profileMessage: null,
      profilePath: null,
      followingCnt: 0,
      followerCnt: 0,
      boardList: {
          boards: []
      },
      awardCount: {
        count1st: 0,
        count2nd: 0,
        count3nd: 0
      },
      stats: [],
      typeMsg: ''
    },
  }),
  mutations: {
    // 프로필 유저
    SET_PROFILE_USER(state, data) {
      state.profile_user.id = data.id;
      localStorage.setItem('profile_id', data.id)
      state.profile_user.username = data.username;
      localStorage.setItem('profile_user', data.username)
      state.profile_user.nickname = data.nickname;
      state.profile_user.profileMessage = data.profileMessage;
      state.profile_user.profilePath = data.profilePath;
      state.profile_user.profileMessage = data.profileMessage;
      state.profile_user.followingCnt = data.followingCnt;
      state.profile_user.followerCnt = data.followerCnt;
      state.profile_user.boardList = data.boardList;
      state.profile_user.enrollDate = data.enrollDate;
      state.profile_user.awardCount = data.awardCount;
      state.profile_user.isManager = data.isManager;
    },
    SET_PROFILE_STATS(state, data) {
      state.profile_user.stats = data

      if (state.profile_user.stats.data.length == 0) {
        state.profile_user.typeMsg = '는 어떤 사람일까?'
      } else {
      // console.log('이거이거', state.profile_user.stats.data[0].tag)
      if (state.profile_user.stats.data[0].tag == "게임") {
          state.profile_user.typeMsg = '는 컴퓨터랑 연애한다 ♥'
        } else if (state.profile_user.stats.data[0].tag == "애니") {
          state.profile_user.typeMsg = '정도면... 갓반인이죠'
        } else if (state.profile_user.stats.data[0].tag == "TV") {
          state.profile_user.typeMsg = '뭐해? 나 누워서 TV봐...'
        } else if (state.profile_user.stats.data[0].tag == "연예인") {
          state.profile_user.typeMsg = '는... 우리 최애 보려고 살아요 ♥'
        } else if (state.profile_user.stats.data[0].tag == "일상") {
          state.profile_user.typeMsg = '는... 감정표현의 대가'
        } else if (state.profile_user.stats.data[0].tag == "스포츠") {
          state.profile_user.typeMsg = '팀은 언제 이기냐?'
        } else if (state.profile_user.stats.data[0].tag == "동물") {
          state.profile_user.typeMsg = '의 신조: 귀여운 동물들이 세상을 구한다!'
        } else if (state.profile_user.stats.data[0].tag == "싸피") {
          state.profile_user.typeMsg = '는 진정한 개발자... 제법 멋져'
        } else if (state.profile_user.stats.data[0].tag == "음식") {
          state.profile_user.typeMsg = '는 살려고 먹는게 아니라 먹으려고 살아'
        }}
    }
  },
  getters: {
  },
  actions: {
    // 프로필 보기
    getProfileUser({ commit }, username) {
      getProfileUser(
        username,
        ({ data }) => {
          commit('SET_PROFILE_USER', data);
        },
        (error) => console.log(error),
      );
    },

    // 프로필 통계 보기
  getProfileStats({ commit }, params) {
    getProfileStat(
      params,
      (res) => {
        commit('SET_PROFILE_STATS', res)
      },
      (err) => {
        console.log('유저 스탯 요청 실패', err);
      }
    )
  }, 
/*   getProfileStats({ commit }, user_id) {
  return new Promise((resolve, reject) => {
    getProfileStat(
      user_id,
      (res) => {
        console.log('유저 보관함 요청 성공', res)
        commit('GET_USER_STORE_LIST', res)
        resolve(res);
      },
      (err) => {
        console.log('유저 보관함 요청 실패', err);
        reject(err);
      },
    )
   });
  }, */
  }
};

export default profileStore;