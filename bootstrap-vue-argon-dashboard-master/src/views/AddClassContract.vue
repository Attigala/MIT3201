<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Add Class Contract</h1>
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
                              name="subject"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Subject"
                              v-model= "classContract.subject">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Last Name"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Medium"
                              v-model="classContract.medium">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="mode"
                              :rules="{required: true}"
                              prepend-icon="ni ni-building"
                              placeholder="Mode"
                              v-model="classContract.mode">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="grade"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Grade"
                              v-model="classContract.grade">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="day"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Day"
                              v-model="classContract.day">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="timeSlot"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time Slot"
                              v-model="classContract.timeSlot">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="Capacity"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Capacity"
                              v-model="classContract.capacity">
                  </base-input>
                  <div class="form-group">
                        <label for="teacher"> Teacher </label>
                        <select v-model="classContract.teacher">
                            <option disabled value="">Please select one</option>
                            <option v-for="teacher in teachers" :value="teacher" :key="teacher.id">{{teacher.firstName}} {{teacher.lastName}}</option>
                        </select>
                  </div>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Class Contract</base-button>
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
import teacherDataService from '../services/TeacherDataService'
import classContractDataService from '../services/ClassContractDataService'
export default{
  name: 'classContract',
  data () {
    return {
      classContract: {
        id: null,
        subject: '',
        medium: '',
        grade: '',
        mode: '',
        day: '',
        timeSlot: '',
        capacity: '',
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
      },
      teachers: []
    }
  },
  methods: {
    onSubmit () {
      var classContract = {
        subject: this.classContract.subject,
        medium: this.classContract.medium,
        mode: this.classContract.mode,
        grade: this.classContract.grade,
        day: this.classContract.day,
        timeSlot: this.classContract.timeSlot,
        capacity: this.classContract.capacity,
        teacher: {
          id: this.classContract.teacher.id,
          firstName: this.classContract.teacher.firstName,
          lastName: this.classContract.teacher.lastName,
          contactNumber: this.classContract.teacher.contactNumber,
          email: this.classContract.teacher.email,
          nic: this.classContract.teacher.nic,
          houseNumber: this.classContract.teacher.houseNumber,
          firstStreetName: this.classContract.teacher.firstStreetName,
          secondStreetName: this.classContract.teacher.secondStreetName,
          city: this.classContract.teacher.city
        }
      }
      console.log(classContract)
      classContractDataService.create(classContract).then(response => {
        this.classContract.id = response.data.id
        console.log(response.data)
        alert("Class Contract Successfully Created! Proceed to scheduling the class!")
      }).catch(e => {
        console.log(e)
      })
    },
    retrieveTeachers () {
      teacherDataService.getAll().then(response => {
        this.teachers = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    }
  },
  mounted() {
    this.retrieveTeachers()
    if(localStorage.getItem("role") === null) {
        this.$router.push({ path: "/login" })
    }
    if(localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
  }
}
</script>
