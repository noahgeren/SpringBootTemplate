<template>
    <router-view @loading="setLoading"/>
</template>
<script>
export default {
  data() {
    return {
      loading: false,
    };
  },
  methods: {
    setLoading(loading) {
      this.loading = loading;
    },
  },
};
</script>
