<template>
  <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container>
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Schedule Extra Onsite Class</h1>
              <h3 class="text-white"> Class {{request.className}} </h3>
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
                  <p> Hall </p>
                  <select v-model="classSchedule.hall">
                    <option disabled value=""> Select Hall for Onsite Class </option>
                    <option v-for="hall in halls" :value="hall" :key="hall.id"> {{hall.hallName}} </option>
                  </select> 
                  <br>
                  <p> Date : </p>
                  <input type="date" v-model="classSchedule.date">
                  <p> Time : </p>
                  <base-input alternative
                              class="mb-3"
                              name="timeSlot"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time format hh:mm-hh:mm"
                              v-model="classSchedule.timeSlot">
                  </base-input>
                  <div class="text-center">
                  <base-button type="primary" native-type="submit" class="my-4">Add Extra Class</base-button>
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
  name: 'ManuallyScheduleOnsite',
  data () {
    return {
      classSchedule:{
        id: null,
        classContract: null,
        date: '',
        timeslot: '',
        requestId: '',
        hallId: null
      },
      request: null,
      halls: null
    }
  },
  methods: {
    test(event) {
      this.retrieveClasses()
    },
    onSubmit () {
      var schedule = {
        classContract: this.request.classId,
        date: this.classSchedule.date,
        timeSlot: this.classSchedule.timeSlot,
        hallId: this.classSchedule.hall.id,
        requestId: this.request.id
      }
      classContractDataService.saveMaualOnsiteExtraClass(schedule).then(response => {
        alert("Extra class added!");
        this.$router.push({ path: "/ClassRequest"})
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
    if(localStorage.getItem("role") !=="admin" ){
       this.$router.push({ path: "/NotFound" })
    }

    this.retrieveHalls();
  },
  created() {
    this.request = this.$route.params.request === null ? null : this.$route.params.request
  }
}
</script>
