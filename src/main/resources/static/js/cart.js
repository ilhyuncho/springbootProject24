// Axios를 이용하는 코드
// Axios를 이용할때 async/await를 같이 이용하면
// 비동기 처리를 동기화된  코드처럼 작성 할수 있다.


//async : 해당 함수가 비동기 처리를 위한 함수 라는 표시
//await : async 함수 내에서 비동기 호출하는 부분
async function getList({page, size, userName, goLast}){
    const result = await axios.get(`/cartRest/list`, {params: {page, size, userName}})

    console.log(result)

    if(goLast){
        const total = result.data.total
        const lastPage = parseInt(Math.ceil(total/size))
        return getList({page:lastPage, size:size, userName:userName})
    }

    return result.data
}

async function modifyCartItem(itemObj){
    const response = await axios.put(`/cartRest/${itemObj.cartId}`, itemObj)
    return response.data
}

async function removeCartItem(cartId){
    console.log(cartId)
    const result = await axios.delete(`/cartRest/${cartId}`)

    console.log(result)

    return result.data
}

// 장바구니 -> 주문 테이블 로
async function addOrderItem(formObj) {
    console.log(formObj)

    const response = await axios.post(`/order/add`, formObj);

    // console.log(response)
    return response.data
}