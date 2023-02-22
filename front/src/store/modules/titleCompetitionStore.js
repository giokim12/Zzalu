import { getComments, getBestComments, getNestedComments, getTitleCompetition } from '@/api/titleCompetition';

const titleCompetitionStore = {
  namespaced: true,
  state: () => ({
    // 제목학원
    open_date: '',
    total_comment_cnt: 0,
    title_competition_id: 0,
    zzal_url: '',
    state: '',

    // 댓글
    sort_type: 'POPULAR',
    comments: [],
    last_comment_id: Number.MAX_SAFE_INTEGER,

    // 대댓글
    new_nested_comments: [],

    // 대댓글 입력
    isNested: false,
    comment_writer: {
      comment_id: '',
      nickname: '',
    },

    // 소켓 관련
    is_top: true,
    socket_comment_cnt: 0,
    socket_comments: [],
  }),
  getters: {
    getDate: (state) => state.open_date,
    getCommentCount: (state) => state.total_comment_cnt,
    getComments: (state) => state.comments,
    getLastCommentId: (state) => state.last_comment_id,
    getState: (state) => state.state,
  },
  mutations: {
    INIT_STORE_DATA(state) {
      state.open_date = '';
      state.total_comment_cnt = 0;
      state.title_competition_id = 0;
      state.zzal_url = '';
      state.state = '';

      // 댓글
      state.sort_typ = 'POPULAR';
      state.comments = [];
      state.last_comment_id = Number.MAX_SAFE_INTEGER;

      // 대댓글
      state.new_nested_comments = [];
      state.isNested = false;
      state.comment_writer = {
        comment_id: '',
        nickname: '',
      };

      // 소켓 관련
      state.is_top = true;
      state.socket_comment_cnt = 0;
      state.socket_comments = [];
    },

    INIT_NESTED_DATA(state) {
      state.isNested = false;
      state.comment_writer = {
        comment_id: '',
        nickname: '',
      };
    },
    // 날짜 바꾸기
    SET_OPEN_DATE(state, open_date) {
      state.open_date = open_date;
    },
    // 제목학원
    SET_TITLE_COMPETITION(state, title_competition_data) {
      state.title_competition_id = title_competition_data.titleHakwonId;
      state.total_comment_cnt = title_competition_data.totalComment;
      state.zzal_url = title_competition_data.zzalUrl;
      state.state = title_competition_data.state;
      // state.state = 'abc';
    },

    // 댓글 sort 수정하기
    MODIFY_SORT_TYPE(state, sort_type) {
      state.sort_type = sort_type;
      if (sort_type == 'LATEST') {
        state.last_comment_id = Number.MAX_SAFE_INTEGER;
      } else if (sort_type == 'CHRONOLOGICAL') {
        state.last_comment_id = Number.MIN_SAFE_INTEGER;
      }
      state.comments = [];
    },

    // 위로 올라가기
    SET_IS_TOP(state) {
      state.is_top = !state.is_top;
    },

    PUSH_SOCKET_COMMENTS(state) {
      state.comments.unshift(...state.socket_comments.reverse());
    },

    SET_SOCKET_DATA_INIT(state) {
      state.socket_comments.splice(0);
      state.socket_comment_cnt = 0;
    },

    // 소켓 관련 저장
    ADD_SOCKET_COMMENT_CNT(state) {
      state.socket_comment_cnt += 1;
    },
    ADD_SOCKET_COMMENT(state, comment) {
      state.socket_comments.push(comment);
    },

    PLUS_TOTAL_COMMENT_CNT(state) {
      state.total_comment_cnt += 1;
    },

    // 댓글 추가하기
    ADD_COMMENTS(state, new_comments) {
      state.comments.push(...new_comments);
    },

    // 마지막으로 읽어온 댓글
    SET_LAST_COMMENT_ID(state, last_comment_id) {
      state.last_comment_id = last_comment_id;
    },

    // 대댓글
    SET_NEW_NESTED_COMMENTS(state, new_nested_comments) {
      state.new_nested_comments = new_nested_comments;
    },

    SET_NEW_NESTED_COMMENTS_EMPTY(state) {
      state.new_nested_comments.splice(0);
    },

    // 작성관련
    // 댓글

    PUSH_COMMENT(state, comment) {
      state.comments.unshift(comment);
    },

    // 대댓글 작성 관련
    SET_COMMENT_WRITER(state, comment_writer) {
      state.comment_writer.comment_id = comment_writer.comment_id;
      state.comment_writer.nickname = comment_writer.nickname;
      state.isNested = true;
    },

    DELETE_COMMENT_WRITER(state) {
      state.comment_writer.id = '';
      state.comment_writer.nickname = '';
      state.isNested = false;
    },
    DELETE_COMMENT(state, comment_index) {
      state.comments.splice(comment_index, 1);
    },

    SET_IS_TOP_TRUE(state) {
      state.is_top = true;
    },
  },
  actions: {
    async initStoreData({ commit }) {
      commit('INIT_STORE_DATA');
    },

    async initNestedData({ commit }) {
      commit('INIT_NESTED_DATA');
    },
    async init({ dispatch }, data) {
      await dispatch('getTitleCompetition', data.open_date);
      await dispatch('getBestComments');
      await dispatch('setLastCommentId', Number.MAX_SAFE_INTEGER);
      await dispatch('modifySortType', 'POPULAR');
    },
    /* async init({ state, dispatch }, data) {
      await dispatch('getTitleCompetition', data.open_date);
      await dispatch('getBestComments');
      await dispatch('setLastCommentId', Number.MAX_SAFE_INTEGER);
      // await dispatch('setLastCommentId', state.comments[state.comments.length - 1].commentId);
    }, */
    // 제목학원 가져오기
    async getTitleCompetition({ commit }, open_date) {
      return new Promise((resolve, reject) => {
        commit('SET_OPEN_DATE', open_date);
        getTitleCompetition(
          open_date,
          ({ data }) => {
            commit('SET_TITLE_COMPETITION', data);
            resolve();
          },
          (error) => {
            console.log(error);
            reject();
          },
        );
      });
    },

    // 댓글 sort 수정하기
    async modifySortType({ commit, dispatch }, sort_type) {
      await commit('MODIFY_SORT_TYPE', sort_type);
      if (sort_type == 'POPULAR') {
        await dispatch('getBestComments');
      } else {
        await dispatch('getComments', 10);
      }
    },

    // 위로 올라가기
    setIsTop({ commit }) {
      commit('SET_IS_TOP');
    },

    setIsTopTrue({ commit }) {
      return new Promise(() => {
        commit('SET_IS_TOP_TRUE');
      });
    },

    // 댓글
    async getComments({ commit, state, dispatch }, size) {
      const params = {
        lastCid: state.last_comment_id,
        limit: size,
        sort: state.sort_type,
      };

      await getComments(
        state.title_competition_id,
        params,
        ({ data }) => {
          commit('ADD_COMMENTS', data);
        },
        (error) => {
          console.log(error);
        },
      );
      dispatch('setLastCommentId', state.comments[state.comments.length - 1].commentId);
    },
    async getBestComments({ commit, state }) {
      const params = {
        limit: 50,
        sort: state.sort_type,
      };
      await getBestComments(
        state.title_competition_id,
        params,
        ({ data }) => {
          commit('ADD_COMMENTS', data);
        },
        (error) => {
          console.log(error);
        },
      );
    },

    async setLastCommentId({ commit }, comment_id) {
      return new Promise((resolve) => {
        commit('SET_LAST_COMMENT_ID', comment_id);
        resolve();
      });
    },

    // 대댓글
    getNestedCommentList({ commit }, datas) {
      const comment_id = datas.comment_id;
      const params = {
        lastCid: datas.lastCid,
        limit: datas.limit,
        sort: 'LATEST',
      };
      return new Promise((resolve, reject) => {
        getNestedComments(
          comment_id,
          params,
          ({ data }) => {
            commit('SET_NEW_NESTED_COMMENTS', data);
            resolve();
          },
          (error) => {
            console.log(error);
            reject();
          },
        );
      });
    },
    setNestedCommentList({ commit }) {
      return new Promise((resolve) => {
        commit('SET_NEW_NESTED_COMMENTS_EMPTY');
        resolve();
      });
    },

    // 작성 관련
    // 댓글
    pushComment({ commit }, comment) {
      commit('PUSH_COMMENT', comment);
    },
    // 대댓글
    pushNestedComment({ commit }, nested_comment) {
      commit('PUSH_NESTED_COMMENT', nested_comment);
    },

    // 대댓글 작성
    writeNestedComment({ commit }, comment_writer) {
      commit('SET_COMMENT_WRITER', comment_writer);
    },
    // 대댓글 작성 취소
    deleteCommentWriter({ commit }) {
      return new Promise((resolve) => {
        commit('DELETE_COMMENT_WRITER');
        resolve();
      });
    },
    // 삭제 관련
    deleteComment({ commit }, comment_index) {
      commit('DELETE_COMMENT', comment_index);
    },

    // 소켓 통신 관련

    plusTotalCommentCnt({ commit }) {
      commit('PLUS_TOTAL_COMMENT_CNT');
    },
    // 새로운 댓글 추가
    addSocketCommentCnt({ commit }) {
      commit('ADD_SOCKET_COMMENT_CNT');
    },
    addSocketComment({ commit }, comment) {
      commit('ADD_SOCKET_COMMENT', comment);
    },
    setSocketDataInit({ commit }) {
      commit('SET_SOCKET_DATA_INIT');
    },
    pushSocketComments({ commit, dispatch }) {
      return new Promise(() => {
        commit('PUSH_SOCKET_COMMENTS');
      }).then(dispatch('setSocketDataInit'));
    },
  },
};

export default titleCompetitionStore;
