<template>
  <div>
    <h2 class="post-title">
      {{ post.title }}
    </h2>
    <div class="post-body">{{ post.text }}</div>


  </div>
</template>

<style>
.post-title {
  font-size: 24px;
  color: #333;
}

.post-body {
  font-size: 16px;
  color: #555;
  margin-top: 10px;
}

.comments-section {
  margin-top: 20px;
}

.comments-header {
  font-size: 18px;
  color: #007BFF;
}


.comments-section p {
  white-space: pre-wrap;
}
</style>

<script>
export default {
  name: "Post",
  props: ["post"],
};
</script>

<style>
.post-title {
  font-size: 24px;
  color: #333;
}

.post-body {
  font-size: 16px;
  color: #555;
  margin-top: 10px;
  white-space: pre-wrap; /* Apply white-space: pre-wrap; to post text */
}

.comments-section {
  margin-top: 20px;
}

.comments-header {
  font-size: 18px;
  color: #007BFF;
}

.comments-section p {
  white-space: pre-wrap;
}
</style>

<script>
export default {
  name: "Post",
  props: ["post"],
};
</script>

<style scoped>
section {
  margin-bottom: 1rem;
}

footer {
  margin-top: 0.5rem;
  color: #888;
}
</style>