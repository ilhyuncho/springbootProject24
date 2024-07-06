// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.
async function getAllHistory({page,size,carId}){
    const response = await axios.get(`/history/${carId}`)

  //  console.log(response)
    return response.data
}
// 주유 기록------------------begin-------------------------------------
async function getHistory({page,size,carId,targetId}){
    const response = await axios.get(`/history/historyList/${targetId}/${carId}`)

   // console.log(response)
    return response.data
}
async function addHistory(formObj) {
   // console.log(formObj)

    const response = await axios.post(`/history/addHistory`, formObj)

   // console.log(response)
    return response.data
}
// 주유 기록------------------end-------------------------------------


// 통계------------------begin-------------------------------------
async function getStatisticsHistory(formObj){

    const response = await axios.get(`/statistics/get`, {params: formObj})

    //console.log(response)
    return response.data
}

async function getStatisticsTotal(formObj){

    const response = await axios.get(`/statistics/total`, {params: formObj})

    //console.log(response)
    return response.data
}
// 통계------------------end-------------------------------------