import Vue from 'vue';
import Vuex from 'vuex'

Vue.use(Vuex)
export default new Vuex.Store({
  state: {
    accessToken: '',
    refreshToken: '',
  },
  mutations: {
    setToken(state, tokenInfo) {
      state.accessToken = tokenInfo.accessToken;
      state.refreshToken = tokenInfo.refreshToken;
      localStorage.setItem('accessToken', tokenInfo.accessToken);
      localStorage.setItem('refreshToken', tokenInfo.refreshToken);
    },
    removeToken(state) {
      state.accessToken = '';
      state.refreshToken = '';
      localStorage.removeItem('accessToken');
      localStorage.removeItem('refreshToken');
    }
  },
  actions: {
    
  },
  getters: {
    getRefreshToken: state => state.getRefreshToken,
  }
})
