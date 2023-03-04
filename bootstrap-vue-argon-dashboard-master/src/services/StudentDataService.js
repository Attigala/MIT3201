import http from '../http-common'

class StudentDataService {
  // Retrieve all guardians from the database
  getAll () {
    return http.get('/students')
  }
  
  getAllForGuardian(email) {
    return http.get('/student/guardian?email='+email)
  }

  create (student) {
    return http.post('/students', student)
  }

  createForGuardian (student, email) {
    return http.post('/guardian/students?email='+email, student)
  }

  getByEmail(email) {
    return http.get('/students/student?email='+email)
  }
}
export default new StudentDataService()
