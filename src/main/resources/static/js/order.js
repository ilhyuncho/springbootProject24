// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.

// 장바구니 -> 주문 테이블 로
async function addOrderItem(formObj) {
    console.log(formObj)

    const response = await axios.post(`/order/add`, formObj);

    // console.log(response)
    return response.data
}

async function cancelOrderItem(orderItemId) {

    console.log("cancelOrderItem....................")
    console.log(orderItemId)

    const response = await axios.post(`/order/cancel/${orderItemId}`)

    //console.log(response)
    return response.data
}