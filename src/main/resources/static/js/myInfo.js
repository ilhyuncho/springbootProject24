
// 고객의 보유 포인트 정보 get
async function getMyPoint(formObj){
    const response = await axios.get(`/myInfo/myPoint`, {params: formObj})
  //  console.log(response)
    return response.data
}

// 해당 userAddressBookId 주소 정보 get
async function getDeliveryAddress(formObj){
    const response = await axios.get(`/myInfo/addressInfo`, {params: formObj})
    //  console.log(response)
    return response.data
}

// 고객의 모든 배송 주소록 정보 get
async function getAllDeliveryAddress(formObj){
    const response = await axios.get(`/myInfo/allAddressInfo`, {params: formObj})
    //  console.log(response)
    return response.data
}

// 배송 주소록 변경 or 삭제
async function sendDeliveryAddress(formObj) {
    console.log(formObj)

    if(formObj.callType === 'register'){
        const response = await axios.post(`/myInfo/registerDeliveryAddress`, formObj)
        console.log(response)
        return response.data
    }
    else if(formObj.callType === 'modify'){
        const response = await axios.post(`/myInfo/modifyDeliveryAddress`, formObj)
        console.log(response)
        return response.data
    }
}

// 배송 주소록 삭제
async function deleteDeliveryAddress(userAddressBookId){

    const result = await axios.post(`/myInfo/deleteAddress/${userAddressBookId}`)
    return result.data
}

// 주문 상품 배송 진행 상황 get
async function getOrderDeliveryProcess(formObj){
    const response = await axios.get(`/myInfo/orderDeliveryProcess`, {params: formObj})
    //  console.log(response)
    return response.data
}

// 기본 주소 등록
async function sendRegisterMainAddress(formObj) {
    console.log(formObj)

    const response = await axios.post(`/myInfo/registerMainAddress`, formObj)
    console.log(response)
    return response.data
}

// 고객의 모든 배송 주소록 정보 get
async function getMainAddress(formObj){
    const response = await axios.get(`/myInfo/getMainAddress`, {params: formObj})
    //  console.log(response)
    return response.data
}