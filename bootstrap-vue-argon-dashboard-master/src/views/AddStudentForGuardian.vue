<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Register Your Child</h1>
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
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="First Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="First Name"
                              v-model= "student.firstName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Last Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Last Name"
                              v-model="student.lastName">
                  </base-input>
                  
                  <base-input alternative
                              class="mb-3"
                              name="School"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="School"
                              v-model="student.school">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="Contact Number"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Contact Number"
                              v-model="student.contactNumber">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Email"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Email"
                              v-model="student.email">
                  </base-input>
                  
                  <base-input alternative
                              class="mb-3"
                              name="birthDate"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Birth Date Format YYYY-MM-DD"
                              v-model="student.birthDate">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add student</base-button>
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
import studentDataService from '../services/StudentDataService'
export default{
  name: 'student',
  data () {
    return {
      student: {
        id: null,
        firstName: '',
        lastName: '',
        contactNumber: '',
        email: '',
        school: '',
        birthDate: '',
        guardian: null
      }
    }
  },
  methods: {
    onSubmit () {
      var student = {
        firstName: this.student.firstName,
        lastName: this.student.lastName,
        contactNumber: this.student.contactNumber,
        email: this.student.email,
        school: this.student.school,
        birthDate: this.student.birthDate
      }
      studentDataService.createForGuardian(student, localStorage.getItem("user")).then(response => {
        this.student.id = response.data.id
        console.log(response.data)
        alert("Student Added")
      }).catch(e => {
        alert("Something went wrong! Please contact site administrator!")
        console.log(e)
      })
    }
  },
  mounted() {
    if(localStorage.getItem("role") !=="guardian" ){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
  }
}
</script>
