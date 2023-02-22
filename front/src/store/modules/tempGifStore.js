import { getAllTempGif, putTempGif, deleteTempGif, postTempGif } from '@/api/tempGif';
import Swal from 'sweetalert2';

const tempGifStore = {
  namespaced: true,
  state: () => ({
    // 임시 게시물 전체 조회
    temp_gif_list: [],
  }),
  mutations: {
    SET_TEMP_GIF_LIST(state, data) {
      state.temp_gif_list = data;
    },
  },
  getters: {
    getTempGifList: (state) => state.temp_gif_list,
  },
  actions: {
    // 임시 게시물 전체 조회
    getAllTempGif: ({ commit }, params) => {
      getAllTempGif(
        params,
        ({ data }) => {
          commit('SET_TEMP_GIF_LIST', data.tempGifList);
        },
        (error) => {
          console.log(error, '실패입니다.');
          Swal.fire({
            icon: 'error',
            html: error.response.data.message,
          });
        },
      );
    },
    // 임시 게시물 +1
    putTempGif: (params, temp_id) => {
      console.log(temp_id);
      return new Promise((resolve, reject) => {
        putTempGif(
          params,
          temp_id,
          ({ data }) => {
            console.log(data);
            resolve('sucess');
          },
          (error) => {
            Swal.fire({
              icon: 'error',
              html: error.response.data.message,
            });
            reject('fail');
          },
        );
      });
    },
    // 임시 게시물 삭제
    deleteTempGif: (params, temp_id) => {
      deleteTempGif(
        params,
        temp_id,
        ({ data }) => {
          console.log(data, '성공입니다.');
        },
        (error) => {
          Swal.fire({
            icon: 'error',
            html: error.response.data.message,
          });
        },
      );
    },
    // 임시 게시물 등록 요청
    postTempGif: (params, form) => {
      postTempGif(
        params,
        form,
        ({ data }) => {
          console.log(data, '성공입니다.');
        },
        (error) => {
          Swal.fire({
            icon: 'error',
            html: error.response.data.message,
          });
        },
      );
    },
  },
};
export default tempGifStore;
