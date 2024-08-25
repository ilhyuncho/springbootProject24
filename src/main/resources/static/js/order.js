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

async function sendCancelOrder(orderId) {

    const response = await axios.post(`/order/cancel/${orderId}`)

    //console.log(response)
    return response.data
}
async function addOrderTemporary(formObj) {
    console.log(formObj)

    // <>[\\]^`{|} 같은 문자가 쿼리스트링에 포함되면 에러가 발생 해서 encodeURI 랩핑
    // The valid characters are defined in RFC 7230 and RFC 3986

    const response = await axios.post(`/order/addOrderTemporary`, formObj);

    console.log(response)
    return response.data
}

async function sendReview(formObj) {

    const response = await axios.post(`/review/write`, formObj);

    console.log(response)
    return response.data
}

async function getListReview({shopItemId, page, size}) {

    const response = await axios.get(`/review/list`, {params: {shopItemId, page, size}})

    console.log(response)
    return response.data
}

async function getReview({reviewId}) {

    const response = await axios.get(`/review/${reviewId}`)

    console.log(response)
    return response.data
}