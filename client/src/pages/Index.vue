<template>
    <div class="text-center">
        <h1>{{ msg }}</h1>
        <b-button variant="primary" @click="test">Test</b-button><br />
        <b-button variant="secondary" @click="logout">Logout</b-button>
    </div>
</template>
<script>
import { securedAxios } from '@/axios';
import { A_LOGOUT } from '@/store/modules/user';

export default {
  data() {
    return {
      msg: 'Welcome to vue!',
    };
  },
  methods: {
    async test() {
      const response = await securedAxios.get('/user/current');
      this.msg = `Hello ${response.data.username}!`;
    },
    logout() {
      this.$store.dispatch(A_LOGOUT)
        .then(() => {
          this.$router.push({
            path: '/login',
            query: { logout: true },
          });
        });
    },
  },
};
</script>
