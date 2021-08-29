import Vue from 'vue';
import BootstrapVue from 'bootstrap-vue';
import { mapState } from 'vuex';
import router from '@/router';
import store from '@/store';
import App from '@/App.vue';

import 'bootstrap-vue/dist/bootstrap-vue.css';

Vue.use(BootstrapVue);

Vue.config.productionTip = false;

Vue.mixin({
  computed: {
    ...mapState({
      loggedIn: (state) => state.user.loggedIn,
    }),
  },
});

new Vue({
  router,
  store,
  render: (h) => h(App),
}).$mount('#app');
