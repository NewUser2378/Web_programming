<template>
  <aside>
    <Post v-for="post in posts" :post="post"  :key="post.id" />

  </aside>
</template>

<script>
import Post from "./Post";

export default {
  name: "AllPosts",
  components: {
    Post,
  },
  props: ["posts"],
  methods: {


  },
};
</script>

<style scoped>
</style>
