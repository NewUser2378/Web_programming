<template>
  <div>
    <div class="users datatable">
      <div class="caption">User</div>
      <table>
        <thead>
        <tr>
          <th>Id</th>
          <th>Login</th>
          <th>Names</th>

        </tr>
        </thead>
        <tbody>
        <template v-if="users.length === 0">
          <tr class="noData">
            <td colspan="4">No data</td>
          </tr>
        </template>
        <template v-else>
          <tr v-for="user in users" :key="user.id">
            <td class="id">{{ user.id }}</td>
            <td class="login">{{ user.login }}</td>
            <td class="name">{{ user.name }}</td>
          </tr>
        </template>
        </tbody>
      </table>
    </div>
  </div>
</template>

<script>
export default {
  name: "Users",
  props: ["users"],

}
</script>

<style scoped>

</style>