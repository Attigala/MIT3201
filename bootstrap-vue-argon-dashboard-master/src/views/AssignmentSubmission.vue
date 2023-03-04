<template>
  <div>
    <base-header class="pb-6 pb-8 pt-5 pt-md-8 bg-gradient-success">
      <!-- Card stats -->
      <b-row>
      </b-row>
    </base-header>
    <b-container fluid class="mt--7">
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Upload Your Assignment</h3>
            <input type="file" @change="uploadFile" ref="file">
            <base-button type="primary" @click="saveFile" class="my-4">Upload</base-button>
            <p style ="color: #FF3131"> {{warning}} </p>
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
  name: 'AssignmentSubmission',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      file: null,
      classInfo: null,
      student: null,
      assignmentId: null,
      warning: ''

    }
  },
  methods: {
    saveFile () {

      var formData = new FormData();
      formData.append('file' , this.file);
      const headers = { 'Content-Type' : 'multipart/form-data'};
      var classId = localStorage.getItem("classId")
      var assignmentId = this.assignmentId
      var studentId = this.student.id
      ClassContractDataService.saveSubmission(classId, studentId, assignmentId, formData, headers).then(response => {
        console.log(response.status)
        if(response.status === 200) {
            alert("Assignment Submission Uploaded")
        }
      })
        .catch(e => {
          console.log(e)
        })
    },
    uploadFile () {
        this.file = this.$refs.file.files[0];
    },
    alreadySubmittedCheck() {
      var assignmentId = this.assignmentId
      var studentId = this.student.id
      ClassContractDataService.getSubmittedAssignmentForStudent(studentId, assignmentId).then(response => {
        console.log("assignment")
        if(response.data) {
          this.warning = "You have already submitted this assignment. Uploading again will delete previous file. \n New submission maybe marked as late";
        }
      }).catch(e=>{
        console.log(e)
      })
    },
    back() {
        this.$router.push({name: 'StudentClass', params: {classInfo: this.classInfo}})
    }
  },
  mounted() {
    if(localStorage.getItem("role") !== "student"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    if(localStorage.getItem("classId") === null) {
        this.$router.push({ path: "/studentHome" })
    }
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
    this.student = this.$route.params.student === null ? null : this.$route.params.student
    this.assignmentId = this.$route.params.assignmentId === null ? null : this.$route.params.assignmentId
    this.alreadySubmittedCheck()
   }
}
</script>
