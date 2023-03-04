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
      <br>
      <br>
      <br>
      <div class="mt-5">
      <div class="text-center">
      
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
            <h3 class="mb-0 text-white">Upload Marksheet</h3>
            <input type="file" @change="uploadFile" ref="file">
            <base-button type="primary" @click="saveFile" class="my-4">Upload</base-button>
        </b-card-header>
        </b-card> 
    </div>
      </div>
      <div class="mt-5">
      <div class="text-center">
      <b-card no-body class="bg-default shadow">
      <b-card-header class="bg-transparent border-0">
      <h3 class="mb-0 text-white">Add Marks</h3>
            <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                
                  
                  <base-input alternative
                              class="mb-3"
                              name="mark"
                              :rules="{required: true}"
                              prepend-icon="ni ni-album-2"
                              placeholder="Mark"
                              v-model="mark.markValue">
                  </base-input>
                  <p> Student </p>
                  <select v-model="mark.markStudent">
                    <option disabled value=""> Please Select a Student </option>
                    <option v-for="student1 in students" :value="student1" :key="student1.studentId" class = "text-black"> {{student1.studentName}} </option>
                    </select>
                    <br>
                    <br/>
                  <div class="text-center">
                    <base-button type="primary" native-type="submit" class="my-4">Add Marks</base-button>
                  </div>
                </b-form>
              </validation-observer>
            <h3 class="mb-0 text-white">Add Marks</h3>
            <el-table class="table-responsive table table-dark"
                  header-row-class-name="thead-dark"
                  :data="studentMarks">
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

            <el-table-column label="Marks"
                             min-width="170px"
                             prop="marks">
                <template v-slot="{row}">
                    <b-media no-body class="align-items-center">
                        <b-media-body>
                            <span class="font-weight-600 name mb-0 text-sm">{{row.mark}}</span>
                        </b-media-body>
                    </b-media>
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
  name: 'MarksList',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      classInfo: null,
      students: [],
      file: null,
      studentMarks: [],
      assignmentId: '',
      mark: {
        markStudent: null,
        markValue: ''
      }
    }
  },
  methods: {
    retrieveStudents() {
      ClassContractDataService.getStudentsInClass(localStorage.getItem("classId")).then(response =>{
        this.students = response.data
        console.log(response.data)
      }).catch(e => {
        console.log(e)
      })
    },
    onSubmit() {
        console.log(this.mark)
        var mark = {
            classContractId:localStorage.getItem("classId"),
            assignmentId:this.assignmentId,
            studentId:this.mark.markStudent.studentId,
            mark:this.mark.markValue
        }
        ClassContractDataService.saveMark(mark).then(response=>{
            alert("marks added")
        }).catch(e=> {
            console.log(e)
        })
    },
    retrieveMarks() {
        ClassContractDataService.getMarksForAssignment(this.assignmentId).then(response => {
            this.studentMarks = response.data
            console.log(this.studentMarks)
        }).catch(e=> {
            console.log(e)
        })
    },
    uploadFile () {
        this.file = this.$refs.file.files[0];
    },
    saveFile () {
      var formData = new FormData();
      formData.append('file' , this.file);
      const headers = { 'Content-Type' : 'multipart/form-data'};
      ClassContractDataService.saveMarksSheet(formData, headers).then(response => {
        console.log(response.status)
        if(response.status === 200) {
            alert("File uploaded")
            this.studentMarks = response.data
        }
      })
        .catch(e => {
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
    this.retrieveStudents()
    this.retrieveMarks()
  },
   created () {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
    this.assignmentId = this.$route.params.assignmentId === null ? null : this.$route.params.assignmentId
   }
}
</script>
