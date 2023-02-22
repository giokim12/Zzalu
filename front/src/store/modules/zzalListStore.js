import { getPopularGIF, getRecommendGIF, getFirstRandomGIF, getMoreRandomGIF, getSearchZzal, getDetailData, putRequest } from "@/api/zzalList"

const zzalListStore = {
  namespaced: true,
  state: () => ({
    popular_gif_data: null,
    recommend_gif_data: null,
    random_gif_data: null,
    search_gif_data: null,
    jjal_data: null,
  }),
  mutations: {
    GET_POPULAR_GIF(state, PopulardataGIFdata) {
      state.popular_gif_data = PopulardataGIFdata.data
    },
    GET_RECOMMEND_GIF(state, RecommendGIFdata) {
      state.recommend_gif_data = RecommendGIFdata.data
    },

    // 랜덤 짤 가져오기
    GET_FIRST_RANDOM_GIF(state, RandomFirstGIFdata) {
      state.random_gif_data = RandomFirstGIFdata.data
      state.search_gif_data = [];
    },
    GET_MORE_RANDOM_GIF(state, RandomMoreGIFdata) {
      state.random_gif_data = [...state.random_gif_data, ...RandomMoreGIFdata.data]
    },

    // 서치 짤 가져오기
    GET_SEARCH_JJAL(state, SearchJjal) {
      state.random_gif_data = []
      if (SearchJjal.data.length == 0) {
        state.search_gif_data = [[]]
      }else if (SearchJjal.data.length < 30) {
        state.search_gif_data = [SearchJjal.data]
      } else {
        // 2차원배열로 저장
        let count = Math.ceil(SearchJjal.data.length / 30)
        let arr = []
        let start = 0
        let end = 30

        for (let i = 0; i < count; i++) {
          let tmp = SearchJjal.data.slice(start,end)
          arr.push(tmp)
          start += 30
          end += 30
        }
        state.search_gif_data = arr
      }
    },


    GET_JJAL_DETAIL(state, JjalDetail) {
      state.jjal_data = JjalDetail.data
    }
  },
  actions: {
    getPopularGIFList({ commit }) {
      getPopularGIF(
        (data) => {
          commit('GET_POPULAR_GIF', data)
        },
        (err) => {
          console.log(err, '인기짤 get 실패');
        }
      )
    },
    getRecommendGIFList({ commit }) {
      getRecommendGIF(
        (data) => {
          commit('GET_RECOMMEND_GIF', data)
        },
        (err) => {
          console.log(err, '추천짤 get 실패');
        }
      )
    },
    getFirstRandomGIFList({ commit }) {
      getFirstRandomGIF(
        (data) => {
          commit('GET_FIRST_RANDOM_GIF', data)
        },
        (err) => {
          console.log(err, '서치 모달 데이터 받지못함');
        }
      )
    },
    getMoreRandomGIFLIST({ commit }, params) {
      getMoreRandomGIF(
        params,
        (data) => {
          commit('GET_MORE_RANDOM_GIF', data)
        },
        (err) => {
          console.log(err, '모얼실패');
        }
      )
    },
    getSearchZzalData({ commit }, params) {
      getSearchZzal(
        params,
        (data) => {
          commit('GET_SEARCH_JJAL', data)
        },
        (err) => {
          console.log(err, '짤 검색 실패');
        }
      )
    },
    getDetailData({ commit }, params) {
      getDetailData(
        params,
        (data) => {
          commit('GET_JJAL_DETAIL', data)
        },
        (err) => {
          console.log(err, '짤 디테일 get 실패');
        }
      )
    },
    putRequestEdit({commit}, params) {
      putRequest(
        commit,
        params,
        (data) => {
          console.log(data, '짤 수정 요청 성공');
        },
        (err) => {
          console.log(err,'짤 수정 요청 실패');
        }
      )
    }
  }
}

export default zzalListStore;