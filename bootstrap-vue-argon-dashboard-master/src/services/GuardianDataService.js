import http from '../http-common'

class GuardianDataService {
  // Retrieve all guardians from the database
  getAll () {
    return http.get('/guardians')
  }

  create (guardian) {
    return http.post('/guardians', guardian)
  }

  getClassesByGuardianEmail (email) {
    return http.get('/studentClass/guardian/email?email=' + email)
  }

  deleteStudentFromClass (studentClass) {
    console.log(studentClass)
    return http.put('/studentClass/delete', studentClass)
  }
}
export default new GuardianDataService()
