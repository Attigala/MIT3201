import http from '../http-common'

class HallDataService {
  saveHall (hall) {
    return http.post('/halls', hall)
  }

  getAll () {
    return http.get('/halls')
  }
}
export default new HallDataService()
