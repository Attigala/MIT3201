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
            <h3 class="mb-0 text-white">Download Study Material</h3>
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
            <h3 class="mb-0 text-white">Assignments</h3>
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
                         <a :href="row.url" style="color: #2ecc71"> Download </a>
                        </span>
                    </badge>
                </template>
            </el-table-column>
             <el-table-column label="Upload"
                             prop="approve"
                             min-width="240px">
                <template v-slot="{row}">
                    <div class="d-flex align-items-center">
                        <span class="completion mr-2"><base-button  @click="upload(row)"  type="primary" class="mt-4">Upload</base-button></span>
                    </div>
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
            <h3 class="mb-0 text-white">Forum</h3>
            <base-button type="primary" @click="viewForum" class="my-4">View Forum</base-button>
        </b-card-header>
        </b-card> 
        
       </div>
      </div>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import StudentDataService from '../services/StudentDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'StudentClass',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classInfo: null,
      classes:[],
      assignments: [],
      studyMaterials: [],
      student: null
    }
  },
  methods: {
    retrieveClasses () {
      ClassContractDataService.getStudentOverview(localStorage.getItem("classId")).then(response => {
        this.classes = response.data
        console.log(response.data)
      })
        .catch(e => {
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
    retrieveAssignments() {
      ClassContractDataService.getAssignments(localStorage.getItem("classId")).then(response =>{
        this.assignments = response.data
        console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    viewForum() {
        this.$router.push({name: 'ForumStudent', params: {classInfo: this.classInfo}})
    }, 
    upload(row){
        this.$router.push({name: 'AssignmentSubmission', params: {classInfo: this.classInfo, student: this.student, assignmentId: row.id}})
    },
    retrieveStudent() {
      StudentDataService.getByEmail(localStorage.getItem("user")).then(response => {
        this.student = response.data
        console.log(this.student)
      }).catch(e =>{
        console.log(e)
      })
    }
  },
  mounted () {
    if(localStorage.getItem("role") !== "student"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    if(localStorage.getItem("classId") === null) {
        this.$router.push({ path: "/studentHome" })
    }
    this.retrieveStudyMaterials()
    this.retrieveAssignments()
    this.retrieveStudent()
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
   }
}
</script>
