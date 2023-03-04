<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Welcome!</h1>
              <p class="text-lead text-white">Login to access Widumanga Online!</p>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1"
             xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            
            <b-card-body class="px-lg-5 py-lg-5">
              <div class="text-center text-muted mb-4">
                <small>Sign in with credentials</small>
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="Email"
                              :rules="{required: true, email: true}"
                              prepend-icon="ni ni-email-83"
                              placeholder="Email"
                              v-model="model.email">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="Password"
                              :rules="{required: true, min: 3}"
                              prepend-icon="ni ni-lock-circle-open"
                              type="password"
                              placeholder="Password"
                              v-model="model.password">
                  </base-input>

                  <b-form-checkbox v-model="model.rememberMe">Remember me</b-form-checkbox>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Sign in</base-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
          </b-row>
        </b-col>
      </b-row>
    </b-container>
  </div>
</template>
<script>
import UserDataService from '../../services/UserDataService'
  export default {
    data() {
      return {
        model: {
          email: '',
          password: '',
          rememberMe: false
        },
        user: {
          id: '',
          username: '',
          password: '',
          role: ''
        },
      };
    },
    methods: {
      onSubmit() {
        this.getUser()        
      },
      getUser(){
        UserDataService.getByEmail(this.model.email).then(response => {
        this.user.id = response.data.id
        this.user.username = response.data.username
        this.user.password = response.data.password
        this.user.role = response.data.role
        this.userRole = response.data.role
        if(this.user !== null){
          if(this.user.role ==="guardian"){
            localStorage.setItem('role', 'guardian')
            localStorage.setItem('user', this.user.username)
          }
          if(this.user.role ==="manager"){
            localStorage.setItem('role', 'manager')
            localStorage.setItem('user', this.user.username)
          }
          if(this.user.role ==="admin"){
            localStorage.setItem('role', 'admin')
            localStorage.setItem('user', this.user.username)
          }
          if(this.user.role ==="teacher"){
            localStorage.setItem('role', 'teacher')
            localStorage.setItem('user', this.user.username)
          }
          if(this.user.role === "student"){
            localStorage.setItem('role', 'student')
            localStorage.setItem('user', this.user.username)
          }
          if(this.user.role === 'admin') {
            this.$router.push({ name: 'guardians' })
          }
          if(this.user.role === 'guardian'){
            this.$router.push({ name: 'addStudentClass' })
          }
          if(this.user.role === 'manager'){
            this.$router.push({ name: 'addHall' })
          }
          if(this.user.role === 'teacher'){
            this.$router.push({ name : 'teacherHome'})
          }
          if(this.user.role === 'student'){
            this.$router.push({ name: 'studentHome' })
          }
        } else{
          alert("Failed to Login! Your username or password might be incorrect, or your account has not been verified")
        }
      })
        .catch(e => {
          alert("Failed to Login! Your username or password might be incorrect, or your account has not been verified")
          console.log(e)
        })
      }
    },
    mounted() {
      localStorage.setItem('role','');
      localStorage.setItem('user', '')
    }
  };
</script>
