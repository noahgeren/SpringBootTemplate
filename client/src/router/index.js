import Vue from 'vue';
import VueRouter from 'vue-router';
import store from '@/store';
import Index from '@/pages/Index.vue';
import Login from '@/pages/Login.vue';
import NotFound from '@/pages/NotFound.vue';

Vue.use(VueRouter);

const routes = [
  // Lazy loading:
  // component: () => import('../pages/page.vue'),
  {
    path: '/',
    name: 'index',
    component: Index,
    meta: { requiresAuth: true },
  },
  {
    path: '/login',
    name: 'login',
    component: Login,
    beforeEnter(to, from, next) {
      if (!store.state.user.loggedIn) {
        next();
        return;
      }
      next('/');
    },
  },
  {
    path: '*',
    name: 'not-found',
    component: NotFound,
  },
];

const router = new VueRouter({
  mode: 'history',
  base: '/',
  routes,
});

router.beforeEach((to, _, next) => {
  if (to.matched.some((record) => record.meta.requiresAuth)) {
    if (!store.state.user.loggedIn) {
      next({
        name: 'login',
        query: { redirect: to.fullPath },
      });
    } else {
      next();
    }
  } else {
    next();
  }
});

export default router;
