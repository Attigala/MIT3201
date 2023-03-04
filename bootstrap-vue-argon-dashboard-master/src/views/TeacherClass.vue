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
            <h3 class="mb-0 text-white">Student and Enrolment Status</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="studentStatuses">
            <el-table-column label="Student Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.studentName}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>

            <el-table-column label="Contact Number"
                             min-width="170px"
                             prop="contactNumber">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.contactNumber}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="Status"
                             min-width="170px"
                             prop="status">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.status}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
        </el-table>
            <base-button type="primary" @click="selectClass" class="my-4">Upload</base-button>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Upload Study Material</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="studyMaterials">
            <el-table-column label="Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.name}}</span>
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
                         <a :href="row.url" class="text-black"> Download </a>
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
            <base-button type="primary" @click="selectClass" class="my-4">Upload</base-button>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Upload Assignments</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="assignments">
            <el-table-column label="Name"
                             min-width="310px"
                             prop="name">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.name}}</span>
                        </b-media-body>
                    </b-media>
                </template>
            </el-table-column>
            <el-table-column label="Due Date"
                             min-width="170px"
                             prop="download">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="dueDate">
                         {{row.dueDate}}
                        </span>
                    </badge>
                </template>
            </el-table-column>

            <el-table-column label="download"
                             min-width="170px"
                             prop="download">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="url">
                         <a :href="row.url" class="text-black"> Download </a>
                        </span>
                    </badge>
                </template>
            </el-table-column>
            
            <el-table-column label="View Sumissions"
                             prop="approve"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="viewSubmissions(row)"  type="primary" class="mt-4">View</base-button></span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column label="Add Marks"
                             prop="approve"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="addMarks(row)"  type="primary" class="mt-4">Marks</base-button></span>
                    </div>
                </template>
            </el-table-column>
            
            <el-table-column label="Marks Sheet"
                             min-width="170px"
                             prop="markSheetUrl">
                <template v-slot="{row}">
                    <badge class="badge-dot mr-4">
                        <span class="markSheetUrl">
                         <a :href="row.markSheetUrl" style="color: #2ecc71"> Marks Sheet </a>
                        </span>
                    </badge>
                </template>
            </el-table-column>
        </el-table>
            <base-button type="primary" @click="addAssignment" class="my-4">Upload</base-button>
        </b-card-header>
        </b-card> 
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Forum</h3>
            <base-button type="primary" @click="viewForum" class="my-4">View Forum</base-button>
        </b-card-header>
        </b-card> 
        
       </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-row class="justify-content-center">
        <b-col lg="5" md="7">
          <b-card no-body class="bg-secondary border-0 mb-0">
            
            <b-card-body class="px-lg-5 py-lg-5 bg-default shadow">
              <div class="text-center text-muted mb-4">
              <h3 class="text-white"> Request an Extra Class </h3>
              </div>
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                  <base-input alternative
                              class="mb-3"
                              name="date"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Date: Format YYYY-MM-DD"
                              v-model="request.date">
                  </base-input>

                  <base-input alternative
                              class="mb-3"
                              name="time"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Time"
                              v-model="request.time">
                  </base-input>
                  <p class="text-white"> Mode </p>
                  <select v-model="request.mode">
                    <option disabled value=""> Please Select a Mode </option>
                    <option> Online </option>
                    <option> Onsite </option>
                  </select> 
                  <br/>
                  <p class="text-white"> Reason </p>
                  <select v-model="request.reason">
                    <option disabled value=""> Please Select a Reason </option>
                    <option> Extra </option>
                    <option> Compensation </option>
                  </select> 
                  <br/>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Submit Request</base-button>
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
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'TeacherClass',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classInfo: null,
      studyMaterials: [],
      assignments: [],
      studentStatuses: [],
      request: {
        id: null,
        classId: null,
        date: '',
        time: '',
        mode: '',
        status: '',
        reason: '',
        processedBy: ''
      }
    }
  },
  methods: {
    retrieveClasses () {
      ClassContractDataService.getOverview(localStorage.getItem("classId")).then(response => {
        this.classes = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
    retrieveStudentStatuses() {
      ClassContractDataService.getStudentStatus(localStorage.getItem("classId")).then(response => {
        this.studentStatuses = response.data
        console.log("student status debug log start")
        console.log(this.studentStatuses)
      }).catch(e => {
        console.log(e)
      })
    },
    retrieveStudyMaterials() {
        ClassContractDataService.getFiles(localStorage.getItem("classId")).then(response => {
           this.studyMaterials = response.data
        }).catch(e => {
            console.log(e)
        })
    },
    viewSubmissions(row) {
        this.$router.push({name: 'TeacherSubmissionView', params: {classInfo: this.classInfo, assignmentId: row.id}})
    },
    addMarks(row) {
      this.$router.push({name: 'MarksList', params: {classInfo: this.classInfo, assignmentId: row.id}})
    },
    retrieveAssignments() {
      ClassContractDataService.getAssignments(localStorage.getItem("classId")).then(response =>{
        this.assignments = response.data
        console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    selectClass() {
        this.$router.push({name: 'StudyMaterialUpload', params: {classInfo: this.classInfo}})
    },
    addAssignment() {
      this.$router.push({name: 'AssignmentUpload', params: {classInfo: this.classInfo}})
    },
    viewForum() {
        this.$router.push({name: 'ForumTeacher', params: {classInfo: this.classInfo}})
    },
    onSubmit () {
        var request = {
            classId: localStorage.getItem("classId"),
            date: this.request.date,
            time: this.request.time,
            mode: this.request.mode,
            status: null,
            reason: this.request.reason,
            processedBy: null
        }
        ClassContractDataService.createClassRequest(request).then(response => {
            this.request.id = response.id
            alert("Request Sent")
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
    if(localStorage.getItem("classId") === null) {
        this.$router.push({ path: "/teacherHome" })
    }
    this.retrieveStudyMaterials()
    this.retrieveAssignments()
    this.retrieveStudentStatuses()
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
   }
}
</script>
