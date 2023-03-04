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
            <h3 class="mb-0 text-white">Upload Study Material</h3>
            <input type="file" @change="uploadFile" ref="file">
            <base-button type="primary" @click="saveFile" class="my-4">Upload</base-button>
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
  name: 'StudyMaterialUpload',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      file: null,
      classInfo: null
    }
  },
  methods: {
    saveFile () {
      var formData = new FormData();
      formData.append('file' , this.file);
      const headers = { 'Content-Type' : 'multipart/form-data'};
      ClassContractDataService.saveFile(localStorage.getItem("classId"), formData, headers).then(response => {
        console.log(response.status)
        if(response.status === 200) {
            alert("File uploaded")
        }
      })
        .catch(e => {
          console.log(e)
        })
    },
    uploadFile () {
        this.file = this.$refs.file.files[0];
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
  },
  created() {
    this.classInfo = this.$route.params.classInfo === null ? null : this.$route.params.classInfo
  }
}
</script>
