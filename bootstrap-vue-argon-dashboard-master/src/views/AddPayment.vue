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
      <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
            <h3 class="mb-0 text-white">Upload Payment</h3>
            <p class="text-white"> Select a Class </p>
            <select v-model="payment.studentClass">
                    <option disabled value=""> Please Select a Student / Class</option>
                    <option v-for="student in studentClasses" :value="student" :key="student.classId"> 
                    {{student.studentName}} 
                    {{student.subject}}
                    {{student.medium}}
                    {{student.grade}}
                    </option>
            </select>
            <br/>
            <br/>
            <p class="text-white"> Select a month </p> <br/>
            <input type=text placeholder="Month" v-model="payment.month"> <br/>
            <p class="text-white"> Select file </p>
            <input type="file" @change="uploadFile" ref="file"> <br/>
            <base-button  @click="add" class="mt-4" type="primary">Upload</base-button>
     </b-form>
        </b-card-header>
        </b-card> 
        
       </div>
      </div>
    </b-container>
  </div>
</template>
<script>
import ClassContractDataService from '../services/ClassContractDataService'
import GuardianDataService from '../services/GuardianDataService'
import { Table, TableColumn} from 'element-ui'
export default {
  name: 'AddPayment',
  components: {
    [Table.name]: Table,
    [TableColumn.name]: TableColumn
  },
  data () {
    return {
      file: null,
      studentClasses:[],
      payment: {
        studentClass:{
        studentId:'',
        classId:'',
        studentName:'',
        subject:'',
        medium:'',
        grade:'',
        },
        month: ''
      }
    }
  },
  methods: {
    add () {
      var formData = new FormData();
      formData.append('file' , this.file);
      const headers = { 'Content-Type' : 'multipart/form-data'};
      ClassContractDataService.savePayment(this.payment.studentClass.studentId, this.payment.studentClass.classId, this.payment.month, formData, headers).then(response => {
        console.log(response.status)
        if(response.status === 200) {
            alert("File uploaded! Payment pending verification")
        }
      })
        .catch(e => {
          console.log(e)
        })
    },
    uploadFile () {
        this.file = this.$refs.file.files[0];
    },
    retrieveGuardians () {
      GuardianDataService.getClassesByGuardianEmail(localStorage.getItem("user")).then(response => {
        this.studentClasses = response.data
        console.log(response.data)
      })
        .catch(e => {
          console.log(e)
        })
    },
  },
  mounted () {
    if(localStorage.getItem("role") !== "guardian"){
       this.$router.push({ path: "/NotFound" })
    }
    if(localStorage.getItem("user") === null ){
       this.$router.push({ path: "/login" })
    }
    this.retrieveGuardians()
  }
}
</script>
