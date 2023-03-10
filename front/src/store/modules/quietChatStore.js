import {
  getQuiet,
  createQuietChat,
  nosearchAllLikeroom,
  SearchCreatedRecentroom,
  PastMessage,
  UserStats,
  noSearchBookmarkRecentroom,
  SearchBookmarkRecentroom,
  nosearchBookmarkLikeroom,
  searchBookmarkLikeroom,
  getimgpath,
  noSearchallrecentroom,
  getHotQuietList,
  getOnlySearch,
  noSearchCreatedRecentroom,
  noSearchCreatedLikeroom,
  SearchAllLikeroom,
  SearchCreatedLikeroom,
  RoomLike,
} from '@/api/quietChatList';

const quietChatStore = {
  namespaced: true,
  state: () => ({
    open_chat_info: false,
    open_chat_id: null,
    quiet_list: null,
    hot_quiet_list: null,
    past_message: [],
    image_path: null,
  }),
  mutations: {
    GET_IMG_PATH(state, data) {
      state.image_path = data.data.urlPath;
    },
    open_chat_info(state) {
      state.open_chat_info = true;
    },
    close_chat_info(state) {
      (state.open_chat_info = false), (state.open_chat_id = null);
    },
    open_chat_id(state, id) {
      state.open_chat_id = id;
    },
    GET_QUIET_CHAT(state, quietChatList) {
      state.quiet_list = quietChatList.data;
    },
    GET_HOT_QUIET(state, hotQuietChat) {
      state.hot_quiet_list = hotQuietChat.data;
    },
    GET_ONLY_SEARCH(state, onlySearch) {
      state.quiet_list = onlySearch.data;
    },
    GET_NOSEARCH_CRATED_RECENT(state, noSearchCreatedRecent) {
      state.quiet_list = noSearchCreatedRecent.data;
    },
    GET_NOSEARCH_CREATED_LIKE(state, noSearchCreatedLike) {
      state.quiet_list = noSearchCreatedLike.data;
    },
    GET_SEARCH_ALL_LIKE(state, searchAllLike) {
      state.quiet_list = searchAllLike.data;
    },
    GET_SEARCH_CREATED_LIKE(state, searchCreatedLike) {
      state.quiet_list = searchCreatedLike.data;
    },
    GET_PAST_MESSAGE(state, pastMessage) {
      for (let i = 0; i < pastMessage.data.length; i++) {
        if (pastMessage.data[i].type == 'TALK') {
          let tmp = '';
          let sendtime = '';
          tmp += pastMessage.data[i].sendDate[11];
          tmp += pastMessage.data[i].sendDate[12];
          if (Number(tmp) > 12) {
            sendtime += '?????? ';
            sendtime += Number(tmp - 12);
          } else if (Number(tmp) == 12) {
            sendtime += '?????? ';
            sendtime += pastMessage.data[i].sendDate[11];
            sendtime += pastMessage.data[i].sendDate[12];
          } else {
            sendtime += '?????? ';
            sendtime += pastMessage.data[i].sendDate[11];
            sendtime += pastMessage.data[i].sendDate[12];
          }
          sendtime += ':';
          sendtime += pastMessage.data[i].sendDate[14];
          sendtime += pastMessage.data[i].sendDate[15];

          pastMessage.data[i].sendDate = JSON.parse(JSON.stringify(sendtime));
        } else {
          continue;
        }
      }
      pastMessage.data = [...pastMessage.data].reverse();
      state.past_message = pastMessage.data;
    },
    GET_NOSEARCH_BOOKMARK_RECENT(state, nosearchbookmarkrecent) {
      state.quiet_list = nosearchbookmarkrecent.data;
    },
    GET_SEARCH_BOOKMARK_RECENT(state, searchbookmarkrecent) {
      state.quiet_list = searchbookmarkrecent.data;
    },
    GET_NOSEARCH_BOOKMARK_LIKE(state, nosearchbookmarklike) {
      state.quiet_list = nosearchbookmarklike.data;
    },
    SEARCHBOOKMARKLIKE(state, searchbookmarklike) {
      state.quiet_list = searchbookmarklike.data;
    },
  },
  actions: {
    // ????????? ?????? ??????
    getImagePath({ commit }, img) {
      getimgpath(
        img,
        (data) => {
          commit('GET_IMG_PATH', data);
        },
        (err) => {
          console.log(err, '???????????? ??????');
        },
      );
    },
    // ?????? ??????
    postUserStat(params, datas) {
      UserStats(
        params,
        datas,
        (data) => {
          console.log('??????????????? ??????', data);
        },
        (err) => {
          console.log('??????????????? ??????', err);
        },
      );
    },
    getPastMessage({ commit }, params) {
      PastMessage(
        params,
        (data) => {
          // console.log(data);
          commit('GET_PAST_MESSAGE', data);
        },
        (err) => {
          console.log(err, '?????? ????????? ???????????? ??????');
        },
      );
    },
    getQuietList({ commit }) {
      getQuiet(
        (data) => {
          commit('GET_QUIET_CHAT', data);
        },
        (err) => {
          console.log(err, '????????? ????????? get ??????');
        },
      );
    },
    create_quiet_room(params, datas) {
      createQuietChat(
        params,
        datas,
        (data) => {
          console.log(data, '????????? ?????? ??????');
          // commit('CREATE_QUIET_CHAT', data)
        },
        (err) => {
          console.log(err, ' ????????? ?????? ??????');
        },
      );
    },
    getHotQuietList({ commit }) {
      getHotQuietList(
        (data) => {
          commit('GET_HOT_QUIET', data);
        },
        (err) => {
          console.log(err, '????????? ?????? ????????? get ??????');
        },
      );
    },

    // ?????????

    chatRoomLike(params, datas) {
      RoomLike(
        params,
        datas,
        (data) => {
          if (data.data == 'true') {
            console.log(data.data, '????????? ????????? ??????');
          } else {
            console.log(data.data, '????????? ????????? ??????');
          }
        },
        (err) => {
          console.log(err, '????????? ????????? ??????');
        },
      );
    },

    // ??????????????? api

    // ????????? ??????

    onlySearchRoom({ commit }, params) {
      getOnlySearch(
        params,
        (data) => {
          commit('GET_ONLY_SEARCH', data);
        },
        (err) => {
          console.log(err, 'only search ????????? get ??????');
        },
      );
    },
    noSearchAllRecent({ commit }) {
      noSearchallrecentroom(
        (data) => {
          commit('GET_ONLY_SEARCH', data);
        },
        (err) => {
          console.log(err, '??????x, ????????????, ???????????? ??????');
        },
      );
    },
    nosearchAllLike({ commit }) {
      nosearchAllLikeroom(
        (data) => {
          commit('GET_ONLY_SEARCH', data);
        },
        (err) => {
          console.log(err, '??????x ????????????, ???????????? ??????');
        },
      );
    },

    // ??????x +
    SearchCreatedRecent({ commit }, params) {
      SearchCreatedRecentroom(
        params,
        (data) => {
          commit('GET_NOSEARCH_CRATED_RECENT', data);
        },
        (err) => {
          console.log(err, '??????, ????????????, ??????????????? ??????');
        },
      );
    },
    noSearchCreatedRecent({ commit }, params) {
      noSearchCreatedRecentroom(
        params,
        (data) => {
          commit('GET_NOSEARCH_CRATED_RECENT', data);
        },
        (err) => {
          console.log(err, '??????x, ????????????, ??????????????? ??????');
        },
      );
    },
    noSearchCreatedLike({ commit }, params) {
      noSearchCreatedLikeroom(
        params,
        (data) => {
          commit('GET_NOSEARCH_CREATED_LIKE', data);
        },
        (err) => {
          console.log(err, '??????x, ????????????, ???????????? ??????');
        },
      );
    },
    searchAllLike({ commit }, params) {
      SearchAllLikeroom(
        params,
        (data) => {
          commit('GET_SEARCH_ALL_LIKE', data);
        },
        (err) => {
          console.log(err, '??????, ??????????????? ???????????? ??????');
        },
      );
    },
    searchCreatedLike({ commit }, params) {
      SearchCreatedLikeroom(
        params,
        (data) => {
          commit('GET_SEARCH_CREATED_LIKE', data);
        },
        (err) => {
          console.log(err, '??????, ?????? ??????, ???????????? ??????');
        },
      );
    },

    // ????????????

    noSearchBookmarkRecent({ commit }) {
      noSearchBookmarkRecentroom(
        (data) => {
          commit('GET_NOSEARCH_BOOKMARK_RECENT', data);
        },
        (err) => {
          console.log(err, '??????x, ????????????, ??????????????? ??????');
        },
      );
    },
    SearchBookmarkRecent({ commit }, params) {
      SearchBookmarkRecentroom(
        params,
        (data) => {
          commit('GET_SEARCH_BOOKMARK_RECENT', data);
        },
        (err) => {
          console.log(err, '??????, ????????????, ??????????????? ??????');
        },
      );
    },
    nosearchBookmarkLike({ commit }, params) {
      nosearchBookmarkLikeroom(
        params,
        (data) => {
          commit('GET_NOSEARCH_BOOKMARK_LIKE', data);
        },
        (err) => {
          console.log(err, '??????x, ????????????, ???????????? ??????');
        },
      );
    },
    searchBookmarkLike({ commit }, params) {
      searchBookmarkLikeroom(
        params,
        (data) => {
          commit('SEARCHBOOKMARKLIKE', data);
        },
        (err) => {
          console.log(err, '??????, ???????????? ???????????? ??????');
        },
      );
    },
  },
};

export default quietChatStore;
