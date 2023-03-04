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
            <h3 class="mb-0 text-white">Class Overview</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="classes"
                  @row-click="selectClass">
            <el-table-column label="Class Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.className}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Schedule"
                             min-width="170px"
                             prop="schedule">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="schedule" v-if="row.schedule != null">
                        {{row.schedule.mode}} 
                        {{row.schedule.day}} 
                        {{row.schedule.time}}
                        {{row.schedule.hall}}
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
    
      <b-row>
        <b-col>
      <div class="TeacherHome">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Extra Class Requests</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="requests">
            <el-table-column label="Class Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.className}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Date"
                             min-width="170px"
                             prop="date">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.date}} 
                        {{row.time}}
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
            <el-table-column label="Reason"
                             min-width="170px"
                             prop="reason">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span>
                        {{row.reason}} 
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
  name: 'TeacherHome',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classes: [],
      requests: []
    }
  },
  methods: {
    retrieveClasses () {
      ClassContractDataService.getOverview(localStorage.getItem("user")).then(response => {
        this.classes = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    selectClass(row) {
        localStorage.setItem('classId', row.classId)
        console.log("Class Selected: " + row.className);
        this.$router.push({name: 'TeacherClass', params: {classInfo: row}})
    },
    retrieveRequests() {
        ClassContractDataService.getClassRequestsForTeacher(localStorage.getItem('user')).then(response => {
            this.requests = response.data
            console.log(response.data)
        }).catch(e => {
            console.log(e)
        })
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "teacher"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    localStorage.setItem('classId', '')
    this.retrieveClasses()
    this.retrieveRequests()
  }
}
</script>
