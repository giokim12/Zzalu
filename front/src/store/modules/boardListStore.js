import { getStoreList, createBoard, putBoardData, getUserBoard, getBoard, nameChange, boardDel, removeBoardJjal, userstatistics } from "@/api/boardList"

const boardListStore = {
  namespaced: true,
  state: () => ({
    // 나의 보관함 리스트
    user_store_list: null,
    // 선택된 GIF Id
    select_gif_id: null,

    // 유저의 보드 리스트
    user_board_list: null,

    // 보드 디테일 리스트
    board_detail_list: null,

    // 수정시 보드 이름
    board_title : null
  }),
  mutations: {
    GET_USER_STORE_LIST(state, user_store_data) {
      state.user_store_list = user_store_data.data
    },
    SELECT_GIF(state, gif_id) {
      state.select_gif_id = gif_id
    },
    SELECT_POPULAR_GIF(state, gif_id) {
      state.select_gif_id = gif_id
    },
    SELECT_RECOMMEND_GIF(state, gif_id) {
      state.select_gif_id = gif_id
    },
    GET_USER_BOARD(state, user_board) {
      state.user_board_list = user_board.data
    },
    GET_BOARD(state, board_detail) {
      state.board_detail_list = board_detail.data
    },
    NAME_CHANGE(state, changed_name) {
      state.board_title = changed_name
    }
  },
  actions: {
    userStat({commit}, params) {
      userstatistics(
        commit,
        params,
        (res) => {
          console.log(res, '유저스탯전달성공');
        },
        (err) => {
          console.log(err, '유저스탯전달실패');
        }
      )
    },
    getUserStoreList({ commit }, params, data) {
      getStoreList(
        params,
        data,
        (res) => {
          commit('GET_USER_STORE_LIST', res)
        },
        (err) => {
          console.log('유저 보관함 요청 실패', err);
        }
      )
    },
    createStoreBoard(params, datas) {
      createBoard(
        params,
        datas,
        (data) => {
          console.log(data, '보드 생성 성공');
        },
        (err) => {
          console.log(err, '보드 생성 실패');
        }
      )
    },
    putBoardData(params, datas) {
      putBoardData(
        params,
        datas,
        (data) => {
          console.log(data, '보드 수정 성공');
        },
        (err) => {
          console.log(err, '보드 수정 실패');
        }
      )
    },
    getUserBoardList({ commit }, params) {
      getUserBoard(
        params,
        (data) => {
          // console.log(data, '보드 요청 성공');
          commit('GET_USER_BOARD', data)
        },
        (err) => {
          console.log(err, '보드 요청 실패');
        }
      )
    },
    getBoardData({ commit }, params) {
      getBoard(
        params,
        (data) => {
          commit('GET_BOARD', data)
        },
        (err) => {
          console.log(err, '보드 디테일 요청 실패');
        }
      )
    },
    boardNameChange({commit }, params) {
      nameChange(
        params,
        (data) => {
          commit('NAME_CHANGE', data)
        },
        (err) => {
          console.log(err, '보드 타이틀 변경 실패');
        }
      )
    },
    boardDelete(datas, params) {
      boardDel(
        datas,
        params,
        (data) => {
          console.log(data, '보드 삭제 성공');
        },
        (err) => {
          console.log(err, '보드 삭제 실패');
        }
      )
    },
    removeBoardJjal(datas, params) {
      removeBoardJjal(
        datas,
        params,
        (data) => {
          console.log(data, '보드 디테일 짤 삭제 성공');
        },
        (err) => {
          console.log(err, '보드 디테일 짤 삭제 실패');
        }
      )
    }
  },

}

export default boardListStore