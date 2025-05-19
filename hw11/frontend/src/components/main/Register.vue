<template>
  <div class="form">
    <div class="header">Register</div>
    <div class="body">
      <form @submit.prevent="onRegister">
        <div class="field">
          <div class="name">
            <label for="login">Login:</label>
          </div>
          <div class="value">
            <input id="login" name="login" v-model="login"/>
          </div>
        </div>
        <div class="field">
          <div class="name">
            <label for="name">Name:</label>
          </div>
          <div class="value">
            <textarea id="name" name="name" v-model="name"></textarea>
          </div>
        </div>


        <div class="field">
          <div class="name">
            <label for="password">Password:</label>
          </div>
          <div class="value">
            <input id="password" name="password" v-model="password"/>
          </div>
        </div>

        <div class="error">{{ error }}</div>
        <div class="button-field">
          <input type="submit" value="Register">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
export default {
  name: "Register",
  data: function () {
    return {
      login: "",
      name: "",
      password: "",
      error: ""
    }
  },
  methods: {
    onRegister () {

      if (/\s/.test(this.name)) {
        this.error = "Ошибка: Имя не должно содержать пробелов.";
        return;
      }

      if (/\s/.test(this.login)) {
        this.error = "Ошибка: Логин не должен содержать пробелов.";
        return;
      }

      this.error = "";
      this.$root.$emit("onRegister", this.login, this.name,this.password);
    }
  },
  beforeMount() {
    this.$root.$on("onRegisterValidationError", error => this.error = error);
  }
}
</script>

<style scoped>

</style>