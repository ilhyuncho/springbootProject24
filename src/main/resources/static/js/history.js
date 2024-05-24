// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.
async function getAllHistory({page,size,carId}){
    const response = await axios.get(`/history/${carId}`)

  //  console.log(response)
    return response.data
}
// 주유 기록------------------begin-------------------------------------
async function getGasHistory({page,size,carId}){
    const response = await axios.get(`/history/gasList/${carId}`)

   // console.log(response)
    return response.data
}
async function addGasHistory(formObj) {
   // console.log(formObj)

    const response = await axios.post(`/history/addGasHistory`, formObj)

   // console.log(response)
    return response.data
}
// 주유 기록------------------end-------------------------------------

// 정비 기록------------------begin-------------------------------------
async function getRepairHistory({page,size,carId}){
    const response = await axios.get(`/history/repairList/${carId}`)

   // console.log(response)
    return response.data
}

async function addRepairHistory(formObj) {
   // console.log(formObj)

    const response = await axios.post(`/history/addRepairHistory`, formObj)

  //  console.log(response)
    return response.data
}

// 정비 기록------------------end-------------------------------------


async function getConsumeHistory({carId, selectYear}){

     console.log(selectYear)
    const response = await axios.get(`/statistics/consume`, {params: {carId, selectYear}})

    //  console.log(response)
    return response.data
}