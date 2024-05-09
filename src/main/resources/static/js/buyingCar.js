async function purchaseOffer(formObj) {

    console.log("purchaseOffer....................")
    console.log(formObj)

    const response = await axios.post(`/buyingCar/offer`, formObj)

    console.log(response)
    return response.data
}

async function modifyOffer(formObj) {

    console.log("modifyOffer....................")
    console.log(formObj)

    const response = await axios.post(`/buyingCar/modifyOffer`, formObj)

    console.log(response)
    return response.data
}

async function getList({currentUser, sellingCarId, page, size}){
    // goLast : 마지막 페이지 호출 여부

    console.log({currentUser, sellingCarId, page, size})

    const result = await axios.get(`/buyingCar/list`, {params: {userName:currentUser, sellingCarId, page, size}})

    console.log(result.data)

    return result.data
}