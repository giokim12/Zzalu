import { follow, unfollow, getFollowingList, getFollowerList, checkFollowState } from '@/api/follow';
import Swal from 'sweetalert2';
const followStore = {
  namespaced: true,
  state: () => ({
    following_list: [],
    follower_list: [],
  }),
  mutations: {
    SET_FOLLOWING_LIST(state, data) {
      state.following_list = data;
    },
    SET_FOLLOWER_LIST(state, data) {
      state.follower_list = data;
    },
    MODIFY_FOLLOWING_STATE(state, index) {
      // console.log(index);

      // console.log(state.following_list[index].followState);
      state.following_list[index].followState = !state.following_list[index].followState;
      // console.log(state.following_list[index].followState);
    },
    MODIFY_FOLLOWER_STATE(state, index) {
      state.follower_list[index].followState = !state.follower_list[index].followState;
    },
  },
  getters: {
    getFollowings: (state) => state.following_list,
    getFollowers: (state) => state.follower_list,
  },
  actions: {
    modifyFollowingState: ({ commit }, index) => {
      commit('MODIFY_FOLLOWING_STATE', index);
    },
    modifyFollowerState: ({ commit }, index) => {
      commit('MODIFY_FOLLOWER_STATE', index);
    },

    // 팔로잉 리스트 가져오기
    getFollowingList: ({ commit }, member_id) => {
      return new Promise(() => {
        // console.log('member_id=', member_id);
        getFollowingList(
          member_id,
          ({ data }) => {
            // console.log(data);
            // console.log('팔로잉리스트: ' + data);
            commit('SET_FOLLOWING_LIST', data.followings);
          },
          (error) => {
            console.log(error);
          },
        );
      });
    },

    // 팔로워 리스트 가져오기
    getFollowerList: ({ commit }, member_id) => {
      return new Promise(() => {
        getFollowerList(
          member_id,
          ({ data }) => {
            // console.log(data);
            // console.log('팔로잉리스트: ' + data.followers);
            commit('SET_FOLLOWER_LIST', data.followers);
          },
          (error) => console.log(error),
        );
      });
    },

    // 팔로우 요청
    requsetFollow(params, your_id) {
      // console.log(your_id);
      return new Promise((resolve, reject) => {
        follow(
          params,
          your_id,
          ({ data }) => {
            console.log(data, '성공입니다.');
            resolve('sucess');
          },
          (error) => {
            console.log(error, '실패입니다.');
            Swal.fire({
              icon: 'error',
              html: error.response.data.message,
            });
            reject('fail');
          },
        );
      });
    },

    // 언팔로우 요청
    requestUnfollow(params, your_id) {
      return new Promise((resolve, reject) => {
        unfollow(
          params,
          your_id,
          ({ data }) => {
            console.log(data, '성공입니다.');
            resolve('1');
          },
          (error) => {
            console.log(error, '실패입니다.');
            Swal.fire({
              icon: 'error',
              html: error.response.data.message,
            });
            reject('2');
          },
        );
      });
    },

    // 팔로우 상태 확인 > 못함
    checkFollowState(your_id) {
      checkFollowState(
        your_id,
        ({ data }) => {
          console.log(data);
        },
        (error) => console.log(error),
      );
    },
  },
};

export default followStore;
