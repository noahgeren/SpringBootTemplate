import { plainAxios } from '@/axios';

const M_LOGGED_IN = 'M_LOGGED_IN';

const A_LOGIN = 'A_LOGIN';
const A_SIGNUP = 'A_SIGNUP';
const A_LOGOUT = 'A_LOGOUT';

const user = {
  state: {
    loggedIn: localStorage.loggedIn === 'true',
  },
  mutations: {
    [M_LOGGED_IN](state, loggedIn) {
      state.loggedIn = loggedIn;
      localStorage.loggedIn = loggedIn;
    },
  },
  actions: {
    async [A_LOGIN]({ commit }, payload) {
      const loggedIn = await plainAxios.post('/login', payload);
      commit(M_LOGGED_IN, true);
      return loggedIn;
    },
    async [A_SIGNUP](_, payload) {
      console.log(`TODO: ${payload}`);
    },
    async [A_LOGOUT]({ commit }) {
      await plainAxios.get('/logout');
      commit(M_LOGGED_IN, false);
    },
  },
  getters: {},
};

export {
  user,
  A_LOGIN,
  A_SIGNUP,
  A_LOGOUT,
};
