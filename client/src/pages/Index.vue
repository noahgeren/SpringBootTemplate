<template>
    <div class="text-center">
        <h1>Welcome to vue!</h1>
        <b-button variant="secondary" @click="logout">Logout</b-button>
    </div>
</template>
<script>
import { A_LOGOUT } from '@/store/modules/user';

export default {
  data() {
    return {};
  },
  methods: {
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
