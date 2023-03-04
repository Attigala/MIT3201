<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
      </b-row>
    </base-header>
    <b-container fluid class="mt--7">
      <b-row>
        <b-col>
      <div class="TeacherHome">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Request Details</h3>
        </b-card-header>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
    
      <b-row>
        <b-col>
      <div class="ClassRequest">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Extra Class Scheduling Suggestions</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="suggestions">
            <el-table-column label="Date"
                             min-width="170px"
                             prop="date">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.date}} 
                        </span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Time"
                             min-width="170px"
                             prop="timeslot">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.timeslot}} 
                        </span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Hall"
                             min-width="170px"
                             prop="hall">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.hallName}} 
                        </span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Mode"
                             min-width="170px"
                             prop="mode">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.mode}}
                        </span>
                    </badge>
                </template>
            </el-table-column>
            <el-table-column label="Status"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.status}} 
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
    <br>
    <div class="text-center">
      <button @click ="manuallySchedule" class="btn btn-primary"> Schedule Manually </button>
    </div>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'ClassRequestSchedule',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classes: [],
      suggestions: [],
      request: null
    }
  },
  methods: {
    retrieveRequests() {
        ClassContractDataService.getClassRequestSuggestion(this.request.id).then(response => {
            this.suggestions = response.data
            console.log(response.data)
        }).catch(e => {
            console.log(e)
        })
    },
    manuallySchedule() {
        if (this.request.mode.toUpperCase() === "ONSITE") {
            this.$router.push({name: 'timetable', params: {request: this.request}})
        } else {
            this.$router.push({name: 'ManuallyScheduleOnline', params: {request: this.request}})
        }
        
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "admin"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    localStorage.setItem('classId', '')
    this.retrieveRequests()
  },
   created () {
    this.request = this.$route.params.request === null ? null : this.$route.params.request
   }
}
</script>
