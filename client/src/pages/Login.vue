<template>
    <b-row>
        <b-col cols="12" lg="4" offset-lg="4"
          class="bg-light my-4 py-3 border border-primary rounded">
            <h1 class="pb-3 border-bottom">Login</h1>
            <b-alert :show="loggedOut" variant="success" dismissible>
              You have been logged out.
            </b-alert>
            <b-alert :show="expired" variant="danger" dismissible>
              Your session has expired. Please login again.
            </b-alert>
            <b-alert v-model="error" variant="danger" dismissible>
              Incorrect username or password.
            </b-alert>
            <b-form @submit.prevent="login">
                <b-form-group
                    label="Username:"
                    label-for="username">
                    <b-form-input v-model="username" id="username" name="username"></b-form-input>
                </b-form-group>
                <b-form-group
                    label="Password:"
                    label-for="password">
                    <b-form-input type="password" v-model="password"
                      id="password" name="password"></b-form-input>
                </b-form-group>
                <div class="text-center">
                    <b-button type="submit" variant="primary">Login</b-button>
                </div>
            </b-form>
        </b-col>
    </b-row>
</template>
<script>
import { A_LOGIN } from '@/store/modules/user';

export default {
  data() {
    return {
      username: null,
      password: null,
      error: false,
    };
  },
  methods: {
    async login() {
      this.$emit('loading', true);
      try {
        await this.$store.dispatch(A_LOGIN, { username: this.username, password: this.password });
        const { redirect } = this.$route.query;
        this.$router.push(redirect ?? '/');
      } catch (err) {
        this.error = true;
      } finally {
        this.$emit('loading', false);
      }
    },
  },
  computed: {
    loggedOut() {
      return !!this.$route.query.logout;
    },
    expired() {
      return !!this.$route.query.expired;
    },
  },
};
</script>
