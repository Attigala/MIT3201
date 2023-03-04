import http from '../http-common'

class TeacherDataService {
  // Retrieve all teachers from the database
  getAll () {
    return http.get('/teachers')
  }

  create (teacher) {
    return http.post('/teachers', teacher)
  }
}
export default new TeacherDataService()
