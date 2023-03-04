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
    <div class="text-center">
      <button @click ="test" class="btn btn-primary"> Get Schedules </button>
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
    end: ''
  }),
  methods: {
    test() {
      console.log(this.start)
      console.log(this.end)
      RegularSchedulingDataService.getOnlineSchedules(this.start, this.end).then(response => {
          this.events = response.data
          //console.log(response.data)
          console.log(this.events)
        }).catch(e=> {
          console.log(e)
        })
    }
  },
  mounted () {
    var date = new Date();
    var firstDay = new Date(date.getFullYear(), date.getMonth(), 1);
    var lastDay = new Date(date.getFullYear(), date.getMonth() + 1, 0);
    firstDay = firstDay.toISOString();
    lastDay = lastDay.toISOString();
    this.start = firstDay;
    this.end = lastDay;
    this.test();
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
