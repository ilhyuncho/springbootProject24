// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.
async function getList({page,size,carId}){
    const response = await axios.get(`/history/${carId}`)

    console.log(response)
    return response.data
}