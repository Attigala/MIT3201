<template>
<div>
  <div> 
  <br>
  <br>
  <br>
   Start Date: 
  <input type="date" v-model="start" required>
   End Date:
  <input type="date" v-model="end" required>
  Hall 
  <select v-model="hall">
     <option disabled value=""> Select Hall</option>
     <option v-for="hall in halls" :value="hall" :key="hall.id"> {{hall.hallName}} </option>
  </select> 
  
    <div class="text-center">
      <button @click ="test" class="btn btn-primary"> Get Schedules </button>
    </div>
  </div>
  <div class="mt-5">
      <div class="text-center">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            
            <b-card-body class="px-lg-5 py-lg-5 bg-default shadow">
              <div class="text-center text-muted mb-4">
              <h3 class="text-white"> Schedule a Class </h3>
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="date"
                              type="date"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Date"
                              v-model="request.startDate">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="From"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time (From): HH:MM"
                              v-model="request.fromTime">
                  </base-input>
                  <base-input alternative
                              class="mb-3"
                              name="To"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time (To): HH:MM"
                              v-model="request.toTime">
                  </base-input>
                  <select v-model="requested">
                      <option disabled value=""> Select Request</option>
                      <option v-for="unscheduledOnsite in unscheduledOnsites" :value="unscheduledOnsite" :key="unscheduledOnsite.id"> 
                      {{unscheduledOnsite.className}} {{unscheduledOnsite.date}} {{unscheduledOnsite.time}} </option>
                  </select> 
                  <p class="text-white"> Schedule for this hall </p>
                  <br/>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4" :disabled="hall === null || hall === undefined && events === undefined || events.length == 0">Schedule Class</base-button>
                  </div>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
          <b-row class="mt-3">
          </b-row>
        </b-col>
      </b-row>
        
       </div>
      </div>
  <vue-cal 
    class="vuecal--blue-theme"
    :time-from="7 * 60" 
    active-view="week" :disable-views="['years', 'year', 'month', 'day']"
    :events="events"
    editable-events
    events-count-on-year-view
    events-on-month-view="short">
  </vue-cal>
  </div>
</template>

<script>
// Documentation: https://antoniandre.github.io/vue-cal



import VueCal from 'vue-cal'
import 'vue-cal/dist/vuecal.css'
import RegularSchedulingDataService from '../services/RegularSchedulingDataService'
import HallDataService from '../services/HallDataService'
import classContractDataService from '../services/ClassContractDataService'
export default {
  components: { VueCal },
  data: () => ({
    events: [],
    start: '',
    end: '',
    hall: null,
    halls: [],
    unscheduledOnsites: [],
    requested: null,
    request: {
      hall: null,
      startDate: '',
      fromTime: '',
      toTime: '',
    }
  }),
  methods: {
    onSubmit() {
      var startTime = this.request.startDate + ' ' + this.request.fromTime
      var endTime = this.request.startDate + ' ' +this.request.toTime
      var blocked = false

      this.events.forEach(item => {
        var eventStartDate = new Date(item.start)
        var eventEndDate = new Date(item.end)
        var requestStartDate = new Date(startTime);
        var requestEndDate = new Date(endTime);
        if (requestStartDate >= eventStartDate && requestEndDate <= eventEndDate) {
          blocked = true
        }
      })
 
      if (blocked === true) {
        alert("There is a clas already booked for this slot. Pick another date/time")
      } else {
          var confirmation = true
          if (this.requested.date !== this.request.startDate) {
            confirmation = confirm("Class was requested on " + this.requested.date + " you are scheduling it on a different date. Are you sure?"  );
          }
          if (confirmation) {
            var OnsiteRequest = {
                classContract : this.requested.classId,
                date: this.request.startDate,
                timeSlot: this.request.fromTime+"-"+this.request.toTime,
                requestId: this.requested.id,
                hallId: this.hall.id
              }
              classContractDataService.saveMaualOnsiteExtraClass(OnsiteRequest).then(response => {
                console.log(response.data)
                if(response.data.id !== null) {
                  alert("Extra class added! Reload timetable!")
                }
              }).catch(e => {
                console.log(e)
              })
          }
      }
      
    },
    test() {
      console.log(this.start)
      console.log(this.end)
      RegularSchedulingDataService.getSchedules(this.start, this.end, this.hall.id).then(response => {
          this.events = response.data
          //console.log(response.data)
          console.log(this.events)
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
    },
    retrieveUnscheduledOnsite () {
      RegularSchedulingDataService.getUnscheduledOnsiteRequests().then(response => {
        this.unscheduledOnsites = response.data
      }).catch(e => {
        console.log(e)
      })
    }
  },
  mounted () {
    this.retrieveHalls();
    this.retrieveUnscheduledOnsite()
  }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

.vuecal {height: 90vh;}

.vuecal {
  margin: auto;
  max-width: 1300px;
  max-height: 662px;
}

.vuecal__event {
  background-color: rgba(173, 216, 230, 0.5);
  box-sizing: border-box;
  padding: 5px;
}

.vuecal__event.lunch {
  background: repeating-linear-gradient(45deg, transparent, transparent 10px, #f2f2f2 10px, #f2f2f2 20px);
}
</style>
