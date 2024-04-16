// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.


//async : 해당 함수가 비동기 처리를 위한 함수 라는 표시
//await : async 함수 내에서 비동기 호출하는 부분


async function cancelOrder(rno){
    const response = await axios.delete(`/cart/${rno}`)
    return response.data
}
