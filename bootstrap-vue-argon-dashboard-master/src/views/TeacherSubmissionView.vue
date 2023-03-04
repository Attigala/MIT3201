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
      <div class="Class">
          <b-card no-body class="bg-default shadow">
        <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">{{classInfo.className}}</h3>
            <h4 class="mb-0 text-black"> {{classInfo.day}} - {{classInfo.time}} </h4>
        </b-card-header>
    </b-card>
    </div>
        </b-col>
      </b-row>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Assignment Submissions</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="assignments">
            <el-table-column label="Student Name"
                             min-width="310px"
                             prop="studentName">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.studentName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="download"
                             min-width="170px"
                             prop="download">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="url">
                         <a :href="row.url" style="color: #2ecc71"> Download </a>
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Students Not Submitted</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="unSubmittedStudents">
            <el-table-column label="Student Name"
                             min-width="310px"
                             prop="studentName">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.firstName}} {{row.lastName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="contactNumber">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.contactNumber}}</span>
                        </b-media-body>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Late Submissions - Students</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="lateSubmittingStudents">
            <el-table-column label="Student Name"
                             min-width="310px"
                             prop="studentName">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.firstName}} {{row.lastName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="contactNumber">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.contactNumber}}</span>
                        </b-media-body>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <base-button type="primary" @click="back" class="my-4">Back to Class</base-button>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'TeacherSubmissionView',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classInfo: null,
      assignments: [],
      assignmentId: '',
      lateSubmittingStudents: [],
      unSubmittedStudents: []
    }
  },
  methods: {
    retrieveAssignments() {
      ClassContractDataService.getAssignmentSubmissions(this.assignmentId).then(response =>{
        this.assignments = response.data
        console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    retrieveNoSubmissions() {
        ClassContractDataService.getNotSubmittedStudents(this.assignmentId).then(response => {
            this.unSubmittedStudents = response.data
        }).catch(e => {
            console.log(e)
        })
    },
    retrieveLateSubmissions() {
        ClassContractDataService.getLateSubmissionStudents(this.assignmentId).then(response => {
            this.lateSubmittingStudents = response.data
        }).catch(e => {
            console.log(e)
        })
    },
    back() {
        this.$router.push({name: 'TeacherClass', params: {classInfo: this.classInfo}})
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "teacher"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    if(localStorage.getItem("classId") === null) {
        this.$router.push({ path: "/teacherHome" })
    }
    this.retrieveAssignments()
    this.retrieveNoSubmissions()
    this.retrieveLateSubmissions()
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
    this.assignmentId = this.$route.params.assignmentId === null ? null : this.$route.params.assignmentId
   }
}
</script>
