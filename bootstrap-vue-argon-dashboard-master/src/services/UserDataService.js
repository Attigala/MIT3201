import http from '../http-common'

class GuardianDataService {
  // Retrieve all guardians from the database
  getAll () {
    return http.get('/users')
  }

  create (user) {
    return http.post('/users', user)
  }
  
  getByEmail (email) {
    return http.get('/user/find?email=' + email)
  }
}
export default new GuardianDataService()
