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
      <div class="StudentHome">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Your Classes</h3>
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
    <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Your Marks</h3>
        </b-card-header>

        <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="classMarks">
            <el-table-column label="Class Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.classContractName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Assignment Number"
                             min-width="170px"
                             prop="schedule">
                <template v-slot="{row}">
                    <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.assignmentId}}</span>
                        </b-media-body>
                </template>
            </el-table-column>
            <el-table-column label="Mark"
                             min-width="170px"
                             prop="schedule">
                <template v-slot="{row}">
                    <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.mark}}</span>
                        </b-media-body>
                </template>
            </el-table-column>
        </el-table>

    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5"></div>
      <div class="mt-5"></div>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'studentHome',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classes: [],
      requests: [],
      classMarks: []
    }
  },
  methods: {
    retrieveClasses () {
      ClassContractDataService.getStudentOverview(localStorage.getItem("user")).then(response => {
        this.classes = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    retrieveMarks(){
      ClassContractDataService.getMarksForStudent(localStorage.getItem("user")).then(response => {
        this.classMarks = response.data
        console.log(this.classMarks)
      }).catch(e=> {
        console.log(e)
      })
    },
    selectClass(row) {
        localStorage.setItem('classId', row.classId)
        console.log("Class Selected: " + row.className);
        this.$router.push({name: 'StudentClass', params: {classInfo: row}})
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "student"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    localStorage.setItem('classId', '')
    this.retrieveClasses()
    this.retrieveMarks()
  }
}
</script>
