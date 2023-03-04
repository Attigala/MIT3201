<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Add Teacher</h1>
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
                              v-model= "teacher.firstName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Last Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Last Name"
                              v-model="teacher.lastName">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="Contact Number"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Contact Number"
                              v-model="teacher.contactNumber">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Email"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Email"
                              v-model="teacher.email">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="NIC"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="NIC"
                              v-model="teacher.nic">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="House Number"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="House Number"
                              v-model="teacher.houseNumber">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="First Street Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="First Street Name"
                              v-model="teacher.firstStreetName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Second Street Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Second Street Name"
                              v-model="teacher.secondStreetName">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="City"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="City"
                              v-model="teacher.city">
                  </base-input>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Teacher</base-button>
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
import TeacherDataService from '../services/TeacherDataService'
export default{
  name: 'teacher',
  data () {
    return {
      teacher: {
        id: null,
        firstName: '',
        lastName: '',
        contactNumber: '',
        email: '',
        nic: '',
        houseNumber: '',
        firstStreetName: '',
        secondStreetName: '',
        city: ''
      }
    }
  },
  methods: {
    onSubmit () {
      var teacher = {
        firstName: this.teacher.firstName,
        lastName: this.teacher.lastName,
        contactNumber: this.teacher.contactNumber,
        email: this.teacher.email,
        nic: this.teacher.nic,
        houseNumber: this.teacher.houseNumber,
        firstStreetName: this.teacher.firstStreetName,
        secondStreetName: this.teacher.secondStreetName,
        city: this.teacher.city
      }
      TeacherDataService.create(teacher).then(response => {
        this.teacher.id = response.data.id
        console.log(response.data)
        alert("Teacher Added")
      }).catch(e => {
        console.log(e)
      })
    }
  },
  mounted() {
    if(localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
  }
}
</script>
