<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Create Regular Schedule for Class</h1>
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
                  <p> Teacher </p>
                  <select v-model="classSchedule.teacher" @change="test($event)">
                    <option disabled value=""> Please Select a Teacher </option>
                    <option v-for="teacher in teachers" :value="teacher" :key="teacher.id"> {{teacher.firstName}} {{teacher.lastName}} </option>
                  </select> 
                  <br>
                  <p> Class </p>
                  <select v-model="classSchedule.classContract">
                    <option disabled value=""> Please Select a Class </option>
                    <option v-for="classContract in classContracts" :value="classContract" :key="classContract.id"> {{classContract.subject}} {{classContract.grade}} {{classContract.medium}} </option>
                  </select> 
                  <br>
                  <p> Hall </p>
                  <select v-model="classSchedule.hall">
                    <option disabled value=""> Select Hall for Onsite Class </option>
                    <option v-for="hall in halls" :value="hall" :key="hall.id"> {{hall.hallName}} </option>
                  </select> 
                  <br>
                  <p> Day </p>
                  <select v-model="classSchedule.day">
                    <option disabled value=""> Please Select a Day </option>
                    <option> Monday </option>
                    <option> Tuesday </option>
                    <option> Wednesday </option>
                    <option> Thursday </option>
                    <option> Friday </option>
                    <option> Saturday </option>
                    <option> Sunday </option>
                  </select> 
                  <br/>
                  <base-input alternative
                              class="mb-3"
                              name="timeSlot"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time format hh:mm-hh:mm"
                              v-model="classSchedule.timeSlot">
                  </base-input>
                  <p> Mode </p>
                  <select v-model="classSchedule.mode">
                    <option disabled value=""> Please Select a Mode </option>
                    <option> Online </option>
                    <option> Onsite </option>
                  </select>
                  <div class="text-center">
                  <base-button type="primary" native-type="submit" class="my-4">Add Schedule</base-button>
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
import HallDataService from '../services/HallDataService'
import teacherDataService from '../services/TeacherDataService'
import classContractDataService from '../services/ClassContractDataService'
import RegularSchedulingDataService from '../services/RegularSchedulingDataService'
export default{
  name: 'AddSchedule',
  data () {
    return {
      classSchedule:{
        id: null,
        teacher: null,
        classContract: null,
        day: '',
        timeSlot: '',
        mode: '',
        hall: null
      },
      teachers: null,
      classContracts: null,
      halls: null
    }
  },
  methods: {
    test(event) {
      this.retrieveClasses()
    },
    onSubmit () {
      console.log(this.classSchedule);
      var schedule = {
        teacherId : this.classSchedule.teacher.id,
        classContractId: this.classSchedule.classContract.id,
        date: this.classSchedule.day,
        timeSlot: this.classSchedule.timeSlot,
        mode: this.classSchedule.mode,
        hallId: this.classSchedule.hall.id
      }
      RegularSchedulingDataService.saveSchedule(schedule).then(response => {
        this.classSchedule.id = response.data.id
        alert("Class has been scheduled");
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
    },
    retrieveClasses() {
      classContractDataService.getClassesByTeacher(this.classSchedule.teacher).then(response => {
        this.classContracts = response.data
      }).catch(e=> {
        console.log(e)
      })
    },
    retrieveHalls () {
      HallDataService.getAll().then(response => {
        this.halls = response.data
      }).catch(e=> {
        console.log(e)
      })
    }
  },
  mounted() {
    if(localStorage.getItem("role") !=="manager" ){
       this.$router.push({ path: "/NotFound" })
    }
    this.retrieveTeachers();
    this.retrieveHalls();
  }
}
</script>
