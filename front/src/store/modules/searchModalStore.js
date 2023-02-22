const searchModalStore = {
  namespaced: true,
  state: () => ({
    //하단내브바 서치모달
    open_search_modal: false,
    open_list_modal: false,
    select_jjal_num: null,
    select_popular_jjal_num: null,
    select_recommend_jjal_num: null,
  }),
  mutations: {
    //하단내브바 서치모달
    open_search_modal(state) {
      state.open_search_modal ? state.open_search_modal = false : state.open_search_modal = true, state.select_jjal_num = null;
      state.select_popular_jjal_num = null;
      state.select_recommend_jjal_num = null;
    },
    open_list_modal(state) {
      state.open_list_modal = true
    },
    close_list_modal(state) {
      state.open_list_modal = false
    },
    send_select_jjal_num(state, e) {
      state.select_jjal_num = e
    },
    send_select_popular_jjal_num(state, e) {
      state.select_popular_jjal_num = e
    },
    send_select_recommend_jjal_num(state, e) {
      state.select_recommend_jjal_num = e
    },
    default_select_num(state) {
      state.select_popular_jjal_num = null;
      state.select_recommend_jjal_num = null;
    },
    default_popular_num(state) {
      state.select_popular_jjal_num = null;
    },
    default_recommend_num(state) {
      state.select_recommend_jjal_num = null;
    }
  },
  actions: {},
  getters: {},
};

export default searchModalStore;
