<template>
  <div>
    <Post :posts="GetPosts"  />
  </div>
</template>

<script>
import Post from "/src/components/sidebar/AllPosts";

export default {
  name: "Index",

  components: {
    Post,
  },

  props: ["posts"],

  computed: {
    GetPosts: function () {
      return Object.values(this.posts);
    },

  },
};
</script>

<style scoped>

</style>
