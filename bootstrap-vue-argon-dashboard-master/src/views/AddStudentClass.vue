<template>
    <div>
    <!-- Header -->
    <div class="header bg-gradient-success py-7 py-lg-8 pt-lg-9">
      <b-container class="container">
        <div class="header-body text-center mb-7">
          <b-row class="justify-content-center">
            <b-col xl="5" lg="6" md="8" class="px-5">
              <h1 class="text-white">Add Student to Class</h1>
              <p class="text-lead text-white">Add Student to a Class and Proceed to Payment</p>
            </b-col>
          </b-row>
        </div>
      </b-container>
      <div class="separator separator-bottom separator-skew zindex-100">
        <svg x="0" y="0" viewBox="0 0 2560 100" preserveAspectRatio="none" version="1.1"
             xmlns="http://www.w3.org/2000/svg">
          <polygon class="fill-default" points="2560 0 2560 100 0 100"></polygon>
        </svg>
      </div>
    </div>
    <!-- Page content -->
    <b-container class="mt--8 pb-5">
      <!-- Table -->
      <b-row class="justify-content-center">
        <b-col lg="6" md="8" >
          <b-card no-body class="bg-secondary border-0">
            <b-card-header class="bg-transparent pb-5">
            </b-card-header>
            <b-card-body class="px-lg-5 py-lg-5">
              <validation-observer v-slot="{handleSubmit}" ref="formValidator">
                <b-form role="form" @submit.prevent="handleSubmit(onSubmit)">
                <table>
                <tr>
                  <div class = "form-group">
                  <td><label for="student"> Student: </label> </td>
                   <td> <select v-model="studentClass.student">
                    <option disabled value=""> Please Select a Student </option>
                    <option v-for="student in students" :value="student" :key="student.id"> {{student.firstName}} {{student.lastName}} </option>
                    </select></td>
                 </div>
                </tr>
        <div class = "form-group">
           <td> <label for="class"> Class &nbsp; &nbsp; : </label> </td>
        <td><select v-model="studentClass.classContract">
                <option disabled value=""> Please Select a Class </option>
                <option v-for="classContract in classContracts" :value="classContract" :key="classContract.id"> {{classContract.subject}} {{classContract.grade}} {{classContract.medium}} </option>
            </select> </td>
        </div>
        <br/>
                  <div class="text-center">
                    <b-button type="submit" variant="primary" class="mt-4">Add to Class</b-button>
                  </div>
                  </table>
                </b-form>
              </validation-observer>
            </b-card-body>
          </b-card>
        </b-col>
      </b-row>
    </b-container>
    </div>
</template>
<script>
import classContractDataService from '../services/ClassContractDataService'
import StudentDataService from '../services/StudentDataService'
export default{
  name: 'StudentClass',
  data () {
    return {
      studentClass: {
        student: null,
        classContract: null
      },
      addition: {
        id: null,
        studentId: '',
        classContractId: '',
        year: '',
        addedDate: ''
      },
      students: [],
      classContracts: []
    }
  },
  methods: {
    onSubmit () {
      var addition = {
        studentId: this.studentClass.student.id,
        classContractId: this.studentClass.classContract.id,
        year: new Date().getFullYear(),
        addedDate: new Date()
      }
      console.log(addition)
      classContractDataService.createStudentClass(addition).then(response => {
        this.addition.id = response.data.id
        console.log(response.data)
        alert("Student added to class")
      })
        .catch(e => {
          console.log(e)
        })
    },
    retrieveClasses () {
      classContractDataService.getAll().then(response => {
        this.classContracts = response.data
        console.log("Student Added to Class");
      }).catch(e => {
        console.log(e)
        alert("Something Went Wrong! Please Contact Admin.");
      })
    },
    retrieveStudents () {
      if(localStorage.getItem("role") !=="guardian" ){
        StudentDataService.getAll().then(response => {
            this.students = response.data
          }).catch(e => {
            console.log(e)
          })
      } else {
        StudentDataService.getAllForGuardian(localStorage.getItem("user")).then(response => {
          this.students = response.data
        }).catch(e => {
          console.log(e)
        })
      }
      
    }
  },
  mounted () {
    this.retrieveClasses()
    this.retrieveStudents()
  }
}
</script>
